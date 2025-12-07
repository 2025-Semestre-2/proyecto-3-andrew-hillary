/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.cargador;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.User;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.users.UsersManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */
public class CargadorBloqueUsuarios {

    private int CantidadUsuarios;
    private int TamanoUsuarios;
    private final List<User> Usuarios;

    public CargadorBloqueUsuarios() {
        Usuarios = new ArrayList<>();
    }

    public void Parse(byte[] data, int cantidadUsuarios, int tamanoUsuario) {
        this.CantidadUsuarios = cantidadUsuarios;
        this.TamanoUsuarios = tamanoUsuario;

        Usuarios.clear();

        // Recorremos cada usuario
        for (int i = 0; i < cantidadUsuarios; i++) {

            byte[] copia = new byte[tamanoUsuario];
            
            int base = tamanoUsuario * i;
            
            for(int indice = 0; indice < tamanoUsuario; indice++){
                copia[indice] = data[indice + base];
            }
            
            if(copia[0] == 0)
                break;

            User u = User.deserialize(copia);
            Usuarios.add(u);
        }
        UsersManager.setUsers(Usuarios);
    }

    public List<User> getUsuarios() {
        return Usuarios;
    }

    public int getCantidadUsuarios() {
        return CantidadUsuarios;
    }

    public int getTamanoUsuarios() {
        return TamanoUsuarios;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Bloque de Usuarios ===\n");
        sb.append("Cantidad de usuarios: ").append(CantidadUsuarios).append("\n");
        sb.append("Tamaño por usuario:   ").append(TamanoUsuarios).append(" bytes\n");
        sb.append("Usuarios cargados:    ").append(Usuarios.size()).append("\n\n");

        for (int i = 0; i < Usuarios.size(); i++) {
            sb.append("Usuario #").append(i + 1).append(":\n");
            sb.append(Usuarios.get(i).toString()).append("\n");
        }

        sb.append("===========================\n");

        return sb.toString();
    }
}
