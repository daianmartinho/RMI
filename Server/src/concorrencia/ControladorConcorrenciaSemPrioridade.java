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
 * Referência: https://www.rfc1149.net/blog/2011/01/07/the-third-readers-writers-problem/
 */
public class ControladorConcorrenciaSemPrioridade implements ControladorConcorrencia{
    
    private final Semaphore acesso = new Semaphore(1);
    private final Semaphore mutexLeitura = new Semaphore(1);
    private final Semaphore ordem = new Semaphore(1);
    private int numeroLeitores;

    @Override
    public void acquireReadLock() throws RemoteException, InterruptedException {
        ordem.acquire();
        mutexLeitura.acquire();
        if(numeroLeitores == 1){
            acesso.acquire();
        }
        numeroLeitores++;
        ordem.release();
        mutexLeitura.release();
    }

    @Override
    public void acquireWriteLock() throws RemoteException, InterruptedException {
        ordem.acquire();
        acesso.acquire();
        ordem.release();
    }

    @Override
    public void releaseWriteLock() throws RemoteException, InterruptedException {
        acesso.release();
    }

    @Override
    public void releaseReadLock() throws RemoteException, InterruptedException {
        mutexLeitura.acquire();
        numeroLeitores--;
        if(numeroLeitores == 0){
            acesso.release();
        }
        mutexLeitura.release();
    }

    
    
}
