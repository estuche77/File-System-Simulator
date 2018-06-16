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
    
}