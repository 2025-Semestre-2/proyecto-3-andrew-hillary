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
    private final int[] DirectBlocks = new int[12];
    private int IndirectBlock;
    private int DoubleIndirectBlock;
    private int Father;
    private int pointer;


    public Inode(int id, String name, String owner, String group, int permissions, boolean isDirectory) {
        this.ID = id;
        this.Name = name;
        this.Owner = owner;
        this.Group = group;
        this.Permissions = permissions;
        this.IsDirectory = isDirectory;

        long now = System.currentTimeMillis();
        this.CreatedAt = now;
        this.ModifiedAt = now;
        this.Size = 0;

        for (int i = 0; i < 12; i++)
            DirectBlocks[i] = -1;

        this.IndirectBlock = -1;
        this.DoubleIndirectBlock = -1;
        this.Father = -1;
    }

    public Inode() {
        this.ID = 0;
        this.Name = "";
        this.Owner = "";
        this.Group = "";
        this.Permissions = 0;
        this.Size = 0;
        this.CreatedAt = 0;
        this.ModifiedAt = 0;
        this.IsDirectory = false;

        for (int i = 0; i < 12; i++)
            DirectBlocks[i] = -1;

        this.IndirectBlock = -1;
        this.DoubleIndirectBlock = -1;
        this.Father = -1;
    }

    // SERIALIZACIÓN
    public byte[] serialize() {
        byte[] data = new byte[256];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.putInt(ID);
        writeFixedString(buffer, Name, 64);
        writeFixedString(buffer, Owner, 32);
        writeFixedString(buffer, Group, 32);
        buffer.putInt(Permissions);
        buffer.putInt(Size);
        buffer.putLong(CreatedAt);
        buffer.putLong(ModifiedAt);
        buffer.put((byte)(IsDirectory ? 1 : 0));

        buffer.put(new byte[3]);

        // Direct blocks
        for (int i = 0; i < 12; i++)
            buffer.putInt(DirectBlocks[i]);

        buffer.putInt(IndirectBlock);
        buffer.putInt(DoubleIndirectBlock);
        buffer.putInt(Father);

        buffer.put(new byte[36]);
        return data;
    }

    private void writeFixedString(ByteBuffer buffer, String s, int max) {
        byte[] raw = s.getBytes(StandardCharsets.UTF_8);
        int len = Math.min(raw.length, max);
        buffer.put(raw, 0, len);
        if (len < max) buffer.put(new byte[max - len]);
    }

    // DESERIALIZACIÓN
    public static Inode deserialize(byte[] data) {
        Inode inode = new Inode();
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        inode.ID = buffer.getInt();
        inode.Name = readFixedString(buffer, 64);
        inode.Owner = readFixedString(buffer, 32);
        inode.Group = readFixedString(buffer, 32);
        inode.Permissions = buffer.getInt();
        inode.Size = buffer.getInt();
        inode.CreatedAt = buffer.getLong();
        inode.ModifiedAt = buffer.getLong();
        inode.IsDirectory = buffer.get() == 1;

        buffer.get(new byte[3]);

        for (int i = 0; i < 12; i++)
            inode.DirectBlocks[i] = buffer.getInt();

        inode.IndirectBlock = buffer.getInt();
        inode.DoubleIndirectBlock = buffer.getInt();
        inode.Father = buffer.getInt();

        return inode;
    }

    private static String readFixedString(ByteBuffer buffer, int max) {
        byte[] raw = new byte[max];
        buffer.get(raw);
        return new String(raw, StandardCharsets.UTF_8).trim();
    }


    public boolean AddDirectBlock(int pointer) {
        for (int i = 0; i < DirectBlocks.length; i++) {
            if (DirectBlocks[i] == -1) {
                DirectBlocks[i] = pointer;
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== INODE =========================\n");
        sb.append("ID:               ").append(ID).append("\n");
        sb.append("Nombre:           ").append(Name).append("\n");
        sb.append("Tipo:             ").append(IsDirectory ? "Directorio" : "Archivo").append("\n");
        sb.append("Tamaño:           ").append(Size).append(" bytes\n\n");

        sb.append("Propietario:      ").append(Owner).append("\n");
        sb.append("Grupo:            ").append(Group).append("\n");
        sb.append("Permisos:         ").append(Permissions).append("\n\n");

        sb.append("Creado:           ").append(CreatedAt).append("\n");
        sb.append("Modificado:       ").append(ModifiedAt).append("\n\n");

        sb.append("Padre:            ").append(Father).append("\n");
        sb.append("Pointer (FS):     ").append(pointer).append("\n\n");

        sb.append("Bloques directos:\n");
        for (int i = 0; i < DirectBlocks.length; i++) {
            sb.append("   [").append(i).append("] = ").append(DirectBlocks[i]).append("\n");
        }

        sb.append("\nBloque indirecto:         ").append(IndirectBlock).append("\n");
        sb.append("Bloque doble indirecto:   ").append(DoubleIndirectBlock).append("\n");
        sb.append("Ubicacion:        ");

        return sb.toString();
    }


    public void setFather(int father) {
        this.Father = father;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getOwner() {
        return Owner;
    }

    public String getGroup() {
        return Group;
    }

    public int getPermissions() {
        return Permissions;
    }

    public int getSize() {
        return Size;
    }

    public long getCreatedAt() {
        return CreatedAt;
    }

    public long getModifiedAt() {
        return ModifiedAt;
    }

    public boolean isIsDirectory() {
        return IsDirectory;
    }

    public int[] getDirectBlocks() {
        return DirectBlocks;
    }

    public int getIndirectBlock() {
        return IndirectBlock;
    }

    public int getDoubleIndirectBlock() {
        return DoubleIndirectBlock;
    }

    public int getFather() {
        return Father;
    }

    public void setOwner(String owner) {
        this.Owner = owner;
    }

    public int getPointer() {
        return this.pointer;
    }

    public void setPointer(int ptr) {
        this.pointer = ptr;
    }

}
