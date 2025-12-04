package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.cargador;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andre
 */
public class CreadorBloqueArranque {
    private int TamanoDisco;
    private int TamanoBloque;
    private int CantidadBloques;
    private int CantidadUsuarios = 8;
    private int TamanoUsuarios = 256; //0: username, 64: full name, 128: password, 192: group;
    private int CantidadGrupos = 20;
    private int tamanoGrupo = 40; // 0: gid, 8: groupName
    private int CantidadFBCs = 0;
    private int TamanoFCB = 256; // Cada FCB ocupa 256 bytes:
                     // 0:  id (4 bytes)
                     // 4:  nombre del archivo (64 bytes, UTF-8 padded)
                     // 68: owner (32 bytes)
                     // 100: group (32 bytes)
                     // 132: permissions (4 bytes)
                     // 136: size (4 bytes)
                     // 140: createdAt (8 bytes)
                     // 148: modifiedAt (8 bytes)
                     // 156: isDirectory (1 byte) + padding (3 bytes)
                     // 160: directBlocks[12] = 48 bytes
                     // 208: indirectBlock (4 bytes)
                     // 212: doubleIndirectBlock (4 bytes)
                     // 216–255: padding para alinear a 256 bytes
    private int TamanoBitMap;
    private int PunteroUsuarios;
    private int PunteroGrupos;
    private int PunteroBipmap;
    private int PunteroFCB;
    
    public CreadorBloqueArranque(int tamanoDisco, int tamanoBloque){
        TamanoDisco = tamanoDisco * 1000000;
        TamanoBloque = tamanoBloque;
        CantidadBloques = (int) (tamanoDisco * 0.9) / tamanoBloque;
        TamanoBitMap = CantidadBloques;
    }

    public int getTamanoDisco() {
        return TamanoDisco;
    }

    public void setTamanoDisco(int TamanoDisco) {
        this.TamanoDisco = TamanoDisco;
    }

    public int getTamanoBloque() {
        return TamanoBloque;
    }

    public void setTamanoBloque(int TamanoBloque) {
        this.TamanoBloque = TamanoBloque;
    }

    public int getCantidadBloques() {
        return CantidadBloques;
    }

    public void setCantidadBloques(int CantidadBloques) {
        this.CantidadBloques = CantidadBloques;
    }

    public int getCantidadUsuarios() {
        return CantidadUsuarios;
    }

    public void setCantidadUsuarios(int CantidadUsuarios) {
        this.CantidadUsuarios = CantidadUsuarios;
    }

    public int getTamanoUsuarios() {
        return TamanoUsuarios;
    }

    public void setTamanoUsuarios(int TamanoUsuarios) {
        this.TamanoUsuarios = TamanoUsuarios;
    }

    public int getCantidadGrupos() {
        return CantidadGrupos;
    }

    public void setCantidadGrupos(int CantidadGrupos) {
        this.CantidadGrupos = CantidadGrupos;
    }

    public int getTamanoGrupo() {
        return tamanoGrupo;
    }

    public void setTamanoGrupo(int tamanoGrupo) {
        this.tamanoGrupo = tamanoGrupo;
    }

    public int getCantidadFBCs() {
        return CantidadFBCs;
    }

    public void setCantidadFBCs(int CantidadFBCs) {
        this.CantidadFBCs = CantidadFBCs;
    }

    public int getTamanoFCB() {
        return TamanoFCB;
    }

    public void setTamanoFCB(int TamanoFCB) {
        this.TamanoFCB = TamanoFCB;
    }

    public int getTamanoBitMap() {
        return TamanoBitMap;
    }

    public void setTamanoBitMap(int TamanoBitMap) {
        this.TamanoBitMap = TamanoBitMap;
    }

    public int getPunteroUsuarios() {
        return PunteroUsuarios;
    }

    public void setPunteroUsuarios(int PunteroUsuarios) {
        this.PunteroUsuarios = PunteroUsuarios;
    }

    public int getPunteroGrupos() {
        return PunteroGrupos;
    }

    public void setPunteroGrupos(int PunteroGrupos) {
        this.PunteroGrupos = PunteroGrupos;
    }

    public int getPunteroBipmap() {
        return PunteroBipmap;
    }

    public void setPunteroBipmap(int PunteroBipmap) {
        this.PunteroBipmap = PunteroBipmap;
    }

    public int getPunteroFCB() {
        return PunteroFCB;
    }

    public void setPunteroFCB(int PunteroFCB) {
        this.PunteroFCB = PunteroFCB;
    }
}
