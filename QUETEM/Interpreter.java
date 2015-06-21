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
	private Stack<String> recursion;
	private final boolean DEBUG = false;
	private HashMap<String, ArrayList<Command>> code;
	private HashMap<String, HashMap<String, Struct>> vars;

	public Interpreter(HashMap<String, ArrayList<Command>> code) {
		this.code = code;
		this.vars = new HashMap<>();
		this.recursion = new Stack<>();
	}

	public Variable run(String name, HashMap<String, Struct> args) throws UatException {
		int i, max;
		Command command = null;
		Variable returnValue = null;
		ArrayList<Command> fn = this.code.get(name);

		this.recursion.push(name);

		if (args != null && args.size() > 0) {
			this.vars.put(name, args);
		}
		else {
			this.vars.put(name, new HashMap<String, Struct>());
		}

		for (i = 1, max = fn.size() - 1; i < max; i++) {
			command = fn.get(i);

			if (this.DEBUG) System.out.println("> " + command);

			try {
				switch (command.code()) {
					case SourceScanner.BRACKET:
						break;
					case SourceScanner.DECL:
						this.decl(command);
						break;
					case SourceScanner.ATR:
						this.atr(command);
						break;
					case SourceScanner.PRINT:
						this.print(command);
						break;
					case SourceScanner.PRINTLN:
						this.println(command);
						break;
					case SourceScanner.SCAN:
						this.scan(command);
						break;
					case SourceScanner.SCANLN:
						this.scan(command);
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
					case SourceScanner.RETURN:
						returnValue = this.ret(command);
						i = max;
						break;
				}
			}
			catch (UatException ue) {
				ue.setNumber(command.lineNumber());
				throw ue;
			}

			// System.out.println("====================");
			// System.out.println(this.recursion.peek());
			// for (Map.Entry<String, Struct> entry: this.vars.get(this.recursion.peek()).entrySet()) {
			// 	System.out.println(entry.getKey());
			// 	entry.getValue().printVars();
			// }
			// System.out.println("====================");
		}

		this.recursion.pop();

		return returnValue;
    }

	private void newStruct(HashMap<String, Struct> struct) {
		this.vars.put(this.recursion.peek(), struct);
	}

	private void newVar(String name, Variable v) {
		Struct struct = new Struct();
		HashMap<String, Struct> variable = this.vars.get(this.recursion.peek());

		struct.newVar(name, v);
		variable.put(name, struct);
	}

	private Struct getStruct(String name) {
		HashMap<String, Struct> struct = this.vars.get(this.recursion.peek());
		if (struct != null) return struct.get(name);
		else return null;
	}

	private Variable getVar(String struct, String field) {
		Struct s = this.getStruct(struct);
		if (s != null) return s.getVar(field);
		else return null;
	}

	private Variable getVar(String name) {
		Struct s = this.getStruct(name);
		if (s != null) return s.getVar(name);
		else return null;
	}

	private void decl(Command command) throws UatException {
		int i, max;
		Variable v = null;
		String[] decl = null;
		ArrayList<String> fields = command.fields();

		for (i = 1, max = fields.size(); i < max; i++) {
			decl = fields.get(i).split(Expression.VSEP.toString());

			// type is the first field
			switch (command.get(0)) {
				case "bool":
					v = new BoolVar();
					break;

				case "int":
					v = new IntVar();
					break;

				case "double":
					v = new DoubleVar();
					break;

				case "string":
					v = new StringVar();
					break;
			}

			if (decl.length > 1) {
				v.setValue(this.solve(decl[1]));
			}

			this.newVar(decl[0], v);
		}
	}

	private Variable ret(Command command) throws UatException {
		return this.solve(command.get(0));
	}

	private void atr(Command command) throws UatException {
		Struct s;
		Variable v;
		String field;
		String varName = command.get(0);
		String expression = command.get(1);
		Matcher arrayM = SourceScanner.arrayP.matcher(varName);

		if (arrayM.matches()) {
			varName = arrayM.group(1);
			field = arrayM.group(2);
			// field = field.substring(1, field.length() - 1);

			s = this.getStruct(varName);
			if (s == null) {
				System.out.println("111111111111111111111111");
				throw new UatException("varNotFound", varName);
			}

			v = s.getVar(field);
			if (v == null) {
				switch (s.type()) {
					case "Boolean":
						v = new BoolVar();
						break;

					case "Integer":
						v = new IntVar();
						break;

					case "Double":
						v = new DoubleVar();
						break;

					case "String":
						v = new StringVar();
						break;
				}

				v.setValue(this.solve(expression));
				s.newVar(field, v);
				if (this.DEBUG) {
					System.out.println("\n---------------");
					s.printVars();
					System.out.println("---------------\n");
				}
			}
			else {
				v.setValue(this.solve(expression));
			}
		}
		else {
			s = this.getStruct(varName);
			if (s == null) {
				System.out.println("222222222222222222222222");
				throw new UatException("varNotFound", varName);
			}

			v = s.getVar(varName);
			v.setValue(this.solve(expression));
		}
	}

	private void print(Command command) throws UatException {
		int i, max;
		String content, toPrint = "";
		ArrayList<String> fields = command.fields();

		for (i = 0, max = fields.size(); i < max; i++) {
			content = fields.get(i);

			if (content.startsWith(Expression.SEP.toString())) {
				toPrint += this.solve(content.substring(1, content.length() - 1));
			}
			else {
				toPrint += content;
			}
		}

		System.out.print(toPrint);
	}

	private void println(Command command) throws UatException {
		this.print(command);
		System.out.println();
	}

	private void scan(Command command) {

	}

	private Variable solve(String expression) throws UatException {
        Variable answ = null, num1 = null, num2 = null;
		String[] t = expression.split(Expression.SEP.toString());
		int i = 0, offset = 0;
		String[] front, back;
		Matcher wholeOpM;
		String op;

		// System.out.println("[INFO_LOG]: SOLVE_EXP = {" + exp + "}");
		// System.out.println("[INFO_LOG]: SOLVE_TOKENS = {" + tokens + "}");

		// System.out.println("solve expression: " + expression);

        if (t.length == 1) {
            answ = this.getOperand(t[0]);
        }

		while (t.length > 1) {

			wholeOpM = SourceScanner.wholeOpP.matcher(t[i]);
			// advance until you don't find an operation to perform
			while (i < t.length && !wholeOpM.matches()) {
				i++;
				if (i < t.length) wholeOpM = SourceScanner.wholeOpP.matcher(t[i]);
			}

			// if (i >= t.length) {
			// 	throw new UatException("invalidExp", exp.original);
			// }

			// then, the operation char is at i's position in the array
			op = t[i];
			// as it's postfix, the 2nd operand is right before op
            num2 = this.getOperand(t[i - 1]);

			/* now, if i > 1, then 1st operand is certainly 2 positions
			 * before op. Else, it doesn't exist lol
			 */
			if (!op.equals("!") && i > 1) {
                num1 = this.getOperand(t[i - 2]);
            }
			else {
                num1 = null;
            }

            if (num2 instanceof DoubleVar || (num1 != null && num1 instanceof DoubleVar)) {
                answ = new DoubleVar(0.0);
            }
            else {
                answ = new IntVar(0);
            }

			/* As I am overwriting my token vector, I gotta
			 * copy the parts that I'm not currently working with:
			 * the first (frontside) and the second (backside) parts,
             * with the result f the current operation in between.
             * Then, if my first operand doesn't exist, I gotta copy
             * the first part until this position (i) - 1. If it exists,
			 * until this position - 2. This way I discard
			 * the spots where the current operands were located
			 * and I leave one spot for the result of this operation
			 * in the middle of the new vector :)
			 */
			if (num1 == null) offset = i - 1;
			else offset = i - 2;

			front = Arrays.copyOfRange(t, 0, offset);
			back = Arrays.copyOfRange(t, i, t.length);
			t = this.merge(front, back);

			/* calculating where the result will be put
			 * same logic as the else above
			 */
			if (num1 == null) i -= 1;
			else i -= 2;

			// System.out.println("[INFO_LOG]: CALCULATE = {" + num1 + ", " + op + ", " + num2 + "}");

			answ = this.calculate(num1, num2, op);
			t[i] = answ.toString();
		}

		// System.out.println("[INFO_LOG]: SOLVE_RESULT = {" + answ + "}");

		return answ;
	}

    private Variable getOperand(String t) throws UatException {
		int index;
        Variable v = null;
		String field = null;
        Matcher intM, fpM, boolM, strM, quotInStrM, varNameM, anyM, arrayM, fnCallM;

        fpM = SourceScanner.jfpP.matcher(t);
        intM = SourceScanner.intP.matcher(t);
		strM = SourceScanner.strP.matcher(t);
		anyM = SourceScanner.anyP.matcher(t);
		boolM = SourceScanner.boolP.matcher(t);
		arrayM = SourceScanner.arrayP.matcher(t);
		fnCallM = SourceScanner.fnCallP.matcher(t);
		varNameM = SourceScanner.varNameP.matcher(t);

		// System.out.println("[INFO_LOG]: GET_OPERAND = {" + t + "}");

        if (intM.matches()) {
            v = new IntVar(Integer.parseInt(t));
        }
        else if (fpM.matches()) {
            v = new DoubleVar(Double.parseDouble(t));
        }
        else if (t.startsWith("-")) {
            t = t.replace("-", "");
            v = checkAndGetVar(t, true);
        }
        else if (t.startsWith("+")) {
            t = t.replace("+", "");
            v = checkAndGetVar(t, false);
        }
		else if (boolM.matches()) {
			if (t.equals("true")) {
				v = new BoolVar(true);
			}
			else {
				v = new BoolVar(false);
			}
		}
		else if (strM.matches()) {
			t = t.substring(t.indexOf("\"") + 1);
			index = t.lastIndexOf("\"");

			t = t.substring(0, index);
			// replacing all \" for an actual "
			quotInStrM = SourceScanner.quotInStrP.matcher(t);
			t = quotInStrM.replaceAll("\"");

			v = new StringVar(t);
		}
		else if (fnCallM.matches()) {
			Struct argValue = new Struct();
			HashMap<String, Struct> args = new HashMap<>();
			String argName = this.code.get(fnCallM.group(1)).get(0).get(1);

			field = fnCallM.group(2);
			argValue.newVar(argName, this.solve(field));

			// System.out.println(":" + this.code.get(fnCallM.group(1)).get(0));
			// System.out.println("argName: {" + argName + "}");
			// System.out.println("argValue:");
			// argValue.printVars();
			args.put(argName, argValue);

			// System.out.println("NAME: {" + fnCallM.group(1) + "}");
			// System.out.println("FIELD: {" + field + "}");

			v = this.run(fnCallM.group(1), args);
		}
		else if (arrayM.matches()) {
			field = this.solve(arrayM.group(2)).toString();

			if ((v = this.getVar(arrayM.group(1), field)) == null) {
				System.out.println("333333333333333333");
				throw new UatException("varNotFound", t);
			}
		}
		else if (varNameM.matches()) {
			if ((v = this.getVar(t)) == null) {
				System.out.println("444444444"+t+"444444444");
				throw new UatException("varNotFound", t);
			}
		}
		// for when I operate with direct strings in an expression.
		// will probably never fall into the else, but leave it alone :P
		else if (anyM.matches()) {
			v = new StringVar(t);
		}
		else {
			throw new UatException("unknownSymbol", t);
		}

        return v;
    }

	private Variable checkAndGetVar(String t, boolean neg) throws UatException {
		Struct s = null;
		Variable v = null;
		int opBr = t.indexOf("[");
		int clBr = t.length() - 1;
		Matcher arrayM = SourceScanner.arrayP.matcher(t);
		Matcher varNameM = SourceScanner.varNameP.matcher(t);

		if (arrayM.matches()) {
			s = this.getStruct(t.substring(0, opBr));
			v = s.getVar(this.solve(t.substring(opBr + 1, clBr)).toString());
		}
		else if (varNameM.matches()) {
			v = this.getVar(t);
		}
		else {
			throw new UatException("invalidExp", t);
		}

		if (v != null) {
			if (neg) v = v.inverted();
		}
		else {
			System.out.println("555555555555555555");
			throw new UatException("varNotFound", t);
		}

		return v;
	}

    private Variable calculate(Variable v1, Variable v2, String op) throws UatException {
        Variable answ = null;
        Matcher opM = SourceScanner.wholeOpP.matcher(op);

        // If it doesn't have 2 operands and the
        // operation is a '-', simply return -v2.
        if (v1 == null) {
            if (op.equals("-")) {
                answ = v2.inverted();
            }
            else if (op.equals("+")) {
                answ = v2;
            }
			else if (op.equals("!")) {
				answ = v2.toBoolVar().inverted();
			}
            else if (opM.matches()) {
                throw new UatException("invalidExp", v2 + " " + op + " ?");
            }
        }
        else {
            switch (op) {
                case "^":
                answ = v1.pow(v2);
                break;

                case "*":
				answ = v1.times(v2);
                break;

				case "/":
                answ = v1.divided(v2);
                break;

                case "%":
				answ = v1.mod(v2);
                break;

                case "+":
                answ = v1.plus(v2);
                break;

                case "-":
                answ = v1.minus(v2);
                break;

				case "&&":
				answ = v1.and(v2);
				break;

				case "||":
				answ = v1.or(v2);
				break;

				case "<":
				answ = v1.lessThan(v2);
				break;

				case "<=":
				answ = v1.lessEquals(v2);
				break;

				case "==":
				answ = v1.equals(v2);
				break;

				case ">=":
				answ = v1.greaterEquals(v2);
				break;

				case ">":
				answ = v1.greaterThan(v2);
				break;

				case "!=":
				answ = v1.equals(v2).inverted();
				break;

                default:
                throw new UatException("unknownSymbol", op);
            }
        }

        return answ;
    }

    private String[] merge(String[] front, String[] back) {
        int i = 0;
        String[] ret = new String[front.length + back.length];

        for (String s: front) {
            ret[i++] = s;
        }
        for (String s: back) {
            ret[i++] = s;
        }

        return ret;
    }
}
