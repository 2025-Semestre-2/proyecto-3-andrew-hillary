/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.entradateclado;

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
        System.out.println("\nIniciando sesión");
        while(true){
            Usuario = "";
            Contrasena = "";
            try{
                SolicitarUsuario();
                SolicitarContrasena();
                return;
            }
            catch(Exception exception){
                System.err.println(exception.getMessage());
            }
        }
    }
    
    public void SolicitarUsuario() throws Exception{
        while(Usuario.equals("")){
            System.out.print("\nEscriba el nombre del usuario: ");
            Usuario = Entrada.nextLine();
            if(Usuario.equals(""))
                throw new Exception("El nombre de usuario está vacío\n");
        }
    }
    
    public void SolicitarContrasena() throws Exception{
        while(Contrasena.equals("")){
            System.out.print("\nEscriba la contraseña del usuario: ");
            Usuario = Entrada.nextLine();
            if(Contrasena.equals(""))
                throw new Exception("La contraseña del usuario está vacío\n");
        }
    }
}
