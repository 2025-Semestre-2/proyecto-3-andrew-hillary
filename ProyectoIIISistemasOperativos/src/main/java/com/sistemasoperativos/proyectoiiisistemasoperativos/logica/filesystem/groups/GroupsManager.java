/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.groups;

import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.datos.Group;
import com.sistemasoperativos.proyectoiiisistemasoperativos.logica.filesystem.DiskConnector;
import java.util.List;

/**
 *
 * @author andre
 */
public class GroupsManager {
    private static int Pointer;
    private static List<Group> Groups;
    
    public static String GroupAdd(String name) throws Exception{
        if(VerifyExistsGroup(name))
            throw new Exception("Ya existe un grupo con ese nombre");
        Integer id = Groups.size() + 1;
        Group group = new Group(id.toString(), name);
        byte[] groupSerialize = group.serialize();
        DiskConnector.WriteBlock(Pointer + (groupSerialize.length * Groups.size()), groupSerialize);
        Groups.add(group);
        return "Se ha creado el grupo exitosamente con el id " + id;
    }
    
    private static boolean VerifyExistsGroup(String name){
        for(Group group: Groups){
            if(group.getGroupName().equals(name)){
                return true;
            }
        }
        return false;
    }
    
    public static Group GetGroupByName(String name) throws Exception{
        for (Group group : Groups) {
            if(group.getGroupName().equals(name)){
                return group;
            }
        }
        throw new Exception("No se encontró el grupo");
    }
    
    public static Group GetGroupById(String id) throws Exception{
        for (Group group : Groups) {
            if(group.getGroupID().equals(id)){
                return group;
            }
        }
        throw new Exception("No se encontró el grupo");
    }
    
    public static String GetIDByName(String name) throws Exception{
        for (Group group : Groups) {
            if(group.getGroupName().equals(name)){
                return group.getGroupID();
            }
        }
        throw new Exception("No se encontró el ID");
    }

    public static int getPointer() {
        return Pointer;
    }

    public static void setPointer(int Pointer) {
        GroupsManager.Pointer = Pointer;
    }

    public static List<Group> getGroups() {
        return Groups;
    }

    public static void setGroups(List<Group> Groups) {
        GroupsManager.Groups = Groups;
    }
    
    
}
