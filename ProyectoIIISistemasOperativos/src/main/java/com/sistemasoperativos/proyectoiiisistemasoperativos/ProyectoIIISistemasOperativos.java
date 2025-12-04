/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.sistemasoperativos.proyectoiiisistemasoperativos;

import com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.Controlador;
import com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.ControladorFalso;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.Comando;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.Consola;
import java.util.List;

/**
 *
 * @author andre
 */
public class ProyectoIIISistemasOperativos {
    
    private static String Archivo = "";

    public static void main(String[] args) {
        if(args.length > 1){
            System.out.println("\n\nError: Solo puede ingresar 0 o 1 argumentos\n");
        }
        if(args.length == 1){
            Archivo = args[0];
        }
        Controlador controlador = new ControladorFalso();
        List<Comando> comandos = CrearComandos.crear(controlador);
        Consola consola = new Consola(comandos);
        try{
            consola.IniciarSistemaArchivos(Archivo);
            consola.Login();
            consola.LeerTexto();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
