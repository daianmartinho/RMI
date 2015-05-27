/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lucas
 */
public class Arquivo {

    private ControleConcorrencia controle;
    private String nome;
    private RandomAccessFile raf;

    public Arquivo(String nome, ControleConcorrencia controle) {
        this.controle = controle;
        this.nome = nome;
    }

    public String read(int numLinha, int qtdLinhas) throws RemoteException {

        String text = "";
        try {
            raf = new RandomAccessFile(nome, "r");
            if(raf.length()!=0){
                int ponteiro=0;                
                int contaLinha=1;
                
                while(ponteiro<raf.length()){
                    if(contaLinha==numLinha){
                        
                    }
                }
                
                
            }
            raf.seek(0);
            int contaLinha = 1;
            int seek = 0;
            do {
                seek += Integer.parseInt(raf.readLine());
                System.out.println("seek atr= " + seek);
                contaLinha++;
            } while (contaLinha != numLinha);

            System.out.println("seek final = " + seek);
            raf.seek(seek);

            while (qtdLinhas != 0) {
                text += raf.readLine() + "\n";
                qtdLinhas--;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Número de linha inválido");
        }
        return text;
    }

    public void write(List<String> conteudo) throws RemoteException {

        try {
            System.out.println("entrou no write");
            raf = new RandomAccessFile(nome, "rw");
            for (String linha : conteudo) {
                System.out.println(linha);
                raf.seek(raf.length());
                //System.out.println("");
                raf.write(linha.getBytes().length);
                raf.writeUTF(linha);
            }
            raf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Número de linha inválido");
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ControleConcorrencia getControle() {
        return controle;
    }

    public void setControle(ControleConcorrencia controle) {
        this.controle = controle;
    }

}
