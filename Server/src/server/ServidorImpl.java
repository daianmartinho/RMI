/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import concorrencia.ControladorConcorrencia;
import concorrencia.ControladorConcorrenciaFactory;
import concorrencia.TipoPrioridade;
import io.Arquivo;
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
    
    private Collection<GerenciadorArquivo> gerenciadores;
    // ALTERE AQUI PARA O CAMINHO DOS SEUS ARQUIVOS
    private static final String NOME_ARQUIVO_1 = "arquivo0.txt";
    private static final String NOME_ARQUIVO_2 = "arquivo1.txt";
    private static final String NOME_ARQUIVO_3 = "arquivo2.txt";
    
    
    public ServidorImpl(TipoPrioridade tipoPrioridade) throws RemoteException{
        super();
        init(tipoPrioridade);
    }

    @Override
    public List<String> read(String nomeArq, int numLinha, int qtdLinhas) throws RemoteException, InterruptedException, FileNotFoundException {
        GerenciadorArquivo gerenciador = find(nomeArq);
        return gerenciador.read(numLinha, qtdLinhas); 
    }

    @Override
    public void write(String nomeArq, List<String> conteudo) throws RemoteException, InterruptedException, FileNotFoundException {
        GerenciadorArquivo gerenciador = find(nomeArq);
        gerenciador.write(conteudo);
    }
     
    private GerenciadorArquivo find(String nomeArq) throws FileNotFoundException {
        for(GerenciadorArquivo gerenciador: gerenciadores){
            if(gerenciador.getArquivo().getNome().equals(nomeArq)){
                return gerenciador;
            }
        }
        throw new FileNotFoundException("Arquivo nao encontrado");
    }

    /*
        Metodo auxiliar para iniciar uma lista com os arquivos, 
        e seus respectivos gerenciadores
    */
    private void init(TipoPrioridade tipoPrioridade){
        ControladorConcorrencia c1 = ControladorConcorrenciaFactory.create(tipoPrioridade);
        Arquivo a1 = new Arquivo(NOME_ARQUIVO_1);
        ControladorConcorrencia c2 = ControladorConcorrenciaFactory.create(tipoPrioridade);
        Arquivo a2 = new Arquivo(NOME_ARQUIVO_2);
        ControladorConcorrencia c3 = ControladorConcorrenciaFactory.create(tipoPrioridade);
        Arquivo a3 = new Arquivo(NOME_ARQUIVO_3);
        GerenciadorArquivo g1 = new GerenciadorArquivo(c1, a1);
        GerenciadorArquivo g2 = new GerenciadorArquivo(c2, a2);
        GerenciadorArquivo g3 = new GerenciadorArquivo(c3, a3);
        gerenciadores = Arrays.asList(g1, g2, g3);
        
    }
}
