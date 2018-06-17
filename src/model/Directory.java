package model;

import java.util.ArrayList;

/**
 *
 * @author estuche
 */
public class Directory extends Element {
    private final ArrayList<Element> elements = new ArrayList<>();

    public Directory(String name, String creationDateTime) {
        super(Element.DIRECTORY, name, creationDateTime);
    }
    
    public ArrayList<Element> getElements() {
        return elements;
    }
    
    public void addDirectory(Directory directory) {
        elements.add(directory);
    }
    
    public void addFile(File file) {
        elements.add(file);
    }
    
    public boolean existsElement(String elementName) {
        for (Element e: this.elements) {
            if (e.getName().equals(elementName)) {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Directory> getDirectories() {
        ArrayList<Directory> directories = new ArrayList<>();
        for (Element e: this.elements) {
            if (e.getType() == Element.DIRECTORY) {
                directories.add((Directory)e);
            }
        }
        return directories;
    }
    
    public ArrayList<File> getFiles() {
        ArrayList<File> files = new ArrayList<>();
        for (Element e: this.elements) {
            if (e.getType() == Element.FILE) {
                files.add((File)e);
            }
        }
        return files;
    }
}