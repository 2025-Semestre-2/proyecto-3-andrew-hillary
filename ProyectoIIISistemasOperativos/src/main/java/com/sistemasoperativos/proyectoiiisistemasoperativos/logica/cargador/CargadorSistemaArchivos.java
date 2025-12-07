package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.cargador;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andre
 */
public class CargadorSistemaArchivos {
    private byte[] SuperBloque;
    private final CargadorBloqueArranque BloqueArranque;
    private final byte[] Arranque = new byte[64];
    private final CargadorBloqueUsuarios BloqueUsuarios;
    private byte[] Usuarios;
    private final CargadorBloqueGrupos BloqueGrupo;
    private byte[] Grupos;
    
    
    public CargadorSistemaArchivos(){
        BloqueArranque = new CargadorBloqueArranque();
        BloqueUsuarios = new CargadorBloqueUsuarios();
        BloqueGrupo = new CargadorBloqueGrupos();
    }
    
    public String CargarSistemaArchivos(String ruta) throws Exception{
        LoadFromFile(ruta);
        CopiarDatos();
        return "";
    }
    
    private void CopiarDatos(){
        CopiarDatosBloqueArranque();
        CopiarDatosBloqueUsuarios();
        CopiarDatosBloqueGrupo();
    }
    
    private void CopiarDatosBloqueArranque(){
        System.arraycopy(SuperBloque, 0, Arranque, 0, 64);
        BloqueArranque.Parse(Arranque);
        System.out.println("\n" + BloqueArranque.toString());
    }
    
    private void CopiarDatosBloqueUsuarios(){
        int puntero = BloqueArranque.getPunteroUsuarios();
        int cantidadUsuarios = BloqueArranque.getCantidadUsuarios();
        int tamanoUsuarios = BloqueArranque.getTamanoUsuarios();
        Usuarios = new byte[cantidadUsuarios * tamanoUsuarios];
        for(int indice = 0; indice < Usuarios.length; indice++){
            Usuarios[indice] = SuperBloque[indice + puntero];
        }
        BloqueUsuarios.Parse(Usuarios, cantidadUsuarios, tamanoUsuarios);
        System.out.println("\n" + BloqueUsuarios.toString());
    }
    
    private void CopiarDatosBloqueGrupo() {
        int puntero = BloqueArranque.getPunteroGrupos();
        int cantidadGrupos = BloqueArranque.getCantidadGrupos();
        int tamanoGrupos = BloqueArranque.getTamanoGrupo();
        Grupos = new byte[cantidadGrupos * tamanoGrupos];
        for(int indice = 0; indice < Grupos.length; indice++){
            Grupos[indice] = SuperBloque[indice + puntero];
        }
        BloqueGrupo.Parse(Grupos, cantidadGrupos, tamanoGrupos);
        System.out.println("\n" + BloqueGrupo.toString());
    }
    
    public void LoadFromFile(String rutaArchivo) throws Exception {

        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(rutaArchivo, "r")) {

            byte[] header = new byte[4];
            raf.seek(0);
            raf.readFully(header);

            java.nio.ByteBuffer bb = java.nio.ByteBuffer.wrap(header);
            bb.order(java.nio.ByteOrder.LITTLE_ENDIAN);

            int tamanoDisco = bb.getInt();

            SuperBloque = new byte[tamanoDisco];
            raf.seek(0);
            raf.readFully(SuperBloque);

        }
    }

}
