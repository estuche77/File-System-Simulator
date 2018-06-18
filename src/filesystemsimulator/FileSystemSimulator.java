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
        fs.changeDirectory("Hola");
        
        fs.makeDirectory("Hola1");
        fs.changeDirectory("Hola1");
        
        fs.makeDirectory("Hola2");
        fs.changeDirectory("Hola2");
        
        fs.makeDirectory("Hola2");
        
        fs.makeDirectory("Hola3");
        
        fs.makeDirectory("Hola4");
        
        fs.createFile("test.txt");
        fs.writeFile("test.txt", "Test file content");
        
        System.out.println(fs.readFile("test.txt"));
        
        fs.createFile("test.txt");
        fs.writeFile("test.txt", "hola");
        
        fs.createFile("test2.txt");
        fs.writeFile("test2.txt", "adios");
        
        System.out.println(fs.readFile("test.txt"));
        System.out.println(fs.readFile("test2.txt"));
        
        fs.changeDirectory("..");
        
        fs.changeDirectory("..");
        
        fs.changeDirectory("..");
        
        list = fs.listDirectories();
        for (String s: list) {
            System.out.println(s);
        }
        
        fs.deleteDirectory("Hola");
        
        list = fs.listDirectories();
        for (String s: list) {
            System.out.println(s);
        }
    }
}
