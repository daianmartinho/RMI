/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import io.Arquivo;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;

/**
 *
 * @author lucas
 */
public class ArquivoTest {
    
    private static final String NOME_ARQUIVO_1 = "/home/lucas/Documents/teste4.txt";
    
    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException, FileNotFoundException, IOException {
        Arquivo a = new Arquivo(NOME_ARQUIVO_1);
        //a.write(Arrays.asList("linha3", "linha4"));
        
        System.out.println(a.read(1, 100));
    }
}
