/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author andre
 */
public class Inode {
    private int ID;
    private String Name;
    private String Owner;
    private String Group;
    private int Permissions;
    private int Size;
    private long CreatedAt;
    private long ModifiedAt;
    private boolean IsDirectory;
    private int[] DirectBlocks;
    private int IndirectBlock;
    private int DoubleIndirectBlock;
    
    public Inode(int id, String name, String owner, String group, int permissions,
             boolean isDirectory) {
        this.ID = id;
        this.Name = name;
        this.Owner = owner;
        this.Group = group;
        this.Permissions = permissions;

        this.IsDirectory = isDirectory;
        this.Size = 0; // archivo vacío inicialmente

        long now = System.currentTimeMillis();
        this.CreatedAt = now;
        this.ModifiedAt = now;

        this.DirectBlocks = new int[12];
        for (int i = 0; i < 12; i++) {
            this.DirectBlocks[i] = -1; // -1 = sin asignar
        }

        this.IndirectBlock = -1;
        this.DoubleIndirectBlock = -1;
    }

    public Inode() {
        this.ID = 0;
        this.Name = "";
        this.Owner = "";
        this.Group = "";
        this.Permissions = 0;
        this.Size = 0;
        this.CreatedAt = 0L;
        this.ModifiedAt = 0L;
        this.IsDirectory = false;

        this.DirectBlocks = new int[12];
        for (int i = 0; i < 12; i++) {
            this.DirectBlocks[i] = 0;
        }
        this.IndirectBlock = -1;
        this.DoubleIndirectBlock = -1;
    }
    
    public byte[] serialize() {
        byte[] data = new byte[256]; // tamaño fijo del inode
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.order(ByteOrder.BIG_ENDIAN); // consistente y portable

        // 0–3: ID
        buffer.putInt(ID);

        // 4–67: Name (64 bytes)
        writeFixedString(buffer, Name, 64);

        // 68–99: Owner (32 bytes)
        writeFixedString(buffer, Owner, 32);

        // 100–131: Group (32 bytes)
        writeFixedString(buffer, Group, 32);

        // 132–135: Permissions
        buffer.putInt(Permissions);

        // 136–139: Size
        buffer.putInt(Size);

        // 140–147: CreatedAt
        buffer.putLong(CreatedAt);

        // 148–155: ModifiedAt
        buffer.putLong(ModifiedAt);

        // 156: IsDirectory (1 byte)
        buffer.put((byte)(IsDirectory ? 1 : 0));

        // 157–159: padding
        buffer.put(new byte[3]);

        // 160–207: DirectBlocks (12 × 4)
        for (int i = 0; i < 12; i++) {
            buffer.putInt(DirectBlocks[i]);
        }

        // 208–211: IndirectBlock
        buffer.putInt(IndirectBlock);

        // 212–215: DoubleIndirectBlock
        buffer.putInt(DoubleIndirectBlock);

        // 216–255: padding final
        buffer.put(new byte[40]);

        return data;
    }

    private void writeFixedString(ByteBuffer buffer, String value, int maxBytes) {
        byte[] raw = value == null ? new byte[0] : value.getBytes(StandardCharsets.UTF_8);
        int len = Math.min(raw.length, maxBytes);
        buffer.put(raw, 0, len);
        if (len < maxBytes) {
            buffer.put(new byte[maxBytes - len]); // rellenar con ceros
        }
    }

    public static Inode deserialize(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.order(ByteOrder.BIG_ENDIAN);

        Inode inode = new Inode();

        inode.ID = buffer.getInt();
        inode.Name = readFixedString(buffer, 64);
        inode.Owner = readFixedString(buffer, 32);
        inode.Group = readFixedString(buffer, 32);
        inode.Permissions = buffer.getInt();
        inode.Size = buffer.getInt();
        inode.CreatedAt = buffer.getLong();
        inode.ModifiedAt = buffer.getLong();
        inode.IsDirectory = buffer.get() == 1;

        buffer.get(new byte[3]); // padding

        inode.DirectBlocks = new int[12];
        for (int i = 0; i < 12; i++) {
            inode.DirectBlocks[i] = buffer.getInt();
        }

        inode.IndirectBlock = buffer.getInt();
        inode.DoubleIndirectBlock = buffer.getInt();

        // padding final se ignora

        return inode;
    }

    private static String readFixedString(ByteBuffer buffer, int maxBytes) {
        byte[] raw = new byte[maxBytes];
        buffer.get(raw);
        return new String(raw, StandardCharsets.UTF_8).trim();
    }
    
    public boolean AddDirectBlock(int pointer){
        for(int indice = 0; indice < DirectBlocks.length; indice += 4){
            if(DirectBlocks[indice + 3] != 0){
                DirectBlocks[indice]     = (byte) (pointer);
                DirectBlocks[indice + 1] = (byte) (pointer >> 8);
                DirectBlocks[indice + 2] = (byte) (pointer >> 16);
                DirectBlocks[indice + 3] = (byte) (pointer >> 24);
                return true;
            }
        }
        return false;
    }
}
