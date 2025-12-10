/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.cargador;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.Inode;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.fileblockcontrol.FileControlBlockManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author andre
 */
public class CargadorBloqueControlArchivos {

    private int CantidadBloques;
    private int TamanoBloques;
    private final List<Inode> Inodos;
    private final HashMap<Integer, Inode> DirTable;
    private final int Puntero;
    public CargadorBloqueControlArchivos() {
        Inodos = new ArrayList<>();
        DirTable = new HashMap<>();
        Puntero = 0;
    }

    public void Parse(byte[] data, int cantidadBloques, int tamanoBloques, int puntero) {
        this.CantidadBloques = cantidadBloques;
        this.TamanoBloques = tamanoBloques;
        DirTable.clear();
        Inodos.clear();

        byte[] copia = new byte[TamanoBloques];
        for(int indice = 0; indice < data.length; indice += TamanoBloques){
            for(int indiceAux = 0; indiceAux < copia.length; indiceAux++){
                copia[indiceAux] = data[indiceAux + indice];
            }
            boolean inodeVacio = true;
            for (int i = 0; i < copia.length; i++) {
                if (copia[i] != 0) {
                    inodeVacio = false;
                    break;
                }
            }
            if (inodeVacio)
                continue;
            
            Inode nodo = Inode.deserialize(copia);
            Inodos.add(nodo);
            DirTable.put(indice + puntero, nodo);
        }
        FileControlBlockManager.setDirList(Inodos);
        FileControlBlockManager.setAmountFCBs(cantidadBloques);
        FileControlBlockManager.setDirTable(DirTable);
        for(Inode inode: Inodos){
            if(inode.getName().equals("home")){
                FileControlBlockManager.setHome(inode);
                break;
            }
        }
    }

    public List<Inode> getInodos() {
        return Inodos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Bloque de Control de Archivos (FCBs / Inodos) ===\n");
        sb.append("Total slots: ").append(CantidadBloques).append("\n");
        sb.append("Tamaño por inodo: ").append(TamanoBloques).append(" bytes\n");
        sb.append("Inodos cargados: ").append(Inodos.size()).append("\n\n");

        for (Inode inode : Inodos) {
            sb.append("Inodo ID ").append(inode.getID()).append(":\n");
            sb.append("  Nombre:          ").append(inode.getName()).append("\n");
            sb.append("  Dueño:           ").append(inode.getOwner()).append("\n");
            sb.append("  Grupo:           ").append(inode.getGroup()).append("\n");
            sb.append("  Permisos:        ").append(inode.getPermissions()).append("\n");
            sb.append("  Tamaño:          ").append(inode.getSize()).append("\n");
            sb.append("  Directorio:      ").append(inode.isIsDirectory()).append("\n");
            sb.append("  Padre:           ").append(inode.getFather()).append("\n");
            sb.append("  DirectBlocks:    ").append(java.util.Arrays.toString(inode.getDirectBlocks())).append("\n");
            sb.append("  IndirectBlock:   ").append(inode.getIndirectBlock()).append("\n");
            sb.append("  DoubleIndirect:  ").append(inode.getDoubleIndirectBlock()).append("\n\n");
        }

        sb.append("=======================================================\n");
        return sb.toString();
    }
}
