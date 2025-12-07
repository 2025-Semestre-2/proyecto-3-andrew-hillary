/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.vista.entradateclado;

import com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.Controlador;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.ComandoEntradaTeclado;

/**
 *
 * @author andrewdeni
 */
public class AutomaticFormat extends ComandoEntradaTeclado {
    private String NombreArchivo;
    private int TamanoDisco;
    private int TamanoBloque;
    private String Contrasena;
    
    public AutomaticFormat(String comando, Controlador controlador){
        super(comando, controlador);
    }

    @Override
    public void EjecutarComando(String comando) throws Exception {
        System.out.println("\n\nCreando el sistema de archivos miDiscoDuro.fs");
        NombreArchivo = comando;
        SolicitarTamanoDisco();
        SolicitarTamanoBloque();
        SolicitarContrasena();
        String mensaje = ControladorAsignado.Format(NombreArchivo, TamanoDisco, TamanoBloque, Contrasena);
        System.out.println(mensaje);
        System.out.println("El nombre del usuario creado es 'root'");
    }
    
    private void SolicitarTamanoDisco(){
        String tamano = "";
        while(tamano.equals("")){
            System.out.print("Ingrese el tamano del disco: ");
            tamano = Entrada.nextLine();
            if(!tamano.matches("[0-9]+")){
                System.err.println("El tamano solo puede tener numeros enteros\n");
                tamano = "";
                continue;
            }
            int numeroEntero = Integer.parseInt(tamano);
            if(numeroEntero < 1){
                System.err.println("El tamano debe ser igual o mayor que 1\n");
                tamano = "";
                continue;
            }
            TamanoDisco = numeroEntero;
        }
    }
    
    private void SolicitarTamanoBloque(){
        String tamano = "";
        while(tamano.equals("")){
            System.out.print("Ingrese el tamano del bloque del sistema de archivos: ");
            tamano = Entrada.nextLine();
            if(!tamano.matches("[0-9]+")){
                System.err.println("El tamano solo puede tener numeros enteros\n");
                tamano = "";
                continue;
            }
            int numeroEntero = Integer.parseInt(tamano);
            if(numeroEntero < 1){
                System.err.println("El tamano debe ser igual o mayor que 1\n");
                tamano = "";
                continue;
            }
            TamanoBloque = numeroEntero;
        }
    }
    
    private void SolicitarContrasena(){
        String contrasena = "";
        while(contrasena.equals("")){
            System.out.print("Ingrese la contraseña del usuario root: ");
            contrasena = Entrada.nextLine();
            if(!contrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,}$")){
                System.err.println("""
                                   La contrasena debe contener lo siguiente:
                                   \tAl menos una minúscula
                                   \tAl menos una mayúscula
                                   \tAl menos un número
                                   \tMínimo 6 caracteres\n
                                   """);
                contrasena = "";
                continue;
            }
            System.out.print("Ingrese nuevamente la misma contraseña: ");
            String confirmacionContrasena = Entrada.nextLine();
            if(!contrasena.equals(confirmacionContrasena)){
                System.err.println("Las contraseñas no coinciden\n");
                contrasena = "";
                continue;
            }
            Contrasena = contrasena;
        }
    }
}
