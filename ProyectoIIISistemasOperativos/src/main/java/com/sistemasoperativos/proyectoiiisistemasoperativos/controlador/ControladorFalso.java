package com.sistemasoperativos.proyectoiiisistemasoperativos.controlador;

import java.util.List;
import java.util.HashMap;

public class ControladorFalso implements Controlador {

    private String usuarioActual = "root";
    private HashMap<String, String> archivos = new HashMap<>(); // nombre → contenido

    @Override
    public String Format(String nombreArchivo, int tamanoDisco, int tamanoBloque, String contrasena) {
        return "[FAKE] Formateado: " + nombreArchivo + " (" + tamanoDisco + "MB, bloque " + tamanoBloque + ")";
    }

    @Override
    public String Passwd(String usuario, String contrasena) {
        return "[FAKE] Contraseña cambiada para " + usuario;
    }

    @Override
    public String UserAdd(String usuario, String nombreCompleto, String contrasena) {
        return "[FAKE] Usuario creado: " + usuario + " (" + nombreCompleto + ")";
    }

    @Override
    public String Su(String usuario, String contrasena) {
        usuarioActual = usuario;
        return usuarioActual;
    }

    @Override
    public String Exit() {
        return "[FAKE] Saliendo del FS...";
    }

    @Override
    public String InfoFS() {
        return "[FAKE] Información del FS: tamaño = 10MB, bloques = 4096 bytes";
    }

    @Override
    public String PWD() {
        return "/fake/directory";
    }

    @Override
    public String MV(String archivoDirectorioSeleccionado, String archivoDirectorioDestino) {
        return "[FAKE] Movido " + archivoDirectorioSeleccionado + " → " + archivoDirectorioDestino;
    }

    @Override
    public String WhereIs(String nombreArchivo) {
        return "[FAKE] El archivo '" + nombreArchivo + "' está en /fake/path";
    }

    @Override
    public String Cat(String nombreArchivo) {

        if (!archivos.containsKey(nombreArchivo)) {
            return ""; // archivo vacío si no existe
        }

        return archivos.get(nombreArchivo);
    }

    @Override
    public String Chown(String usuario, String nombreArchivoDirectorio, boolean esRecursivo) {
        return "[FAKE] Propietario cambiado a " + usuario + " para " + nombreArchivoDirectorio;
    }

    @Override
    public String Chmod(String nombreArchivoDirectorio, int permisosUsuario, int permisosGrupo) {
        return "[FAKE] Permisos cambiados: " + nombreArchivoDirectorio +
               " → U:" + permisosUsuario + " G:" + permisosGrupo;
    }

    @Override
    public String CloseFile(String nombreArchivo) {
        return "[FAKE] Archivo cerrado: " + nombreArchivo;
    }

    @Override
    public String GroupAdd(String nombreGrupo) {
        return "[FAKE] Grupo agregado: " + nombreGrupo;
    }

    @Override
    public String Whoami() {
        return usuarioActual;
    }

    @Override
    public String MkDir(List<String> archivos) {
        return "[FAKE] Directorios creados: " + archivos.toString();
    }

    @Override
    public String RM(String nombreArchivoDirectorio, boolean esRecursivo) {
        return "[FAKE] Eliminado: " + nombreArchivoDirectorio;
    }

    @Override
    public String LS(boolean esRecursivo) {
        return "[FAKE] Lista de archivos: file1.txt, file2.txt, carpeta/";
    }

    @Override
    public String CD(String directorioDestino) {
        return "[FAKE] Cambiado a directorio: " + directorioDestino;
    }

    @Override
    public String Touch(String directorioDestino) {
        archivos.put(directorioDestino, "");
        return "[FAKE] Archivo creado: " + directorioDestino;
    }

    @Override
    public String Chgrp(String nombreGrupo, String nombreDirectorio, boolean esRecursivo) {
        return "[FAKE] Grupo cambiado: " + nombreDirectorio + " → " + nombreGrupo;
    }

    @Override
    public String OpenFile(String directorioDestino) {
        return "[FAKE] Archivo abierto: " + directorioDestino;
    }

    @Override
    public String LoadFileSystem(String ruta) {
        return "[FAKE] File System cargado: " + ruta;
    }

    @Override
    public String Login(String usuario, String contrasena) {
        usuarioActual = usuario;
        return "[FAKE] Sesión iniciada como " + usuario;
    }

    @Override
    public String ViewFCB(String nombreArchivo) {
        return "[FAKE] FCB de " + nombreArchivo + ": tamaño 32 bytes, propietario root";
    }

    @Override
    public String CurrentUser() {
        return usuarioActual;
    }

    @Override
    public String SaveFile(String nombreArchivo, String contenido) {

        archivos.put(nombreArchivo, contenido);

        return "[FAKE] Guardado archivo '" + nombreArchivo +
               "' con " + contenido.length() + " caracteres.";
    }
}
