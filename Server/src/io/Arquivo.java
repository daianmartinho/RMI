/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lucas
 */
public class Arquivo {

    private String nome;

    public Arquivo(String nome) {
        this.nome = nome;
    }

    public List<String> read(int inicio, int qtdLinhas) throws RemoteException {
        List<String> linhas = new ArrayList();
        long ponteiro = 2;//arquivo vazio tem 2 bytes não sei pq        
        int contaLinhas = 0;
        
        try {
            RandomAccessFile raf = new RandomAccessFile(nome, "r");
            //encontra linha            
            raf.seek(ponteiro); 
            while(inicio!=contaLinhas){
                raf.seek(ponteiro);//sempre reposicionar pois outra thread pode modificar o ponteiro durante a execução
                raf.readUTF();                
                contaLinhas++;               
                ponteiro = raf.getFilePointer();
            }            
                                  
            while (qtdLinhas != 0) {
                raf.seek(ponteiro);                 
                String linha = raf.readUTF();
                ponteiro = raf.getFilePointer();
                System.out.println(Thread.currentThread().getId()+" leu "+linha + "    ponteiro = " + ponteiro);
                linhas.add(linha);                
                qtdLinhas--;               

            }
            //if(controle.getNumeroLeitores()==1){
                raf.close();
            //}
        } catch (FileNotFoundException ex) {
            System.out.println("read: arquivo nao encontrado");
        } catch (IOException ex) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
        } 

        return linhas;
    }

    public void write(List<String> linhas) throws RemoteException {
        try {
            RandomAccessFile raf = new RandomAccessFile(nome, "rw");
            for (String linha : linhas) {
                //System.out.println(""+raf.length());
                long eof = raf.length(); //guardando o final do arquivo
                raf.seek(eof);               
                raf.writeUTF(linha);
                raf.seek(eof);//verificando o conteudo escrito
                System.out.println(""+raf.readUTF());
            }
            raf.close();

        } catch (FileNotFoundException ex) {
            System.out.println("write: arquivo nao encontrado");
        } catch (IOException ex) {
            System.out.println("write: Número de linha inválido");
        }
        System.out.println("Escrevendo no servidor..");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
