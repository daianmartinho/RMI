
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
    private Servidor db;
    private final String arquivo;
    private final List<String> conteudo;


    /*----------------------------------------------------------------------------------------------------
    ------------------------------------ BLOCO PARA USO DE THREADS ---------------------------------------
    -----------------------------------------------------------------------------------------------------*/
    
    public Escritor(String arq, String c) {
        
        this.arquivo = arq;
        this.conteudo = new ArrayList();
        this.conteudo.add(c);
    
        // INICIALIZA THREAD DE ESCRITA
        inicializaEscrita();
    }

    
    private void inicializaEscrita(){
        new Thread(this).start();

    }
    
    @Override
    public void run() {
    //primeira coisa q a thread faz quando inicia é conectar com o servidor
        connectServer();

        while (running) {
          
            //List<String> conteudo = new ArrayList();
            //conteudo.add(""+Thread.currentThread());

            try {
                db.write(this.arquivo, this.conteudo);
               // System.out.println(Thread.currentThread() + "\n"+ text);

            } catch (RemoteException ex) {
                System.out.println("Problema no acesso remoto");
            } catch (FileNotFoundException ex) {
                System.out.println("Arquivo não encontrado");
            } catch (InterruptedException | IOException ex) {
                ex.printStackTrace();
            }

            running = false;

        }    
    }
   
    
    /*----------------------------------------------------------------------------------------------------
    ----------------------------------- FIM DO BLOCO COM THREADS -----------------------------------------
    -----------------------------------------------------------------------------------------------------*/
    
    
    
    
    /*----------------------------------------------------------------------------------------------------
    ------------------------------------ BLOCO SEM USO DE THREADS ---------------------------------------
    -----------------------------------------------------------------------------------------------------*/
    
    /*
        ESSE CONSTRUTOR EXECUTA SEM THREADS. 
        OU SEJA, SÓ RETORNA PARA O MENU QUANDO A ESCRITA TIVER ACABADO.
        PARA USAR O CLIENTE COM THREADS COMENTA ESSA CONSTRUTOR E DESCOMENTA O OUTRO.
        E VICE VERSA.
    */
    /*public Escritor(String arq, String c) {
        
        // RECEBE PARÂMETROS PARA ESCRITA
        this.arquivo = arq;
        this.conteudo = new ArrayList();
        this.conteudo.add(c);
    
        connectServer();
        
        try {
                db.write(this.arquivo, this.conteudo);
               // System.out.println(Thread.currentThread() + "\n"+ text);

            } catch (RemoteException ex) {
                System.out.println("Problema no acesso remoto");
            } catch (FileNotFoundException ex) {
                System.out.println("Arquivo não encontrado");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

    }
    */
    
    
    /*----------------------------------------------------------------------------------------------------
    ----------------------------------- FIM DO BLOCO SEM THREADS -----------------------------------------
    -----------------------------------------------------------------------------------------------------*/
    
    
    
    private void connectServer() {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            this.db = (Servidor) reg.lookup("RWAPI");
            System.out.println(Thread.currentThread() + ": conectou ao servidor para escrita no arquivo " + this.arquivo);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
