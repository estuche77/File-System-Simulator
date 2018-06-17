package filesystemsimulator;

import application.FileSystem;

/**
 *
 * @author estuche
 */
public class FileSystemSimulator {

    public static void main(String[] args) {
        FileSystem fs = new FileSystem("./test.txt", 5, 5);
    }
    
}
