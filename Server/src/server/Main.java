/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import rmi.Servidor;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Main {
    
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            Servidor servidor = new ServidorImpl();
            reg.rebind("RWAPI", servidor);
            System.out.println("server started");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
