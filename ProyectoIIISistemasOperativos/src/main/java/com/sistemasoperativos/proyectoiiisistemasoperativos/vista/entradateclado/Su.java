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
public class Su extends ComandoEntradaTeclado{
    private String Usuario;
    private String Contrasena;
    
    public Su(String comando, Controlador controlador){
        super(comando, controlador);
    }

    @Override
    public void EjecutarComando(String comando) throws Exception {
        if(ContarTamanoComando(comando) != 1 && ContarTamanoComando(comando) != 0){
            throw new Exception("Su esperaba 0 o 1 argumentos, en cambio recibió " + ContarTamanoComando(comando) + " argumentos.");
        }
        ExtraerNombreUsuario(comando);
        SolicitarContrasena();
        String respuesta = ControladorAsignado.Su(Usuario, Contrasena);
        System.out.println("\n" + respuesta);
    }
    
    private void ExtraerNombreUsuario(String comando){
        if(ContarTamanoComando(comando) == 0){
            Usuario = "";
            return;
        }
        List<String> parametros = ExtraerParametros(comando);
        Usuario = parametros.get(0);
    }
    
    private void SolicitarContrasena(){
        String contrasena = "";
        while(contrasena.equals("")){
            System.out.print("Ingrese la contraseña del usuario: ");
            contrasena = Entrada.nextLine();
            if(contrasena.equals("")){
                System.err.println("Escriba una contraseña");
                continue;
            }
            Contrasena = contrasena;
        }
    }
}
