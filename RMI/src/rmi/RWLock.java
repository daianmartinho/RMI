/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Daian
 */
public interface RWLock extends Remote {
    public abstract void acquireReadLock(String arq) throws RemoteException;
    public abstract void acquireWriteLock(String arq) throws RemoteException;
    public abstract void releaseReadLock(String arq) throws RemoteException;
    public abstract void releaseWriteLock(String arq) throws RemoteException;
    public abstract String read(String nomeArq, int numLinha, int qtdLinhas) throws RemoteException;
    public abstract void write() throws RemoteException;
    
}
