/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.creador;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.Inode;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.fileblockcontrol.FileControlBlockManager;

/**
 *
 * @author andre
 */
public class CreadorBloqueControlArchivos {
    private int CantidadBloques;
    private int TamanoBloques;
    private int PunteroActual;
    
    public CreadorBloqueControlArchivos(){
        CantidadBloques = 0;
        TamanoBloques = 0;
        PunteroActual = 0;
    }
    
    public byte[] serialize(){
        int tamano = CantidadBloques * TamanoBloques;
        byte[] espacio = new byte[tamano];
        for(int indice = 0; indice < tamano; indice++)
            espacio[indice] = 0;
        Inode raiz = CrearRaiz();
        Inode home = CrearHome();
        FileControlBlockManager.setHome(home);
        Inode root = CrearCarpetaRoot();
        raiz.AddDirectBlock(PunteroActual + TamanoBloques);
        home.AddDirectBlock(PunteroActual + TamanoBloques * 2);
        home.setFather(PunteroActual);
        root.setFather(PunteroActual + TamanoBloques);
        byte[] raizSerializado = raiz.serialize();
        System.arraycopy(raizSerializado, 0, espacio, 0, raizSerializado.length);
        byte[] homeSerializado = home.serialize();
        for(int indice = TamanoBloques; indice < homeSerializado.length + TamanoBloques; indice++){
            espacio[indice] = homeSerializado[indice - TamanoBloques];
        }
        byte[] rootSerializado = root.serialize();
        for(int indice = TamanoBloques * 2; indice < rootSerializado.length + TamanoBloques * 2; indice++){
            espacio[indice] = rootSerializado[indice - TamanoBloques * 2];
        }
        return espacio;
    }
    
    private Inode CrearRaiz(){
        Inode nodo = new Inode(
                0,
                "/",
                "root",
                "root",
                7,
                true
        );
        
        return nodo;
    }
    
    private Inode CrearHome(){
        Inode nodo = new Inode(
                1,
                "home",
                "root",
                "root",
                7,
                true
        );
        
        return nodo;
    }
    
    private Inode CrearCarpetaRoot(){
        Inode nodo = new Inode(
                2,
                "root",
                "root",
                "root",
                7,
                true
        );
        
        return nodo;
    }

    public int getCantidadBloques() {
        return CantidadBloques;
    }

    public void setCantidadBloques(int CantidadBloques) {
        this.CantidadBloques = CantidadBloques;
    }

    public int getTamanoBloques() {
        return TamanoBloques;
    }

    public void setTamanoBloques(int TamanoBloques) {
        this.TamanoBloques = TamanoBloques;
    }

    public int getPunteroActual() {
        return PunteroActual;
    }

    public void setPunteroActual(int PunteroActual) {
        this.PunteroActual = PunteroActual;
    }
    
    
}
