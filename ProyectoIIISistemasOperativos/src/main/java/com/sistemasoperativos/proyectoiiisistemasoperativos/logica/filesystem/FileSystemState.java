/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.Inode;
import java.util.List;

/**
 *
 * @author males
 */

/**
 * Estado global del sistema de archivos durante la ejecución.
 * Se almacena:
 *  - Inodos cargados desde el FCB
 *  - Directorio actual
 *  - Tabla de archivos abiertos (OpenFileTable)
 */
public class FileSystemState {

    // Inodos cargados desde el disco
    public static List<Inode> inodos;

    // Directorio actual (por defecto el inode 0 "/")
    public static Inode currentDirectory;

    // Tabla de archivos abiertos
    public static OpenFileTable oft = new OpenFileTable();
}
