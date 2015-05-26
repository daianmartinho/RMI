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
    
    
    public String read(String nomeArq, int numLinha, int qtdLinhas) throws RemoteException {
        
        String text = "";
        try {
            raf = new RandomAccessFile(nomeArq, "r");
            raf.seek(numLinha);

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

    public void write() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
