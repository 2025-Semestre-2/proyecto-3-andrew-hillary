/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.controlador;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.cargador.CargadorSistemaArchivos;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.creador.CreadorSistemaArchivos;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.fileblockcontrol.FileControlBlockManager;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.groups.GroupsManager;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.users.UsersManager;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.FileSystemUtils;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.FileSystemState;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.Inode;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.FreeSpaceManager;

import java.util.List;
/**
 *
 * @author andrewdeni
 */

public class ControladorImpl implements Controlador {
    private CreadorSistemaArchivos FormatClass = new CreadorSistemaArchivos();
    private CargadorSistemaArchivos LoadFileSystemClass = new CargadorSistemaArchivos();

    @Override
    public String Format(String nombreArchivo, int tamanoDisco, int tamanoBloque, String contrasena) throws Exception {
        return FormatClass.Format(nombreArchivo, tamanoDisco, tamanoBloque, contrasena);
    }

    @Override
    public String Passwd(String usuario, String contrasena) throws Exception {
        return UsersManager.Passwd(usuario, contrasena);
    }

    @Override
    public String UserAdd(String usuario, String nombreCompleto, String contrasena) throws Exception {
        return UsersManager.AddUser(usuario, nombreCompleto, contrasena);
    }

    @Override
    public String Su(String usuario, String contrasena) throws Exception {
        return UsersManager.Su(usuario, contrasena);
    }

    @Override
    public String Exit() throws Exception {
        throw new Exception("Exit: No implementado");
    }

    @Override
    public String InfoFS() throws Exception {
        return FreeSpaceManager.info();
    }

    @Override
    public String PWD() throws Exception {
        return FileControlBlockManager.PWD();
    }

    @Override
    public String MV(String archivoDirectorioSeleccionado, String archivoDirectorioDestino) throws Exception {
        return FileControlBlockManager.MV(archivoDirectorioSeleccionado, archivoDirectorioDestino);
    }

    @Override
    public String WhereIs(String nombreArchivo) throws Exception {
        return FileControlBlockManager.WhereIs(nombreArchivo);
    }

    @Override
    public String Cat(String nombreArchivo) throws Exception {
        return FileControlBlockManager.Cat(nombreArchivo);
    }

    @Override
    public String Chown(String usuario, String nombre, boolean recursivo) throws Exception {
        return FileControlBlockManager.Chown(usuario, nombre, recursivo);
    }


    @Override
    public String Chmod(String nombreArchivoDirectorio, int permisosUsuario, int permisosGrupo) throws Exception {

        if (permisosUsuario < 0 || permisosUsuario > 7 ||
            permisosGrupo < 0 || permisosGrupo > 7)
            throw new Exception("Los permisos deben ser números entre 0 y 7.");

        String permisos = "" + permisosUsuario + permisosGrupo;
        return FileControlBlockManager.Chmod(permisos, nombreArchivoDirectorio);
    }


    @Override
    public String OpenFile(String nombre) throws Exception {

        Inode nodo = FileSystemUtils.buscarInodeEnDirectorioFile(nombre);

        if (nodo == null)
            throw new Exception("El archivo '" + nombre + "' no existe en este directorio.");

        if (nodo.isIsDirectory())
            throw new Exception("No se pueden abrir directorios.");

        int fdExistente = FileSystemState.oft.findByInode(nodo.getID());
        if (fdExistente != -1)
            return "El archivo ya está abierto. FD = " + fdExistente;

        int fd = FileSystemState.oft.open(nodo.getID(), "rw");

        return "Archivo '" + nombre + "' abierto correctamente. FD = " + fd;
    }



    @Override
    public String CloseFile(String nombre) throws Exception {

        Inode nodo = FileSystemUtils.buscarInodeEnDirectorioFile(nombre);

        if (nodo == null)
            throw new Exception("El archivo '" + nombre + "' no existe.");

        int fd = FileSystemState.oft.findByInode(nodo.getID());

        if (fd == -1)
            throw new Exception("El archivo '" + nombre + "' no está abierto.");

        FileSystemState.oft.close(fd);

        return "Archivo '" + nombre + "' cerrado correctamente.";
    }


    @Override
    public String GroupAdd(String nombreGrupo) throws Exception {
        return GroupsManager.GroupAdd(nombreGrupo);
    }

    @Override
    public String Whoami() throws Exception {
        return UsersManager.Whoami();
    }

    @Override
    public String MkDir(List<String> archivos) throws Exception {
        return FileControlBlockManager.MkDir(archivos);
    }

    @Override
    public String RM(String nombreArchivoDirectorio, boolean esRecursivo) throws Exception {
        return FileControlBlockManager.RM(nombreArchivoDirectorio, esRecursivo);
    }

    @Override
    public String LS(boolean esRecursivo) throws Exception {
        return FileControlBlockManager.LS(esRecursivo);
    }

    @Override
    public String CD(String directorioDestino) throws Exception {
        return FileControlBlockManager.CD(directorioDestino);
    }

    @Override
    public String Touch(String name) throws Exception {
        return FileControlBlockManager.Touch(name);
    }


    @Override
    public String Chgrp(String nombreGrupo, String nombreArchivoDirectorio, boolean esRecursivo) throws Exception {
        return FileControlBlockManager.Chgrp(nombreGrupo, nombreArchivoDirectorio, esRecursivo);
    }


    @Override
    public String LoadFileSystem(String ruta) throws Exception {
        return LoadFileSystemClass.CargarSistemaArchivos(ruta);
    }

    @Override
    public String Login(String usuario, String contrasena) throws Exception {
        return UsersManager.Su(usuario, contrasena);
    }

    @Override
    public String ViewFCB(String nombreArchivo) throws Exception {
        return FileControlBlockManager.ViewFCB(nombreArchivo);
    }

    @Override
    public String CurrentUser() throws Exception {
        String user = UsersManager.getCurrentUsername();
        return user + "@myFS";
    }

    @Override
    public String SaveFile(String nombreArchivo, String contenido) throws Exception {
        return FileControlBlockManager.SaveFile(nombreArchivo, contenido);
    }
}

