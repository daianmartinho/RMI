
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import server.Servidor;
import server.ServidorImpl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lucas
 */
public class ExemploDeChamadaMain {
    
    public static void main(String[] args) {
        try {
           Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
           Servidor servidor = (Servidor) registry.lookup("RWAPI");
           String nomeArquivo1 = "/home/lucas/Documents/teste.txt";
           List<String> conteudoLido  = servidor.read(nomeArquivo1, 1, 10);
           System.out.println(conteudoLido);
           
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
