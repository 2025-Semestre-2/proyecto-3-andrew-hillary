/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.entradateclado;

import com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.Controlador;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.ComandoPadre;

/**
 *
 * @author andre
 */
public class CurrentUser extends ComandoPadre {
    
    public CurrentUser(String comando, Controlador controlador){
        super(comando, controlador);
    }

    @Override
    public void EjecutarComando(String comando) throws Exception {
        if(ContarTamanoComando(comando) != 0){
            throw new Exception("CurrentUser esperaba 0 argumentos, en cambio recibió " + ContarTamanoComando(comando) + " argumentos.");
        }
        String respuesta = ControladorAsignado.CurrentUser();
        System.out.println(respuesta);
    }
    
}
