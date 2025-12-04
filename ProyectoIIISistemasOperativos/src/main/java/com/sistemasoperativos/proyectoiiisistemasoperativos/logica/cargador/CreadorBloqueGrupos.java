 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.cargador;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.Group;

/**
 *
 * @author andre
 */
public class CreadorBloqueGrupos {
    private final int CantidadGrupos;
    private final int TamanoGrupo;
    private final Group GroupUser;
    
    public CreadorBloqueGrupos(int cantidadUsuarios, int tamanoUsuarios){
        CantidadGrupos = cantidadUsuarios;
        TamanoGrupo = tamanoUsuarios;
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
}
