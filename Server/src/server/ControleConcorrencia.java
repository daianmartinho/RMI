/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.rmi.RemoteException;
import java.util.concurrent.Semaphore;

/**
 *
 * @author lucas
 */
public class ControleConcorrencia {

    public ControleConcorrencia() {
        numeroLeitores = 0;
        mutex = new Semaphore(1);
        mutexAcessoArquivo = new Semaphore(1);

    }
    
    private int numeroLeitores;
    private Semaphore mutex;
    private Semaphore mutexAcessoArquivo;

    public void acquireReadLock() throws RemoteException {
        try {
            //pede lock pra alterar qtd de leitores no arquivo especifico.               
            mutex.acquire();
            
            ++numeroLeitores;            
            //se sou o primeiro leitor, diz a todos os outros
            //que o arquivo está sendo lido
            if (numeroLeitores == 1) {
                mutexAcessoArquivo.acquire();
            }
            mutex.release();


        } catch (InterruptedException ex) {
            System.out.println(ex);
        }

    }

    public void acquireWriteLock() throws RemoteException {
        try {
              mutexAcessoArquivo.acquire();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }

    }

    public void releaseReadLock() throws RemoteException {
        try {
            mutex.acquire();
            --numeroLeitores;
            //se sou o último leitor, diz a todos os outros
            //que o arquivo não está mais sendo lido
            if (numeroLeitores == 0) {
                mutexAcessoArquivo.release();
            }
            mutex.release();
          

        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    public void releaseWriteLock() throws RemoteException {
        mutexAcessoArquivo.release(); 
    }
    
    public int getNumeroLeitores(){
        return this.numeroLeitores;
    }
}
