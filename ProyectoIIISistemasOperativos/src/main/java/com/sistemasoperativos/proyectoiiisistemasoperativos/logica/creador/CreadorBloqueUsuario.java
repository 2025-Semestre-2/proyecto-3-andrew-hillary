/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.creador;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.User;

/**
 *
 * @author andre
 */
public class CreadorBloqueUsuario {
    private int CantidadUsuarios;
    private int TamanoUsuarios;
    private String ContrasenaRoot;
    private User Root;
    
    public CreadorBloqueUsuario(){
        CantidadUsuarios = 0;
        TamanoUsuarios = 0;
        ContrasenaRoot = "";
        Root = CrearUsuarioRoot(ContrasenaRoot);
    }
    
    private User CrearUsuarioRoot(String password){
        User root = new User("root", "1", password);
        root.setGroupID("1");
        return root;
    }
    
    public byte[] Serialize(){
        Root = CrearUsuarioRoot(ContrasenaRoot);
        byte[] serializacion = new byte[CantidadUsuarios * TamanoUsuarios];
        for(int indice = 0; indice < serializacion.length; indice++){
            serializacion[indice] = 0;
        }
        byte[] user = Root.serialize();
        System.arraycopy(user, 0, serializacion, 0, user.length);
        return serializacion;
    }

    public int getCantidadUsuarios() {
        return CantidadUsuarios;
    }

    public void setCantidadUsuarios(int CantidadUsuarios) {
        this.CantidadUsuarios = CantidadUsuarios;
    }

    public int getTamanoUsuarios() {
        return TamanoUsuarios;
    }

    public void setTamanoUsuarios(int TamanoUsuarios) {
        this.TamanoUsuarios = TamanoUsuarios;
    }

    public String getContrasenaRoot() {
        return ContrasenaRoot;
    }

    public void setContrasenaRoot(String ContrasenaRoot) {
        this.ContrasenaRoot = ContrasenaRoot;
    }

    public User getRoot() {
        return Root;
    }

    public void setRoot(User Root) {
        this.Root = Root;
    }
}
