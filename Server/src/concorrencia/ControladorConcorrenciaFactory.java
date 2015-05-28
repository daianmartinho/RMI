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
public class ControladorConcorrenciaFactory {
    
    public final static String PRIORIDADE_ESCRITOR = "PrioridadeEscritor";
    public final static String PRIORIDADE_LEITOR = "PrioridadeLeitor";
    public final static String SEM_PRIORIDADE = "SemPrioridade";
    
    public static ControladorConcorrencia create(String tipoPrioridade) {
        if(tipoPrioridade == null){
            throw new RuntimeException("Tipo de controlador de concorrencia invalido");
        }
        switch (tipoPrioridade) {
            case PRIORIDADE_ESCRITOR:
                return new ControladorConcorrenciaPrioridadeEscritor();
            case PRIORIDADE_LEITOR:
                return new ControladorConcorrenciaPrioridadeLeitor();
            case SEM_PRIORIDADE:
                return new ControladorConcorrenciaSemPrioridade();
            default:
                return new ControladorConcorrenciaSemPrioridade();
        }
    }

}
