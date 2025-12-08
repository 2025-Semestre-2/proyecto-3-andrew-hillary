/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.fileblockcontrol;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.Inode;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.DiskConnector;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.datablocks.DataBlocksManager;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.users.UsersManager;
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
            message += "\t" + node.getName() + "\n";
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
            message += subNode.getName() + "\n";
            message += LSRecursive(subNode, tabs + 1);
        }
        return message;
    }
    
    public static String MkDir(List<String> names) throws Exception{
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
        if (VerifyName(name))
            throw new Exception("El archivo '" + name + "' ya existe en este directorio.");
        Inode file = new Inode(
                NextID,
                name,
                UsersManager.getCurrentUser().getUserName(),
                "",
                6,
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
        int[] direcciones = CurrentDir.getDirectBlocks();

        for (int ptr : direcciones) {
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

        byte[] data = nodo.serialize();
        DiskConnector.WriteBlock(nodo.getPointer(), data);

        if (!nodo.isIsDirectory() || !recursivo)
            return;

        for (int ptr : nodo.getDirectBlocks()) {
            if (ptr == -1) continue;

            Inode hijo = DirTable.get(ptr);
            if (hijo != null)
                aplicarChownInterno(hijo, nuevoUsuario, true);
        }
    }



}
