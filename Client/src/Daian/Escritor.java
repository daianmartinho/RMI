package Daian;



import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Servidor;



public class Escritor implements Runnable {
    private boolean running = true;
    private Servidor db;
    private final String arquivo;  

    public static void main(String[] args) {
        new Daian.Escritor("arquivo1.txt");
    }
    public Escritor(String arq) {
        
        this.arquivo = arq;
        //for (int i = 0; i < 5; i++) {
            inicializaEscrita();            
        //}
        
    }
    
    
    private void inicializaEscrita(){
        
        new Thread(this).start();

    }
    
    @Override
    public void run() {
    //primeira coisa q a thread faz quando inicia é conectar com o servidor
        connectServer();

        while (running) {
          
            List<String> conteudo = new ArrayList();
            conteudo.add(""+Thread.currentThread().getId());

            try {
                db.write(this.arquivo, conteudo);
                System.out.println(Thread.currentThread().getId() + ": ESCREVEU "+conteudo+" no "+this.arquivo);

            } catch (RemoteException ex) {
                System.out.println("Problema no acesso remoto");
            } catch (FileNotFoundException ex) {
                System.out.println("Arquivo não encontrado");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            running = false;

        }    
    }   
    
    private void connectServer() {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            this.db = (Servidor) reg.lookup("RWAPI");
            System.out.println(Thread.currentThread().getId() + ": conectou para escrita em " + this.arquivo);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
