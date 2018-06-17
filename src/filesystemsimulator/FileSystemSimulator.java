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
        
        fs.changeDirectory("Hola");
        
        list = fs.listDirectories();
        for (String s: list) {
            System.out.println(s);
        }
        
        fs.makeDirectory("Bye");
        
        list = fs.listDirectories();
        for (String s: list) {
            System.out.println(s);
        }
        
        fs.changeDirectory("..");
        
        list = fs.listDirectories();
        for (String s: list) {
            System.out.println(s);
        }
        
        ArrayList<Integer> sectors = fs.findAvailableSectors();
        for (int s: sectors) {
            System.out.println(s);
        }
    }
}
