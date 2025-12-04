/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.vista;

/**
 *
 * @author andre
 */
public interface Comando {
    public void EjecutarComando(String comando) throws Exception;
    public boolean CompararComando(String comando);
}
