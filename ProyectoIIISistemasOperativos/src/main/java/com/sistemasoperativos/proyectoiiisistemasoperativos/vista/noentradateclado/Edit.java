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
        System.out.println("Guardar: Ctrl+S   |   Salir: Ctrl+X");
        System.out.println("-------------------------------------");

        // Mostrar contenido actual
        if (contenido != null && !contenido.isEmpty()) {
            System.out.println(contenido);
            buffer.append(contenido);
        }

        while (true) {
            int c = System.in.read();

            if (c == -1) continue;

            if (c == 19) { // Ctrl+S
                System.out.println("\n[Guardado temporal]\n");
            }

            else if (c == 24) { // Ctrl+X
                System.out.println("\n[Editor cerrado]");
                return buffer.toString();
            }

            else {
                // Caracter imprimible
                if (c >= 32 && c <= 126) {
                    System.out.print((char) c);
                    buffer.append((char) c);
                }

                // Enter
                else if (c == '\n' || c == '\r') {
                    System.out.println();
                    buffer.append('\n');
                }
            }
        }
    }
    
}
