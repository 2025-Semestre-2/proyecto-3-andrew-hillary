/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.vista.entradateclado;

import com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.Controlador;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.ComandoEntradaTeclado;
import java.util.List;

/**
 *
 * @author andre
 */
public class Format extends ComandoEntradaTeclado{
    
    private String NombreArchivo;
    private int TamanoDisco;
    private int TamanoBloque;
    private String Contrasena;
    
    public Format(String comando, Controlador controlador){
        super(comando, controlador);
    }

    @Override
    public void EjecutarComando(String comando) throws Exception {
        if(ContarTamanoComando(comando) != 0){
            throw new Exception("Format esperaba 0 argumentos, en cambio recibió " + ContarTamanoComando(comando) + " argumentos.");
        }
        System.out.println("Creando un nuevo Sistema de archivos");
        SolicitarNombre();
        SolicitarTamanoDisco();
        SolicitarTamanoBloque();
        SolicitarContrasena();
        String respuesta = ControladorAsignado.Format(NombreArchivo, TamanoDisco, TamanoBloque, Contrasena);
        System.out.println("\n" + respuesta);
    }
    
    private void SolicitarNombre(){
        String nombre = "";
        while(true){
            System.out.print("Escriba el nombre del sistema de archivos: ");
            nombre = Entrada.nextLine();
            if(nombre.equals(""))
                break;
            if(!nombre.matches("[A-Za-z0-9._-]+")){
                System.err.println("""
                                   El nombre solo puede contener los siguientes caracteres:
                                        Letras de la 'A' a la 'Z'
                                   \tLetras de la 'a' a la 'z'
                                   \tNúmeros del '0' al '9'\n""");
                nombre = "";
                continue;
            }
            break;
        }
        NombreArchivo = nombre;
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
