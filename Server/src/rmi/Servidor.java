/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;


public interface Servidor extends Remote{
    
    String read(String nomeArq, int numLinha, int qtdLinhas) throws RemoteException, FileNotFoundException;
    void write(String nomeArq, List<String> conteudo) throws RemoteException, FileNotFoundException;
    
}
