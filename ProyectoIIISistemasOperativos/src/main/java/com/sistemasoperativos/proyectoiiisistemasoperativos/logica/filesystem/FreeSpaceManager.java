/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.datablocks.DataBlocksManager;
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

    private static byte[] Bitmap;
    private static int TotalBlocks; 
    private static int BlockSize;
    private static int PointerBitMap;
    private static int PointerFiles;

    /** FIRST-FIT pero bit a bit */
    public static int allocate() throws Exception {

        for (int byteIndex = 0; byteIndex < Bitmap.length; byteIndex++) {
            byte b = Bitmap[byteIndex];

            for (int bit = 0; bit < 8; bit++) {

                int blockIndex = byteIndex * 8 + bit;

                if (blockIndex >= TotalBlocks) break;

                // Si está libre (bit = 0)
                if ((b & (1 << bit)) == 0) {

                    // Marcar bit como ocupado (ponerlo en 1)
                    Bitmap[byteIndex] = (byte) (b | (1 << bit));

                    Save();
                    return (blockIndex * BlockSize) + PointerFiles;
                }
            }
        }

        return -1; // no hay espacio
    }

    /** Liberar un bloque */
    public static void free(int blockIndex) throws Exception {
        if (blockIndex < 0 || blockIndex >= TotalBlocks)
            throw new Exception("Bloque fuera de rango");

        int byteIndex = blockIndex / 8;
        int bit = blockIndex % 8;

        Bitmap[byteIndex] &= ~(1 << bit); // Poner bit en 0

        Save();
    }
    
    public static String info() {
        int freeBlocks = 0;
        int usedBlocks = 0;
        for (int byteIndex = 0; byteIndex < Bitmap.length; byteIndex++) {
            byte b = Bitmap[byteIndex];
            for (int bit = 0; bit < 8; bit++) {
                int blockIndex = byteIndex * 8 + bit;
                if (blockIndex >= TotalBlocks) break;
                boolean ocupado = ((b >> bit) & 1) == 1;
                if (ocupado) usedBlocks++;
                else freeBlocks++;
            }
        }
        long freeBytes = (long) freeBlocks * BlockSize;
        long usedBytes = (long) usedBlocks * BlockSize;
        double freeMB = freeBytes / (1024.0 * 1024.0);
        double usedMB = usedBytes / (1024.0 * 1024.0);
        StringBuilder sb = new StringBuilder();
        sb.append("Total de bloques:          ").append(TotalBlocks).append("\n");
        sb.append("Bloques libres:            ").append(freeBlocks).append("\n");
        sb.append("Bloques ocupados:          ").append(usedBlocks).append("\n\n");
        sb.append("Tamaño de bloque:          ").append(BlockSize).append(" bytes\n\n");
        sb.append("Espacio libre:             ")
                .append(freeBytes).append(" bytes (")
                .append(String.format("%.2f", freeMB)).append(" MB)\n");
        sb.append("Espacio ocupado:           ")
                .append(usedBytes).append(" bytes (")
                .append(String.format("%.2f", usedMB)).append(" MB)\n");
        return sb.toString();
    }

    /** Guardar el bitmap en disco */
    private static void Save() throws Exception {
        DiskConnector.WriteBlock(PointerBitMap, Bitmap);
    }

    public static byte[] getBitmap() {
        return Bitmap;
    }

    public static void setBitmap(byte[] Bitmap) {
        FreeSpaceManager.Bitmap = Bitmap;
    }

    public static int getTotalBlocks() {
        return TotalBlocks;
    }

    public static void setTotalBlocks(int TotalBlocks) {
        FreeSpaceManager.TotalBlocks = TotalBlocks;
    }

    public static int getBlockSize() {
        return BlockSize;
    }

    public static void setBlockSize(int BlockSize) {
        FreeSpaceManager.BlockSize = BlockSize;
    }

    public static int getPointerBitMap() {
        return PointerBitMap;
    }

    public static void setPointerBitMap(int PointerBitMap) {
        FreeSpaceManager.PointerBitMap = PointerBitMap;
    }

    public static int getPointerFiles() {
        return PointerFiles;
    }

    public static void setPointerFiles(int PointerFiles) {
        FreeSpaceManager.PointerFiles = PointerFiles;
    }

    
}
