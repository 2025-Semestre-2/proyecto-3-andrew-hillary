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
public class Passwd extends ComandoEntradaTeclado{
    private String NombreUsuario;
    private String Contrasena;
    
    public Passwd(String comando, Controlador controlador){
        super(comando, controlador);
    }

    @Override
    public void EjecutarComando(String comando) throws Exception {
        if(ContarTamanoComando(comando) != 1){
            throw new Exception("Passwd esperaba 1 argumentos, en cambio recibió " + ContarTamanoComando(comando) + " argumentos.");
        }
        ExtraerNombreUsuario(comando);
        SolicitarContrasena();
        String respuesta = ControladorAsignado.Passwd(NombreUsuario, Contrasena);
        System.out.println("\n" + respuesta);
    }
    
    private void SolicitarContrasena(){
        String contrasena = "";
        while(contrasena.equals("")){
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
    
    private void ExtraerNombreUsuario(String comando){
        List<String> parametros = ExtraerParametros(comando);
        NombreUsuario = parametros.get(0);
    }
}
