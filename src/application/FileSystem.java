package application;

import core.DateTime;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Directory;
import model.File;

/**
 *
 * @author estuche
 */
public class FileSystem {
    
    private final static String DEFAULT_NAME = "C:";
    private final static char EMPTY_CHAR = '@';
    private final static char NULL_CHAR = '#';
    
    private final String filePath;
    private final int sectorCount;
    private final int sectorSize;
    private final Directory root;
    private final boolean[] availability;
    private final Stack<Directory> directoryStack;

    public FileSystem(String filePath, int sectorCount, int sectorSize) {
        this.filePath = filePath;
        this.sectorCount = sectorCount;
        this.sectorSize = sectorSize;
        this.root = new Directory(DEFAULT_NAME, DateTime.now());
        this.availability = new boolean[this.sectorCount * this.sectorSize];
        Arrays.fill(this.availability, true);
        this.directoryStack = new Stack();
        this.directoryStack.push(this.root);
        this.createFileSystemDisk();
    }
    
    private void createFileSystemDisk() {
        try (PrintWriter out = new PrintWriter(new FileWriter(this.filePath))) {
            for (int i = 0; i < this.sectorCount; i++) {
                for (int j = 0; j < this.sectorSize; j++) {
                    out.print(NULL_CHAR);
                }
            }
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Directory getCurrentDirectory() {
        return this.directoryStack.peek();
    }
    
    private void popDirectory() {
        this.directoryStack.pop();
        if (this.directoryStack.empty()) {
            this.directoryStack.push(this.root);
        }
    }
    
    private char[] getFileSystemDisk() {
        char characters[] = null;
        try (FileReader fr = new FileReader(this.filePath)) {
            characters = new char[this.sectorCount * this.sectorSize];
            fr.read(characters);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return characters;
    }
    
    private int freeSectors() {
        int count = 0;
        for (int i = 0; i < this.sectorCount; i++) {
            if (this.availability[i]) {
                count++;
            }
        }
        return count;
    }
    
    private int allocateNextFreeSector() {
        for (int i = 0; i < this.sectorCount; i++) {
            if (this.availability[i]) {
                this.availability[i] = false;
                return i;
            }
        }
        return -1;
    }
    
    private void deallocateSector(int sector) {
        this.availability[sector] = true;
    }
    
    private ArrayList<Integer> findAvailableSectors() {
        ArrayList<Integer> emptySectors = new ArrayList<>();
        for (int i = 0; i < this.sectorCount; i++) {
            if (this.availability[i]) {
                emptySectors.add(i);
            }
        }
        return emptySectors;
    }
    
    public ArrayList<Integer> findAvailableSectorsHARD() {
        ArrayList<Integer> emptySectors = new ArrayList<>();
        
        char characters[] = getFileSystemDisk();
        
        for (int i = 0; i < this.sectorCount; i++) {
            for (int j = 0; j < this.sectorSize; j++) {
                // Formula taken from:
                // https://stackoverflow.com/questions/14015556/how-to-map-the-indexes-of-a-matrix-to-a-1-dimensional-array-c
                if (characters[i*sectorSize+j] == NULL_CHAR) {
                    emptySectors.add(i);
                }
                // This line should be removed later
                break;
            }
        }
        
        return emptySectors;
    }
    
    public void makeDirectory(String dirName) {
        Directory currentDirectory = getCurrentDirectory();
        if (!currentDirectory.existsElement(dirName)) {
            Directory newDirectory = new Directory(dirName, DateTime.now());
            currentDirectory.addDirectory(newDirectory);
        }
    }
    
    public void changeDirectory(String dirName) {
        if (dirName.equals("..")) {
            popDirectory();
        } else {
            Directory currentDirectory = getCurrentDirectory();
            for (Directory directory: currentDirectory.getDirectories()) {
                if (directory.getName().equals(dirName)) {
                    this.directoryStack.push(directory);
                    break;
                }
            }
        }
    }
    
    public ArrayList<String> listDirectories() {
        ArrayList<String> elementList = new ArrayList<>();
        Directory currentDirectory = getCurrentDirectory();
        for (Directory directory: currentDirectory.getDirectories()) {
            elementList.add(directory.getName() + "-DIR");
        }
        for (File file: currentDirectory.getFiles()) {
            elementList.add(file.getName() + "-FILE");
        }
        return elementList;
    }
    
    public void createFile(String fileName) {
        Directory currentDirectory = getCurrentDirectory();
        if (!currentDirectory.existsElement(fileName)) {
            File newFile = new File(fileName, DateTime.now());
            
            if (this.freeSectors() > 0) {
                int firstSector = allocateNextFreeSector();
                newFile.allocateSector(firstSector);
                currentDirectory.addFile(newFile);
            }
        }
    }
    
    public void writeFile(String fileName, String content) {
        
        Directory currentDirectory = getCurrentDirectory();
        File file = currentDirectory.getFile(fileName);
        
        int neededSectors = (int)Math.ceil((float)content.length() / (float)sectorSize);
        int asignedSectors = file.assignedSectors();
        int difference = neededSectors - asignedSectors;
        
        if (difference > this.freeSectors()) {
            // No free space
            return;
        }

        // More sectors are needed
        if (difference > 0) {
            for (int i = 0; i < difference; i++) {
                file.allocateSector(this.allocateNextFreeSector());
            }
        }
        // Less sectors are needed
        else if (difference < 0) {
            for (int i = 0; i > difference; i--) {
                this.deallocateSector(file.deallocateLastSector());
            }
        }
    }
}
