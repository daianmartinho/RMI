/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concorrencia;

import java.rmi.RemoteException;
import java.util.concurrent.Semaphore;

/**
 *
 * @author lucas
 */
public class ControladorConcorrenciaPrioridadeLeitor implements ControladorConcorrencia{
    
    private int numeroLeitores;
    private Semaphore mutex;
    private Semaphore acessoArquivo;

    public ControladorConcorrenciaPrioridadeLeitor() {
        mutex = new Semaphore(1);
        acessoArquivo = new Semaphore(1);
    }
    
    

    @Override
    public void acquireReadLock() throws RemoteException, InterruptedException {
        //pede lock pra alterar qtd de leitores no arquivo especifico.               
        mutex.acquire();

        ++numeroLeitores;            
        //se sou o primeiro leitor, diz a todos os outros
        //que o arquivo está sendo lido
        if (numeroLeitores == 1) {
            acessoArquivo.acquire();
        }
        mutex.release();
    }

    @Override
    public void acquireWriteLock() throws RemoteException, InterruptedException {
       acessoArquivo.acquire();
    }

    @Override
    public void releaseWriteLock() throws RemoteException, InterruptedException {
        acessoArquivo.release();
    }

    @Override
    public void releaseReadLock() throws RemoteException, InterruptedException {
        mutex.acquire();
        --numeroLeitores;
        //se sou o último leitor, diz a todos os outros
        //que o arquivo não está mais sendo lido
        if (numeroLeitores == 0) {
            acessoArquivo.release();
        }
        mutex.release();
    }

    
    
}
