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
	private HashMap<String, Variable> vars;

	public Interpreter() {
		this.vars = new HashMap<String, Variable>();
	}

	public void execute(ArrayList<Line> code) throws UatException {
	}
}
