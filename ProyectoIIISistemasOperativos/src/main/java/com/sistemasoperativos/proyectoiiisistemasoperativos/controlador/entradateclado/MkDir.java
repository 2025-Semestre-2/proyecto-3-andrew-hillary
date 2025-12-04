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
public class MkDir extends ComandoPadre{
    private List<String> Carpetas;
    
    public MkDir(String comando, Controlador controlador){
        super(comando, controlador);
    }

    @Override
    public void EjecutarComando(String comando) throws Exception {
        if(ContarTamanoComando(comando) <= 0){
            throw new Exception("MkDir esperaba 1 o más argumentos, en cambio recibió " + ContarTamanoComando(comando) + " argumentos.");
        }
        String respuesta = ControladorAsignado.MkDir(Carpetas);
        System.out.println(respuesta);
    }
    
    private void ExtraerNombres(String comando) throws Exception {
        Carpetas.clear();
        List<String> parametros = ExtraerParametros(comando);
        for(String parametro: parametros){
            if(!parametro.matches("[A-Za-z0-9]+")){
                throw new Exception("""
                                   El nombre solo puede contener los siguientes caracteres:
                                   \tLetras de la 'A' a la 'Z'
                                   \tLetras de la 'a' a la 'z'
                                   \tNúmeros del '0' al '9'\n""");
            }
            Carpetas.add(parametro);
        }
    }
}
