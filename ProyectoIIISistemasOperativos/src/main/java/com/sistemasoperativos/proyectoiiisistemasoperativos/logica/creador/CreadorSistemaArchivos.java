/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.creador;

/**
 *
 * @author andrewdeni
 */
public class CreadorSistemaArchivos {
    private final CreadorBloqueArranque BloqueArranque;
    private final CreadorBloqueControlArchivos BloqueControlArchivos;
    private final CreadorBloqueEspacioLibre BloqueEspacioLibre;
    private final CreadorBloqueGrupos BloqueGrupos;
    private final CreadorBloqueUsuario BloqueUsuario;
    private int TamanoAlmacenamiento;
    private int TamanoBloque;
    private byte[] Arranque;
    private byte[] ControlArchivos;
    private byte[] EspacioLibre;
    private byte[] Grupos;
    private byte[] Usuarios;
    private byte[] SuperBloque;
    private int Indice;
    private String ContrasenaRoot;
    
    public CreadorSistemaArchivos(){
        BloqueArranque = new CreadorBloqueArranque();
        BloqueControlArchivos = new CreadorBloqueControlArchivos();
        BloqueEspacioLibre = new CreadorBloqueEspacioLibre();
        BloqueGrupos = new CreadorBloqueGrupos();
        BloqueUsuario = new CreadorBloqueUsuario();
        TamanoAlmacenamiento = 0;
        TamanoBloque = 0;
        Indice = 0;
    }
    
    public String Format(String nombreArchivo, int tamanoDisco, int tamanoBloque, String contrasena) throws Exception{
        TamanoAlmacenamiento = tamanoDisco * 1024 * 1024;
        if(!VerificarEsPotenciaDeDos(tamanoBloque))
            throw new Exception("El tamano del bloque debe ser de base 2");
        if(nombreArchivo.equals(""))
            nombreArchivo = "miDiscoDuro.fs";
        if(!nombreArchivo.endsWith(".fs"))
            throw new Exception("El nombre del archivo debe terminar con .fs");
        TamanoBloque = tamanoBloque;
        Indice = 0;
        SuperBloque = new byte[TamanoAlmacenamiento];
        LimpiarSuperBloque();
        ContrasenaRoot = contrasena;
        CrearBloques();
        CargarBloques();
        CrearArchivo(nombreArchivo);
        return "Se ha creado el nuevo FS con el nombre " + nombreArchivo;
    }
    
    private void CrearArchivo(String nombreArchivo) throws Exception {
        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(nombreArchivo, "rw")) {
            raf.setLength(TamanoAlmacenamiento);  // asegura tamaño exacto del disco virtual
            raf.seek(0);                          // escribir desde el inicio
            raf.write(SuperBloque);               // guardar todo el FS inicial
        }
    }

    
    private void LimpiarSuperBloque(){
        for(int indice = 0; indice < TamanoAlmacenamiento; indice++){
            SuperBloque[indice] = 0;
        }
    }
    
    private void CrearBloques(){
        BloqueArranque.setTamanoBloque(TamanoBloque);
        BloqueArranque.setTamanoDisco(TamanoAlmacenamiento);
        BloqueArranque.setCantidadFBCs((int) Math.ceil((TamanoAlmacenamiento * 0.8) / TamanoBloque));
        BloqueArranque.setCantidadGrupos(30);
        BloqueArranque.setCantidadUsuarios(30);
        BloqueArranque.setTamanoUsuarios(256);
        BloqueArranque.setTamanoGrupo(64);
        BloqueArranque.setTamanoFCB(256);
        BloqueArranque.setTamanoBitMap((int) Math.ceil((TamanoAlmacenamiento * 0.8) / TamanoBloque) / 8);
        BloqueArranque.setCantidadBloques((int) Math.ceil((TamanoAlmacenamiento * 0.8) / TamanoBloque));
        Arranque = BloqueArranque.Serialize();
        BloqueUsuario.setCantidadUsuarios(30);
        BloqueUsuario.setContrasenaRoot(ContrasenaRoot);
        BloqueUsuario.setTamanoUsuarios(256);
        Usuarios = BloqueUsuario.Serialize();
        BloqueGrupos.setCantidadGrupos(30);
        BloqueGrupos.setTamanoGrupo(64);
        Grupos = BloqueGrupos.Serialize();
        BloqueEspacioLibre.setTamanoAlmacenamiento(TamanoAlmacenamiento);
        BloqueEspacioLibre.setTamanoBloque(TamanoBloque);
        EspacioLibre = BloqueEspacioLibre.serialize();
        BloqueControlArchivos.setCantidadBloques(EspacioLibre.length * 8);
        BloqueControlArchivos.setTamanoBloques(256);
        ControlArchivos = BloqueControlArchivos.serialize();
    }
    
    private void CargarBloques(){
        CargarBloqueArranque();
        CargarUsuarios();
        CargarGrupos();
        CargarControlArchivos();
        CargarEspacioLibre();
        CargarArchivos();
        CargarBloqueArranque();
    }
    
    private boolean VerificarEsPotenciaDeDos(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
    
    private void CargarBloqueArranque(){
        Arranque = BloqueArranque.Serialize();
        Indice = 0;
        for(;Indice < Arranque.length; Indice++)
            SuperBloque[Indice] = Arranque[Indice];
    }

    private void CargarUsuarios(){
        int indiceAnterior = Indice;
        BloqueArranque.setPunteroUsuarios(indiceAnterior);
        for(;Indice < (indiceAnterior + Usuarios.length); Indice++){
            SuperBloque[Indice] = Usuarios[Indice - indiceAnterior];
        }
    }
    
    private void CargarGrupos(){
        int indiceAnterior = Indice;
        BloqueArranque.setPunteroGrupos(indiceAnterior);
        for(;Indice < (indiceAnterior + Grupos.length); Indice++){
            SuperBloque[Indice] = Grupos[Indice - indiceAnterior];
        }
    }
    
    private void CargarControlArchivos(){
        int indiceAnterior = Indice;
        BloqueArranque.setPunteroFCB(indiceAnterior);
        BloqueControlArchivos.setPunteroActual(Indice);
        ControlArchivos = BloqueControlArchivos.serialize();
        for(;Indice < (indiceAnterior + ControlArchivos.length); Indice++){
            SuperBloque[Indice] = ControlArchivos[Indice - indiceAnterior];
        }
    }
    
    private void CargarEspacioLibre(){
        int indiceAnterior = Indice;
        BloqueArranque.setPunteroBipmap(indiceAnterior);
        for(;Indice < (indiceAnterior + EspacioLibre.length); Indice++){
            SuperBloque[Indice] = EspacioLibre[Indice - indiceAnterior];
        }
    }
    
    private void CargarArchivos(){
        int puntero = (int) Math.ceil(TamanoAlmacenamiento * 0.8);
        BloqueArranque.setPunteroArchivos(puntero);
    }
}
