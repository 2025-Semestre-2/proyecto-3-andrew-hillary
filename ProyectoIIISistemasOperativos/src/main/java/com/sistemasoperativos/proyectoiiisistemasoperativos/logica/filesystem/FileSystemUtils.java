/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem;
import java.util.HashMap;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.Inode;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.fileblockcontrol.FileControlBlockManager;


/**
 *
 * @author males
 */

public class FileSystemUtils {

    /**
     * Busca un archivo por nombre dentro del directorio actual.
     */
    public static Inode buscarInodeEnDirectorio(String nombre) {

        if (FileSystemState.inodos == null)
            return null;

        for (Inode nodo : FileSystemState.inodos) {
            if (nodo.getFather() == FileSystemState.currentDirectory.getID()
             && nodo.getName().equals(nombre)) {
                return nodo;
            }
        }

        return null;
    }

    public static Inode buscarInodeEnDirectorioFile(String nombre) {

        Inode current = FileControlBlockManager.getCurrentDir();
        HashMap<Integer, Inode> tabla = FileControlBlockManager.getDirTable();

        for (int ptr : current.getDirectBlocks()) {

            if (ptr == -1) continue;

            Inode hijo = tabla.get(ptr);

            if (hijo != null && hijo.getName().equals(nombre))
                return hijo;
        }

        return null;
    }

}
