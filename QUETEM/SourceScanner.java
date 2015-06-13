package QUETEM;
/*******************************************************************************
Name: SourceScanner.java
Authors: Acácia dos Campos da Terra - terra.acacia@gmail.com
         Gabriel Batista Galli - g7.galli96@gmail.com
         Vladimir Belinski - vlbelinski@gmail.com

Description: Class SourceScanner of OQUE, a programming language based on Java.
             Responsible for reading the source code, put it into an ArrayList
			 of Lines and after change each line to a specific codification that
			 will be utilized for the compiller. Regexes and matchers are found
			 in this class.
*******************************************************************************/

import java.io.*;
import java.util.*;
import java.util.regex.*;

class SourceScanner {
	public static int BRACKET = -1, FN = 0, DECL = 1, ATR = 2, PRINT = 3, PRINTLN = 4, SCAN = 5, SCANLN = 6;
	private static boolean patternsInitd = false;
	private static final Map<String, Boolean> reservedWords = mapReservedWords();
	public static Pattern typeP, wholeDeclP, varNameP, atrP, wholeAtrP, semicP, wholePrintP, wholeScanP, wholeScanlnP, wholeOpP, signP, intP, fpP, charP, strP, strAssignP, quotMarkP, strBackP, parenP, numBuildP, boolP, upperCaseP, strNotEmptyP, opGroupP, ufpP, jufpP, jfpP, quotInStrP, invalidFpP, wholeIfP, wholeElsifP, wholeElseP, ifP, elsifP, ifEndingP, wholeWhileP, wholeForP, forSplitP, anyP, fixAtrP, fixAtrTypeP, fnP;

	public SourceScanner() {
		if (!patternsInitd) {
			initPatterns();
		}
	}

	public HashMap<String, ArrayList<Command>> compile(File f) throws IOException, UatException {
		Line line;
		int i, max;
		Matcher fnM;
		String command;
		ArrayList<Command> codeBlock;
		ArrayList<Line> fullCode = this.read(f);
		HashMap<String, ArrayList<Command>> code = new HashMap<>();

		for (i = 0, max = fullCode.size(); i < max; i++) {
			line = fullCode.get(i);
			command = line.toString();

			fnM = fnP.matcher(command);
			if (fnM.matches()) {
				codeBlock = this.buildBlock(fullCode, i);
				codeBlock.get(0).add((new Integer(codeBlock.size())).toString());
				i += codeBlock.size() - 1;

				System.out.println("\nCompiled block:");
				this.printCommandBlock(codeBlock);

				code.put(codeBlock.get(0).get(0), codeBlock); // adding to HashMap with fn name
			}
			else {
				System.out.println("SYNTAX 1");
				throw new UatException("syntaxError", command);
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
		int clBracket;
		Line line = null;
		boolean endOfBlock = false;
		String command, lineEnding;
		int i, max, bracketCount = 0;
		ArrayList<Command> block = new ArrayList<>();
		Matcher fnM, elseM, wholeIfM, elsifM, wholeAtrM, wholeForM, wholeDeclM, wholeScanM, wholeWhileM, wholePrintM, wholeScanlnM;

		for (i = index, max = code.size(); i < max && !endOfBlock; i++) {
			line = code.get(i);
			command = line.toString();

			// System.out.println("*** " + command);

			fnM = fnP.matcher(command);
			elseM = wholeElseP.matcher(command);
			wholeIfM = wholeIfP.matcher(command);
			elsifM = wholeElsifP.matcher(command);
			wholeAtrM = wholeAtrP.matcher(command);
			wholeForM = wholeForP.matcher(command);
			wholeDeclM = wholeDeclP.matcher(command);
			wholeScanM = wholeScanP.matcher(command);
			wholeWhileM = wholeWhileP.matcher(command);
			wholePrintM = wholePrintP.matcher(command);
			wholeScanlnM = wholeScanlnP.matcher(command);

			// clBracket = command.lastIndexOf("}");
			// if (clBracket >= 0) {
			// 	bracketCount--;
			// 	// add '}'
			// }
			if (command.startsWith("}")) {
				block.add(new Command(BRACKET, new ArrayList<String>(), line.getNumber()));
				bracketCount--;
			}
			else {
				if (fnM.matches()) {
					block.add(this.fn(line));
					bracketCount++;
				}
				else if (wholeDeclM.matches()) {
					block.add(this.varDecl(line));
				}
				else if (wholeAtrM.matches()) {
					block.add(this.varAtr(line));
				}
				else if (wholePrintM.matches()) {
					block.add(this.print(line));
				}
				else if (wholeScanM.matches() || wholeScanlnM.matches()) {
					block.add(this.scan(line));
				}
				// else if (wholeScanlnM.matches()) {
				// 	block.add(this.scanln(line));
				// }
				else if (wholeIfM.matches()) {
					block.add(this.ifBr(line));
					bracketCount++;
				}
				else if (elsifM.matches()) {
					block.add(this.elsifBr(line));
					bracketCount++;
				}
				else if (elseM.matches()) {
					block.add(this.elseBr(line));
					bracketCount++;
				}
				else if (wholeWhileM.matches()) {
					block.add(this.whileLoop(line));
					bracketCount++;
				}
				else if (wholeForM.matches()) {
					block.add(this.forLoop(line));
					bracketCount++;
				}
				else if (command.equals("break;")) {
					block.add(this.breakLoop(line));
				}
				else if (command.equals("continue;")) {
					block.add(this.continueLoop(line));
				}
				else {
					System.out.println("SYNTAX 2");
					throw new UatException("syntaxError", command);
				}
			}

			if (bracketCount == 0) {
				endOfBlock = true;
			}
		}

		if (!endOfBlock) {
			throw new UatException("bracketNotFound", code.get(index).toString());
		}

		return block;
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

	private Command varDecl(Line line) {
		int i, j;
		Matcher strM;
		String[] aux;
		String type, lineString = line.toString(), tmp = "";
		ArrayList<String> tokens = new ArrayList<String>();

		// from right after "let" until end of line
		lineString = lineString.substring(3);
		// type
		i = lineString.lastIndexOf(":");
		tokens.add(lineString.substring(i + 1, lineString.length()).trim());

		lineString = lineString.substring(0, i);
		aux = lineString.split(",");
		for (i = 0; i < aux.length; i++) {

			strM = strP.matcher(aux[i].trim());
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
				tokens.add(tmp.trim());
			}
			else {
				tokens.add(aux[i].trim());
			}
		}

		return new Command(DECL, tokens, line.getNumber());
	}

	private Command varAtr(Line line) {
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
			// removing first "
			quotMarkM = quotMarkP.matcher(atr[1]);
			atr[1] = quotMarkM.replaceFirst("");

			// removing ";
			strBackM = strBackP.matcher(atr[1]);
			atr[1] = strBackM.replaceFirst("");

			// replacing all \" for an actual "
			quotInStrM = quotInStrP.matcher(atr[1]);
	        atr[1] = quotInStrM.replaceAll("\"");
        }

        assignment.add(atr[0]);
        assignment.add(atr[1]);

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
					words.add(exp);
					i = lineString.indexOf("$", i + 1);
				}
				else {
					throw new UatException("invalidExp", lineString);
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

	private String getExp(String content, int fromIndex) {
		int offset = content.indexOf("$", fromIndex + 1);

		if (offset > fromIndex) {
			return content.substring(fromIndex, offset + 1);
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

	private Command scanln(Line line) {
		return null;
	}

	private Command ifBr(Line line) {
		return null;
	}

	private Command elsifBr(Line line) {
		return null;
	}

	private Command elseBr(Line line) {
		return null;
	}

	private Command whileLoop(Line line) {
		return null;
	}

	private Command forLoop(Line line) {
		return null;
	}

	private Command breakLoop(Line line) {
		return null;
	}

	private Command continueLoop(Line line) {
		return null;
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
        result.put("break", true);
        result.put("continue", true);

        result.put("fn", true);
        result.put("main", true);
        return Collections.unmodifiableMap(result);
    }

	// public static final String semicR = "( )*;";
	public static final String typeR = "int|double|string|bool";
	// public static final String fixAtrTypeR = ":( )*(" + typeR + ")" + semicR;
	public static final String fixAtrTypeR = ":( )*(" + typeR + ")";
	public static final String varNameR = "[A-Za-z_][A-Za-z_0-9]*";
	// public static final String wholeDeclR = "(let)( )+(.+)( )*:( )*(\\w)+" + semicR;
	public static final String wholeDeclR = "(let)( )+(.+)( )*:( )*(\\w)+";

	public static final String parenR = "\\(|\\)";
	public static final String boolOpR = "\\!|\\&\\&|\\|\\|";
	public static final String compOpR = "\\!\\=|\\<\\=|\\=\\=|\\>\\=|\\<|\\>";
	public static final String compOrBoolR = compOpR + "|" + boolOpR;
	public static final String mathOpR = "\\-|\\+|\\/|\\%|\\*|\\^";

	public static final String wholeOpR = parenR + "|" + mathOpR + "|" + compOpR + "|" + boolOpR;

	public static final String atrR = varNameR + "( )*=( )*.+";
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
		typeP = Pattern.compile(typeR);
		fixAtrTypeP = Pattern.compile(fixAtrTypeR);
		atrP = Pattern.compile(atrR);

		fnP = Pattern.compile(fnR);

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
