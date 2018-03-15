package componentes;

import java.util.HashSet;

public class Estado {	
	public HashSet<Transicao> transicoes;
	public String nome;
	
	public Estado(String nome) {
		this.nome = nome;
		this.transicoes = new HashSet<Transicao>();
	}
	
	public Transicao getTransicao(String simboloFita) {
		Transicao transicaoProcurada = null;
		
		for(Transicao transicao : this.transicoes) {
			if(transicao.simboloAtual.equals(simboloFita)) {
				transicaoProcurada = transicao;
				break;
			}
		}
		
		return transicaoProcurada;
	}
	
	public void addTransicao(Transicao transicao){
		transicoes.add(transicao);
	}
	
	
	public HashSet<Transicao> getTransicoes(){
		return transicoes;
	}

	public String getNome() {
		return nome;
	}
	
}
