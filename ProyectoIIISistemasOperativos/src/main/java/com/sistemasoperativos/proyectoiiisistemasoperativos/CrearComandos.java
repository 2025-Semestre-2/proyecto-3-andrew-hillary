package com.sistemasoperativos.proyectoiiisistemasoperativos;

import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.CloseFile;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.Touch;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.PWD;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.MV;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.Exit;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.Chmod;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.LoadFileSystem;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.InfoFS;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.OpenFile;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.GroupAdd;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.LS;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.Cat;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.Clear;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.CD;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.WhereIs;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.Chgrp;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.Edit;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.Whoami;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.CurrentUser;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.RM;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.ViewFCB;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.MkDir;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.noentradateclado.Chown;
import com.sistemasoperativos.proyectoiiisistemasoperativos.controlador.Controlador;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.Comando;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.entradateclado.AutomaticFormat;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.entradateclado.Format;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.entradateclado.Login;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.entradateclado.Passwd;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.entradateclado.Su;
import com.sistemasoperativos.proyectoiiisistemasoperativos.vista.entradateclado.UserAdd;

import java.util.ArrayList;

public class CrearComandos {

    /**
     * Crea todos los comandos del sistema.
     * @param controlador instancia del Controlador asignado al Shell
     * @return lista de comandos disponibles
     */
    public static ArrayList<Comando> crear(Controlador controlador) {

        ArrayList<Comando> comandos = new ArrayList<>();

        // Crear Clear una sola vez (Edit necesita uno)
        Clear clear = new Clear("clear", controlador);

        // === Comandos estándar ===
        comandos.add(new AutomaticFormat("aformat", controlador));
        comandos.add(new CD("cd", controlador));
        comandos.add(new Cat("cat", controlador));
        comandos.add(new Chgrp("chgrp", controlador));
        comandos.add(new Chmod("chmod", controlador));
        comandos.add(new Chown("chown", controlador));
        comandos.add(new CloseFile("closefile", controlador));
        comandos.add(new CurrentUser("currentuser", controlador));
        comandos.add(new Exit("exit", controlador));
        comandos.add(new Format("format", controlador));
        comandos.add(new GroupAdd("groupadd", controlador));
        comandos.add(new InfoFS("infofs", controlador));
        comandos.add(new Login("login", controlador));
        comandos.add(new LS("ls", controlador));
        comandos.add(new LoadFileSystem("loadfs", controlador));
        comandos.add(new MV("mv", controlador));
        comandos.add(new MkDir("mkdir", controlador));
        comandos.add(new OpenFile("openfile", controlador));
        comandos.add(new Passwd("passwd", controlador));
        comandos.add(new PWD("pwd", controlador));
        comandos.add(new RM("rm", controlador));
        comandos.add(new Su("su", controlador));
        comandos.add(new Touch("touch", controlador));
        comandos.add(new UserAdd("useradd", controlador));
        comandos.add(new ViewFCB("viewfcb", controlador));
        comandos.add(new WhereIs("whereis", controlador));
        comandos.add(new Whoami("whoami", controlador));
        comandos.add(clear);

        // === Comando Edit (diferente constructor) ===
        comandos.add(new Edit("note", controlador, clear));

        return comandos;
    }
}
