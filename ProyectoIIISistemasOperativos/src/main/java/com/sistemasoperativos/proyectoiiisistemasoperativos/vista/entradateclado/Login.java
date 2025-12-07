/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.vista.entradateclado;

import com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.Controlador;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.ComandoEntradaTeclado;

/**
 *
 * @author andre
 */
public class Login extends ComandoEntradaTeclado{
    private String Usuario;
    private String Contrasena;
    
    public Login(String comando, Controlador controlador){
        super(comando, controlador);
    }

    @Override
    public void EjecutarComando(String comando) throws Exception {
        if(ContarTamanoComando(comando) != 0){
            throw new Exception("Login esperaba 0 argumentos, en cambio recibió " + ContarTamanoComando(comando) + " argumentos.");
        }
        Usuario = "";
        Contrasena = "";
        SolicitarUsuario();
        SolicitarContrasena();
        String respuesta = ControladorAsignado.Login(Usuario, Contrasena);
        System.out.println("\n" + respuesta);
    }
    
    private void SolicitarUsuario(){
        String nombre = "";
        while(nombre.equals("")){
            System.out.print("Escriba el nombre completo del usuario: ");
            nombre = Entrada.nextLine();
            if(nombre.equals("")){
                System.err.println("Debe escribir un nombre");
            }
        }
        Usuario = nombre;
    }
    
    private void SolicitarContrasena(){
        String contrasena = "";
        while(contrasena.equals("")){
            System.out.print("Ingrese la contraseña del usuario: ");
            contrasena = Entrada.nextLine();
            if(contrasena.equals("")){
                System.err.println("Debe escribir una contraseña");
                continue;
            }
            Contrasena = contrasena;
        }
    }
}
