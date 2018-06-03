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

}
