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
public class Chgrp extends ComandoPadre {
    private String NombreGrupo;
    private String NombreArchivoDirectorio;
    private boolean EsRecursivo;
    
    public Chgrp(String comando, Controlador controlador){
        super(comando, controlador);
    }

    @Override
    public void EjecutarComando(String comando) throws Exception {
        if(ContarTamanoComando(comando) != 2 && ContarTamanoComando(comando) != 3){
            throw new Exception("chgrp esperaba 2 o 3 argumentos, en cambio recibió " + ContarTamanoComando(comando) + " argumentos.");
        }
        ExtraerParametrosActuales(comando);
        String respuesta = ControladorAsignado.Chgrp(NombreGrupo, NombreArchivoDirectorio, EsRecursivo);
        System.out.println(respuesta);
    }
    
    private void ExtraerParametrosActuales(String comando) throws Exception{
        List<String> parametros = ExtraerParametros(comando);
        if(parametros.size() == 3){
            if(!parametros.get(0).toLowerCase().equals("-r")){
                throw new Error("Parametro incorrecto: se esperaba -r");
            }
            EsRecursivo = true;
            NombreGrupo = parametros.get(1);
            NombreArchivoDirectorio = parametros.get(2);
            return;
        }
        EsRecursivo = false;
        NombreGrupo = parametros.get(0);
        NombreArchivoDirectorio = parametros.get(1);
    }
}
