/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import rmi.Servidor;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Collection;
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
        //System.out.println("nº leitores em " + nomeArq + " = "+arquivo.getControle().getNumeroLeitores());
        arquivo.getControle().acquireReadLock();
        
        String conteudoLido = arquivo.read(numLinha, qtdLinhas);
        arquivo.getControle().releaseReadLock(); 
        return conteudoLido;
    }

    @Override
    public void write(String nomeArq, List<String> conteudo) throws RemoteException, FileNotFoundException {
        Arquivo arquivo = buscarArquivo(nomeArq);
        arquivo.getControle().acquireWriteLock();
        arquivo.write(conteudo);
        arquivo.getControle().releaseWriteLock();
        
    }

    private Arquivo buscarArquivo(String nomeArq) throws FileNotFoundException {
        for (Arquivo arquivo : arquivos) {
            if(arquivo.getNome().equals(nomeArq)){               
                return arquivo;
            }
        }        
        throw new FileNotFoundException();
    }

    private void popular(){
        Arquivo a1 = new Arquivo("arquivo1.txt", new ControleConcorrencia());
        Arquivo a2 = new Arquivo("arquivo2.txt", new ControleConcorrencia());
        Arquivo a3 = new Arquivo("arquivo3.txt", new ControleConcorrencia());
        arquivos = Arrays.asList(a1, a2, a3);
        
    }
     
}
