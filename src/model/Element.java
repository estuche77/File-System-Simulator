package model;

/**
 *
 * @author estuche
 */
public class Element {
    
    public final static int DIRECTORY = 1;
    public final static int FILE = 2;
    
    private final int type;
    protected String name;
    private String creationDateTime;
    private String modifiedDateTime;

    public Element(int type, String name, String creationDateTime) {
        this.type = type;
        this.name = name;
        this.creationDateTime = creationDateTime;
        this.modifiedDateTime = creationDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(String modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }
    
}
