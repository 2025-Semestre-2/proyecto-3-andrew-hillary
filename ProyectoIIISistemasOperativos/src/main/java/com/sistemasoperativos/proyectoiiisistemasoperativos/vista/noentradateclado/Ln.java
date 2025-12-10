/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado;
import com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.Controlador;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.ComandoPadre;

/**
 *
 * @author males
 */


import java.util.List;

public class Ln extends ComandoPadre {

    public Ln(String comando, Controlador controlador) {
        super(comando, controlador);
    }

    @Override
    public void EjecutarComando(String comando) throws Exception {

        if (ContarTamanoComando(comando) != 2) {
            throw new Exception("Uso: ln nombreLink ruta/del/archivo");
        }

        List<String> params = ExtraerParametros(comando);

        String nombreLink = params.get(0);
        String rutaDestino = params.get(1);

        String respuesta = ControladorAsignado.Ln(nombreLink, rutaDestino);
        System.out.println(respuesta);
    }
}

