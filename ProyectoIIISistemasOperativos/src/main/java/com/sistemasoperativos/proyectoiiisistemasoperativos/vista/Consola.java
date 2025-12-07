/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.vista;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author andre
 */
public class Consola {
    private final List<Comando> Comandos;
    private final Scanner Entrada;
    
    public Consola(List<Comando> comandos){
        Comandos = comandos;
        Entrada = new Scanner(System.in);
    }
    
    public void IniciarSistemaArchivos(String ruta) throws Exception{
        File archivo = new File(ruta);
        if(ruta.equals("") || !archivo.exists()){
            Comando comando = BuscarComando("aformat");
            comando.EjecutarComando(ruta);
            return;
        }
        Comando comando = BuscarComando("loadfs");
        if(comando == null)
            throw new Exception("\nNo se encontró el comando para iniciar sesión.");
        comando.EjecutarComando("loadfs " + ruta);
    }
    
    public void Login() throws Exception{
        Comando comando = BuscarComando("login");
        if(comando == null)
            throw new Exception("\nNo se encontró el comando para iniciar sesión.");
        comando.EjecutarComando("login");
    }
    
    public void LeerTexto() throws Exception{
        Comando currentUser = BuscarComandoCurrentUser();
        while(true){
            try{
                currentUser.EjecutarComando("currentuser");
                String entrada = Entrada.nextLine();
                String comandoString = ExtraerComando(entrada);
                Comando comando = BuscarComando(comandoString);
                comando.EjecutarComando(entrada);
            }
            catch(Exception e){
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public Comando BuscarComandoCurrentUser() throws Exception{
        Comando comando = BuscarComando("currentuser");
        if(comando.CompararComando("currentuser"))
            return comando;
        throw new Exception("No existe el comando que indica la máquina y usuario actual");
    }
    
    private Comando BuscarComando(String comandoString) throws Exception{
        for(Comando comando: Comandos){
            if(comando.CompararComando(comandoString)){
                return comando;
            }
        }
        throw new Exception("No existe el comando " + comandoString);
    }
    
    private String ExtraerComando(String comando){
        String[] palabras = comando.split(" ");
        return palabras[0];
    }
}
