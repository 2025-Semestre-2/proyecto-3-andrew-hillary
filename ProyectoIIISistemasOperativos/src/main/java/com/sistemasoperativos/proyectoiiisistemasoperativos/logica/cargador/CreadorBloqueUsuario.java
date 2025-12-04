/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.cargador;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.User;

/**
 *
 * @author andre
 */
public class CreadorBloqueUsuario {
    private final int CantidadUsuarios;
    private final int TamanoUsuarios;
    private final String ContrasenaRoot;
    private final User Root;
    
    public CreadorBloqueUsuario(int cantidadUsuarios, int tamanoUsuarios, String contrasenaRoot){
        CantidadUsuarios = cantidadUsuarios;
        TamanoUsuarios = tamanoUsuarios;
        ContrasenaRoot = contrasenaRoot;
        Root = CrearUsuarioRoot(ContrasenaRoot);
    }
    
    private User CrearUsuarioRoot(String password){
        User root = new User("root", "root", password);
        root.setGroupID("1");
        return root;
    }
    
    public byte[] Serialize(){
        byte[] serializacion = new byte[CantidadUsuarios * TamanoUsuarios];
        for(int indice = 0; indice < serializacion.length; indice++){
            serializacion[indice] = 0;
        }
        byte[] user = Root.serialize();
        System.arraycopy(user, 0, serializacion, 0, user.length);
        return serializacion;
    }
}
