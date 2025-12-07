/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.cargador;

/**
 *
 * @author andre
 */
public class CargadorBloqueEspacioLibre {

    private int totalBloques;
    private int tamanoBloque;

    private int libres;
    private int ocupados;

    public void Parse(byte[] data, int cantidadBloques, int tamanoBloque) {
        this.totalBloques = cantidadBloques;
        this.tamanoBloque = tamanoBloque;

        libres = 0;
        ocupados = 0;

        int bitIndex = 0;

        for (int i = 0; i < data.length && bitIndex < cantidadBloques; i++) {
            byte b = data[i];

            for (int bit = 0; bit < 8 && bitIndex < cantidadBloques; bit++) {
                boolean estaOcupado = ((b >> bit) & 1) == 1;

                if (estaOcupado) ocupados++;
                else libres++;

                bitIndex++;
            }
        }
    }

    @Override
    public String toString() {
        long espacioLibreBytes = (long) libres * tamanoBloque * 8;
        long espacioOcupadoBytes = (long) ocupados * tamanoBloque * 8;

        double espacioLibreMB = espacioLibreBytes / (1024.0 * 1024.0);
        double espacioOcupadoMB = espacioOcupadoBytes / (1024.0 * 1024.0);

        StringBuilder sb = new StringBuilder();

        sb.append("=== Bitmap: Espacio Libre ===\n");
        sb.append("Total de bloques:          ").append(totalBloques).append("\n");
        sb.append("Bloques libres:            ").append(libres).append("\n");
        sb.append("Bloques ocupados:          ").append(ocupados).append("\n\n");

        sb.append("Tamaño de bloque:          ").append(tamanoBloque).append(" bytes\n\n");

        sb.append("Espacio libre:             ").append(espacioLibreBytes)
          .append(" bytes (").append(String.format("%.2f", espacioLibreMB)).append(" MB)\n");

        sb.append("Espacio ocupado:           ").append(espacioOcupadoBytes)
          .append(" bytes (").append(String.format("%.2f", espacioOcupadoMB)).append(" MB)\n");

        sb.append("=============================\n");

        return sb.toString();
    }

    public int getLibres() { return libres; }
    public int getOcupados() { return ocupados; }
}