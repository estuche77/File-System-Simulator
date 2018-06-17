package application;

import core.DateTime;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
    private final Stack<Directory> directoryStack;

    public FileSystem(String filePath, int sectorCount, int sectorSize) {
        this.filePath = filePath;
        this.sectorCount = sectorCount;
        this.sectorSize = sectorSize;
        this.root = new Directory(DEFAULT_NAME, DateTime.now());
        this.directoryStack = new Stack();
        this.directoryStack.push(this.root);
        // this.createFileSystemDisk();
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
    
    public ArrayList<Integer> findEmptySectors() {
        ArrayList<Integer> emptySectors = new ArrayList<>();
        
        try (FileReader fr = new FileReader(this.filePath)) {
            char []characters = new char[this.sectorCount * this.sectorSize];
            fr.read(characters);
            
            for (int i = 0; i < this.sectorCount; i++) {
                for (int j = 0; j < this.sectorSize; j++) {
                    // Formula taken from:
                    // https://stackoverflow.com/questions/14015556/how-to-map-the-indexes-of-a-matrix-to-a-1-dimensional-array-c
                    if (characters[i*sectorSize+j] == NULL_CHAR) {
                        emptySectors.add(i);
                        break;
                    }
                }
            }
            
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
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
}
