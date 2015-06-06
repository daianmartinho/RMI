/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concorrencia;

/**
 *
 * @author lucas
 */
public class LeitorTest implements Runnable{

      public LeitorTest(ControladorConcorrenciaTest controlador) {
        this.controlador = controlador;
    }
    
    private ControladorConcorrenciaTest controlador;

    @Override
    public void run() {
        try {
            controlador.read();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
       
    }
    
}
