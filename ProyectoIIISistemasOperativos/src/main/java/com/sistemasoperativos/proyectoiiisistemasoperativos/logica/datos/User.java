/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos;

/**
 *
 * @author andre
 */
public class User {
    private String UserName;
    private String FullName;
    private String Password;
    private String GroupID;

    public User(String UserName, String FullName, String Password) {
        this.UserName = UserName;
        this.FullName = FullName;
        this.Password = Password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String GroupID) {
        this.GroupID = GroupID;
    }
    
    public byte[] serialize() {
        byte[] data = new byte[256];
        writeFixed(data, 0, 64, UserName);
        writeFixed(data, 64, 64, FullName);
        writeFixed(data, 128, 64, Password);
        writeFixed(data, 192, 64, GroupID != null ? GroupID : "");
        return data;
    }

    private void writeFixed(byte[] dest, int offset, int length, String value) {
        byte[] raw = value.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        int copyLen = Math.min(raw.length, length);
        System.arraycopy(raw, 0, dest, offset, copyLen);
    }
    
    public static User deserialize(byte[] data) {
        String username = readFixed(data, 0, 64);
        String fullname = readFixed(data, 64, 64);
        String password = readFixed(data, 128, 64);
        String groupID  = readFixed(data, 192, 64);

        User u = new User(username, fullname, password);
        u.setGroupID(groupID);
        return u;
    }

    private static String readFixed(byte[] src, int offset, int length) {
        return new String(src, offset, length, 
            java.nio.charset.StandardCharsets.UTF_8).trim();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Usuario ===\n");
        sb.append("UserName:  ").append(UserName).append("\n");
        sb.append("FullName:  ").append(FullName).append("\n");
        sb.append("Password:  ").append(Password).append("\n");
        sb.append("GroupID:   ").append(GroupID).append("\n");
        sb.append("================\n");
        return sb.toString();
    }
}
