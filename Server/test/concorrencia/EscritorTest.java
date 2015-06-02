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
public class EscritorTest implements Runnable{

    public EscritorTest(ControladorConcorrenciaTest controlador) {
        this.controlador = controlador;
    }
    
    private ControladorConcorrenciaTest controlador;

    @Override
    public void run() {
        try {
            controlador.write();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
       
    }
    
}
