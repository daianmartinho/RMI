/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concorrencia;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lucas
 */
public class ControladorConcorrenciaFactoryTest {
    
    public ControladorConcorrenciaFactoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testConstrutores(){
        ControladorConcorrencia controladorPrioridadeLeitor = ControladorConcorrenciaFactory.
                create(ControladorConcorrenciaFactory.PRIORIDADE_LEITOR);
        ControladorConcorrencia controladorPrioridadeEscritor = ControladorConcorrenciaFactory.
                create(ControladorConcorrenciaFactory.PRIORIDADE_ESCRITOR);
        ControladorConcorrencia controladorSemPrioridade = ControladorConcorrenciaFactory.
                create(ControladorConcorrenciaFactory.SEM_PRIORIDADE);
        ControladorConcorrencia controladorDefault = ControladorConcorrenciaFactory.
                create("");
       
        assertEquals(controladorPrioridadeLeitor.getClass().getName(), ControladorConcorrenciaPrioridadeLeitor.class.getName());
        assertEquals(controladorPrioridadeEscritor.getClass().getName(), ControladorConcorrenciaPrioridadeEscritor.class.getName());
        assertEquals(controladorSemPrioridade.getClass().getName(), ControladorConcorrenciaSemPrioridade.class.getName());
        assertEquals(controladorDefault.getClass().getName(), ControladorConcorrenciaSemPrioridade.class.getName());
        
       
    }
}
