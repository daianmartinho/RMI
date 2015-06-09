/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daian;

import java.util.Random;

/**
 *
 * @author Daian
 */
public class Main {

    public static void main(String[] args) {
        Random r = new Random();
        for (int i = 0; i < 50; i++) {
            new Daian.Escritor("arquivo" + r.nextInt(3) + ".txt");
            new Daian.Leitor("arquivo" + r.nextInt(3) + ".txt", 0, 1);

        }

    }
}
