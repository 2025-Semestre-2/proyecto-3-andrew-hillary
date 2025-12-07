/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.cargador;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.User;
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

            int offset = i * tamanoUsuario;

            // Si el bloque está vacío, dejamos de leer
            boolean esVacio = true;
            for (int j = offset; j < offset + tamanoUsuario; j++) {
                if (data[j] != 0) {
                    esVacio = false;
                    break;
                }
            }
            if (esVacio) {
                continue; // espacio sin usar
            }

            // Extraemos los 256 bytes del usuario
            byte[] usuarioBytes = new byte[tamanoUsuario];
            System.arraycopy(data, offset, usuarioBytes, 0, tamanoUsuario);

            // Convertimos bytes → User
            User u = User.deserialize(usuarioBytes);
            Usuarios.add(u);
        }
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
