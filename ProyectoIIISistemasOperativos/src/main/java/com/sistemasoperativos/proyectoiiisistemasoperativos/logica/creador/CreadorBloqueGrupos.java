 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.creador;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.Group;

/**
 *
 * @author andre
 */
public class CreadorBloqueGrupos {
    private int CantidadGrupos;
    private int TamanoGrupo;
    private Group GroupUser;
    
    public CreadorBloqueGrupos(){
        CantidadGrupos = 0;
        TamanoGrupo = 0;
        GroupUser = new Group("1", "root");
    }
    
    public byte[] Serialize(){
        byte[] serializacion = new byte[CantidadGrupos * TamanoGrupo];
        for(int indice = 0; indice < serializacion.length; indice++){
            serializacion[0] = 0;
        }
        byte[] user = GroupUser.serialize();
        System.arraycopy(user, 0, serializacion, 0, user.length);
        return serializacion;
    }

    public int getCantidadGrupos() {
        return CantidadGrupos;
    }

    public void setCantidadGrupos(int CantidadGrupos) {
        this.CantidadGrupos = CantidadGrupos;
    }

    public int getTamanoGrupo() {
        return TamanoGrupo;
    }

    public void setTamanoGrupo(int TamanoGrupo) {
        this.TamanoGrupo = TamanoGrupo;
    }

    public Group getGroupUser() {
        return GroupUser;
    }

    public void setGroupUser(Group GroupUser) {
        this.GroupUser = GroupUser;
    }
    
    
}
