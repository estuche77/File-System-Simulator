package model;

import core.DateTime;

/**
 *
 * @author estuche
 */
public class FileSystem {
    
    private final static String DEFAULT_NAME = "C:";
    
    private final String filePath;
    private final int sectorCount;
    private final int sectorSize;
    private final Directory root;

    public FileSystem(String filePath, int sectorCount, int sectorSize) {
        this.filePath = filePath;
        this.sectorCount = sectorCount;
        this.sectorSize = sectorSize;
        this.root = new Directory(DEFAULT_NAME, DateTime.now());
    }

    public String getFilePath() {
        return filePath;
    }

    public int getSectorCount() {
        return sectorCount;
    }

    public int getSectorSize() {
        return sectorSize;
    }

    public Directory getRoot() {
        return root;
    }
}
