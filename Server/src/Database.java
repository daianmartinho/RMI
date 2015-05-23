
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Database extends UnicastRemoteObject implements RWLock {

    //arquivo 1
    private int readerCountArquivo1;
    private Semaphore mutexArquivo1;
    private Semaphore dbArquivo1;

    //arquivo 2
    private int readerCountArquivo2;
    private Semaphore mutexArquivo2;
    private Semaphore dbArquivo2;

    //arquivo 3
    private int readerCountArquivo3;
    private Semaphore mutexArquivo3;
    private Semaphore dbArquivo3;

    private RandomAccessFile raf;

    public Database() throws RemoteException {
        super();
        readerCountArquivo1 = 0;
        mutexArquivo1 = new Semaphore(1);
        dbArquivo1 = new Semaphore(1);

        readerCountArquivo2 = 0;
        mutexArquivo2 = new Semaphore(1);
        dbArquivo2 = new Semaphore(1);

        readerCountArquivo3 = 0;
        mutexArquivo3 = new Semaphore(1);
        dbArquivo3 = new Semaphore(1);

    }

    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("server", new Database());
            System.out.println("server started");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void acquireReadLock(String arq) throws RemoteException {
        try {
            if (arq.equals("arquivo1.txt")) {
                //pede lock pra alterar qtd de leitores no arquivo especifico.               
                mutexArquivo1.acquire();
                ++readerCountArquivo1;

                //se sou o primeiro leitor, diz a todos os outros
                //que o arquivo está sendo lido
                if (readerCountArquivo1 == 1) {
                    dbArquivo1.acquire();
                }
                mutexArquivo1.release();

            } else if (arq.equals("arquivo2.txt")) {

                mutexArquivo2.acquire();
                ++readerCountArquivo2;

                //se sou o primeiro leitor, diz a todos os outros
                //que o arquivo está sendo lido
                if (readerCountArquivo2 == 1) {
                    dbArquivo2.acquire();
                }
                mutexArquivo2.release();

            } else if (arq.equals("arquivo3.txt")) {

                mutexArquivo3.acquire();
                ++readerCountArquivo3;

                //se sou o primeiro leitor, diz a todos os outros
                //que o arquivo está sendo lido
                if (readerCountArquivo3 == 1) {
                    dbArquivo3.acquire();
                }
                mutexArquivo3.release();

            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void acquireWriteLock(String arq) throws RemoteException {
        try {
            if (arq.equals("arquivo1.txt")) {
                dbArquivo1.acquire();
            } else if (arq.equals("arquivo2.txt")) {
                dbArquivo2.acquire();
            } else if (arq.equals("arquivo3.txt")) {
                dbArquivo3.acquire();
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void releaseReadLock(String arq) throws RemoteException {
        try {
            if (arq.equals("arquivo1.txt")) {
                mutexArquivo1.acquire();
                --readerCountArquivo1;
                //se sou o último leitor, diz a todos os outros
                //que o arquivo não está mais sendo lido
                if (readerCountArquivo1 == 0) {
                    dbArquivo1.release();
                }
                mutexArquivo1.release();
            } else if (arq.equals("arquivo2.txt")) {
                mutexArquivo2.acquire();
                --readerCountArquivo2;
                //se sou o último leitor, diz a todos os outros
                //que o arquivo não está mais sendo lido
                if (readerCountArquivo2 == 0) {
                    dbArquivo2.release();
                }
                mutexArquivo2.release();
            } else if (arq.equals("arquivo3.txt")) {
                mutexArquivo3.acquire();
                --readerCountArquivo3;
                //se sou o último leitor, diz a todos os outros
                //que o arquivo não está mais sendo lido
                if (readerCountArquivo3 == 0) {
                    dbArquivo3.release();
                }
                mutexArquivo3.release();
            }

        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void releaseWriteLock(String arq) throws RemoteException {
        if (arq.equals("arquivo1.txt")) {
            dbArquivo1.release();
        } else if (arq.equals("arquivo2.txt")) {
            dbArquivo2.release();
        } else if (arq.equals("arquivo3.txt")) {
            dbArquivo3.release();
        }
        
    }

    @Override
    public String read(String nomeArq, int numLinha, int qtdLinhas) throws RemoteException {
        String text = "";
        try {
            raf = new RandomAccessFile(nomeArq, "r");
            raf.seek(numLinha);

            while (qtdLinhas != 0) {
                text += raf.readLine() + "\n";
                qtdLinhas--;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Número de linha inválido");
        }
        return text;
    }

    @Override
    public void write() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
