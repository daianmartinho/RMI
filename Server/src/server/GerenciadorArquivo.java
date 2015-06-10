/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import concorrencia.ControladorConcorrencia;
import io.Arquivo;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author lucas
 * 
 */
public class GerenciadorArquivo {
    
    private ControladorConcorrencia controlador;
    private Arquivo arquivo;

    public GerenciadorArquivo(ControladorConcorrencia controlador, Arquivo arquivo) {
        this.controlador = controlador;
        this.arquivo = arquivo;
    } 
    
    public List<String> read(int numeroLinha, int quantidadeDeLinhas) throws RemoteException, InterruptedException, IOException {
        controlador.acquireReadLock();
        List<String> conteudoLido = arquivo.read(numeroLinha, quantidadeDeLinhas);
        controlador.releaseReadLock();
        return conteudoLido;
    }
    
    public void write(List<String> conteudo) throws RemoteException, FileNotFoundException, InterruptedException, IOException {
        controlador.acquireWriteLock();
        arquivo.write(conteudo);
        controlador.releaseWriteLock();
    }

    public ControladorConcorrencia getControlador() {
        return controlador;
    }

    public void setControlador(ControladorConcorrencia controlador) {
        this.controlador = controlador;
    }

    public Arquivo getArquivo() {
        return arquivo;
    }

    public void setArquivo(Arquivo arquivo) {
        this.arquivo = arquivo;
    }
    
    
    
    
    
}
