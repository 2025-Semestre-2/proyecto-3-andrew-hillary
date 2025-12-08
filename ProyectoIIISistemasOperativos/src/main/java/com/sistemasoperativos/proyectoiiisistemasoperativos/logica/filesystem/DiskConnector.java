/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem;

/**
 *
 * @author andrewdeni
 */
public class DiskConnector {
    private static Disk Storage = null;
    
    public static void CreateDisk(String path, int blockSize, int totalBlocks) throws Exception{
        Storage = new Disk(path, blockSize, totalBlocks);
    }
    
    public static void WriteBlock(int blockNumber, byte[] data) throws Exception{
        if(Storage == null)
            throw new Exception("No se ha creado la clase que controla el almacenamiento.");
        Storage.writeBlock(blockNumber, data);
    }
    
    public static byte[] ReadBlock(int blockNumber) throws Exception{
        if(Storage == null)
            throw new Exception("No se ha creado la clase que controla el almacenamiento.");
        return Storage.readBlock(blockNumber);
    }
    
    public static byte[] ReadBlock(int blockNumber, int blockSize) throws Exception{
        if(Storage == null)
            throw new Exception("No se ha creado la clase que controla el almacenamiento.");
        return Storage.readBlock(blockNumber, blockSize);
    }
    
    public static void CloseFileSystem() throws Exception{
        if(Storage == null)
            throw new Exception("No se ha creado la clase que controla el almacenamiento.");
        Storage.close();
    }
}
