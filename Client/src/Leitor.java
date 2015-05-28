
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

    public Leitor(String arq) {
        this.arquivo = arq;
    }

    public static void main(String[] args) {
        //to criando 2 threads pra cada arquivo
        for (int i = 0; i < 6; i++) {
            (new Thread(new Leitor("arquivo1.txt"))).start();
            //(new Thread(new Reader("arquivo2.txt"))).start();
            //(new Thread(new Reader("arquivo3.txt"))).start();
        }
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

            List<String> text;
            try {
                text = db.read(this.arquivo, 0, 1);
                System.out.println(Thread.currentThread().getName() +" leitura:" + "\n"+ text);

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

}
