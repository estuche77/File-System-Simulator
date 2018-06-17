package application;

import core.DateTime;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Directory;

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
    private final Stack directoryStack;

    public FileSystem(String filePath, int sectorCount, int sectorSize) {
        this.filePath = filePath;
        this.sectorCount = sectorCount;
        this.sectorSize = sectorSize;
        this.root = new Directory(DEFAULT_NAME, DateTime.now());
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
    
    public void makeDirectory(String dirName) {
        
    }
    
    public void changeDirectory(String dirName) {
        
    }
    
    public ArrayList<String> listDirectories() {
        
    }
}
