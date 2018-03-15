package main;

import java.io.IOException;
import operador.Operador;

public class Main {
	
	public static Operador operation;
	
	public static void main(String[] args) throws IOException {
		operation = Operador.getInstance();
		
		operation.init();
		operation.showMenu();
	}

}
