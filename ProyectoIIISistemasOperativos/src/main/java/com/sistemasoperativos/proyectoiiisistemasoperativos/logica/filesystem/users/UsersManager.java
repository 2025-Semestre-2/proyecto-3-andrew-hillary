/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.users;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.User;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.DiskConnector;
import java.util.List;

/**
 *
 * @author andrewdeni
 */
public class UsersManager {
    private static List<User> Users;
    private static int Pointer;
    private static User CurrentUser;
    
    public static String Su(String username, String password) throws Exception{
        User user = FindUser(username);
        if(!user.getPassword().equals(password)){
            throw new Exception("La contraseña es incorrecta.");
        }
        CurrentUser = user;
        return "Inicio de sesión de exitoso.";
    }
    
    public static String AddUser(String usuario, String nombreCompleto, String contrasena) throws Exception{
        for(User user: Users){
            if(user.getUserName().equals(usuario))
                throw new Exception("El nombre se usuario ingresado ya está siendo utilizado");
        }
        User user = new User(usuario, nombreCompleto, contrasena);
        user.setGroupID("0");
        byte[] userSerialized = user.serialize();
        int sizeBlock = userSerialized.length;
        int nextIndex = Users.size();
        DiskConnector.WriteBlock(Pointer + (sizeBlock * nextIndex), userSerialized);
        Users.add(user);
        return "Se ha creado el usuario";
    }
    
    private static User FindUser(String username) throws Exception{
        if(username.equals("")){
            return Users.get(0);
        }
        for(User user: Users){
            if(user.getUserName().equals(username)){
                return user;
            }
        }
        throw new Exception("No existe el usuario con nombre de usuario '" + username + "'");
    }
    
    public static String Whoami() throws Exception{
        if(CurrentUser == null)
            throw new Exception("No se ha iniciado sesión con algún usuario");
        String text = "";
        text += "Nombre de usuario: ";
        text += CurrentUser.getUserName();
        text += "\nNombre completo: ";
        text += CurrentUser.getFullName();
        return text;
    }
    
    public static String Passwd(String usuario, String contrasena) throws Exception{
        int index = 0;
        for(User user: Users){
            if(user.getUserName().equals(usuario)){
                user.setPassword(contrasena);
                byte[] userSerialized = user.serialize();
                int sizeBlock = userSerialized.length;
                DiskConnector.WriteBlock(Pointer + sizeBlock * index, userSerialized);
                return "Se ha cambiado exitosamente la contraseña";
            }
            index++;
        }
        throw new Exception("No se encontró el usuario");
    }

    public static List<User> getUsers() {
        return Users;
    }

    public static int getPointer() {
        return Pointer;
    }

    public static User getCurrentUser() {
        return CurrentUser;
    }
    
    public static String getCurrentUsername(){
        return CurrentUser.getUserName();
    }

    public static void setUsers(List<User> Users) {
        UsersManager.Users = Users;
    }

    public static void setPointer(int Pointer) {
        UsersManager.Pointer = Pointer;
    }

    public static void setCurrentUser(User CurrentUser) {
        UsersManager.CurrentUser = CurrentUser;
    }
    
    
}
