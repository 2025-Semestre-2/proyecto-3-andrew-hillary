/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem;

/**
 *
 * @author males
 */

/**
 * Un archivo abierto en memoria.
 * Entrada en la Open File Table del sistema operativo.
 */

public class OpenFileEntry {

    private final int inodeID;   // ID del inodo del archivo
    private final String mode;   // modo de apertura: r, w, rw, a

    public OpenFileEntry(int inodeID, String mode) {
        this.inodeID = inodeID;
        this.mode = mode;
    }

    public int getInodeID() {
        return inodeID;
    }

    public String getMode() {
        return mode;
    }
}