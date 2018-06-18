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
        
        System.out.println(fs.readFile("test.txt"));
        
        fs.createFile("test.txt");
        fs.writeFile("test.txt", "hola");
        
        fs.createFile("test2.txt");
        fs.writeFile("test2.txt", "adios");
        
        System.out.println(fs.readFile("test.txt"));
        System.out.println(fs.readFile("test2.txt"));
    }
}
