/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import concorrencia.ControladorConcorrenciaFactory;
import concorrencia.TipoPrioridade;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import server.Servidor;
import server.ServidorImpl;

/**
 *
 * @author lucas
 */
public class MainSemPrioriadade {
   public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            Servidor servidor = new ServidorImpl(TipoPrioridade.SEM_PRIORIDADE);
            reg.rebind("RWAPI", servidor);
            System.out.println("Servidor rodando");
            System.out.println("Tipo de prioridade: " + TipoPrioridade.SEM_PRIORIDADE);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
