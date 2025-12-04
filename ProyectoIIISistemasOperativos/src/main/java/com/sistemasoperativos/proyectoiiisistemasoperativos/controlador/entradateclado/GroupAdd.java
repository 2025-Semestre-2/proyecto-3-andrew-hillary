/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.entradateclado;

import com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.Controlador;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.ComandoPadre;
import java.util.List;

/**
 *
 * @author andre
 */
public class GroupAdd extends ComandoPadre{
    
    private String NombreGrupo;
    
    public GroupAdd(String comando, Controlador controlador){
        super(comando, controlador);
    }

    @Override
    public void EjecutarComando(String comando) throws Exception {
        if(ContarTamanoComando(comando) != 1){
            throw new Exception("GroupAdd esperaba 1 argumentos, en cambio recibió " + ContarTamanoComando(comando) + " argumentos.");
        }
        ExtraerNombreUsuario(comando);
        String respuesta = ControladorAsignado.GroupAdd(NombreGrupo);
        System.out.println("\n" + respuesta);
    }
    
    private void ExtraerNombreUsuario(String comando){
        List<String> parametros = ExtraerParametros(comando);
        NombreGrupo = parametros.get(0);
    }
}
