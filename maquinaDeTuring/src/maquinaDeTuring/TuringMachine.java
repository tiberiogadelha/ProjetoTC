package maquinaDeTuring;

import java.util.HashSet;

import componentes.Estado;
import componentes.Fita;
import componentes.Transicao;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TuringMachine {

	private HashSet<Estado> estados;
	private Estado estadoInicial;
	private Estado estadoAtual;
	private HashSet<Estado> estadosFinais;
	private int passos;
	private Fita fita;

	public TuringMachine() {
		this.estados = new HashSet<Estado>();
		this.estadoInicial = new Estado("0");
		this.setEstadoAtual(this.estadoInicial);
		this.estadosFinais = new HashSet<Estado>();
		this.setPassos(0);
		this.setFita(new Fita());
		this.estados.add(estadoInicial);
	}

	protected void newState(String state) {
		if (this.searchState(state) == null) {
			Estado novoEstado = new Estado(state);
			estados.add(novoEstado);

			String[] palavra = state.split("");
			if (palavra.length >= 4) {
				String checkHalt = palavra[0] + palavra[1] + palavra[2] + palavra[3];
				if (checkHalt.equals("halt")) {
					estadosFinais.add(novoEstado);
				}
			}
		}
	}

	protected Estado searchState(String estado) {
		Estado estadoProcurado = null;
		
		for (Estado state : estados) {
			if (state.getNome().equals(estado)) {
				estadoProcurado = state;
			}
		}
		return estadoProcurado;
	}

	public void addTransition(String estado1, String simboloAtual, String simboloNovo, String direcao, String estado2) {
		this.newState(estado1);
		this.newState(estado2);

		Estado e1 = this.searchState(estado1);
		Estado e2 = this.searchState(estado2);

		Transicao transicao = new Transicao(simboloAtual, simboloNovo, direcao, e2);

		e1.addTransicao(transicao);
	}

	public void writeNewWord(String palavra) {
		this.setPassos(0);
		this.setEstadoAtual(this.estadoInicial);
		this.setFita(new Fita());
		this.getFita().escreverPalavra(palavra);
	}

	public void runByStep() throws Exception {
		// Se tiver terminado
		if (estadosFinais.contains(this.getEstadoAtual())) {
			return;
		}
		
		Transicao transicao = this.getEstadoAtual().getTransicao(this.getFita().getSimboloAtual());
		
		if (transicao == null) {
			transicao = this.getEstadoAtual().getTransicao("*");
			
			// Se houver algum problema
			if (transicao == null) {
				throw new Exception("Algo de errado aconteceu! Mude a palavra (3) ou a sintaxe (4).");
			}
		}
		
		if (!transicao.getNovoSimbolo().equals("*")) {
			getFita().escreverSimbolo(transicao.getNovoSimbolo());
		}
		
		getFita().run(transicao.getDirecao());
		
		if (!transicao.getNovoEstado().equals("*")) {
			this.setEstadoAtual(transicao.getNovoEstado());
		}
		
		this.setPassos(this.getPassos() + 1);
	}

	public void runMachine() throws Exception {
		while (!this.estadosFinais.contains(getEstadoAtual())) {
			this.runByStep();
		}
	}

	public void readFromConsole() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nDigite a nova sintaxe seguida de 'end' para continuar: ");
		String in;

		while (!(in = reader.readLine()).trim().equalsIgnoreCase("end")) {
			if (!in.isEmpty() && !in.trim().equals("")) {
				String[] read = in.split(" ");
				if (!read[0].equals(";")) {
					addTransition(read[0], read[1], read[2], read[3], read[4]);
				}
			}

		}
		
	}
	
	public void reset() {
		this.estados = new HashSet<Estado>();
		this.estadoInicial = new Estado("0");
		this.setEstadoAtual(this.estadoInicial);
		this.estadosFinais = new HashSet<Estado>();
		this.setPassos(0);
		this.setFita(new Fita());
		this.estados.add(estadoInicial);
	}

	public void readFile() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("syntax.txt"));
		String line;

		while ((line = in.readLine()) != null) {
			if (!line.isEmpty()) {
				String[] read = line.split(" ");
				if (!read[0].equals(";")) {
					addTransition(read[0], read[1], read[2], read[3], read[4]);
				}
			}

		}
		
		in.close();
	}

	public int getPassos() {
		return passos;
	}

	public void setPassos(int passos) {
		this.passos = passos;
	}

	public Estado getEstadoAtual() {
		return estadoAtual;
	}

	public void setEstadoAtual(Estado estadoAtual) {
		this.estadoAtual = estadoAtual;
	}

	public Fita getFita() {
		return fita;
	}

	public void setFita(Fita fita) {
		this.fita = fita;
	}

}
