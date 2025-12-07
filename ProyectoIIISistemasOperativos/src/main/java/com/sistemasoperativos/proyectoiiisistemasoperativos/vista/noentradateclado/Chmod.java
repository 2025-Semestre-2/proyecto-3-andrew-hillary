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
public class Chmod extends ComandoPadre {
    private int PermisosUsuario;
    private int PermisosGrupo;
    private String NombreArchivo;
    
    public Chmod(String comando, Controlador controlador){
        super(comando, controlador);
    }
    
    @Override
    public void EjecutarComando(String comando) throws Exception {
        if(ContarTamanoComando(comando) != 2){
            throw new Exception("Chmod esperaba 2 argumentos, en cambio recibió " + ContarTamanoComando(comando) + " argumentos.");
        }
        ExtraerParametrosActuales(comando);
        String respuesta = ControladorAsignado.Chmod(NombreArchivo, PermisosUsuario, PermisosGrupo);
        System.out.println(respuesta);
    }
    
    private void ExtraerParametrosActuales(String comando) throws Exception{
        List<String> parametros = ExtraerParametros(comando);
        ExtaerPermisos(parametros.get(0));
        NombreArchivo = parametros.get(1);
    }
    
    private void ExtaerPermisos(String permisos) throws Exception{
        if(permisos.length() != 2)
            throw new Exception("Deben haber 2 números en los permisos, en cambio se recibieron " + permisos.length() + " permisos.");
        String permisosEntero = permisos.substring(0, 1);
        PermisosUsuario = Integer.parseInt(permisosEntero);
        if(PermisosUsuario < 0 || PermisosUsuario > 7)
            throw new Exception("Los permisos del usuario deben estar entre 0 y 7, en cambio está en " + PermisosUsuario);
        permisosEntero = permisos.substring(1, 2);
        PermisosGrupo = Integer.parseInt(permisosEntero);
        if(PermisosGrupo < 0 || PermisosGrupo > 7)
            throw new Exception("Los permisos del grupo deben estar entre 0 y 7, en cambio está en " + PermisosGrupo);
    }
}
