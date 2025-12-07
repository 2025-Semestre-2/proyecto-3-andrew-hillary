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
public class MV extends ComandoPadre {
    
    private String NombreArchivoDirectorio;
    private String NombreDestinoArchivoDirectorio;
    
    public MV(String comando, Controlador controlador){
        super(comando, controlador);
    }

    @Override
    public void EjecutarComando(String comando) throws Exception {
        if(ContarTamanoComando(comando) != 2){
            throw new Exception("MV esperaba 2 argumentos, en cambio recibió " + ContarTamanoComando(comando) + " argumentos.");
        }
        ExtraerParametrosActuales(comando);
        String respuesta = ControladorAsignado.MV(NombreArchivoDirectorio, NombreDestinoArchivoDirectorio);
        System.out.println("\n" + respuesta);
    }
    
    private void ExtraerParametrosActuales(String comando) throws Exception{
        List<String> parametros = ExtraerParametros(comando);
        EvaluarNombre(parametros.get(0));
        NombreArchivoDirectorio = parametros.get(0);
        EvaluarNombre(parametros.get(1));
        NombreDestinoArchivoDirectorio = parametros.get(1);
    }
    
    private void EvaluarNombre(String nombre) throws Exception{
        if(!nombre.matches("[A-Za-z0-9]+")){
            throw new Exception("""
                               Los nombres de archivs solo pueden contener los siguientes caracteres:
                               \tLetras de la 'A' a la 'Z'
                               \tLetras de la 'a' a la 'z'
                               \tNúmeros del '0' al '9'\n""");
        } 
    }
}
