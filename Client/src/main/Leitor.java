package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import server.Servidor;

public class Leitor implements Runnable {

    private boolean running = true;
    private Servidor servidor;
    private final String arquivo;
    private final int linhaInicio;
    private final int qtdLinhasLidas;

    public Leitor(String nomeArq, int inicio, int qtdLinhas) {

        this.arquivo = nomeArq;
        this.linhaInicio = inicio;
        this.qtdLinhasLidas = qtdLinhas;

        inicializaLeitura();
    }

    private void inicializaLeitura() {
        new Thread(this).start();
    }

    @Override
    public void run() {

        connectServer();

        while (running) {
            
            try {
                List<String> text = servidor.read(this.arquivo, linhaInicio, qtdLinhasLidas);
                System.out.println(Thread.currentThread().getId() + ": LEU " + text.toString() + " do " + this.arquivo);

            } catch (RemoteException ex) {
                System.out.println("Problema no acesso remoto");
            } catch (FileNotFoundException ex) {
                System.out.println("Arquivo não encontrado: " + this.arquivo);
            } catch (InterruptedException | IOException ex) {
                ex.printStackTrace();
            }

            running = false;

        }
    }

    private void connectServer() {

        try {

            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            this.servidor = (Servidor) reg.lookup("RWAPI");
            System.out.println(Thread.currentThread().getId() + ": conectou para leitura em " + this.arquivo);

        } catch (Exception e) {
            System.out.println(e);

        }

    }

}
