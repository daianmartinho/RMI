/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucas
 */
public class Arquivo {

    private String nome;

    public Arquivo(String nome) {
        this.nome = nome;
    }

    public List<String> read(Integer linhaInicial, Integer quantidadeDeLinhas) throws IOException {
        if (linhaInicial <= 0) {
            throw new IllegalArgumentException("Linha inicial deve ser maior que 0");
        }
        if (quantidadeDeLinhas <= 0) {
            throw new IllegalArgumentException("Numero de linhas deve ser maior que 0");
        }
        int linhaFinal = linhaInicial + quantidadeDeLinhas;
        
        BufferedReader br = getBufferedReader();

        List<String> retorno = new ArrayList<String>();

        int numeroLinha = 0;
        String linhaLida = br.readLine();
        while (linhaLida != null) {
            numeroLinha++;
            
            if((numeroLinha >= linhaInicial) && (numeroLinha <= linhaFinal)){
                retorno.add(linhaLida);
            }

            linhaLida = br.readLine();
        }
        br.close();
        return retorno;
    }

    public void write(List<String> novoConteudo) throws IOException {
        
        BufferedWriter bw = getBufferedWritter();

        for (String str : novoConteudo) {
            bw.write(str);
            bw.newLine();
        }

        bw.close();
    }

    private BufferedReader getBufferedReader() throws FileNotFoundException{
        InputStream is = new FileInputStream(nome);
        InputStreamReader isr = new InputStreamReader(is);
        return new BufferedReader(isr);
    }
    
    private BufferedWriter getBufferedWritter() throws FileNotFoundException{
        OutputStream os = new FileOutputStream(nome, true);
        OutputStreamWriter ows = new OutputStreamWriter(os);
        return new BufferedWriter(ows);
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
