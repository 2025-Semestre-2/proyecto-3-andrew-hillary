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
public class UserAdd extends ComandoEntradaTeclado{
    private String Usuario;
    private String NombreCompleto;
    private String Contrasena;
    
    public UserAdd(String comando, Controlador controlador){
        super(comando, controlador);
    }
    
    @Override
    public void EjecutarComando(String comando) throws Exception {
        if(ContarTamanoComando(comando) != 1){
            throw new Exception("UserAdd esperaba 1 argumentos, en cambio recibió " + ContarTamanoComando(comando) + " argumentos.");
        }
        ExtraerNombreUsuario(comando);
        SolicitarNombreCompleto();
        SolicitarContrasena();
        String respuesta = ControladorAsignado.UserAdd(Usuario, NombreCompleto, Contrasena);
        System.out.println("\n" + respuesta);
    }
    
    private void ExtraerNombreUsuario(String comando){
        List<String> parametros = ExtraerParametros(comando);
        Usuario = parametros.get(0);
    }
    
    private void SolicitarNombreCompleto(){
        String nombre = "";
        while(nombre.equals("")){
            System.out.print("Escriba el nombre completo del usuario: ");
            nombre = Entrada.nextLine();
            if(!nombre.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ]+ [A-Za-zÁÉÍÓÚáéíóúÑñ]+ [A-Za-zÁÉÍÓÚáéíóúÑñ]*")){
                System.err.println("""
                                   El nombre es inválido.
                                   Debe contener exactamente dos o tres palabras separadas por un solo espacio.
                                   Cada palabra solo puede incluir letras de la 'A' a la 'Z' o de la 'a' a la 'z'.
                                   Ejemplo válido: Juan Carlos Pérez
                                   Ejemplo válido: Marcos Araya\n""");
                nombre = "";
            }
        }
        NombreCompleto = nombre;
    }
    
    private void SolicitarContrasena(){
        String contrasena = "";
        while(!contrasena.equals("")){
            System.out.print("Ingrese la contraseña del usuario: ");
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
