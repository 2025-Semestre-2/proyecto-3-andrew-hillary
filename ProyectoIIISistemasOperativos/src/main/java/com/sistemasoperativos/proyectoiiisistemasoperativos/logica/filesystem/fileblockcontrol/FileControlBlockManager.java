/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.fileblockcontrol;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.Inode;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.User;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.DiskConnector;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.FreeSpaceManager;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.datablocks.DataBlocksManager;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.users.UsersManager;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.groups.GroupsManager;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andre
 */
public class FileControlBlockManager {
    private static int Pointer = 0;
    private static Inode CurrentDir;
    private static HashMap<Integer, Inode> DirTable;
    private static List<Inode> DirList;
    private static int NextID = 0;
    private static int AmountFCBs = 0;
    
    public static String PWD(){
        String direction = "";
        Inode node = CurrentDir;
        while(node.getFather() != -1){
            direction = "/" + node.getName() + direction;
            Integer fatherPointer = node.getFather();
            node = DirTable.get(fatherPointer);
        }
        return direction;
    }
    
    public static String CD(String to) throws Exception{
        if(!canRead(UsersManager.getCurrentUser(), CurrentDir))
            throw new Exception("No tiene los permisos suficientes");
        if(to.equals("../")){
            int pointerFather = CurrentDir.getFather();
            if(pointerFather == -1)
                throw new Exception("Estas en la carpeta raíz, no puedes ir más arriba");
            Inode father = DirTable.get(pointerFather);
            CurrentDir = father;
            return "Se ha cambiado de directorio";
        }
        int[] directBlocks = CurrentDir.getDirectBlocks();
        for(int indice = 0; indice < directBlocks.length; indice++){
            int pointer = directBlocks[indice];
            if(pointer == -1)
                continue;
            Inode node = DirTable.get(pointer);
            if(node.getName().equals(to)){
                CurrentDir = node;
                return "Se ha cambiado de directorio";
            }
        }
        throw new Exception("No se ha encontrado la carpeta especificada");
    }
    
    public static String LS(boolean isRecursive){
        if(!isRecursive)
            return LSNotRecursive();
        return LSRecursive(CurrentDir, 0);
    }
    
    private static String LSNotRecursive(){
        String message = "";
        message += "Carpeta " + CurrentDir.getName() + "\n";
        int[] directBlocks = CurrentDir.getDirectBlocks();
        for(int indice = 0; indice < directBlocks.length; indice++){
            if(directBlocks[indice] == -1)
                continue;
            Inode node = DirTable.get(directBlocks[indice]);
            if(node.isIsDirectory())
                message += "\t" + node.getName() + " - dir\n";
            else
                message += "\t" + node.getName() + " - arch\n";
        }
        return message;
    }
    
    private static String LSRecursive(Inode node, int tabs){
        String message = "";
        int[] directBlocks = node.getDirectBlocks();
        for(int indice = 0; indice < directBlocks.length; indice++){
            int pointer = directBlocks[indice];
            if(pointer == -1)
                continue;
            message += "\t".repeat(tabs);
            Inode subNode = DirTable.get(pointer);
            if(node.isIsDirectory()){
                message += subNode.getName() + " - dir\n";
                message += LSRecursive(subNode, tabs + 1);
            }
            else
                message += "\t" + node.getName() + " - arch\n";
        }
        return message;
    }
    
    public static String MkDir(List<String> names) throws Exception{
        if(!canWrite(UsersManager.getCurrentUser(), CurrentDir))
            throw new Exception("No tiene los permisos suficientes");
        String message = "";
        int pointerFather = CalculatePointerFather();
        for(String name: names){
            message += CreateDir(name, pointerFather);
        }
        byte[] currentDirSerialized = CurrentDir.serialize();
        DiskConnector.WriteBlock(pointerFather, currentDirSerialized);
        return message;
    }
    
    private static String CreateDir(String name, int pointerFather) throws Exception{
        if(VerifyName(name))
            throw new Exception("El nombre " + name + " esta duplicado");
        Inode node = new Inode(
                NextID,
                name,
                UsersManager.getCurrentUser().getUserName(),
                "",
                7,
                true
        );
        int pointer = FindSpace();
        CurrentDir.AddDirectBlock(pointer);
        node.setFather(pointerFather);
        byte[] nodeSerialized = node.serialize();
        DiskConnector.WriteBlock(pointer, nodeSerialized);
        NextID++;
        DirTable.put(pointer, node);
        DirList.add(node);
        return "Se ha creado " + name + "\n";
    }
    
    private static Integer CalculatePointerFather(){
        for (Map.Entry<Integer, Inode> entry : DirTable.entrySet()) {
            if (entry.getValue().equals(CurrentDir)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    private static int FindSpace() throws Exception{
        int jump = CurrentDir.serialize().length;
        for(int base = 0; base < AmountFCBs; base++){
            byte[] bytes = DiskConnector.ReadBlock(Pointer + (base * jump), 5);
            if(bytes[4] == 0)
                return Pointer + (base * jump);
        }
        throw new Exception("No hay más espacio");
    }
    
    private static boolean VerifyName(String name) {
        int[] punteros = CurrentDir.getDirectBlocks();
        for(int indice = 0; indice < punteros.length; indice++){
            int puntero = punteros[indice];
            if(puntero == -1)
                continue;
            Inode nodo = DirTable.get(puntero);
            if(nodo.getName().equals(name))
                return true;
        }
        return false;
    }

    public static int getPointer() {
        return Pointer;
    }

    public static void setPointer(int Pointer) {
        FileControlBlockManager.Pointer = Pointer;
    }

    public static Inode getCurrentDir() {
        return CurrentDir;
    }

    public static void setCurrentDir(Inode CurrentDir) {
        FileControlBlockManager.CurrentDir = CurrentDir;
    }

    public static HashMap<Integer, Inode> getDirTable() {
        return DirTable;
    }

    public static void setDirTable(HashMap<Integer, Inode> DirTable) {
        FileControlBlockManager.DirTable = DirTable;
    }

    public static List<Inode> getDirList() {
        return DirList;
    }

    public static void setDirList(List<Inode> DirList) {
        FileControlBlockManager.DirList = DirList;
        for(Inode node: DirList){
            if(node.getName().equals("home")){
                CurrentDir = node;
                break;
            }
        }
        PutNextID();
    }
    
    private static void PutNextID(){
        Inode nodeMax = DirList.get(0);
        for(Inode node: DirList){
            if(node.getID() > nodeMax.getID())
                nodeMax = node;
        }
        NextID = nodeMax.getID() + 1;
    }

    public static int getNextID() {
        return NextID;
    }

    public static int getAmountFCBs() {
        return AmountFCBs;
    }

    public static void setAmountFCBs(int AmountFCBs) {
        FileControlBlockManager.AmountFCBs = AmountFCBs;
    }

    public static String Touch(String name) throws Exception {
        if(!canWrite(UsersManager.getCurrentUser(), CurrentDir))
            throw new Exception("No tiene los permisos suficientes");
        if (VerifyName(name))
            throw new Exception("El archivo '" + name + "' ya existe en este directorio.");
        Inode file = new Inode(
                NextID,
                name,
                UsersManager.getCurrentUser().getUserName(),
                "",
                77,
                false
        );
        int pointerFather = CalculatePointerFather();
        file.setFather(pointerFather);
        int pointer = FindSpace();
        if (!CurrentDir.AddDirectBlock(pointer))
            throw new Exception("No hay espacio en los punteros directos del directorio.");
        byte[] fileSerialized = file.serialize();
        DiskConnector.WriteBlock(pointer, fileSerialized);
        DirTable.put(pointer, file);
        DirList.add(file);
        byte[] dirSerialized = CurrentDir.serialize();
        DiskConnector.WriteBlock(pointerFather, dirSerialized);
        NextID++;
        return "Archivo '" + name + "' creado.";
    }

    public static String Cat(String name) throws Exception{
        if(!canRead(UsersManager.getCurrentUser(), CurrentDir))
            throw new Exception("No tiene los permisos suficientes");
        int[] pointers = CurrentDir.getDirectBlocks();
        Inode node = null;
        for(int index = 0; index < pointers.length; index++){
            int pointer = pointers[index];
            if(pointer == -1)
                continue;
            node = DirTable.get(pointer);
            if(node.getName().equals(name))
                break;
        }
        if(node == null)
            throw new Exception("No existe el archivo");
        if(node.isIsDirectory())
            throw new Exception("No se puede usar cat con un directorio");
        pointers = node.getDirectBlocks();
        if(pointers[0] == -1)
            return "";
        byte[] data = DataBlocksManager.ReadData(pointers[0]);
        return new String(data, "UTF-8").replace("\u0000", "");
    }
    
    public static String Chown(String nuevoUsuario, String nombre, boolean recursivo) throws Exception {

        if (!UsersManager.getCurrentUser().getUserName().equals("root")) {
            throw new Exception("Solo el usuario root puede ejecutar chown.");
        }

        boolean existe = UsersManager.getUsers()
                .stream()
                .anyMatch(u -> u.getUserName().equals(nuevoUsuario));

        if (!existe)
            throw new Exception("El usuario '" + nuevoUsuario + "' no existe.");

        if (nombre.endsWith("/"))
            nombre = nombre.replaceAll("/+$", "");

        Inode objetivo = null;

        for (int ptr : CurrentDir.getDirectBlocks()) {
            if (ptr == -1) continue;

            Inode hijo = DirTable.get(ptr);
            if (hijo != null && hijo.getName().equals(nombre)) {
                objetivo = hijo;
                break;
            }
        }

        if (objetivo == null)
            throw new Exception("El archivo/directorio '" + nombre + "' no existe en este directorio.");

        aplicarChownInterno(objetivo, nuevoUsuario, recursivo);

        return "Propietario cambiado a '" + nuevoUsuario + "' en '" + nombre + "'";
    }



    private static void aplicarChownInterno(Inode nodo, String nuevoUsuario, boolean recursivo) throws Exception {

        nodo.setOwner(nuevoUsuario);

        int pointerReal = -1;

        for (Map.Entry<Integer, Inode> entry : DirTable.entrySet()) {
            if (entry.getValue() == nodo) {
                pointerReal = entry.getKey();
                break;
            }
        }

        if (pointerReal == -1)
            throw new Exception("Error interno: no se encontró el puntero del inodo para chown.");

        DiskConnector.WriteBlock(pointerReal, nodo.serialize());

        if (!nodo.isIsDirectory() || !recursivo)
            return;

        for (int ptr : nodo.getDirectBlocks()) {
            if (ptr == -1) continue;

            Inode hijo = DirTable.get(ptr);
            if (hijo != null)
                aplicarChownInterno(hijo, nuevoUsuario, true);
        }
    }

    
    public static String SaveFile(String nombreArchivo, String contenido) throws Exception{
        if(!canWrite(UsersManager.getCurrentUser(), CurrentDir))
            throw new Exception("No tiene los permisos suficientes");
        int[] pointers = CurrentDir.getDirectBlocks();
        Inode node = null;
        int pointerStorage = 0;
        for(int index = 0; index < pointers.length; index++){
            int pointer = pointers[index];
            if(pointer == -1)
                continue;
            node = DirTable.get(pointer);
            if(node.getName().equals(nombreArchivo)){
                pointerStorage = pointer;
                break;
            }
        }
        if(node == null)
            throw new Exception("No existe el archivo");
        if(node.isIsDirectory())
            throw new Exception("No se puede usar cat con un directorio");
        byte[] bytes = contenido.getBytes(StandardCharsets.UTF_8);
        if(node.getDirectBlocks()[0] != -1){
            int pointer = node.getDirectBlocks()[0];
            DataBlocksManager.OverwriteData(pointer, bytes);
        }
        else{
            int pointer = DataBlocksManager.SaveData(bytes);
            node.AddDirectBlock(pointer);
        }
        byte[] nodeSerialized = node.serialize();
        DiskConnector.WriteBlock(pointerStorage, nodeSerialized);
        return "Se ha guardado el contenido del archivo";
    }
    
    public static String WhereIs(String name) throws Exception{
        Inode node = null;
        for(Inode inode: DirList){
            if(inode.isIsDirectory())
                continue;
            if(inode.getName().equals(name)){
                node = inode;
                break;
            }
        }
        if(node == null)
            throw new Exception("No existe el archivo");
        String message = "";
        while(node.getFather() != -1){
            message = "/" + node.getName() + message;
            node = DirTable.get(node.getFather());
        }
        return message;
    }
    
    public static String ViewFCB(String name) throws Exception{
        if(!canRead(UsersManager.getCurrentUser(), CurrentDir))
            throw new Exception("No tiene los permisos suficientes");
        int[] punteros = CurrentDir.getDirectBlocks();
        for(int indice = 0; indice < punteros.length; indice++){
            int puntero = punteros[indice];
            if(puntero == -1)
                continue;
            Inode nodo = DirTable.get(puntero);
            if(nodo.getName().equals(name) && !nodo.isIsDirectory()){
                String message = nodo.toString();
                String route = "";
                while(nodo.getFather() != -1){
                    route = "/" + nodo.getName() + route;
                    nodo = DirTable.get(nodo.getFather());
                }
                return message + route;
            }
                
        }
        throw new Exception("No se encontró el archivo");
    }
    
    /* 
    public static String RM(String nombreArchivoDirectorio, boolean esRecursivo) throws Exception{
        if(!canWrite(UsersManager.getCurrentUser(), CurrentDir))
            throw new Exception("No tiene los permisos suficientes");
        int[] punteros = CurrentDir.getDirectBlocks();
        byte[] whiteBlock = new byte[256];
        for(int indice = 0; indice < punteros.length; indice++){
            int puntero = punteros[indice];
            if(puntero == -1)
                continue;
            Inode nodo = DirTable.get(puntero);
            if(nodo.getName().equals(nombreArchivoDirectorio)){
                if(esRecursivo){
                    RMRecursive(nodo, whiteBlock);
                    byte[] serializado = nodo.serialize();
                    DiskConnector.WriteBlock(Pointer, serializado);
                }
                else{
                    RMNotRecursive(puntero, whiteBlock);
                    punteros[indice] = -1;
                    int punteroActual = CalculatePointerFather();
                    byte[] actualSerializado = CurrentDir.serialize();
                    DiskConnector.WriteBlock(punteroActual, actualSerializado);
                }
                return "Se ha eliminado el recurso";
            }
                
        }
        throw new Exception("No se encontró el archivo");
    }
    */
    private static void RMRecursive(Inode node, byte[] whiteBlock) throws Exception{
        int[] directBlocks = node.getDirectBlocks();
        for(int index = 0; index < directBlocks.length; index++){
            int pointer = directBlocks[index];
            if(pointer == -1)
                continue;
            Inode nodeAux = DirTable.get(pointer);
            RMRecursive(nodeAux, whiteBlock);
        }
        for(int index = 0; index < directBlocks.length; index++){
            int pointer = directBlocks[index];
            if(pointer == -1)
                continue;
            DiskConnector.WriteBlock(pointer, whiteBlock);
        }
        for(int index = 0; index < directBlocks.length; index++)
            directBlocks[index] = -1;
    }
    
    private static void RMNotRecursive(int pointer, byte[] whiteBlock) throws Exception{
        DiskConnector.WriteBlock(pointer, whiteBlock);
    }
    
    public static boolean canRead(User user, Inode inode) {
        if (!user.getUserName().equals(inode.getOwner())) return false;
        return (inode.getPermissions() & 4) != 0;
    }

    public static boolean canWrite(User user, Inode inode) {
        if (!user.getUserName().equals(inode.getOwner())) return false;
        return (inode.getPermissions() & 2) != 0;
    }

    public static boolean canExecute(User user, Inode inode) {
        if (!user.getUserName().equals(inode.getOwner())) return false;
        return (inode.getPermissions() & 1) != 0;
    }
    
    public static String MV(String archivo, String destino) throws Exception{
        if(!VerifyName(archivo))
            throw new Exception("No existe el archivo a mover");
        int punteroArch = 0;
        Inode archivoInode = null;
        int[] punteros = CurrentDir.getDirectBlocks();
        for(int indice = 0; indice < punteros.length; indice++){
            int puntero = punteros[indice];
            if(puntero == -1)
                continue;
            Inode nodo = DirTable.get(puntero);
            if(nodo.getName().equals(archivo)){
                archivoInode = nodo;
                punteroArch = puntero;
                punteros[indice] = -1;
                break;
            }
        }
        if(destino.equals("../")){
            int punteroPadre = CurrentDir.getFather();
            Inode nodo = DirTable.get(punteroPadre);
            nodo.AddDirectBlock(punteroArch);
            byte[] serializado = nodo.serialize();
            archivoInode.setFather(punteroPadre);
            DiskConnector.WriteBlock(punteroPadre, serializado);
            byte[] serializadoArchivo = archivoInode.serialize();
            DiskConnector.WriteBlock(punteroArch, serializadoArchivo);
            return "Se movió el archivo al padre";
        }
        else if(VerifyName(destino)){
            int punteroDestino = 0;
            Inode carpetaDestino = null;
            for(int indice = 0; indice < punteros.length; indice++){
                int puntero = punteros[indice];
                if(puntero == -1)
                    continue;
                Inode nodo = DirTable.get(puntero);
                if(nodo.getName().equals(destino)){
                    carpetaDestino = nodo;
                    punteroDestino = puntero;
                    break;
                }
            }
            carpetaDestino.AddDirectBlock(punteroArch);
            archivoInode.setFather(punteroDestino);
            byte[] carpetaDestinoSerializada = carpetaDestino.serialize();
            DiskConnector.WriteBlock(punteroDestino, carpetaDestinoSerializada);
            byte[] archivoSerializado = archivoInode.serialize();
            DiskConnector.WriteBlock(punteroDestino, archivoSerializado);
            return "Se ha movido al archivo a otra carpeta";
        }
        else{
            archivoInode.setName(destino);
            byte[] serializado = archivoInode.serialize();
            DiskConnector.WriteBlock(punteroArch, serializado);
            return "Se renombró el archivo";
        }
    }

    public static String Chgrp(String nuevoGrupo, String nombre, boolean recursivo) throws Exception {

        if (!UsersManager.getCurrentUser().getUserName().equals("root")) {
            throw new Exception("Solo el usuario root puede ejecutar chgrp.");
        }
        boolean existeGrupo = GroupsManager.getGroups()
                .stream()
                .anyMatch(g -> g.getGroupName().equals(nuevoGrupo));

        if (!existeGrupo)
            throw new Exception("El grupo '" + nuevoGrupo + "' no existe.");
        if (nombre.endsWith("/"))
            nombre = nombre.replaceAll("/+$", "");

        Inode objetivo = null;

        for (int ptr : CurrentDir.getDirectBlocks()) {
            if (ptr == -1) continue;

            Inode hijo = DirTable.get(ptr);

            if (hijo != null && hijo.getName().equals(nombre)) {
                objetivo = hijo;
                break;
            }
        }

        if (objetivo == null)
            throw new Exception("El archivo/directorio '" + nombre + "' no existe en este directorio.");
        aplicarChgrpInterno(objetivo, nuevoGrupo, recursivo);

        return "Grupo cambiado a '" + nuevoGrupo + "' en '" + nombre + "'";
    }

    private static void aplicarChgrpInterno(Inode nodo, String nuevoGrupo, boolean recursivo) throws Exception {

        nodo.setGroup(nuevoGrupo);

        int pointerReal = -1;
        for (Map.Entry<Integer, Inode> entry : DirTable.entrySet()) {
            if (entry.getValue() == nodo) {
                pointerReal = entry.getKey();
                break;
            }
        }

        if (pointerReal == -1)
            throw new Exception("Error interno: no se encontró el puntero del inodo.");

        DiskConnector.WriteBlock(pointerReal, nodo.serialize());

        if (!nodo.isIsDirectory() || !recursivo)
            return;

        for (int ptr : nodo.getDirectBlocks()) {
            if (ptr == -1) continue;

            Inode hijo = DirTable.get(ptr);
            if (hijo != null)
                aplicarChgrpInterno(hijo, nuevoGrupo, true);
        }
    }

    public static String Chmod(String permisos, String nombre) throws Exception {

        if (!permisos.matches("[0-7]{2}"))
            throw new Exception("Formato inválido. Use dos dígitos entre 0 y 7. Ej: 75");

        int nuevosPermisos = Integer.parseInt(permisos);

        Inode objetivo = null;
        int pointerStorage = 0;

        // Buscar archivo en el directorio actual
        for (int ptr : CurrentDir.getDirectBlocks()) {
            if (ptr == -1) continue;

            Inode hijo = DirTable.get(ptr);
            if (hijo != null && hijo.getName().equals(nombre)) {
                objetivo = hijo;
                pointerStorage = ptr;
                break;
            }
        }

        if (objetivo == null)
            throw new Exception("El archivo/directorio '" + nombre + "' no existe en este directorio.");

        aplicarChmodInterno(objetivo, nuevosPermisos, pointerStorage);

        return "Permisos cambiados a '" + permisos + "' en '" + nombre + "'";
    }

    private static void aplicarChmodInterno(Inode nodo, int permisos, int pointerStorage) throws Exception {

        nodo.setPermissions(permisos);

        byte[] data = nodo.serialize();

        DiskConnector.WriteBlock(pointerStorage, data);
    }

    public static String RM(String nombre, boolean recursivo) throws Exception {

        if (nombre.endsWith("/"))
            nombre = nombre.replaceAll("/+$", "");

        int[] directBlocks = CurrentDir.getDirectBlocks();
        Inode objetivo = null;
        int pointerObjetivo = -1;

        for (int ptr : directBlocks) {
            if (ptr == -1) continue;

            Inode hijo = DirTable.get(ptr);
            if (hijo != null && hijo.getName().equals(nombre)) {
                objetivo = hijo;
                pointerObjetivo = ptr;
                break;
            }
        }

        if (objetivo == null)
            throw new Exception("El archivo/directorio '" + nombre + "' no existe.");

        if (objetivo.isIsDirectory() && !recursivo)
            throw new Exception("El directorio no está vacío o falta usar -R.");

        borrarNodo(objetivo, pointerObjetivo, recursivo);

        return "Se eliminó '" + nombre + "' correctamente.";
    }


    private static void borrarNodo(Inode nodo, int pointer, boolean recursivo) throws Exception {

        if (nodo.isIsDirectory() && recursivo) {
            for (int ptr : nodo.getDirectBlocks()) {
                if (ptr == -1) continue;

                Inode hijo = DirTable.get(ptr);
                if (hijo != null) {
                    borrarNodo(hijo, ptr, true);
                }
            }
        }

        for (int ptr : nodo.getDirectBlocks()) {
            if (ptr != -1) {
                DataBlocksManager.FreeData(ptr);   
            }
        }

        CurrentDir.removeDirectBlock(pointer);
        DiskConnector.WriteBlock(CurrentDir.getFather(), CurrentDir.serialize());
        DirTable.remove(pointer);
        DirList.remove(nodo);
        FreeSpaceManager.freeFCB(pointer);
    }

    public static String Ln(String nombreLink, String rutaDestino) throws Exception {

        Inode destino = buscarPorRuta(rutaDestino);
        if (destino == null)
            throw new Exception("No existe el archivo destino: " + rutaDestino);

        if (!canRead(UsersManager.getCurrentUser(), destino))
            throw new Exception("No tiene permisos para enlazar el archivo destino.");

        if (VerifyName(nombreLink))
            throw new Exception("Ya existe un archivo o directorio con ese nombre en este directorio.");

        Inode enlace = new Inode(
                NextID,
                nombreLink,
                UsersManager.getCurrentUser().getUserName(),
                destino.getGroup(),
                7,              
                false           
        );

        String contenidoLink = rutaDestino;
        int ptrData = DataBlocksManager.SaveData(contenidoLink.getBytes(StandardCharsets.UTF_8));
        enlace.AddDirectBlock(ptrData);

        enlace.setFather(CalculatePointerFather());

        // Guardarlo como archivo normal (igual que touch)
        int pointer = FindSpace();
        CurrentDir.AddDirectBlock(pointer);

        byte[] ser = enlace.serialize();
        DiskConnector.WriteBlock(pointer, ser);

        DirTable.put(pointer, enlace);
        DirList.add(enlace);
        NextID++;

        // Actualizar directorio
        DiskConnector.WriteBlock(enlace.getFather(), CurrentDir.serialize());

        return "Enlace creado: " + nombreLink + " -> " + rutaDestino;
    }

    public static Inode buscarPorRuta(String ruta) throws Exception {

        if (ruta == null || ruta.isEmpty())
            throw new Exception("Ruta inválida");

        Inode actual;

        // Si empieza con "/"  ruta absoluta
        if (ruta.startsWith("/")) {

        // El usuario ve "home" como raíz
        Inode raizVisible = null;

        for (Inode nodo : DirList) {
            if (nodo.getName().equals("home")) {
                raizVisible = nodo;
                break;
            }
        }

        if (raizVisible == null)
            throw new Exception("No se encontró /home como raíz visible del sistema.");

        actual = raizVisible;
        ruta = ruta.substring(1); 
        }else {

            actual = CurrentDir;
        }

        if (ruta.isEmpty()) return actual;

        String[] partes = ruta.split("/");

        for (String p : partes) {

            if (p.equals("") || p.equals(".")) continue;

            if (p.equals("..")) {
                if (actual.getFather() == -1)
                    throw new Exception("No se puede subir más arriba de la raíz");
                actual = DirTable.get(actual.getFather());
                continue;
            }

            // Buscar hijo dentro del directorio actual
            Inode hijo = null;

            for (int ptr : actual.getDirectBlocks()) {
                if (ptr == -1) continue;

                Inode aux = DirTable.get(ptr);

                if (aux != null && aux.getName().equals(p)) {
                    hijo = aux;
                    break;
                }
            }

            if (hijo == null)
                throw new Exception("No existe '" + p + "' en la ruta");

            actual = hijo;
        }

        return actual;
    }

    private static Inode encontrarRaiz() {

        for (Inode nodo : DirList) {
            if (nodo.getFather() == -1) {
                return nodo;
            }
        }

        throw new RuntimeException("ERROR: No se encontró la raíz del filesystem");
    }

    
}
