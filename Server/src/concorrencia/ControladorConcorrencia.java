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
public interface ControladorConcorrencia {
    
    void acquireReadLock() throws RemoteException, InterruptedException;
    
    void acquireWriteLock() throws RemoteException, InterruptedException;
    
    void releaseWriteLock() throws RemoteException, InterruptedException;
    
    void releaseReadLock() throws RemoteException, InterruptedException;
    
    
}
