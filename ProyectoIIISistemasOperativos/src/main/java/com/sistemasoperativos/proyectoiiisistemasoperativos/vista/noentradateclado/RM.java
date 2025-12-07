/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado;

import com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.Controlador;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.ComandoPadre;
import java.util.List;

/**
 *
 * @author andre
 */
public class RM extends ComandoPadre {
    private String NombreArchivoDirectorio;
    private boolean EsRecursivo;
    
    public RM(String comando, Controlador controlador){
        super(comando, controlador);
    }
    
    @Override
    public void EjecutarComando(String comando) throws Exception {
        if(ContarTamanoComando(comando) != 1 && ContarTamanoComando(comando) != 2){
            throw new Exception("RM esperaba 1 o 2 argumentos, en cambio recibió " + ContarTamanoComando(comando) + " argumentos.");
        }
        ExtraerParametrosActuales(comando);
        String respuesta = ControladorAsignado.RM(NombreArchivoDirectorio, EsRecursivo);
        System.out.println("\n" + respuesta);
        
    }
            
    private void ExtraerParametrosActuales(String comando) throws Exception{
        List<String> parametros = ExtraerParametros(comando);
        if(parametros.size() == 2){
            if(!parametros.get(0).toLowerCase().equals("-r")){
                throw new Error("Parametro incorrecto: se esperaba -r");
            }
            EsRecursivo = true;
            NombreArchivoDirectorio = parametros.get(1);
            return;
        }
        EsRecursivo = false;
        NombreArchivoDirectorio = parametros.get(0);
    }
}
