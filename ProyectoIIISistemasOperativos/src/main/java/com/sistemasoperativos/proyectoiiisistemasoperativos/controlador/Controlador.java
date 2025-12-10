/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.controlador;

import java.util.List;

/**
 *
 * @author andre
 */
public interface Controlador {
    // Comandos de administración del FS
    String Format(String nombreArchivo, int tamanoDisco, int tamanoBloque, String contrasena) throws Exception;

    String Passwd(String usuario, String contrasena) throws Exception;

    String UserAdd(String usuario, String nombreCompleto, String contrasena) throws Exception;

    String Su(String usuario, String contrasena) throws Exception;

    String Exit() throws Exception;

    String InfoFS() throws Exception;

    String PWD() throws Exception;

    // Comandos de archivos/directorios
    String MV(String archivoDirectorioSeleccionado, String archivoDirectorioDestino) throws Exception;

    String WhereIs(String nombreArchivo) throws Exception;

    String Cat(String nombreArchivo) throws Exception;

    String Chown(String usuario, String nombreArchivoDirectorio, boolean esRecursivo) throws Exception;

    String Chmod(String nombreArchivoDirectorio, int permisosUsuario, int permisosGrupo) throws Exception;

    String CloseFile(String nombreArchivo) throws Exception;

    String GroupAdd(String nombreGrupo) throws Exception;

    String Whoami() throws Exception;

    String MkDir(List<String> archivos) throws Exception;

    String RM(String nombreArchivoDirectorio, boolean esRecursivo) throws Exception;

    String LS(boolean esRecursivo) throws Exception;

    String CD(String directorioDestino) throws Exception;

    String Touch(String directorioDestino) throws Exception;

    String Chgrp(String nombreGrupo, String nombreDirectorio, boolean esRecursivo) throws Exception;

    String OpenFile(String directorioDestino) throws Exception;

    String LoadFileSystem(String ruta) throws Exception;
    
    String Login(String usuario, String contrasena) throws Exception;
    
    String ViewFCB(String nombreArchivo) throws Exception;
    
    String CurrentUser() throws Exception;
    
    String SaveFile(String nombreArchivo, String contenido) throws Exception;

    String Ln(String nombreLink, String rutaDestino) throws Exception;

}
