/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concorrencia;

import java.rmi.RemoteException;

/**
 *
 * @author lucas
 */
public class ControladorConcorrenciaSemPrioridade implements ControladorConcorrencia{

    @Override
    public void acquireReadLock() throws RemoteException, InterruptedException {
        
    }

    @Override
    public void acquireWriteLock() throws RemoteException, InterruptedException {
        
    }

    @Override
    public void releaseWriteLock() throws RemoteException, InterruptedException {
        
    }

    @Override
    public void releaseReadLock() throws RemoteException, InterruptedException {
        
    }

    
    
}
