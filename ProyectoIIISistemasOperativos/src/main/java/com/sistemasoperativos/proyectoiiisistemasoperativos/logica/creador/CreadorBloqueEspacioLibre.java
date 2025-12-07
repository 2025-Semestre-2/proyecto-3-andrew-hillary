/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.creador;

/**
 *
 * @author andrewdeni
 */
public class CreadorBloqueEspacioLibre {
    private int TamanoAlmacenamiento;
    private int TamanoBloque;
    
    public CreadorBloqueEspacioLibre(){
        TamanoAlmacenamiento = 0;
        TamanoBloque = 0;
    }
    
    public byte[] serialize(){
        int cantidadBytes = (int) Math.ceil((TamanoAlmacenamiento * 0.8) / TamanoBloque / 8);
        byte[] vectorBits = new byte[cantidadBytes];
        for(int indice = 0; indice < cantidadBytes; indice++)
            vectorBits[indice] = 0;
        return vectorBits;
    }

    public int getTamanoAlmacenamiento() {
        return TamanoAlmacenamiento;
    }

    public void setTamanoAlmacenamiento(int TamanoAlmacenamiento) {
        this.TamanoAlmacenamiento = TamanoAlmacenamiento;
    }

    public int getTamanoBloque() {
        return TamanoBloque;
    }

    public void setTamanoBloque(int TamanoBloque) {
        this.TamanoBloque = TamanoBloque;
    }
    
    
}
