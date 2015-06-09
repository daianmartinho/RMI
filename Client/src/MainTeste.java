/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bia Barroso
 */
public class MainTeste {
    
    public static void main(String[] args){
        
        for (int i=0; i<5; i++){
            Leitor l = new Leitor("arquivo1.txt", (i+1), 2);
            
            String s = "Escritor "+ (i+1) + " escreveu";
            Escritor e = new Escritor("arquivo1.txt", s);
        }
    }
}
