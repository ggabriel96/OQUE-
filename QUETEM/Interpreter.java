package QUETEM;
/*******************************************************************************
Name: Interpreter.java
Authors: Acácia dos Campos da Terra - terra.acacia@gmail.com
         Gabriel Batista Galli - g7.galli96@gmail.com
         Vladimir Belinski - vlbelinski@gmail.com

Description: Class Interpreter of OQUE, a programming language based on Java.
             Responsible for the execution of the code.
*******************************************************************************/

import java.util.*;
import java.util.regex.*;

class Interpreter {
	private HashMap<String, HashMap<String, Struct>> vars;

	public Interpreter() {
		this.vars = new HashMap<>();
	}

	public void run(HashMap<String, ArrayList<Command>> code) {
    }

	public void execute(ArrayList<Command> code) throws UatException {
		int i, max;
		Command command = null;

		for (i = 1, max = code.size(); i < max; i++) {
			command = code.get(i);

			switch (command.code()) {
				case SourceScanner.FN:
					break;
				case SourceScanner.BRACKET:
					break;
				case SourceScanner.DECL:
					break;
				case SourceScanner.ATR:
					break;
				case SourceScanner.PRINT:
					break;
				case SourceScanner.PRINTLN:
					break;
				case SourceScanner.SCAN:
					break;
				case SourceScanner.SCANLN:
					break;
				case SourceScanner.IF:
					break;
				case SourceScanner.ELSIF:
					break;
				case SourceScanner.ELSE:
					break;
				case SourceScanner.WHILE:
					break;
				case SourceScanner.FOR:
					break;
				case SourceScanner.BREAK:
					break;
				case SourceScanner.CONTINUE:
					break;
			}
		}
	}
}
