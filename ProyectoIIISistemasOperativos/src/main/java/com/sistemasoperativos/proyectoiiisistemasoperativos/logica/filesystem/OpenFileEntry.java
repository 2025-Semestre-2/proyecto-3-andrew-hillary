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

    private final int fcbId;      // ID del FCB o INode asociado al archivo
    private int cursor;           // posición de lectura/escritura dentro del archivo
    private final String mode;    // modo: r, w, rw, a
    private boolean locked;      

    public OpenFileEntry(int fcbId, String mode) {
        this.fcbId = fcbId;
        this.mode = mode;
        this.cursor = 0;
        this.locked = false;
    }

    public int getFcbId() {
        return fcbId;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public String getMode() {
        return mode;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
