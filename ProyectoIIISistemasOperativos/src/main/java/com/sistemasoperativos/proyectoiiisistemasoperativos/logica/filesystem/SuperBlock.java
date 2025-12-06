/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


/**
 *
 * @author males
 */
public class SuperBlock {

    public static final String FS_NAME = "FS";  // Nombre del FileSystem

    private int blockSize;          // Tamaño del bloque
    private int totalBlocks;        // Número total de bloques
    private int bitmapSize;         // Número total de bits (uno por bloque)

    private int usersCount;
    private int userEntrySize;

    private int groupsCount;
    private int groupEntrySize;

    private int fcbCount;
    private int fcbEntrySize;

    // Punteros (offsets en número de bloque)
    private int ptrUsers;
    private int ptrGroups;
    private int ptrBitmap;
    private int ptrFCB;
    private int ptrDataBlocks;

    public SuperBlock() { }

    public SuperBlock(
            int blockSize, int totalBlocks, int bitmapSize,
            int usersCount, int userEntrySize,
            int groupsCount, int groupEntrySize,
            int fcbCount, int fcbEntrySize,
            int ptrUsers, int ptrGroups, int ptrBitmap,
            int ptrFCB, int ptrDataBlocks) {

        this.blockSize = blockSize;
        this.totalBlocks = totalBlocks;
        this.bitmapSize = bitmapSize;
        this.usersCount = usersCount;
        this.userEntrySize = userEntrySize;
        this.groupsCount = groupsCount;
        this.groupEntrySize = groupEntrySize;
        this.fcbCount = fcbCount;
        this.fcbEntrySize = fcbEntrySize;
        this.ptrUsers = ptrUsers;
        this.ptrGroups = ptrGroups;
        this.ptrBitmap = ptrBitmap;
        this.ptrFCB = ptrFCB;
        this.ptrDataBlocks = ptrDataBlocks;
    }

    /**
     * Serializa el SuperBlock para escribirlo en un bloque.
     */
    public byte[] serialize(int blockSize) {
        ByteBuffer buffer = ByteBuffer.allocate(blockSize);

        buffer.put(FS_NAME.getBytes(StandardCharsets.UTF_8));  // Magic number
        buffer.position(16); // Reservar espacio fijo para FS_NAME

        buffer.putInt(this.blockSize);
        buffer.putInt(this.totalBlocks);
        buffer.putInt(this.bitmapSize);

        buffer.putInt(this.usersCount);
        buffer.putInt(this.userEntrySize);

        buffer.putInt(this.groupsCount);
        buffer.putInt(this.groupEntrySize);

        buffer.putInt(this.fcbCount);
        buffer.putInt(this.fcbEntrySize);

        buffer.putInt(this.ptrUsers);
        buffer.putInt(this.ptrGroups);
        buffer.putInt(this.ptrBitmap);
        buffer.putInt(this.ptrFCB);
        buffer.putInt(this.ptrDataBlocks);

        return buffer.array();
    }

    /**
     * Reconstruye el SuperBlock desde un bloque leído del disco.
     */
    public static SuperBlock deserialize(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);

        byte[] nameBytes = new byte[16];
        buffer.get(nameBytes);
        String fsName = new String(nameBytes).trim();

        if (!fsName.startsWith(FS_NAME)) {
            throw new RuntimeException("El archivo no es un sistema de archivos válido.");
        }

        SuperBlock sb = new SuperBlock();

        sb.blockSize = buffer.getInt();
        sb.totalBlocks = buffer.getInt();
        sb.bitmapSize = buffer.getInt();

        sb.usersCount = buffer.getInt();
        sb.userEntrySize = buffer.getInt();

        sb.groupsCount = buffer.getInt();
        sb.groupEntrySize = buffer.getInt();

        sb.fcbCount = buffer.getInt();
        sb.fcbEntrySize = buffer.getInt();

        sb.ptrUsers = buffer.getInt();
        sb.ptrGroups = buffer.getInt();
        sb.ptrBitmap = buffer.getInt();
        sb.ptrFCB = buffer.getInt();
        sb.ptrDataBlocks = buffer.getInt();

        return sb;
    }
}
