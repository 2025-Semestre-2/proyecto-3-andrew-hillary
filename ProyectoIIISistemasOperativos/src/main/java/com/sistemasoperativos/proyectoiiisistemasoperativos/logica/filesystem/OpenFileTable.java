/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author males
 */

/**
 * Tabla de archivos abiertos del FS.
 * solo existe durante la ejecución.
 */
public class OpenFileTable {

    private final Map<Integer, OpenFileEntry> openFiles = new HashMap<>();
    private int nextFD = 3; // 0,1,2 reservados

    /**
     * Abre un archivo y devuelve un file descriptor.
     * @param fcbId número del inode/FCB del archivo
     * @param mode modo de apertura (r, w, rw, a)
     */
    public int open(int fcbId, String mode) {
        int fd = nextFD++;
        openFiles.put(fd, new OpenFileEntry(fcbId, mode));
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
}
