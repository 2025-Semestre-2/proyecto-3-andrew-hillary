package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem;

import java.util.HashMap;
import java.util.Map;

/**
 * Tabla de archivos abiertos del FS.
 * Solo existe durante la ejecución.
 */
public class OpenFileTable {

    private static class OFTEntry {
        int inodeID;
        String mode;
        boolean used;
    }

    private final OFTEntry[] table = new OFTEntry[64]; // máximo 64 archivos abiertos

    public OpenFileTable() {
        for (int i = 0; i < table.length; i++)
            table[i] = new OFTEntry();
    }

    // Buscar si un inodo ya está abierto
    public int findByInode(int inodeID) {
        for (int i = 0; i < table.length; i++) {
            if (table[i].used && table[i].inodeID == inodeID)
                return i;
        }
        return -1;
    }

    // Abrir archivo
    public int open(int inodeID, String mode) throws Exception {
        for (int i = 0; i < table.length; i++) {
            if (!table[i].used) {
                table[i].used = true;
                table[i].inodeID = inodeID;
                table[i].mode = mode;
                return i;
            }
        }
        throw new Exception("La tabla OFT está llena.");
    }

    // Cerrar archivo
    public void close(int fd) {
        table[fd].used = false;
    }
}

