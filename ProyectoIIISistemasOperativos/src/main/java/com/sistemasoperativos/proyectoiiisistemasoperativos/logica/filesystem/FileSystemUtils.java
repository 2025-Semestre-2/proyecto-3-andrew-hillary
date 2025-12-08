/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.Inode;


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
}
