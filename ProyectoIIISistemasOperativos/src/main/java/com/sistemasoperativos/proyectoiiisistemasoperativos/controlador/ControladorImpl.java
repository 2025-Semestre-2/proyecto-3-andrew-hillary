/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.controlador;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.creador.CreadorSistemaArchivos;
import java.util.List;
/**
 *
 * @author andrewdeni
 */

public class ControladorImpl implements Controlador {
    private CreadorSistemaArchivos FormatClass = new CreadorSistemaArchivos();

    @Override
    public String Format(String nombreArchivo, int tamanoDisco, int tamanoBloque, String contrasena) throws Exception {
        return FormatClass.Format(nombreArchivo, tamanoDisco, tamanoBloque, contrasena);
    }

    @Override
    public String Passwd(String usuario, String contrasena) throws Exception {
        throw new Exception("Passwd: No implementado");
    }

    @Override
    public String UserAdd(String usuario, String nombreCompleto, String contrasena) throws Exception {
        throw new Exception("UserAdd: No implementado");
    }

    @Override
    public String Su(String usuario, String contrasena) throws Exception {
        throw new Exception("Su: No implementado");
    }

    @Override
    public String Exit() throws Exception {
        throw new Exception("Exit: No implementado");
    }

    @Override
    public String InfoFS() throws Exception {
        throw new Exception("InfoFS: No implementado");
    }

    @Override
    public String PWD() throws Exception {
        throw new Exception("PWD: No implementado");
    }

    @Override
    public String MV(String archivoDirectorioSeleccionado, String archivoDirectorioDestino) throws Exception {
        throw new Exception("MV: No implementado");
    }

    @Override
    public String WhereIs(String nombreArchivo) throws Exception {
        throw new Exception("WhereIs: No implementado");
    }

    @Override
    public String Cat(String nombreArchivo) throws Exception {
        throw new Exception("Cat: No implementado");
    }

    @Override
    public String Chown(String usuario, String nombreArchivoDirectorio, boolean esRecursivo) throws Exception {
        throw new Exception("Chown: No implementado");
    }

    @Override
    public String Chmod(String nombreArchivoDirectorio, int permisosUsuario, int permisosGrupo) throws Exception {
        throw new Exception("Chmod: No implementado");
    }

    @Override
    public String CloseFile(String nombreArchivo) throws Exception {
        throw new Exception("CloseFile: No implementado");
    }

    @Override
    public String GroupAdd(String nombreGrupo) throws Exception {
        throw new Exception("GroupAdd: No implementado");
    }

    @Override
    public String Whoami() throws Exception {
        throw new Exception("Whoami: No implementado");
    }

    @Override
    public String MkDir(List<String> archivos) throws Exception {
        throw new Exception("MkDir: No implementado");
    }

    @Override
    public String RM(String nombreArchivoDirectorio, boolean esRecursivo) throws Exception {
        throw new Exception("RM: No implementado");
    }

    @Override
    public String LS(boolean esRecursivo) throws Exception {
        throw new Exception("LS: No implementado");
    }

    @Override
    public String CD(String directorioDestino) throws Exception {
        throw new Exception("CD: No implementado");
    }

    @Override
    public String Touch(String directorioDestino) throws Exception {
        throw new Exception("Touch: No implementado");
    }

    @Override
    public String Chgrp(String nombreGrupo, String nombreDirectorio, boolean esRecursivo) throws Exception {
        throw new Exception("Chgrp: No implementado");
    }

    @Override
    public String OpenFile(String directorioDestino) throws Exception {
        throw new Exception("OpenFile: No implementado");
    }

    @Override
    public String LoadFileSystem(String ruta) throws Exception {
        throw new Exception("LoadFileSystem: No implementado");
    }

    @Override
    public String Login(String usuario, String contrasena) throws Exception {
        return "Aceptado";
    }

    @Override
    public String ViewFCB(String nombreArchivo) throws Exception {
        throw new Exception("ViewFCB: No implementado");
    }

    @Override
    public String CurrentUser() throws Exception {
        return "Andrew@myFS";
    }

    @Override
    public String SaveFile(String nombreArchivo, String contenido) throws Exception {
        throw new Exception("SaveFile: No implementado");
    }
}

