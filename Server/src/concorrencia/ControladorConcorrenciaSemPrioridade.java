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
    private final Semaphore ordem = new Semaphore(1, true); //true para garantir fairness
    private int numeroLeitores;

    @Override
    public void acquireReadLock() throws RemoteException, InterruptedException {
        ordem.acquire(); // marcar ordem de chegada
        System.out.println("Servidor: " + Thread.currentThread().getId() + " pediu para read");
        mutexLeitura.acquire(); //vamos manipular a variavel 'numeroLeitores'
        numeroLeitores++;
        if(numeroLeitores == 1){ // se sou o primeiro leitor, peco acesso exclusivo
            acesso.acquire();
        }
        
        ordem.release(); 
        mutexLeitura.release();
    }

    @Override
    public void acquireWriteLock() throws RemoteException, InterruptedException {
        ordem.acquire(); // marcar ordem de chegada
        System.out.println("Servidor: " + Thread.currentThread().getId() + " pediu para write");
        acesso.acquire(); // pedir acesso exclusivo
        ordem.release();
    }

    @Override
    public void releaseWriteLock() throws RemoteException, InterruptedException {
        acesso.release();
    }

    @Override
    public void releaseReadLock() throws RemoteException, InterruptedException {
        mutexLeitura.acquire(); //vamos manipular a variavel 'numeroLeitores'
        numeroLeitores--;
        if(numeroLeitores == 0){ //se sou o ultimo leitor, libero o acesso excluivo
            acesso.release();
        }
        mutexLeitura.release();
    }

    
    
}
