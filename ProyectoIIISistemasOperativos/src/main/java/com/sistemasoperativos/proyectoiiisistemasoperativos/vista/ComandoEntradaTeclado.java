/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.vista;

import com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.Controlador;
import java.util.Scanner;

/**
 *
 * @author andre
 */
public abstract class ComandoEntradaTeclado extends ComandoPadre{
    protected Scanner Entrada;
    
    protected ComandoEntradaTeclado(String comando, Controlador controlador){
        super(comando, controlador);
        Entrada = new Scanner(System.in);
    }
}