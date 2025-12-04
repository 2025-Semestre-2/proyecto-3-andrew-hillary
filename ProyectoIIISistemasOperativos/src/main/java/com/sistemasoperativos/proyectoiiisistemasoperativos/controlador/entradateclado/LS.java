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
public class LS extends ComandoPadre {
    private boolean EsRecursivo;
    
    public LS(String comando, Controlador controlador){
        super(comando, controlador);
    }
    
    @Override
    public void EjecutarComando(String comando) throws Exception {
        if(ContarTamanoComando(comando) != 0 && ContarTamanoComando(comando) != 1){
            throw new Exception("LS esperaba 0 o 1 argumentos, en cambio recibió " + ContarTamanoComando(comando) + " argumentos.");
        }
        ExtraerParametrosActuales(comando);
        String respuesta = ControladorAsignado.LS(EsRecursivo);
        System.out.println("\n" + respuesta);
    } 
    
    private void ExtraerParametrosActuales(String comando) throws Exception{
        List<String> parametros = ExtraerParametros(comando);
        if(!parametros.get(0).toLowerCase().equals("-r")){
            EsRecursivo = true;
            return;
        }
        EsRecursivo = false;
    }
}
