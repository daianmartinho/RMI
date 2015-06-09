package Daian;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import server.Servidor;

public class Leitor implements Runnable {

    private boolean running = true;
    private Servidor db;
    private final String arquivo;
    private final int linhaInicio;
    private final int qtdLinhasLidas;

    public static void main(String[] args) {
        new Daian.Leitor("arquivo1.txt", 0, 2);
    }

    public Leitor(String nomeArq, int inicio, int qtdLinhas) {

        // RECEBE PARÂMETROS PARA LEITURA
        this.arquivo = nomeArq;
        this.linhaInicio = inicio;
        this.qtdLinhasLidas = qtdLinhas;

        // INICIALIZA THREAD DE LEITURA
        //for (int i = 0; i < 5; i++) {
        inicializaLeitura();
        //}

    }

    /*
     MÉTODO QUE CRIA A THREAD NO CASO DE EXECUÇÃO COM THREADS.
     */
    private void inicializaLeitura() {
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
                System.out.println(Thread.currentThread().getId() + ": LEU " + text.toString() + " do " + this.arquivo);

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
            System.out.println(Thread.currentThread().getId() + ": conectou para leitura em " + this.arquivo);

        } catch (Exception e) {

            System.out.println(e);

        }

    }

}
