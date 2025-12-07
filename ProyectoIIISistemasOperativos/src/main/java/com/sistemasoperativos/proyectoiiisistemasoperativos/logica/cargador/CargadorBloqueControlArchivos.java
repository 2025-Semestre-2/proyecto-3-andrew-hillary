/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.cargador;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.Inode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */
public class CargadorBloqueControlArchivos {

    private int CantidadBloques;
    private int TamanoBloques;
    private final List<Inode> Inodos;

    public CargadorBloqueControlArchivos() {
        Inodos = new ArrayList<>();
    }

    public void Parse(byte[] data, int cantidadBloques, int tamanoBloques) {
        this.CantidadBloques = cantidadBloques;
        this.TamanoBloques = tamanoBloques;

        Inodos.clear();

        byte[] copia = new byte[TamanoBloques];
        for(int indice = 0; indice < data.length; indice += TamanoBloques){
            for(int indiceAux = 0; indiceAux < copia.length; indiceAux++){
                copia[indiceAux] = data[indiceAux + indice];
            }
            if(copia[4] == 0)
                continue;
            Inode nodo = Inode.deserialize(copia);
            Inodos.add(nodo);
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
