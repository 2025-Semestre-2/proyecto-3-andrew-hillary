/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem;

/**
 *
 * @author males
 */

import java.io.IOException;
import java.io.RandomAccessFile;

public class Disk {

    private final RandomAccessFile archivoFS;  // archivo físico .fs
    private final int blockSize;               // tamaño del bloque en bytes
    private final int totalBlocks;             // número total de bloques del disco

    /**
     * Constructor del disco virtual.
     *
     * @param path         Ruta/nombre del archivo .fs
     * @param blockSize    Tamaño del bloque (bytes)
     * @param totalBlocks  Número total de bloques del disco
     */
    public Disk(String path, int blockSize, int totalBlocks) throws IOException {
        this.blockSize = blockSize;
        this.totalBlocks = totalBlocks;
        this.archivoFS = new RandomAccessFile(path, "rw");

        // se ajusta el tamaño del archivo por si es nuevo
        this.archivoFS.setLength((long) blockSize * totalBlocks);
    }

    /**
     * Escribe un bloque completo en la posición indicada.
     * @param blockNumber
     * @param data
     * @throws java.io.IOException
     */
    public void writeBlock(int blockNumber, byte[] data) throws IOException {
        if (blockNumber < 0 || blockNumber >= totalBlocks) {
            throw new IllegalArgumentException("Número de bloque fuera de rango.");
        }

        if (data.length > blockSize) {
            throw new IllegalArgumentException("El tamaño del bloque excede blockSize.");
        }

        long offset = (long) blockNumber * blockSize;
        archivoFS.seek(offset);

        // Si el bloque es más pequeño, rellenar con ceros
        byte[] padded = new byte[blockSize];
        System.arraycopy(data, 0, padded, 0, data.length);

        archivoFS.write(padded);
    }

    /**
     * Lee un bloque completo del disco.
     * @param blockNumber
     * @return 
     * @throws java.io.IOException
     */
    public byte[] readBlock(int blockNumber) throws IOException {
        if (blockNumber < 0 || blockNumber >= totalBlocks) {
            throw new IllegalArgumentException("Número de bloque fuera de rango.");
        }

        long offset = (long) blockNumber * blockSize;
        archivoFS.seek(offset);

        byte[] buffer = new byte[blockSize];
        archivoFS.readFully(buffer);

        return buffer;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public int getTotalBlocks() {
        return totalBlocks;
    }

    /**
     * Fuerza a guardar cambios en disco.
     * @throws java.io.IOException
     */
    public void flush() throws IOException {
        archivoFS.getFD().sync();
    }

    /**
     * Cierra el archivo .fs correctamente.
     * @throws java.io.IOException
     */
    public void close() throws IOException {
        archivoFS.close();
    }
}
