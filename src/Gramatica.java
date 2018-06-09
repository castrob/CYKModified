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

    String simboloPartida; /*Simbolo que representa inicio da Gramatica*/
    String naoTerminais; /*Todos os nao terminais presentes na gramatica*/
    String terminais; /*Todos os terminais presentes na gramatica*/
    List<Regra> regras; /*Lista de regras presentes na gramatica com producoes*/
    List<String> anulaveis; /*Lista de regras anulaveis*/
    List<String> relacaoUnitaria; /*Relacao unitaria da gramatica*/
    List<String> relacaoUnitariaReversa; /*Reversa da relacao unitaria*/

    /**
     * Construtor padrao
     */
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

    public int indexOf(String simboloInicial){
        for(int i = 0; i < regras.size(); i++)
            if(regras.get(i).simboloInicial.equals(simboloInicial))
                return i;
        return -1;
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

    /**
     * Metodo necessario para auxiliar no algoritmo de conjuntoAnulavel
     * testa se em uma determinada producao existe apenas terminais
     * @param s producao a ser analisada
     * @return true caso exista apenas terminais, false caso exista pelo menos um nao terminal.
     */
    public boolean isOnlyTerminal(String s){
        for( int i = 0; i < s.length(); i++)
            if(!terminais.contains(s.charAt(i)+"") && s.charAt(i) != '?')
                return false;
        return true;
    }

    /**
     * Metodo para fazer um split de uma producao para cada Terminal ou Nao Terminal
     * @param s Producao a ser splitada por terminais/naoterminais
     * @return Array de string contendo cada um dos elementos
     */
    public String[] splitProducao(String s) {
        int n = 1;
        for(int i = 1; i < s.length(); i++)
            if(s.charAt(i) != '\'')
                n++;

        String[] retorno = new String[n];
        int pos = 0;
        retorno[pos] = s.charAt(0) + "";
        for(int i = 1; i < s.length(); i++){
            if(s.charAt(i) == '\''){
                retorno[pos]+=s.charAt(i);
            }else{
                pos++;
                retorno[pos] = s.charAt(i) + "";
            }
        }
        return retorno;
    }

    /**
     * Metodo para coletar Regras que sao anulaveis atraves da producao lambda e producao de apenas outras regras anulaveis
     * pseudo codigo:
     *
     * input: a CFG G = (N,Σ,S,→) in 2NF
     * Nullable(G) =
     * 1    nullable : = ∅
     * 2    todo : = ∅
     * 3    for all A ∈ N do
     * 4      occurs(A) : = ∅
     * 5    for all A → B do
     * 6      occurs(B) := occurs(B) ∪{ A }
     * 7    for all A → BC do
     * 8      occurs(B) := occurs(B) ∪{ 〈A,C〉}
     * 9      occurs(C) := occurs(C) ∪{ 〈A,B 〉}
     * 10    for all A → e do
     * 11      nullable : = nullable ∪{A}
     * 12      todo : = todo ∪{A}
     * 13    while todo ≠ ∅ do
     * 14      remove some B from todo
     * 15      for all A, 〈A,C〉 ∈ occurs(B) with C ∈ nullable do
     * 16        if A ∉ nullable then
     * 17          nullable : = nullable ∪{A}
     * 18          todo : = todo ∪{A}
     * 19    return nullable
     */
    public void conjuntoAnulavel(){
        List<String> anulavel = new ArrayList<String>();
        List<String> todo = new ArrayList<String>();
        String[] ocorrencias = new String [regras.size()];

        for (int i = 0; i < ocorrencias.length; i++)
            ocorrencias[i] = "";
        for(Regra r : regras){
            for( String s : r.producoes){
                if(s.length() == 1 && !s.equals("?")){
                    if(!terminais.contains(s))
                        ocorrencias[indexOf(s)] += r.getSimboloInicial() + " ";
                }else if (s.length() > 1 && !s.equals("?") ){
                    String [] producoes = splitProducao(s);
                    String regra1 = producoes[0];
                    String regra2 = "";
                    for(int i = 1; i < producoes.length; i++)
                        regra2 += producoes[i];

                    if(!isOnlyTerminal(regra1) && !isOnlyTerminal(regra2)){
                        ocorrencias[indexOf(regra1)] += r.getSimboloInicial() + regra2 +" ";
//                        System.out.println(ocorrencias[indexOf(regra1)]);
                    }
                    if(!isOnlyTerminal(regra2)){
                        ocorrencias[indexOf(regra2)] += r.getSimboloInicial() + regra1 + " ";
//                        System.out.println(ocorrencias[indexOf(regra2)]);
                    }
                }else if(s.equals("?")){
                    anulavel.add(r.getSimboloInicial());
                    todo.add(r.getSimboloInicial());
                }
            }
        }

        while(!todo.isEmpty()){
            String g = todo.get(0);
            todo.remove(0);
//            System.out.println(g);
            String[] array = ocorrencias[indexOf(g)].split(" ");
            for(int i = 0; i < array.length; i++){
                String s = array[i];
                if(s.length() == 1){
                    if(!anulavel.contains(s)){
                        anulavel.add(s);
                        todo.add(s);
                    }
                }else if(s.length() > 1){
                    String[] producoes = splitProducao(s);
                    String regra1 = producoes[0];
                    String regra2 = "";
                    for(int j = 1; j < producoes.length; j++)
                        regra2 += producoes[j];

                    if(anulavel.contains(regra2)){
                        if(!anulavel.contains(regra1)){
                            anulavel.add(regra1);
                            todo.add(regra1);
                        }
                    }
                }
            }
        }
        System.out.println("Conjunto Anulavel: " + anulavel);
        System.out.println();
        this.anulaveis = anulavel;
    }

    /**
     * Algoritmo para converter a gramatica GLC em uma Gramatica em 2NF
     * para ser utilizado o algoritmo de formaAnulavel
     */
    public  void formaNormalBinaria(){
        List<Regra> regrasBinarias = new ArrayList<Regra>();
        for(Regra r : regras){
            regrasBinarias.addAll(r.formaBinaria());
        }

        if(regrasBinarias.size() > this.regras.size()){
            this.setRegras(regrasBinarias);
            this.naoTerminais = "";
            for(int i = 0; i < this.regras.size(); i++){
                this.naoTerminais += this.regras.get(i).simboloInicial;
            }

        }
    }

    /**
     * Metodo para coletar a relacao unitaria entre as regras e suas producoes
     * para quando uma regra X -> A onde A e' unitario. sendo Unitario pela unidade
     * ou por ser unico nao anulavel na producao.
     * Relacao unitaria reversa A -> X para utilizacao do grafo posteriormente.
     */
    public void relacaoUnitaria(){
        List<String> relacaoUnitaria = new ArrayList<String>();
        List<String> relacaoUnitariaReversa = new ArrayList<String>();
        for(Regra r : regras){
            for(String s : r.producoes){
                if(s.length() == 1 && !s.equals("?") && !anulaveis.contains(s)){
                    String novaRelacao = "("+r.getSimboloInicial() + "," + s + ")";
                    if(!relacaoUnitaria.contains(novaRelacao))
                        relacaoUnitaria.add(novaRelacao);
                    String novaRelacaoReversa = "("+ s + "," + r.getSimboloInicial() + ")";
                    if(!relacaoUnitariaReversa.contains(novaRelacaoReversa))
                        relacaoUnitariaReversa.add(novaRelacaoReversa);
                }else if(s.length() > 1){
                    String[] producoes = splitProducao(s);
                    if(!anulaveis.contains(producoes[0]) && anulaveis.contains(producoes[1])){
                        String novaRelacao = "("+r.getSimboloInicial() + "," + producoes[0] + ")";
                        if(!relacaoUnitaria.contains(novaRelacao))
                            relacaoUnitaria.add(novaRelacao);
                        String novaRelacaoReversa = "("+ producoes[0] + "," + r.getSimboloInicial() + ")";
                        if(!relacaoUnitariaReversa.contains(novaRelacaoReversa))
                            relacaoUnitariaReversa.add(novaRelacaoReversa);
                    }else if(anulaveis.contains(producoes[0]) && !anulaveis.contains(producoes[1])){
                        String novaRelacao = "("+r.getSimboloInicial() + "," + producoes[1] + ")";
                        if(!relacaoUnitaria.contains(novaRelacao))
                            relacaoUnitaria.add(novaRelacao);
                        String novaRelacaoReversa = "("+ producoes[1] + "," + r.getSimboloInicial() + ")";
                        if(!relacaoUnitariaReversa.contains(novaRelacaoReversa))
                            relacaoUnitariaReversa.add(novaRelacaoReversa);
                    }
                }
            }
        }

        System.out.println("Ug: " + relacaoUnitaria);
        this.relacaoUnitaria = relacaoUnitaria;
        System.out.println("Ûg: " + relacaoUnitariaReversa);
        this.relacaoUnitariaReversa = relacaoUnitariaReversa;

    }
}
