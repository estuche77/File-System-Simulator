package filesystemsimulator;

import application.FileSystem;
import java.util.ArrayList;

/**
 *
 * @author estuche
 */
public class FileSystemSimulator {

    public static void main(String[] args) {
        
        FileSystem fs = new FileSystem("./test.txt", 5, 5);
        ArrayList<String> list;
        
        list = fs.listDirectories();
        for (String s: list) {
            System.out.println(s);
        }
        
        fs.makeDirectory("Hola");
        
        list = fs.listDirectories();
        for (String s: list) {
            System.out.println(s);
        }
        
        fs.createFile("test.txt");
        fs.writeFile("test.txt", "Test file content");
        
        fs.createFile("test2.txt");
        fs.writeFile("test2.txt", "2");
    }
}
