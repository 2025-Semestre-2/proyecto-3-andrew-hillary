/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem;

import java.io.IOException;
import java.util.Arrays;
/**
 *
 * @author males
 */


/**
 * Gestiona el espacio libre usando un bitmap.
 * Cada posición del arreglo es 0 = libre, 1 = ocupado.
 */
public class FreeSpaceManager {

    private final byte[] bitmap;           // Bitmap en memoria
    private final int totalBlocks;         // Número total de bloques del disco
    private final Disk disk;               // Referencia al disco virtual
    private final int bitmapStartBlock;    // Bloque donde inicia el bitmap en el disco
    private final int blocksBitmapOccupies;// Cuántos bloques ocupa el bitmap físico

    /**
     * Constructor para crear un bitmap nuevo (usado en Format).
     * @param totalBlocks
     * @param disk
     * @param bitmapStartBlock
     * @param blocksBitmapOccupies
     */
    public FreeSpaceManager(int totalBlocks, Disk disk, int bitmapStartBlock, int blocksBitmapOccupies) {
        this.totalBlocks = totalBlocks;
        this.disk = disk;
        this.bitmapStartBlock = bitmapStartBlock;
        this.blocksBitmapOccupies = blocksBitmapOccupies;
        this.bitmap = new byte[totalBlocks]; // 0 = libre por defecto
    }

    /**
     * Constructor para cargar un bitmap existente desde el disco.
     * @param disk
     * @param bitmapStartBlock
     * @param totalBlocks
     * @param blocksBitmapOccupies
     * @throws java.io.IOException
     */
    public FreeSpaceManager(Disk disk, int bitmapStartBlock, int totalBlocks, int blocksBitmapOccupies) throws IOException {
        this.totalBlocks = totalBlocks;
        this.disk = disk;
        this.bitmapStartBlock = bitmapStartBlock;
        this.blocksBitmapOccupies = blocksBitmapOccupies;

        this.bitmap = new byte[totalBlocks];

        loadFromDisk();
    }

    /**
     * Algoritmo FIRST-FIT: encuentra el primer bloque libre.
     * @return 
     * @throws java.io.IOException
     */
    public int allocate() throws IOException {
        for (int i = 0; i < totalBlocks; i++) {
            if (bitmap[i] == 0) {
                bitmap[i] = 1; // marcar como ocupado
                saveToDisk();  // guardar el bitmap actualizado
                return i;
            }
        }
        return -1; // no hay bloques libres
    }

    /**
     * Libera un bloque dado.
     * @param blockIndex
     * @throws java.io.IOException
     */
    public void free(int blockIndex) throws IOException {
        if (blockIndex < 0 || blockIndex >= totalBlocks) {
            throw new IllegalArgumentException("Índice de bloque inválido");
        }
        bitmap[blockIndex] = 0;
        saveToDisk();
    }

    /**
     * Devuelve cuántos bloques libres hay.
     * @return 
     */
    public int getFreeCount() {
        int count = 0;
        for (byte b : bitmap) {
            if (b == 0) count++;
        }
        return count;
    }

    /**
     * Serializa el bitmap para escribirlo en el disco.
     */
    private byte[] serialize() {
        return Arrays.copyOf(bitmap, bitmap.length);
    }

    /**
     * Guarda el bitmap completo en el disco virtual.
     * @throws java.io.IOException
     */
    public void saveToDisk() throws IOException {
        byte[] serialized = serialize();

        int blockSize = disk.getBlockSize();
        int offset = 0;

        // Escribe el bitmap en múltiples bloques (si es grande)
        for (int b = 0; b < blocksBitmapOccupies; b++) {
            byte[] slice = new byte[blockSize];

            int remaining = serialized.length - offset;
            int length = Math.min(remaining, blockSize);

            System.arraycopy(serialized, offset, slice, 0, length);

            disk.writeBlock(bitmapStartBlock + b, slice);

            offset += length;

            if (remaining <= blockSize) break;
        }
    }

    /**
     * Carga el bitmap desde el disco.
     */
    private void loadFromDisk() throws IOException {
        int blockSize = disk.getBlockSize();
        byte[] temp = new byte[blocksBitmapOccupies * blockSize];

        int offset = 0;

        for (int b = 0; b < blocksBitmapOccupies; b++) {
            byte[] blockData = disk.readBlock(bitmapStartBlock + b);
            System.arraycopy(blockData, 0, temp, offset, blockSize);
            offset += blockSize;
        }

        System.arraycopy(temp, 0, bitmap, 0, totalBlocks);
    }
}
