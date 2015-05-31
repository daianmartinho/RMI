
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import server.Servidor;
import server.ServidorImpl;
import java.util.Scanner;


/*  CENTRALIZEI AQUI TODA A EXECUÇÃO DO CLIENTE.  */

public class ExemploDeChamadaMain {
    
    public static void main(String[] args) {
        
        
        /*  INICIALIZAÇÃO DE VARIÁVEIS, PRINT PARA FICAR ORGANIZADO E SLEEP SÓ PARA DAR TEMPO DE LER    */
        System.out.println("INICIANDO MÓDULO CLIENTE...\n\n");
        boolean execucao = true;
        Scanner s = new Scanner(System.in);
        try {
            Thread.sleep(3000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        
        /*  
            ESSE WHILE FICARÁ EM EXECUÇÃO ENQUANTO NÃO HOUVER PEDIDO DE FINALIZAÇÃO.
            ESSE WHILE FICARÁ ESCUTANDO O QUE DEVERÁ SER EXECUTADO.
        */
        while (execucao){
            
            /*  IMPRIME AS OPÇÕES E AGUARDA A PRÓXIMA TAREFA*/
            System.out.println("Digite:\n1- Para ler de arquivo\n2- Para escrever em arquivo\n3- Para finalizar\n");
            int opcao = s.nextInt();
            
            switch(opcao){
                
                /*   FUNÇÃO DE LEITURA   */
                case 1: 
                    System.out.println("INICIANDO A LEITURA...");
                    System.out.println("Digite o nome do arquivo (com a extensão):");
                    String arqL = s.next();
                    System.out.println("\nDigite o número da linha de início da leitura:");
                    int i = s.nextInt();
                    System.out.println("\nDigite a quantidade de linhas a serem lidas:");
                    int qtd = s.nextInt();
                    
                    new Leitor(arqL, i, qtd);
                    
                    break;
                
                /*   FUNÇÃO DE ESCRITA   */
                case 2: 
                    System.out.println("INICIANDO A ESCRITA... ");
                    System.out.println("Digite o nome do arquivo (com a extensão):");
                    String arqE = s.next();
                    System.out.println("\nDigite o número da linha de início da leitura:");
                    String conteudo = s.next();
                    
                    new Escritor(arqE, conteudo);
                    
                    break;
                    
                /*   FINALIZA EXECUÇÃO   */
                case 3: 
                    System.out.println("FINALIZANDO O MODULO CLIENTE...\n");
                    try {
                        Thread.sleep(3000);
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    execucao = false;
                    System.out.println("MODULO CLIENTE FINALIZADO!\n");
                    break;
                    
                /*   CASO NÚMERO DIGITADO SEJA DIFERENTE DAS OPÇÕES POSSÍVEIS   */
                default:
                    System.out.println("OPÇÃO INVÁLIDA!\n");
                    break;
            }
        }
    }
}
