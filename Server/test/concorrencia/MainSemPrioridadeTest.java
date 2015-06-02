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
public class MainSemPrioridadeTest {
    static final int NUMERO_THREADS = 10;
    public static void main(String[] args) {
        ControladorConcorrenciaTest controlador = 
                new ControladorConcorrenciaTest(TipoPrioridade.SEM_PRIORIDADE);
        try {
            for (int i = 0; i < NUMERO_THREADS; i++) {
                    EscritorTest escritor = new EscritorTest(controlador);
                    LeitorTest leitor = new LeitorTest(controlador);
                    new Thread(leitor).start();
                    new Thread(escritor).start();
            }
            
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
