import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Pontificia Universidade Catolica de Minas Gerais
 * @author Joao Paulo de Castro Bento Pereira
 * @author Paulo Junio Reis Rodrigues
 * Fundamentos Teoricos da Computacao - Ciencia da Computacao - ICEI - 2018
 */

public class Main {

    /**
     * Metodo que ira ler um arquivo contendo uma gramatica e decidir se
     * uma determinada sentenca pertence a essa LLC que a gramatica representa
     * o arquivo deve conter a descricao da gramatica na seguinte forma
     * 1 - Conjunto de Nao-Terminais separados por virgula
     * 2 - Conjunto de Terminais separados por virgula
     * 3 - N Linhas contendo Regras de producoes da gramatica comecando pelo Simbolo de Partida :
     *     Nao Terminal->Producao|Producao|Producao ( Obs: Producao Lambda deve ser representado com o simbolo ? )
     * 4 - Linha contendo Simbolo de partida da gramatica
     * 5 - N Linhas contendo sequencias de testes para decidir se pertence ou nao a LLC
     *
     *  Para maior entendimento, olhar arquivo src/Exemplo.txt
     */

    /**
     * Metodo para fazer o parse para uma regra
     * @param entrada Linha contendo simbolo inicial e producoes
     * @return Objeto regra
     */
    public static Regra parseRegra(String entrada){
        /*Separando simbolo inicial de producoes*/
        String[] splitEntrada = entrada.split("->");
        String simboloInicial = splitEntrada[0];
        String[] splitProducoes = splitEntrada[1].split("\\|");

        Regra regra = new Regra(simboloInicial);
        /*Adicionando todas as producoes a regra*/
        for(int i = 0; i < splitProducoes.length; i++){
            regra.addProducao(splitProducoes[i]);
        }
        return regra;
    }


    public static void main (String [] args){
        Gramatica g;
        FileReader fr = null;
        BufferedReader br = null;
        BufferedReader io = null;
        String line;
        String nomeArq;

        try{
            System.out.println("Teste de sentenca para Gramatica de uma LLC");
            System.out.println("Favor inserir o nome do Arquivo incluindo sua extensao. Ex(Exemplo.txt)");
            System.out.print("Nome do Arquivo: ");
            io = new BufferedReader(new InputStreamReader(System.in));
            nomeArq = io.readLine();
            fr = new FileReader(nomeArq);
            br = new BufferedReader(fr);

            /* Fazendo parse do arquivo */
            String naoTerminais = br.readLine(); /*Lendo nao terminais*/
            String terminais = br.readLine(); /*Lendo terminais*/
            if(naoTerminais != null && terminais != null){
                naoTerminais = naoTerminais.replaceAll(",", "");/*Removendo virgulas*/
                terminais = terminais.replaceAll(",", "");/*Removendo virgulas*/
                /*Criando gramatica*/
                g = new Gramatica(naoTerminais, terminais);
                /*Lendo conjunto de regras*/
                while((line = br.readLine()) != null && line.contains("->")){
                    Regra regra = parseRegra(line);
                    g.addRegra(regra);
                }
                /*Lendo simbolo de partida da gramatica*/
                String simboloPartida = br.readLine();
                g.setSimboloPartida(simboloPartida);

                /*Mostrando gramatica lida*/
                System.out.println("Gramatica lida: \n" + g);
                g.formaNormalBinaria();
                System.out.println("Gramatica 2NF: \n" + g);
                g.conjuntoAnulavel();
                g.relacaoUnitaria();

                /*TODO
                * 1 - Passar da gramatica GLC para 2NF - OK
                * (Conj. Eg'') - OK
                *  Rel. Unitaria
                *  Inv. Rel. ''
                * 2 - CYKModified
                * 3 - Pertence ?*/

            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(br != null) br.close();
                if(fr != null) fr.close();
                if(io != null) io.close();
            }catch (Exception i){
                i.printStackTrace();
            }
        }

    }

}
