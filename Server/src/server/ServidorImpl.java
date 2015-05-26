/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author lucas
 */
public class ServidorImpl extends UnicastRemoteObject implements Servidor{
    
    private List<Arquivo> arquivos;
    
    public ServidorImpl()throws RemoteException{
        super();
        popular();
    }

    @Override
    public String read(String nomeArq, int numLinha, int qtdLinhas) throws RemoteException, FileNotFoundException {
        Arquivo arquivo = buscarArquivo(nomeArq);
        arquivo.getControle().acquireReadLock();
        String conteudoLido = arquivo.read(nomeArq, numLinha, qtdLinhas);
        arquivo.getControle().releaseReadLock(); 
        return conteudoLido;
    }

    @Override
    public void write(String nomeArq, Collection<String> conteudo) throws RemoteException, FileNotFoundException {
        Arquivo arquivo = buscarArquivo(nomeArq);
        arquivo.getControle().acquireWriteLock();
        arquivo.write();
        arquivo.getControle().releaseWriteLock();
        
    }

    private Arquivo buscarArquivo(String nomeArq) throws FileNotFoundException {
        for (Iterator<Arquivo> iterator = arquivos.iterator(); iterator.hasNext();) {
            Arquivo arquivo = iterator.next();
            if(arquivo.getNome().equals(nomeArq)){
                return arquivo;
            }
        }
        throw new FileNotFoundException("Arquivo nao existe");
    }

    private void popular(){
        Arquivo a1 = new Arquivo("caminhoarquivo1", new ControleConcorrencia());
        Arquivo a2 = new Arquivo("caminhoarquivo2", new ControleConcorrencia());
        Arquivo a3 = new Arquivo("caminhoarquivo3", new ControleConcorrencia());
        arquivos = Arrays.asList(a1, a2, a3);
        
    }
     
}
