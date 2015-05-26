/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 *
 * @author lucas
 */
public interface Servidor extends Remote{
    
    String read(String nomeArq, int numLinha, int qtdLinhas) throws RemoteException, FileNotFoundException;
    void write(String nomeArq, Collection<String> conteudo) throws RemoteException, FileNotFoundException;
    
}
