/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.cargador;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.Group;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */
public class CargadorBloqueGrupos {

    private int CantidadGrupos;
    private int TamanoGrupo;
    private final List<Group> Grupos;

    public CargadorBloqueGrupos() {
        Grupos = new ArrayList<>();
    }

    public void Parse(byte[] data, int cantidadGrupos, int tamanoGrupo) {
        this.CantidadGrupos = cantidadGrupos;
        this.TamanoGrupo = tamanoGrupo;

        Grupos.clear();

        for (int i = 0; i < cantidadGrupos; i++) {
            int offset = i * tamanoGrupo;

            boolean vacio = true;
            for (int j = offset; j < offset + tamanoGrupo; j++) {
                if (data[j] != 0) {
                    vacio = false;
                    break;
                }
            }
            if (vacio) continue;

            byte[] grupoBytes = new byte[tamanoGrupo];
            System.arraycopy(data, offset, grupoBytes, 0, tamanoGrupo);

            Group g = Group.deserialize(grupoBytes);
            Grupos.add(g);
        }
    }

    public List<Group> getGrupos() {
        return Grupos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Bloque de Grupos ===\n");
        sb.append("Cantidad de grupos: ").append(CantidadGrupos).append("\n");
        sb.append("Tamaño por grupo:   ").append(TamanoGrupo).append(" bytes\n");
        sb.append("Grupos cargados:    ").append(Grupos.size()).append("\n\n");

        for (int i = 0; i < Grupos.size(); i++) {
            sb.append("Grupo #").append(i + 1).append(":\n");
            sb.append(" - ID:   ").append(Grupos.get(i).getGroupID()).append("\n");
            sb.append(" - Name: ").append(Grupos.get(i).getGroupName()).append("\n\n");
        }

        sb.append("=========================\n");
        return sb.toString();
    }
}
