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
    
    public void deleteElement(String fileName) {
        for (int i = 0; i < this.elements.size(); i++) {
            if (elements.get(i).getName().equals(fileName)) {
                this.elements.remove(i);
            }
        }
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
    
    public Directory getDirectory(String dirName) {
        for (Element e: this.elements) {
            if (e.getType() == Element.DIRECTORY && e.getName().equals(dirName)) {
                return (Directory)e;
            }
        }
        return null;
    }
    
    public File getFile(String fileName) {
        for (Element e: this.elements) {
            if (e.getType() == Element.FILE && e.getName().equals(fileName)) {
                return (File)e;
            }
        }
        return null;
    }
    
    public Element cutElement(String fileName) {
        for (int i = 0; i < this.elements.size(); i++) {
            if (this.elements.get(i).getName().equals(fileName)) {
                Element e = this.elements.get(i);
                this.elements.remove(i);
                return e;
            }
        }
        return null;
    }
    
    public void pasteElement(Element element) {
        this.elements.add(element);
    }
    
    public void renameElement(String fileName, String newName) {
        for (Element e: this.elements) {
            if (e.getName().equals(fileName)) {
                e.setName(newName);
            }
        }
    }
}