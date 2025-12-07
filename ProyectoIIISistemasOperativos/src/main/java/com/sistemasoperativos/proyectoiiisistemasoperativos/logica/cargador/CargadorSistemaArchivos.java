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
    
    public CargadorSistemaArchivos(){
        BloqueArranque = new CargadorBloqueArranque();
        BloqueUsuarios = new CargadorBloqueUsuarios();
    }
    
    public String CargarSistemaArchivos(String ruta) throws Exception{
        LoadFromFile(ruta);
        CopiarDatos();
        return "";
    }
    
    private void CopiarDatos(){
        CopiarDatosBloqueArranque();
        CopiarDatosBloqueUsuarios();
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
