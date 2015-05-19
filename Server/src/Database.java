
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
public class Database extends UnicastRemoteObject implements RWLock{
    private int readerCount;
    private Semaphore mutex;
    private Semaphore db;
    private RandomAccessFile raf;
    public Database() throws RemoteException{
        super();
        readerCount=0;
        mutex = new Semaphore(1);
        db = new Semaphore(1);
               
    }
    
    public static void main(String[] args) {
        try{
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("server", new Database());
            System.out.println("server started");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    @Override
    public void acquireReadLock() throws RemoteException {
        try {
            mutex.acquire();
            ++readerCount;
            
            //se sou o primeiro leitor, diz a todos os outros
            //que o banco de dados está sendo lido
            if(readerCount==1){
                db.acquire();
            }
            mutex.release();
            
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        
    }

    @Override
    public void acquireWriteLock() throws RemoteException {
        try{
            db.acquire();
        }catch(InterruptedException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void releaseReadLock() throws RemoteException {
        try {
            mutex.acquire();
            --readerCount;
            
            //se sou o último leitor, diz a todos os outros
            //que o banco de dados não está mais sendo lido
            if(readerCount==0){
                db.release();
            }
            mutex.release();
            
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void releaseWriteLock() throws RemoteException {
        db.release();
    }

    @Override
    public String read(String nomeArq, int numLinha, int qtdLinhas) throws RemoteException {
        String text = "";
        try {
            raf = new RandomAccessFile(nomeArq,"r");
            raf.seek(numLinha);
            
            while(qtdLinhas!=0){
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
