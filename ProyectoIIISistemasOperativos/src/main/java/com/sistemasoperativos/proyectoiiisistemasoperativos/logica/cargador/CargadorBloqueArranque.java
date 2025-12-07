/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.cargador;

/**
 *
 * @author andre
 */
public class CargadorBloqueArranque {
    private int TamanoDisco;
    private int TamanoBloque;
    private int CantidadBloques;
    private int CantidadUsuarios;
    private int TamanoUsuarios;
    private int CantidadGrupos;
    private int tamanoGrupo;
    private int CantidadFBCs;
    private int TamanoFCB;
    private int PunteroUsuarios;
    private int PunteroGrupos;
    private int PunteroBipmap;
    private int PunteroFCB;
    private int PunteroArchivos;
    private int TamanoBitMap;
    
    public CargadorBloqueArranque(){
        
    }
    
    public void Parse(byte[] data) {
        java.nio.ByteBuffer buffer = java.nio.ByteBuffer.wrap(data);
        buffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);

        TamanoDisco      = buffer.getInt();   // 0
        TamanoBloque     = buffer.getInt();   // 4
        CantidadBloques  = buffer.getInt();   // 8

        CantidadUsuarios = buffer.getInt();   // 12
        TamanoUsuarios   = buffer.getInt();   // 16

        CantidadGrupos   = buffer.getInt();   // 20
        tamanoGrupo      = buffer.getInt();   // 24

        CantidadFBCs     = buffer.getInt();   // 28
        TamanoFCB        = buffer.getInt();   // 32

        TamanoBitMap     = buffer.getInt();   // 36

        PunteroUsuarios  = buffer.getInt();   // 40
        PunteroGrupos    = buffer.getInt();   // 44
        PunteroBipmap    = buffer.getInt();   // 48
        PunteroFCB       = buffer.getInt();   // 52
        PunteroArchivos  = buffer.getInt();   // 56

        buffer.getInt(); // reservado (60)
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Bloque de Arranque ===\n");
        sb.append("Tamaño del Disco:        ").append(TamanoDisco).append(" bytes\n");
        sb.append("Tamaño del Bloque:       ").append(TamanoBloque).append(" bytes\n");
        sb.append("Cantidad de Bloques:     ").append(CantidadBloques).append("\n\n");

        sb.append("Cantidad de Usuarios:    ").append(CantidadUsuarios).append("\n");
        sb.append("Tamaño de Usuarios:      ").append(TamanoUsuarios).append(" bytes\n");
        sb.append("Cantidad de Grupos:      ").append(CantidadGrupos).append("\n");
        sb.append("Tamaño de Grupo:         ").append(tamanoGrupo).append(" bytes\n\n");

        sb.append("Cantidad de FCBs:        ").append(CantidadFBCs).append("\n");
        sb.append("Tamaño de FCB:           ").append(TamanoFCB).append(" bytes\n");
        sb.append("Tamaño del Bitmap:       ").append(TamanoBitMap).append(" bytes\n\n");

        sb.append("Puntero a Usuarios:      ").append(PunteroUsuarios).append("\n");
        sb.append("Puntero a Grupos:        ").append(PunteroGrupos).append("\n");
        sb.append("Puntero al Bitmap:       ").append(PunteroBipmap).append("\n");
        sb.append("Puntero a FCBs:          ").append(PunteroFCB).append("\n");
        sb.append("Puntero a Archivos:      ").append(PunteroArchivos).append("\n");

        sb.append("===========================\n");

        return sb.toString();
    }

    public int getTamanoDisco() {
        return TamanoDisco;
    }

    public int getTamanoBloque() {
        return TamanoBloque;
    }

    public int getCantidadBloques() {
        return CantidadBloques;
    }

    public int getCantidadUsuarios() {
        return CantidadUsuarios;
    }

    public int getTamanoUsuarios() {
        return TamanoUsuarios;
    }

    public int getCantidadGrupos() {
        return CantidadGrupos;
    }

    public int getTamanoGrupo() {
        return tamanoGrupo;
    }

    public int getCantidadFBCs() {
        return CantidadFBCs;
    }

    public int getTamanoFCB() {
        return TamanoFCB;
    }

    public int getPunteroUsuarios() {
        return PunteroUsuarios;
    }

    public int getPunteroGrupos() {
        return PunteroGrupos;
    }

    public int getPunteroBipmap() {
        return PunteroBipmap;
    }

    public int getPunteroFCB() {
        return PunteroFCB;
    }

    public int getPunteroArchivos() {
        return PunteroArchivos;
    }

    public int getTamanoBitMap() {
        return TamanoBitMap;
    }
}
