/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.vista;

import com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.Controlador;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author andre
 */
public abstract class ComandoPadre implements Comando{
    protected String Comando;
    protected Controlador ControladorAsignado;
    
    protected ComandoPadre(String comando, Controlador controlador){
        Comando = comando;
        ControladorAsignado = controlador;
    }
    
    @Override
    public boolean CompararComando(String comando){
        String[] comandoArreglo = comando.split(" ");
        ArrayList<String> comandoLista = new ArrayList<>(Arrays.asList(comandoArreglo));
        String nombreComando = comandoLista.get(0);
        nombreComando = nombreComando.toLowerCase();
        return Comando.equals(nombreComando);
    }
    
    protected int ContarTamanoComando(String comando){
        String[] comandoLista = comando.split(" ");
        return comandoLista.length - 1;
    }
    
    protected List<String> ExtraerParametros(String comando){
        String[] comandoArreglo = comando.split(" ");
        ArrayList<String> comandoLista = (ArrayList<String>) Arrays.asList(comandoArreglo);
        comandoLista.remove(0);
        return comandoLista;
    }
}
