
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Servidor;


public class Leitor implements Runnable {

    private boolean running = true;
    private Servidor db;
    private final String arquivo;
    private final int linhaInicio;
    private final int qtdLinhasLidas;

    
    /*----------------------------------------------------------------------------------------------------
    ------------------------------------ BLOCO PARA USO DE THREADS ---------------------------------------
    -----------------------------------------------------------------------------------------------------*/
    
    /*
        ESSE CONSTRUTOR É PARA O CASO DE THREADS NO CLIENTE.
        SE QUISER O CLIENTE COM THREADS É SÓ DESCOMENTAR ESSE CONSTRUTOR E COMENTAR O OUTRO.
        E VICE VERSA.
    */
    public Leitor(String nomeArq, int inicio, int qtdLinhas) {
        
        // RECEBE PARÂMETROS PARA LEITURA
        this.arquivo = nomeArq;
        this.linhaInicio = inicio;
        this.qtdLinhasLidas = qtdLinhas;
        
        // INICIALIZA THREAD DE LEITURA
        inicializaLeitura();
    }

    
    /*
        MÉTODO QUE CRIA A THREAD NO CASO DE EXECUÇÃO COM THREADS.
    */
    private void inicializaLeitura(){
        new Thread(this).start();
    }
    

    @Override
    public void run() {
        //primeira coisa q a thread faz quando inicia é conectar com o servidor
        connectServer();

        while (running) {
            List<String> text;
            try {

                text = db.read(this.arquivo, linhaInicio, qtdLinhasLidas);
                System.out.println(Thread.currentThread().getName() +" \nLido do arquivo "+this.arquivo + ":\n"+ text);


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
        OU SEJA, SÓ RETORNA PARA O MENU QUANDO A LEITURA TIVER ACABADO.
        PARA USAR O CLIENTE COM THREADS COMENTA ESSA CONSTRUTOR E DESCOMENTA O OUTRO.
    */
    /*public Leitor(String nomeArq, int inicio, int qtdLinhas) {
        
        // RECEBE PARÂMETROS PARA LEITURA
        this.arquivo = nomeArq;
        this.linhaInicio = inicio;
        this.qtdLinhasLidas = qtdLinhas;
        
        connectServer();
        
        List<String> text;
        try {
            
            text = db.read(this.arquivo, linhaInicio, qtdLinhasLidas);
            System.out.println("Lido do arquivo " +this.arquivo+ ":\n"+ text);

        } catch (RemoteException ex) {
            System.out.println("Problema no acesso remoto");
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não encontrado");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }*/
    
    
    /*----------------------------------------------------------------------------------------------------
    ----------------------------------- FIM DO BLOCO SEM THREADS -----------------------------------------
    -----------------------------------------------------------------------------------------------------*/
    
    
    

    private void connectServer() {
        
        try {
            
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            this.db = (Servidor) reg.lookup("RWAPI");
            System.out.println("Conectou ao servidor para leitura do arquivo " + this.arquivo);
            
        } catch (Exception e) {
            
            System.out.println(e);
            
        }
    
    }


}
