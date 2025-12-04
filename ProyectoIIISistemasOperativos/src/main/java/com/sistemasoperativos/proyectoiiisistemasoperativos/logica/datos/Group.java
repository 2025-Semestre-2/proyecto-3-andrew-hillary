/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author andre
 */
public class Group {
    private String GroupID;
    private String GroupName;

    public Group(String GroupID, String GroupName) {
        this.GroupID = GroupID;
        this.GroupName = GroupName;
    }

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String GroupID) {
        this.GroupID = GroupID;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }
    
    public byte[] serialize() {
        byte[] data = new byte[40];
        byte[] idBytes = GroupID.getBytes(StandardCharsets.UTF_8);
        int idLength = Math.min(idBytes.length, 8);
        System.arraycopy(idBytes, 0, data, 0, idLength);
        byte[] nameBytes = GroupName.getBytes(StandardCharsets.UTF_8);
        int nameLength = Math.min(nameBytes.length, 32);
        System.arraycopy(nameBytes, 0, data, 8, nameLength);
        return data;
    }

    public static Group deserialize(byte[] data) {
        String gid = new String(data, 0, 8, StandardCharsets.UTF_8).trim();
        String name = new String(data, 8, 32, StandardCharsets.UTF_8).trim();
        return new Group(gid, name);
    }
}
