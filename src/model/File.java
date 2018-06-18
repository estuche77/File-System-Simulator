package model;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author estuche
 */
public class File extends Element {
    private final Stack<Integer> linkedSectors = new Stack();

    public File(String name, String creationDateTime) {
        super(Element.FILE, name, creationDateTime);
    }
    
    public void allocateSector(int sector) {
        this.linkedSectors.push(sector);
    }
    
    public int deallocateLastSector() {
        return this.linkedSectors.pop();
    }
    
    public int assignedSectors() {
        return this.linkedSectors.size();
    }
    
    public ArrayList<Integer> getSectors() {
        return new ArrayList(this.linkedSectors);
    }
    
    public String getFileExtension() {
        String[] parts = this.name.split(".");
        return parts[parts.length-1];
    }
}