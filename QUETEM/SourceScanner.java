package QUETEM;
/*******************************************************************************
Name: SourceScanner.java
Authors: Acácia dos Campos da Terra - terra.acacia@gmail.com
         Gabriel Batista Galli - g7.galli96@gmail.com
         Vladimir Belinski - vlbelinski@gmail.com

Description: Class SourceScanner of OQUE, a programming language based on Java.
             Responsible for reading the source code, put it into an ArrayList
			 of Lines and then compile each line into a specific codification that
			 will be used by the Interpreter. Regexes and matchers are found
			 in this class.
*******************************************************************************/

import java.io.*;
import java.util.*;
import java.util.regex.*;

class SourceScanner {
	public static final int FN = 10, RETURN = 11, BRACKET = 12, DECL = 20, ATR = 30, PRINT = 40, PRINTLN = 41, SCAN = 50, SCANLN = 51, IF = 60, ELSIF = 61, ELSE = 62, WHILE = 70, FOR = 71, BREAK = 72, CONTINUE = 73;
	private static boolean patternsInitd = false;
	private static final Map<String, Boolean> reservedWords = mapReservedWords();
	public static Pattern wholeDeclP, varNameP, atrP, wholeAtrP, semicP, wholePrintP, wholeScanP, wholeScanlnP, wholeOpP, signP, intP, fpP, charP, strP, strAssignP, quotMarkP, strBackP, parenP, numBuildP, boolP, upperCaseP, strNotEmptyP, opGroupP, ufpP, jufpP, jfpP, quotInStrP, invalidFpP, wholeIfP, wholeElsifP, wholeElseP, ifP, elsifP, ifEndingP, wholeWhileP, wholeForP, forSplitP, anyP, fixAtrP, fixAtrTypeP, fnP, fnCallP, arrayP, opNoParP, emptyArrP;

	public SourceScanner() {
		if (!patternsInitd) {
			initPatterns();
		}
	}

	public HashMap<String, ArrayList<Command>> compile(File f) throws IOException, UatException {
		int i, max;
		Matcher fnM;
		String command;
		Line line = null;
		boolean foundMain = false;
		ArrayList<Command> codeBlock;
		ArrayList<Line> fullCode = this.read(f);
		HashMap<String, ArrayList<Command>> code = new HashMap<>();

		if (fullCode.size() > 0) {
			for (i = 0, max = fullCode.size(); i < max; i++) {
				line = fullCode.get(i);
				command = line.toString();

				fnM = fnP.matcher(command);
				if (fnM.matches()) {

					if (!foundMain && command.substring(3, command.indexOf("(")).trim().equals("main")) {
						foundMain = true;
					}

					codeBlock = this.buildBlock(fullCode, i);
					i += codeBlock.size() - 1;

					code.put(codeBlock.get(0).get(0), codeBlock); // adding to HashMap with fn name
				}
				else {
					throw new UatException("syntaxError", line.toString(), line.getNumber());
				}
			}

			if (!foundMain) {
				throw new UatException("mainNotFound", line.toString(), line.getNumber());
			}
		}

		return code;
	}

    public ArrayList<Line> read(File f) throws IOException {
        int i, comm;
        String line;
        Scanner sc = new Scanner(f);
        ArrayList<Line> input = new ArrayList<Line>();

        for (i = 1; sc.hasNext(); i++) {
            line = sc.nextLine().trim();

            if (line.isEmpty() || line.startsWith("--")) {
                continue;
            }
            else {
                comm = line.indexOf("--");
                if (comm > 0) {
                    line = line.substring(0, comm).trim();
                }
            }

            input.add(new Line(line, i));
        }
        sc.close();

        return input;
    }

    private ArrayList<Command> buildBlock(ArrayList<Line> code, int index) throws UatException {
		int i, max;
		Line line = null;
		boolean endOfBlock = false;
		Command compiledLine, poppedLine, loopLine;
		ArrayList<Command> block = new ArrayList<>();
		Stack<Command> blStack = new Stack<Command>(), loopStack = new Stack<Command>(), jStack = new Stack<Command>();

		for (i = index, max = code.size(); i < max && !endOfBlock; i++) {
			line = code.get(i);

			try {
				compiledLine = this.buildCommand(line);
			}
			catch (UatException ue) {
				ue.setNumber(line.getNumber());
				throw ue;
			}

			block.add(compiledLine);

			if (compiledLine.code() == FN || compiledLine.code() == IF || compiledLine.code() == ELSIF || compiledLine.code() == ELSE || compiledLine.code() == WHILE || compiledLine.code() == FOR) {

				if (compiledLine.code() == WHILE || compiledLine.code() == FOR) {
					loopStack.push(compiledLine);
				}

				blStack.push(compiledLine);
			}
			else if (compiledLine.code() == BRACKET) {
				poppedLine = blStack.pop();

				// # of line to reference the start of the block
				compiledLine.add(new Integer(poppedLine.lineNumber()).toString());
				// linesToJump
				poppedLine.add(new Integer(compiledLine.lineNumber() - poppedLine.lineNumber()).toString());

				if (poppedLine.code() == WHILE || poppedLine.code() == FOR) {
					while (!jStack.empty()) {
						loopLine = jStack.pop();
						if (loopLine.code() == BREAK) {
							// linesToJump
							loopLine.add(new Integer(poppedLine.lineNumber() + Integer.parseInt(poppedLine.get(poppedLine.size() - 1)) - loopLine.lineNumber()).toString());
						}
						else {
							// parentLine
							loopLine.add(new Integer(poppedLine.lineNumber()).toString());
						}
					}

					loopStack.pop();
				}
			}
			else if (compiledLine.code() == BREAK || compiledLine.code() == CONTINUE) {
				poppedLine = loopStack.peek();
				if (poppedLine.code() == WHILE || poppedLine.code() == FOR) {
					jStack.push(compiledLine);
				}
				else throw new UatException("notLooping", line.toString(), line.getNumber());
			}

			if (blStack.isEmpty()) {
				endOfBlock = true;
			}
		}

		if (!endOfBlock) {
			line = code.get(index);
			throw new UatException("bracketNotFound", line.toString(), line.getNumber());
		}

		return block;
	}

	private Command buildCommand(Line line) throws UatException {
		String command = line.toString();
		Matcher fnM = fnP.matcher(command);
		Matcher elseM = wholeElseP.matcher(command);
		Matcher wholeIfM = wholeIfP.matcher(command);
		Matcher elsifM = wholeElsifP.matcher(command);
		Matcher wholeAtrM = wholeAtrP.matcher(command);
		Matcher wholeForM = wholeForP.matcher(command);
		Matcher wholeDeclM = wholeDeclP.matcher(command);
		Matcher wholeScanM = wholeScanP.matcher(command);
		Matcher wholeWhileM = wholeWhileP.matcher(command);
		Matcher wholePrintM = wholePrintP.matcher(command);
		Matcher wholeScanlnM = wholeScanlnP.matcher(command);

		if (command.startsWith("}")) {
			return new Command(BRACKET, new ArrayList<String>(), line.getNumber());
		}
		else {
			if (fnM.matches()) {
				return this.fn(line);
			}
			else if (wholeDeclM.matches()) {
				return this.varDecl(line);
			}
			else if (wholeAtrM.matches()) {
				return this.varAtr(line);
			}
			else if (wholePrintM.matches()) {
				return this.print(line);
			}
			else if (wholeScanM.matches() || wholeScanlnM.matches()) {
				return this.scan(line);
			}
			else if (wholeIfM.matches() || elsifM.matches()) {
				return this.ifBr(line);
			}
			else if (elseM.matches()) {
				return new Command(ELSE, new ArrayList<String>(), line.getNumber());
			}
			else if (wholeWhileM.matches()) {
				return this.whileLoop(line);
			}
			else if (wholeForM.matches()) {
				return this.forLoop(line);
			}
			else if (command.equals("break")) {
				return this.breakLoop(line);
			}
			else if (command.equals("continue")) {
				return this.continueLoop(line);
			}
			else if (command.startsWith("return")) {
				return this.ret(line);
			}
			else {
				throw new UatException("syntaxError", line.toString());
			}
		}
	}

	private Command fn(Line line) {
		int opBr, clBr;
		String lineString = line.toString(), name;
		ArrayList<String> arguments = new ArrayList<>();

		opBr = lineString.indexOf("(");
		clBr = lineString.lastIndexOf(")");
		// from "fn" until '('
		name = lineString.substring(2, opBr).trim();
		// arguments
		lineString = lineString.substring(opBr + 1, clBr).trim();

		arguments.add(name);
		if (!lineString.isEmpty()) {
			arguments.addAll(Arrays.asList(lineString.split("( )*,( )*")));
		}

		return new Command(FN, arguments, line.getNumber());
	}

	private Command ret(Line line) throws UatException {
		String lineString;
		ArrayList<String> expression = new ArrayList<>();

		if (!line.toString().equals("return")) {
			lineString = line.toString().substring(0, 7) + "=" + line.toString().substring(6);
			expression.add(this.varAtr(new Line(lineString, line.getNumber())).get(1));
		}

		return new Command(RETURN, expression, line.getNumber());
	}

	private Command varDecl(Line line) throws UatException {
		int i, j;
		String[] aux;
		Matcher strM;
		ArrayList<String> tokens = new ArrayList<String>();
		String type, lineString = line.toString(), tmp = "";

		// from right after "let" until end of line
		lineString = lineString.substring(3).trim();
		// type
		i = lineString.lastIndexOf(":");
		tokens.add(lineString.substring(i + 1).trim());

		lineString = lineString.substring(0, i);
		aux = lineString.split(",");
		for (i = 0; i < aux.length; i++) {

			strM = strP.matcher(aux[i]);
			if (aux[i].contains("\"") && !strM.find()) {
				tmp = aux[i];

				for (j = i + 1; j < aux.length; j++) {
					tmp += "," + aux[j];

					strM = strP.matcher(tmp.trim());
					if (strM.find()) {
						break;
					}
				}
				i = j;
				tmp = this.fixVarDecl(tmp);
				tokens.add(tmp);
			}
			else {
				aux[i] = this.fixVarDecl(aux[i]);
				tokens.add(aux[i]);
			}
		}

		return new Command(DECL, tokens, line.getNumber());
	}

	private String fixVarDecl(String line) throws UatException {
		Matcher varNameM;
		String varName, fixedLine;
		int eqIndex = line.indexOf("=");

		if (eqIndex > 0) {
			varName = line.substring(0, eqIndex).trim();
			fixedLine = line.substring(eqIndex + 1).trim();
			fixedLine = new Expression(fixedLine).toPostfix();
			fixedLine = varName + Expression.VSEP.toString() + fixedLine;
		}
		else varName = fixedLine = line.trim();

		varNameM = varNameP.matcher(varName);
		if (varNameM.matches()) return fixedLine;
		else throw new UatException("invalidVarName", varName);
	}

	private Command varAtr(Line line) throws UatException {
		int equalsIndex;
		String[] atr = new String[2];
		String lineString = line.toString();
		ArrayList<String> assignment = new ArrayList<>();
		Matcher quotMarkM, strBackM, strAssignM, quotInStrM;

		equalsIndex = lineString.indexOf("=");
		atr[0] = lineString.substring(0, equalsIndex).trim();
		atr[1] = lineString.substring(equalsIndex + 1).trim();

		// if it's a string (enclosed with "");
		strAssignM = strAssignP.matcher(atr[1]);

        if (strAssignM.matches()) {
			// // removing first "
			// quotMarkM = quotMarkP.matcher(atr[1]);
			// atr[1] = quotMarkM.replaceFirst("");
			//
			// // removing ";
			// strBackM = strBackP.matcher(atr[1]);
			// atr[1] = strBackM.replaceFirst("");

			// replacing all \" for an actual "
			quotInStrM = quotInStrP.matcher(atr[1]);
	        atr[1] = quotInStrM.replaceAll("\"");
        }

        assignment.add(atr[0]);
        assignment.add(new Expression(atr[1]).toPostfix());

		return new Command(ATR, assignment, line.getNumber());
	}

	private Command print(Line line) throws UatException {
		int i, offset;
		String[] content;
		boolean breakLine = false;
		ArrayList<String> words = new ArrayList<>();
		String lineEnding, exp, text = "", lineString = line.toString();

		if (lineString.startsWith("println")) {
			breakLine = true;
		}

		lineString = lineString.substring(lineString.indexOf("(") + 1, lineString.lastIndexOf(")"));
		content = lineString.split("");
		for (i = 0; i < content.length; i++) {
			// \t 	Insert a tab in the text at this point.
			// \n 	Insert a newline in the text at this point.
			// \$ 	Insert a '$' character in the text at this point.
			// \- 	Insert a hyphen character in the text at this point.
			// \\ 	Insert a backslash character in the text at this point.
			if (content[i].equals("\\") && i + 1 < content.length) {

				if (content[i + 1].equals("t")) {
					text += "\t";
				}
				else if (content[i + 1].equals("n")) {
					text += "\n";
				}
				else if (content[i + 1].equals("$")) {
					text += "$";
				}
				else if (content[i + 1].equals("-")) {
					text += "-";
				}
				else if (content[i + 1].equals("\\")) {
					if (i + 2 < content.length && content[i + 2].equals("n")) {
						text += "\\n";
						i++;
					}
					else text += "\\";
				}
				else {
					throw new UatException("unknownEscape", content[i] + content[i + 1] + ", from \"" + lineString + "\"");
				}

				i++;
			}
			// content.length - 2 because it's the maximum index in the
			// string for a variable or expression to exist, for example: $x$
			else if (content[i].equals("$")) {
				if (!text.isEmpty()) {
					words.add(text);
					text = "";
				}

				// i is the index of the first '$'
				exp = this.getExp(lineString, i);

				if (!exp.isEmpty()) {
					words.add(Expression.SEP.toString() + new Expression(exp).toPostfix() + Expression.SEP.toString());
					i = lineString.indexOf("$", i + 1);
				}
				else {
					throw new UatException("invalidExp", line.toString());
				}
			}
			else {
				text += content[i];
			}
		}

		if (!text.isEmpty()) {
			words.add(text);
		}

		if (breakLine) return new Command(PRINTLN, words, line.getNumber());
		else return new Command(PRINT, words, line.getNumber());
	}

	// returns what was between '$'
	private String getExp(String content, int fromIndex) {
		int offset = content.indexOf("$", fromIndex + 1);

		if (offset > fromIndex) {
			return content.substring(fromIndex + 1, offset);
		}

		return "";
	}

	private Command scan(Line line) {
		boolean ln = false;
		String lineString = line.toString();
		ArrayList<String> vars = new ArrayList<>();

		if (lineString.startsWith("scanln")) {
			ln = true;
		}

		lineString = lineString.substring(lineString.indexOf("(") + 1, lineString.indexOf(")")).replaceAll(" ", "");

		if (ln) return new Command(SCANLN, lineString.split(","), line.getNumber());
		else return new Command(SCAN, lineString.split(","), line.getNumber());
	}

	private Command ifBr(Line line) throws UatException {
		boolean elsif = false;
		String lineString = line.toString();
		ArrayList<String> fields = new ArrayList<>();

		if (lineString.startsWith("elsif")) {
			elsif = true;
		}

		lineString = lineString.substring(lineString.indexOf("(") + 1, lineString.lastIndexOf(")")).trim();

		fields.add(new Expression(lineString).toPostfix());

		if (elsif) return new Command(ELSIF, fields, line.getNumber());
		else return new Command(IF, fields, line.getNumber());
	}

	private Command whileLoop(Line line) throws UatException {
		String lineString = line.toString();
		ArrayList<String> condition = new ArrayList<>();

		lineString = lineString.substring(lineString.indexOf("(") + 1, lineString.lastIndexOf(")")).trim();

		condition.add(new Expression(lineString).toPostfix());

		return new Command(WHILE, condition, line.getNumber());
	}

	private Command forLoop(Line line) throws UatException {
		Command comInit, comInc;
		int firstSemic, lastSemic;
		ArrayList<String> fields = new ArrayList<>();
		String lineString = line.toString(), forInit, forCond, forInc;

		firstSemic = lineString.indexOf(";");
		forInit = lineString.substring(lineString.indexOf("(") + 1, firstSemic).trim();
		if (forInit.equals("break") || forInit.equals("continue")) {
			throw new UatException("invalidLoopComm", line.toString());
		}
		comInit = this.buildCommand(new Line(forInit, line.getNumber()));

		lastSemic = lineString.lastIndexOf(";");
		forInc = lineString.substring(lastSemic + 1, lineString.lastIndexOf(")")).trim();
		if (forInc.equals("break") || forInc.equals("continue")) {
			throw new UatException("invalidLoopComm", line.toString());
		}
		comInc = this.buildCommand(new Line(forInc, line.getNumber()));

		forCond = lineString.substring(firstSemic + 1, lastSemic).trim();
		forCond = new Expression(forCond).toPostfix();

		fields.add(new Integer(comInit.code()).toString());
		fields.addAll(comInit.fields());

		fields.add(forCond);

		fields.add(new Integer(comInc.code()).toString());
		fields.addAll(comInc.fields());

		return new Command(FOR, fields, line.getNumber());
	}

	private Command breakLoop(Line line) {
		return new Command(BREAK, new ArrayList<String>(), line.getNumber());
	}

	private Command continueLoop(Line line) {
		return new Command(CONTINUE, new ArrayList<String>(), line.getNumber());
	}

	public void printCommandBlock(ArrayList<Command> block) {
		for (int i = 0, max = block.size(); i < max; i++) {
			System.out.println(block.get(i));
		}
	}

    public void printBlock(ArrayList<Line> block) {
        int i, max = block.size();

        for (i = 0; i < max; i++) {
            System.out.println(block.get(i));
        }
    }

    private static Map<String, Boolean> mapReservedWords() {
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        result.put("let", true);
        result.put("int", true);
        result.put("double", true);
        result.put("string", true);
        result.put("bool", true);
        result.put("true", true);
        result.put("false", true);

        result.put("print", true);
        result.put("println", true);
        result.put("scan", true);
        result.put("scanln", true);

        result.put("if", true);
        result.put("elsif", true);
        result.put("else", true);

        result.put("for", true);
        result.put("while", true);
        result.put("repetix", true);
        result.put("break", true);
        result.put("continue", true);

        result.put("fn", true);
        result.put("main", true);
        result.put("return", true);

        return Collections.unmodifiableMap(result);
    }

	// public static final String semicR = "( )*;";
	public static final String typeR = "int|double|string|bool";
	// public static final String fixAtrTypeR = ":( )*(" + typeR + ")" + semicR;
	// public static final String fixAtrTypeR = ":( )*(" + typeR + ")";
	public static final String varNameR = "[A-Za-z_][A-Za-z_0-9]*";
	public static final String emptyArrR = "(" + varNameR + ")\\[\\]";
	public static final String arrayR = "(" + varNameR + ")\\[([^\\[\\]])*?\\]";
	// public static final String wholeDeclR = "(let)( )+(.+)( )*:( )*(\\w)+" + semicR;
	public static final String wholeDeclR = "let +(.+?)( *\\, *(.+?))* *: *(" + typeR + ")";

	public static final String parenR = "\\(|\\)";
	public static final String boolOpR = "\\!|\\&\\&|\\|\\|";
	public static final String compOpR = "\\!\\=|\\<\\=|\\=\\=|\\>\\=|\\<|\\>";
	public static final String compOrBoolR = compOpR + "|" + boolOpR;
	public static final String mathOpR = "\\-|\\+|\\/|\\%|\\*|\\^";
	public static final String opNoParR = mathOpR + "|" + compOpR + "|" + boolOpR;

	public static final String wholeOpR = parenR + "|" + opNoParR;

	public static final String atrR = "(" + arrayR + "|" + varNameR + ")" + "( )*=( )*.+";
	// public static final String wholeAtrR = atrR + semicR;
	public static final String wholeAtrR = atrR;
	public static final String fixAtrR = ".+[,:]";
	public static final String stripAtrR = "( )*=( )*";

	public static final String fnR = "(fn)( )+(" + varNameR + ")( )*\\(.*\\)( )*\\{";

	// public static final String fnParenR = "( )*\\(.*\\)" + semicR;
	public static final String fnParenR = "( )*\\(.*\\)";
	public static final String printR = "(print|println)";
	public static final String wholePrintR = printR + fnParenR;
	public static final String scanContentR = "(" + varNameR + ")(( )*,( )*(" + varNameR + "))*";
	// public static final String wholeScanR = "(scan)( )*\\(( )*" + scanContentR + "( )*\\)" + semicR;
	public static final String wholeScanR = "(scan)( )*\\(( )*" + scanContentR + "( )*\\)";
	// public static final String wholeScanlnR = "(scanln)( )*\\(( )*" + scanContentR + "( )*\\)" + semicR;
	public static final String wholeScanlnR = "(scanln)( )*\\(( )*" + scanContentR + "( )*\\)";

	public static final String ifR = "(if)( )*\\(( )*";
	public static final String ifEndR = "( )*\\)( )*\\{";
	public static final String elsifR = "(elsif)( )*\\(( )*";
	public static final String wholeElseR = "(else)( )*\\{";

	public static final String forSplitR = "(for)( )*\\(.+\\;(( )*)\\b";
	public static final String wholeForR = "(for)( )*\\((.+;){2}.+\\)( )*\\{";

	public static final String quotMarkR = "\\\"";
	public static final String quotInStrR = "\\\\" + quotMarkR;
	// public static final String strBackR = quotMarkR + semicR;
	public static final String strBackR = quotMarkR;

	// strings with any character enclosed with "". Supports escaped " too.
	public static final String strR = quotMarkR + "(?:\\\\.|[^" + quotMarkR + "\\\\])*" + quotMarkR;
	// "(?:\\.|[^"\\])*"
	// matches 0 or more of the following, enclosed with "
	// matches a \ followed by any character, or...
	// doesn't match a " or a \

	// ([A-Za-z_][A-Za-z_0-9]*) *\(("(?:\\.|[^"\\])*"|[^\)])*\)
	public static final String fnCallR = "(" + varNameR + ") *\\(((" + strR + ")|[^\\)])*\\)";
	// public static final String fnCallR = varNameR + "( )*\\((.+?)*\\)";

	public static final String signR = "[+-]";
	public static final String intR = signR + "?[0-9]+";
	public static final String boolR = "(true|false)";

	public static final String ufpR = "(\\d+(\\.\\d*)?|(\\d*\\.)?\\d+)";
	public static final String fpR = "(" + signR + "( )*)?" + ufpR;

	public static final String invalidFpR = "([+-]( )*)?(\\d+( )+\\.(( )*\\d)*|(\\d( )*)*\\.( )+\\d+)";

	// from Javadoc
	private static final String Digits     = "(\\p{Digit}+)";
	private static final String HexDigits  = "(\\p{XDigit}+)";
	// an exponent is 'e' or 'E' followed by an optionally
	// signed decimal integer.
	private static final String Exp        = "[eE][+-]?"+Digits;
	public static final String ujfpR       =
	    "NaN|" +           // "NaN" string
	    "Infinity|" +      // "Infinity" string

	    // A decimal floating-point string representing a finite positive
	    // number without a leading sign has at most five basic pieces:
	    // Digits . Digits ExponentPart FloatTypeSuffix
	    //
	    // Since this method allows integer-only strings as input
	    // in addition to strings of floating-point literals, the
	    // two sub-patterns below are simplifications of the grammar
	    // productions from section 3.10.2 of
	    // The Java Language Specification.

	    // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
	    "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

	    // . Digits ExponentPart_opt FloatTypeSuffix_opt
	    "(\\.("+Digits+")("+Exp+")?)|"+

	    // Hexadecimal strings
	    "((" +
	    // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
	    "(0[xX]" + HexDigits + "(\\.)?)|" +

	    // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
	    "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

	    ")[pP][+-]?" + Digits + "))" +
	    "[fFdD]?)";
	public static final String jfpR = signR + "?(" + ujfpR + ")";

	private static void initPatterns() {
		anyP = Pattern.compile(".+");

		varNameP = Pattern.compile(varNameR);
		arrayP = Pattern.compile(arrayR);
		emptyArrP = Pattern.compile(emptyArrR);
		fnCallP = Pattern.compile(fnCallR);
		// typeP = Pattern.compile(typeR);
		// fixAtrTypeP = Pattern.compile(fixAtrTypeR);
		atrP = Pattern.compile(atrR);

		fnP = Pattern.compile(fnR);

		opNoParP = Pattern.compile(opNoParR);
		wholeOpP = Pattern.compile(wholeOpR);
		signP = Pattern.compile(signR);
		intP = Pattern.compile(intR);
		ufpP = Pattern.compile(ufpR);
		fpP = Pattern.compile(fpR);
		jufpP = Pattern.compile(ujfpR);
		jfpP = Pattern.compile(jfpR);
		invalidFpP = Pattern.compile(invalidFpR);
		boolP = Pattern.compile(boolR);
		charP = Pattern.compile("\\w");
		strP = Pattern.compile(strR);
		quotMarkP = Pattern.compile(quotMarkR);
		quotInStrP = Pattern.compile(quotInStrR);
		strBackP = Pattern.compile(strBackR);
		strNotEmptyP = Pattern.compile("\\S");
		opGroupP = Pattern.compile(signR + "(( )*" + signR + ")+");
		// "((" + signR + "+)(( )*(" + signR + "+))*)+"

		// strAssignP = Pattern.compile(strR + semicR);
		strAssignP = Pattern.compile(strR);
		upperCaseP = Pattern.compile("[A-Z]+");

		wholeDeclP = Pattern.compile(wholeDeclR);
		wholeAtrP = Pattern.compile(wholeAtrR);
		fixAtrP = Pattern.compile(fixAtrR);

		// semicP = Pattern.compile(semicR);
		wholePrintP = Pattern.compile(wholePrintR);
		wholeScanP = Pattern.compile(wholeScanR);
		wholeScanlnP = Pattern.compile(wholeScanlnR);

		ifP = Pattern.compile(ifR);
		wholeIfP = Pattern.compile(ifR + ".+" + ifEndR);
		elsifP = Pattern.compile(elsifR);
		wholeElsifP = Pattern.compile(elsifR + ".+" + ifEndR);
		wholeElseP = Pattern.compile(wholeElseR);
		ifEndingP = Pattern.compile(ifEndR);

		wholeWhileP = Pattern.compile("while( )*\\(.+\\)( )*\\{");
		forSplitP = Pattern.compile(forSplitR);
		wholeForP = Pattern.compile(wholeForR);

		parenP = Pattern.compile("[()]");
		numBuildP = Pattern.compile("(\\w|\\.)+");

		patternsInitd = true;
	}
}
