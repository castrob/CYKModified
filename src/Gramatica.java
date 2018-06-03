/**
 * Pontificia Universidade Catolica de Minas Gerais
 * @author Joao Paulo de Castro Bento Pereira
 * @author Paulo Junio Reis Rodrigues
 * Fundamentos Teoricos da Computacao - Ciencia da Computacao - ICEI - 2018
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Gramatica {

    String simboloPartida;
    String naoTerminais;
    String terminais;
    List<Regra> regras;


    public Gramatica() {
        regras = new ArrayList<Regra>();
    }

    /**
     * Construtor para Gramatica
     * @param naoTerminais Caracteres nao terminais
     * @param terminais Caracteres Terminais
     */
    public Gramatica(String naoTerminais, String terminais) {
        this.naoTerminais = naoTerminais;
        this.terminais = terminais;
        this.regras = new ArrayList<Regra>();
    }

    /**
     * Getter para simbolo de partida da gramatica
     * @return simbolo de partida da gramatica
     */
    public String getSimboloPartida() {
        return simboloPartida;
    }

    /**
     * Setter para simbolo de partida da gramatica
     * @param simboloPartida simbolo de partida da gramatica
     */
    public void setSimboloPartida(String simboloPartida) {
        this.simboloPartida = simboloPartida;
    }

    /**
     * Getter para nao terminais
     * @return String com nao-terminais
     */
    public String getNaoTerminais() {
        return naoTerminais;
    }

    /**
     * Setter para String de nao-terminais
     * @param naoTerminais String de nao-terminais
     */
    public void setNaoTerminais(String naoTerminais) {
        this.naoTerminais = naoTerminais;
    }

    /**
     * Getter para String de Terminais
     * @return String de terminais
     */
    public String getTerminais() {
        return terminais;
    }

    /**
     * Setter para Terminais
     * @param terminais String com Terminais
     */
    public void setTerminais(String terminais) {
        this.terminais = terminais;
    }

    /**
     * Getter de conjunto de Regras da Gramatica
     * @return Lista contendo conjunto de regras
     */
    public List<Regra> getRegras() {
        return regras;
    }

    /**
     * Setter para Conjunto de Regras
     * @param regras List contendo conjunto de regras
     */
    public void setRegras(List<Regra> regras) {
        this.regras = regras;
    }

    /**
     * Metodo para Adicionar um novo Nao-Terminal
     * @param naoTerminal nao-terminal a ser adicionado
     */
    public void addNaoTerminais(char naoTerminal){
        this.naoTerminais += naoTerminal;
    }

    /**
     * Metodo para adicionar Regras a esta gramatica
     * @param regra regra a ser adicionada
     */
    public void addRegra(Regra regra){
        this.regras.add(regra);
    }

    @Override
    public String toString() {
        String str = "G: " + regras.get(0) + "\n";
        for(int i = 1; i < regras.size(); i++){
            str += "   " + regras.get(i) + "\n";
        }
        return  str;
    }
}
