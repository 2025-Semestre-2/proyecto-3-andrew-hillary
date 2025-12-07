/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem;

/**
 *
 * @author males
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Disk {

    private RandomAccessFile archivoFS;
    private final int blockSize;
    private final int totalBlocks;
    private final String Path;

    public Disk(String path, int blockSize, int totalBlocks) throws IOException {
        this.blockSize = blockSize;
        this.totalBlocks = totalBlocks;
        this.Path = path;

        File f = new File(path);

        // Abrir archivo
        archivoFS = new RandomAccessFile(path, "rw");

        // Solo si el archivo NO existe, lo inicializamos con el tamaño correcto
        if (!f.exists() || f.length() == 0) {
            archivoFS.setLength((long) blockSize * totalBlocks);
        }
    }

    public void writeBlock(int blockNumber, byte[] data) throws IOException {
        if (blockNumber < 0 || blockNumber >= totalBlocks)
            throw new IllegalArgumentException("Bloque fuera de rango.");

        if (data.length > blockSize)
            throw new IllegalArgumentException("El bloque excede blockSize.");

        archivoFS.seek(blockNumber);

        byte[] padded = new byte[data.length];
        System.arraycopy(data, 0, padded, 0, data.length);

        archivoFS.write(padded);
    }

    public byte[] readBlock(int blockNumber) throws IOException {
        if (blockNumber < 0 || blockNumber >= totalBlocks)
            throw new IllegalArgumentException("Bloque fuera de rango.");

        long offset = (long) blockNumber * blockSize;

        archivoFS.seek(offset);
        byte[] buffer = new byte[blockSize];
        archivoFS.readFully(buffer);

        return buffer;
    }

    public void flush() throws IOException {
        archivoFS.getFD().sync();
    }

    public void close() throws IOException {
        archivoFS.close();
    }

    public RandomAccessFile getArchivoFS() {
        return archivoFS;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public int getTotalBlocks() {
        return totalBlocks;
    }

    public String getPath() {
        return Path;
    }
    
    
}
