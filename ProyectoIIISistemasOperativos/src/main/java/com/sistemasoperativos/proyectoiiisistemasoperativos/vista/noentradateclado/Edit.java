/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado;

import com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.Controlador;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.ComandoPadre;
import java.util.List;

/**
 *
 * @author andre
 */
public class Edit extends ComandoPadre {
    private String NombreArchivo;
    private final Clear ComandoClear;
    
    public Edit(String comando, Controlador controlador, Clear clear){
        super(comando, controlador);
        ComandoClear = clear;
    }

    @Override
    public void EjecutarComando(String comando) throws Exception {
        if(ContarTamanoComando(comando) != 1){
            throw new Exception("Edit esperaba 1 argumentos, en cambio recibió " + ContarTamanoComando(comando) + " argumentos.");
        }
        ExtraerParametrosActuales(comando);
        ComandoClear.EjecutarComando("clear");
        String contenidoArchivo = ControladorAsignado.Cat(NombreArchivo);
        String nuevoContenido = EditarArchivo(contenidoArchivo);
        String mensaje = ControladorAsignado.SaveFile(NombreArchivo, nuevoContenido);
        System.out.println(mensaje);
    }
    
    private void ExtraerParametrosActuales(String comando) throws Exception{
        List<String> parametros = ExtraerParametros(comando);
        NombreArchivo = parametros.get(0);
    }
    
    /**
     * Editor simple de consola:
     * - Muestra contenido inicial
     * - Permite escribir texto nuevo
     * - Ctrl+S → guardar temporal
     * - Ctrl+X → salir y retornar string
     */
    private String EditarArchivo(String contenido) throws Exception {
        StringBuilder buffer = new StringBuilder();

        System.out.println("=== EDITOR DE TEXTO ===");
        System.out.println("Archivo: " + NombreArchivo);
        System.out.println("Comandos:");
        System.out.println("  :w    → guardar");
        System.out.println("  :q    → salir (guardar y salir)");
        System.out.println("  :x    → salir sin guardar");
        System.out.println("  :d    → eliminar la ultima línea del archivo");
        System.out.println("-------------------------------------");

        if (contenido != null && !contenido.isEmpty()) {
            System.out.println(contenido);
            buffer.append(contenido);
        }

        java.io.BufferedReader reader =
                new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

        while (true) {
            String linea = reader.readLine();

            if (linea == null) continue;

            if (linea.equals(":w")) {
                System.out.println("[Guardado temporal]");
            }
            else if (linea.equals(":q")) {
                System.out.println("[Guardado y salida]");
                return buffer.toString();
            }
            else if (linea.equals(":x")) {
                System.out.println("[Salida sin guardar]");
                return contenido; 
            } 
            else if (linea.equals(":d")) {
                if (buffer.length() > 0) {
                    int lastNl = buffer.lastIndexOf("\n");

                    if (lastNl == -1) {
                        buffer = buffer.delete(0, buffer.length());
                    } else {
                        int secondLast = buffer.lastIndexOf("\n", lastNl - 1);
                        if (secondLast == -1) {
                            buffer = buffer.delete(0, buffer.length());
                        } else {
                            buffer = buffer.delete(secondLast, lastNl);
                        }
                    }
                    System.out.println("[Última línea eliminada]");
                }
            }
            else {
                buffer.append(linea).append("\n");
            }
        }
    }
}
