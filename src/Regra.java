/**
 * Pontificia Universidade Catolica de Minas Gerais
 * @author Joao Paulo de Castro Bento Pereira
 * @author Paulo Junio Reis Rodrigues
 * Fundamentos Teoricos da Computacao - Ciencia da Computacao - ICEI - 2018
 */

import java.util.ArrayList;
import java.util.List;

public class Regra{

    String simboloInicial;
    List<String> producoes;

    /**
     * Construtor Padrao
     * @param simboloInicial Simbolo inicial da Regra
     * @param producoes Lista de Producoes
     */
    public Regra(String simboloInicial, List<String> producoes) {
        this.simboloInicial = simboloInicial;
        this.producoes = producoes;
    }

    /**
     * Construtor Padrao
     * @param simboloInicial Simbolo Inicial da Regra
     */
    public Regra(String simboloInicial) {
        this.simboloInicial = simboloInicial;
        this.producoes = new ArrayList<String>();
    }

    /**
     * Getter para SimboloInicial
     * @return simboloInicial
     */
    public String getSimboloInicial() {
        return simboloInicial;
    }

    /**
     * Setter para Simbolo Inicial
     * @param simboloInicial Simbolo Inicial da Regra
     */
    public void setSimboloInicial(String simboloInicial) {
        this.simboloInicial = simboloInicial;
    }

    /**
     * Getter para Lista de Producoes
     * @return producoes
     */
    public List<String> getProducoes() {
        return producoes;
    }

    /**
     * Getter para uma producao na lista de producoes
     * @param index Posicao da lista de producoes
     * @return String regra
     */
    public String getProducoes(int index){
        return this.producoes.get(index);
    }

    /**
     * Setter para lista de producoes
     * @param producoes lista de producoes para a regra
     */
    public void setProducoes(List<String> producoes) {
        this.producoes = producoes;
    }

    /**
     * Metodo para adicionar uma nova producao a lista de producoes
     * @param producao producao a ser adicionada
     */
    public void addProducao(String producao){
        this.producoes.add(producao);
    }

    @Override
    public String toString() {
        String str = this.simboloInicial + " -> ";
        for(String s : producoes)
            str += s + "|";

        str = str.substring(0, str.length()-1);

        return str;
    }

    /**
     * Funcao Booleana para testar se producao e' binaria ( |producao| < 2 )
     * @param s - Producao
     * @return - true ou false
     */
    public boolean isBinaria (String s){
        int contador = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) != '\''){
                contador++;
            }
        }
        return contador <= 2 ;
    }

    /**
     * Funcao auxiliar para metodos: formaBinaria ; para retornar o numero de aspas na string s
     * @param s - String com aspas
     * @return - numero de aspas
     */
    public int numberOf(String s){
        int contador = 2; /*Contador sera o numero que sera reduzido no indexOf do substring, portanto -2 padrao pois A' sao dois caracteres*/
        for (int i = 0; i < s.length(); i++)
            if(s.charAt(i) == '\'')
                    contador++; /*no caso de A''', 2 + 1 = 3, portando substring do tamanho de s - 3 */
        return  contador;
    }

    /**
     * Funcao para reduzir uma regra de uma GLC para forma binaria, para que a gramatica passe para 2NF
     * @return Regra reduzida para 2NF
     */

    public List<Regra> formaBinaria() {
        String simboloBinario = this.simboloInicial; /*Simbolo para novas regras binarias*/
        List<Regra> regrasBinarias = new ArrayList<Regra>(); /*Lista de regras binarias*/
        regrasBinarias.add(this); /*Adicionado a propria regra para garantir a primeira posicao na lista*/
        int indice = 0; /*Indice da producao s na lista de producoes, para alteracao do novo valor AAA <-> AA'*/
        for(String s : producoes){ /*Para cada producao s em producoes*/
            if(!isBinaria(s)){ /*Verifica se ja nao e' uma producao binaria*/
                while (!isBinaria(s)){ /*enquanto nao for binaria, reduzir*/
                    Regra rBinaria = new Regra(simboloBinario+="'"); /*regra que ira receber reducao vem com aspas na frente*/
                    String novaProducao;
                    /*caso s ja tenha sido reduzido, e' necessario desconsiderar as aspas ao fazer o substring, portanto utilizado a funcao numberOf(s)*/
                    if(s.contains("'"))
                        novaProducao = s.substring(s.length()-numberOf(s));
                    else
                        novaProducao = s.substring(s.length()-2);

                    rBinaria.producoes.add(novaProducao);
                    regrasBinarias.add(rBinaria);

                    /*caso s ja tenha sido reduzido, e' necessario desconsiderar as aspas ao fazer o substring, portanto utilizado a funcao numberOf(s)*/

                    if(s.contains("'"))
                        s = s.substring(0,s.length()-numberOf(s)) + rBinaria.simboloInicial;
                    else
                        s = s.substring(0,s.length()-2) + rBinaria.simboloInicial;
                    this.producoes.set(indice,s);
                }
            }
            indice++; /*proxima producao de producoes*/
        }
        regrasBinarias.set(0, this); /*atualizando o valor da primeira posicao da lista de regrasBinarias com regra original reduzida*/
    return regrasBinarias;
    }
}
