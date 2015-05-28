/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author lucas
 */
public interface Servidor extends Remote{
    List<String> read(String nomeArq, int numLinha, int qtdLinhas) throws RemoteException, InterruptedException, FileNotFoundException;
    void write(String nomeArq, List<String> conteudo) throws RemoteException, InterruptedException, FileNotFoundException;
}
