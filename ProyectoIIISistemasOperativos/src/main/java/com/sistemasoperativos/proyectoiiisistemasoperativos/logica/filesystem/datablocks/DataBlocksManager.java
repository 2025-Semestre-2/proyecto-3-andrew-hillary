/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.datablocks;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.Disk;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.FreeSpaceManager;
import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class DataBlocksManager {
    private static int Pointer;
    private static int BlockSize;
    private static int StorageSize;
    private static Disk Storage; 
    private static FreeSpaceManager SpaceManager;
    
    public static int SaveData(byte[] data) throws Exception {
        int payloadSize = BlockSize - 4;
        int index = 0;
        ArrayList<Integer> pointers = new ArrayList<>();
        ArrayList<byte[]> blocks = new ArrayList<>();
        while (index < data.length) {
            int len = Math.min(payloadSize, data.length - index);
            byte[] block = new byte[BlockSize];
            System.arraycopy(data, index, block, 4, len);
            index += len;
            int blockPtr = SpaceManager.allocate();
            pointers.add(blockPtr);
            blocks.add(block);
        }
        if (pointers.isEmpty()) return -1;
        for (int i = 0; i < pointers.size(); i++) {

            byte[] block = blocks.get(i);

            int nextPtr = (i == pointers.size() - 1)
                ? -1
                : pointers.get(i + 1);
            block[0] = (byte) (nextPtr);
            block[1] = (byte) (nextPtr >> 8);
            block[2] = (byte) (nextPtr >> 16);
            block[3] = (byte) (nextPtr >> 24);
        }
        for (int i = 0; i < pointers.size(); i++) {
            Storage.writeBlock(pointers.get(i), blocks.get(i));
        }
        return pointers.get(0);
    }
    
    public static byte[] ReadData(int pointer) throws Exception {
        if (pointer == -1) return new byte[0];
        int payloadSize = BlockSize - 4;
        ArrayList<byte[]> chunks = new ArrayList<>();
        int current = pointer;
        while (current != -1) {
            byte[] block = Storage.readBlock(current);
            byte[] chunk = new byte[payloadSize];
            System.arraycopy(block, 4, chunk, 0, payloadSize);
            chunks.add(chunk);
            int next =
                    (block[0] & 0xFF) |
                    ((block[1] & 0xFF) << 8) |
                    ((block[2] & 0xFF) << 16) |
                    ((block[3] & 0xFF) << 24);
            current = next;
        }
        int total = chunks.size() * payloadSize;
        byte[] result = new byte[total];
        int pos = 0;
        for (byte[] c : chunks) {
            System.arraycopy(c, 0, result, pos, c.length);
            pos += c.length;
        }
        return result;
    }


    public static int getPointer() {
        return Pointer;
    }

    public static void setPointer(int Pointer) {
        DataBlocksManager.Pointer = Pointer;
    }

    public static int getBlockSize() {
        return BlockSize;
    }

    public static void setBlockSize(int BlockSize) {
        DataBlocksManager.BlockSize = BlockSize;
    }

    public static int getStorageSize() {
        return StorageSize;
    }

    public static void setStorageSize(int StorageSize) {
        DataBlocksManager.StorageSize = StorageSize;
    }

    public static Disk getStorage() {
        return Storage;
    }

    public static void setStorage(Disk Storage) {
        DataBlocksManager.Storage = Storage;
    }

    public static FreeSpaceManager getSpaceManager() {
        return SpaceManager;
    }

    public static void setSpaceManager(FreeSpaceManager SpaceManager) {
        DataBlocksManager.SpaceManager = SpaceManager;
    }
    
    
}
