
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
    private final List<String> conteudo;

<<<<<<< HEAD
    /*----------------------------------------------------------------------------------------------------
    ------------------------------------ BLOCO PARA USO DE THREADS ---------------------------------------
    -----------------------------------------------------------------------------------------------------*/
    
    public Escritor(String arq, String c) {
        
=======
    public static void main(String[] args) {
        //define qtd de threads
        for (int i = 0; i < 6; i++) {
            (new Thread(new Escritor("arquivo1.txt"))).start();
            //(new Thread(new Escritor("arquivo1.txt"))).start();
            //(new Thread(new Reader("arquivo3.txt"))).start();
        }
    }

    public Escritor(String arq) {
>>>>>>> origin/master
        this.arquivo = arq;
        this.conteudo = new ArrayList();
        this.conteudo.add(c);
    
        // INICIALIZA THREAD DE ESCRITA
        inicializaEscrita();
    }
<<<<<<< HEAD
    
    private void inicializaEscrita(){
        new Thread(this).start();
=======
    private void connectServer() {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            this.db = (Servidor) reg.lookup("RWAPI");
            System.out.println(Thread.currentThread().getId() + " conectou no " + this.arquivo);

        } catch (Exception e) {
            System.out.println(e);
        }
>>>>>>> origin/master
    }
    
    @Override
    public void run() {
    //primeira coisa q a thread faz quando inicia é conectar com o servidor
        connectServer();

        while (running) {
            
<<<<<<< HEAD
            //List<String> conteudo = new ArrayList();
            //conteudo.add(""+Thread.currentThread());
=======
            List<String> conteudo = new ArrayList();
            //adicionei um valor qualquer na lista, só pra ter oq escrever no arquivo.
            conteudo.add(""+Thread.currentThread().getId());
>>>>>>> origin/master
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
            System.out.println(Thread.currentThread() + "conectou ao servidor para escrita no arquivo " + this.arquivo);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
