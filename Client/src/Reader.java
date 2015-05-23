
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmi.RWLock;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Daian
 */
public class Reader implements Runnable {
    private boolean running = true;
    private RWLock db;
    private final String arquivo;
    public Reader(String arq){
        this.arquivo = arq;
    }
    public static void main(String[] args) {
        
        //to criando 2 threads pra cada arquivo
        for (int i = 0; i < 2; i++) {
            (new Thread(new Reader("arquivo1.txt"))).start();
            (new Thread(new Reader("arquivo2.txt"))).start();
            (new Thread(new Reader("arquivo3.txt"))).start();
            
        }


    }

    private void connectServer() {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            this.db = (RWLock) reg.lookup("server");
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
            try {
                //o cliente pede lock de leitura pro arquivo especifico
                db.acquireReadLock(this.arquivo);
                //faz a leitura
                String text = db.read(this.arquivo, 0, 2);
                System.out.println(Thread.currentThread() + text);
                //e libera o lock de leitura, 
                //lembrando q lock de leitura nao impossibilita dois lerem ao mesmo tempo.. 
                //é só olhar como é implementado no servidor.
                db.releaseReadLock(this.arquivo);               
                
                running = false;
            } catch (RemoteException ex) {
            }

        }
    }

}
