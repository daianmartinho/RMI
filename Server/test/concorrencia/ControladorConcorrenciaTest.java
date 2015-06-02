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
public class ControladorConcorrenciaTest {

    public ControladorConcorrenciaTest(TipoPrioridade tipoPrioridade) {
        controlador = ControladorConcorrenciaFactory.create(tipoPrioridade);
    }
    
    private ControladorConcorrencia controlador;
    private static final int umSegundo = 1000;
    
    public void read() throws Exception{
        controlador.acquireReadLock();
            System.out.println(Thread.currentThread().getId() + " lendo..");
            Thread.sleep(2 * umSegundo);
        controlador.releaseReadLock();
    }
    
    
    public void write() throws Exception{
        controlador.acquireWriteLock();
            System.out.println(Thread.currentThread().getId() + " escrevendo..");
            Thread.sleep(2 * umSegundo);
        controlador.releaseWriteLock();
    }
    
    
        
}
