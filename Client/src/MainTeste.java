
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import sun.text.resources.ar.FormatData_ar;

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
    
    public static void main (String[] args){
    
        for (int j=0; j<5; j++){

            Leitor l = new Leitor ( "arquivo1.txt", (j+1), 1);
        }
        
        for(int i=0; i<5; i++){
            String s = "Escritor "+ (i+1)+ " escreveu\n";
            Escritor e = new Escritor("arquivo1.txt", s);
        }
    }
}
