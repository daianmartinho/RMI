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
 * @author lucas Referência:
 * https://cs.nmt.edu/~cs325/spring2013/Lec11-SynchronizationII.pdf Slide 9
 */
public class ControladorConcorrenciaPrioridadeEscritor implements ControladorConcorrencia {

    private final Semaphore acessoEscrita = new Semaphore(1);
    private final Semaphore acessoLeitura = new Semaphore(1);
    private final Semaphore setupLeitura = new Semaphore(1);
    private final Semaphore mutexLeitura = new Semaphore(1);
    private final Semaphore mutexEscrita = new Semaphore(1);
    
    private int numeroLeitores;
    private int numeroEscritores;

    @Override
    public void acquireReadLock() throws RemoteException, InterruptedException {
        setupLeitura.acquire();
        acessoLeitura.acquire();//Indica que um leitor esta tentando entrar
        mutexLeitura.acquire();
        numeroLeitores++;
        if (numeroLeitores == 1) {
            acessoEscrita.acquire(); //se sou o primeiro leitor, bloqueio o acesso para escrita
        }
        mutexLeitura.release();
        acessoLeitura.release();
        setupLeitura.release();
    }

    @Override
    public void acquireWriteLock() throws RemoteException, InterruptedException {
        mutexEscrita.acquire();
        numeroEscritores++;
        if (numeroEscritores == 1) {
            acessoLeitura.acquire(); //como vou escrever, bloqueio o acesso para leitura
        }
        mutexEscrita.release();
        acessoEscrita.acquire();
    }

    @Override
    public void releaseWriteLock() throws RemoteException, InterruptedException {
        acessoEscrita.release();
        mutexEscrita.acquire();
        numeroEscritores--;
        if (numeroEscritores == 0) {
            acessoLeitura.release(); //como mais ninguem esta escrevendo, libero o acesso para leitura
        }
        mutexEscrita.release();
    }

    @Override
    public void releaseReadLock() throws RemoteException, InterruptedException {
        mutexLeitura.acquire();
        numeroLeitores--;
        if (numeroLeitores == 0) {
            acessoEscrita.release(); //como mais ninguem esta lendo, libero o acesso para escrita
        }
        mutexLeitura.release();
    }

}
