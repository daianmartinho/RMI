/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daian;

/**
 *
 * @author Daian
 */
public class Main {
    public static void main(String[] args) {
               
        for (int i = 0; i < 50; i++) {
            new Daian.Escritor("arquivo1.txt"); 
            new Daian.Leitor("arquivo1.txt",0,1);
            
        }

        
        
        
    }
}
