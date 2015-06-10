package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import server.Servidor;



public class Escritor implements Runnable {
    private boolean running = true;
    private Servidor servidor;
    private final String arquivo;  

    public Escritor(String arq) {
        this.arquivo = arq;
        inicializaEscrita();            
    }
    
    
    private void inicializaEscrita(){
        new Thread(this).start();
    }
    
    @Override
    public void run() {
        //primeira coisa q a thread faz quando inicia é conectar com o servidor
        connectServer();

        List<String> conteudo = new ArrayList();
        conteudo.add("ThreadId: " + Thread.currentThread().getId());

        try {
            servidor.write(this.arquivo, conteudo);
            System.out.println(Thread.currentThread().getId() + ": ESCREVEU " + conteudo + " no " + this.arquivo);

        } catch (RemoteException ex) {
            System.out.println("Problema no acesso remoto");
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não encontrado");
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }

    }
    
    private void connectServer() {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            this.servidor = (Servidor) reg.lookup("RWAPI");
            System.out.println(Thread.currentThread().getId() + ": conectou para escrita em " + this.arquivo);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
