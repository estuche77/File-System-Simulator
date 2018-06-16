package model;

import java.util.ArrayList;

/**
 *
 * @author estuche
 */
public class File extends Element {
    private final ArrayList<Integer> linkedSectors = new ArrayList<>();

    public File(String name, String creationDateTime) {
        super(Element.FILE, name, creationDateTime);
    }
}