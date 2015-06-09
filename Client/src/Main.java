
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Servidor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lucas
 */
public class Main {
    
    private static final String NOME_ARQUIVO_1 = "/home/lucas/Documents/teste.txt";
    private static final String NOME_ARQUIVO_2 = "/home/lucas/Documents/teste2.txt";
    private static final String NOME_ARQUIVO_3 = "/home/lucas/Documents/teste3.txt";
    private static final List<String> conteudo = Arrays.asList("1", "2", "3");
    
    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException, FileNotFoundException {
        Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
        final Servidor servidor = (Servidor) reg.lookup("RWAPI");
        System.out.println("Cliente conectado");
        
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    try {
                        servidor.write(NOME_ARQUIVO_1, conteudo);
                        servidor.read(NOME_ARQUIVO_1, 1, 3);
                    } catch (RemoteException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }.start();
            
        }
    }
    
}
