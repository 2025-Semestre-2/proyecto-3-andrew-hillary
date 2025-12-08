package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem;

import java.util.HashMap;
import java.util.Map;

/**
 * Tabla de archivos abiertos del FS.
 * Solo existe durante la ejecución.
 */
public class OpenFileTable {

    private final Map<Integer, OpenFileEntry> openFiles = new HashMap<>();
    private int nextFD = 3; // 0,1,2 reservados como Unix

    /**
     * Abre un archivo y devuelve un file descriptor.
     * @param inodeId número del inode del archivo
     * @param mode modo de apertura (r, w, rw, a)
     * @return 
     */
    public int open(int inodeId, String mode) {
        int fd = nextFD++;
        openFiles.put(fd, new OpenFileEntry(inodeId, mode));
        return fd;
    }

    /**
     * Obtiene la entrada de un archivo abierto.
     */
    public OpenFileEntry get(int fd) throws Exception {
        if (!openFiles.containsKey(fd)) {
            throw new Exception("FD " + fd + " no está abierto.");
        }
        return openFiles.get(fd);
    }

    /**
     * Cierra un archivo (lo quita de la tabla).
     */
    public void close(int fd) throws Exception {
        if (!openFiles.containsKey(fd)) {
            throw new Exception("FD no existe.");
        }
        openFiles.remove(fd);
    }

    /**
     * ¿Está abierto el descriptor?
     */
    public boolean isOpen(int fd) {
        return openFiles.containsKey(fd);
    }

    /**
     * Buscar un FD a partir del inode ID.
     * Esto permite cerrar archivos usando el nombre.
     */
    public int findByInode(int inodeId) {
        for (Map.Entry<Integer, OpenFileEntry> e : openFiles.entrySet()) {
            if (e.getValue().getInodeID() == inodeId)
                return e.getKey();
        }
        return -1;
    }
}
