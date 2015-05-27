
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import rmi.Servidor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daian
 */
public class Escritor implements Runnable {
    private boolean running = true;
    private Servidor db;
    private final String arquivo;

    public static void main(String[] args) {
        //to criando 2 threads pra cada arquivo
        for (int i = 0; i < 1; i++) {
            (new Thread(new Escritor("arquivo1.txt"))).start();
            //(new Thread(new Reader("arquivo2.txt"))).start();
            //(new Thread(new Reader("arquivo3.txt"))).start();
        }
    }

    public Escritor(String arq) {
        this.arquivo = arq;
    }
    private void connectServer() {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            this.db = (Servidor) reg.lookup("RWAPI");
            System.out.println(Thread.currentThread() + "conectou ao server no " + this.arquivo);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
    //primeira coisa q a thread faz quando inicia é conectar com o servidor
        connectServer();

        while (running) {
            
            List<String> conteudo = new ArrayList();
            conteudo.add("primeira linha arquivo 1");
            try {
                db.write(this.arquivo, conteudo);
               // System.out.println(Thread.currentThread() + "\n"+ text);

            } catch (RemoteException ex) {
                System.out.println("Problema no acesso remoto");
            } catch (FileNotFoundException ex) {
                System.out.println("Arquivo não encontrado");
            }

            running = false;

        }    
    }
}