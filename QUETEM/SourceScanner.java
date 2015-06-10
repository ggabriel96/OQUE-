package QUETEM;

import java.io.*;
import java.util.*;
import java.util.regex.*;

class SourceScanner {
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
				// add to HashMap with fn name
			}
			else {
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
		Matcher elseM, wholeIfM, elsifM, wholeAtrM, wholeForM, wholeDeclM, wholeScanM, wholeWhileM, wholePrintM, wholeScanlnM;

		for (i = index, max = code.size(); i < max && !endOfBlock; i++) {
			line = code.get(i);
			command = line.toString();

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

			clBracket = command.lastIndexOf("}");
			if (clBracket >= 0) {
				bracketCount--;
				// add '}'
			}
			else {
				if (wholeDeclM.matches()) {
					block.add(this.varDecl(command));
				}
				else if (wholeAtrM.matches()) {
					block.add(this.varAtr(command));
				}
				else if (wholePrintM.matches()) {
					block.add(this.print(command));
				}
				else if (wholeScanM.matches()) {
					block.add(this.scan(command));
				}
				else if (wholeScanlnM.matches()) {
					block.add(this.scanln(command));
				}
				else if (wholeIfM.matches()) {
					block.add(this.ifBr(command));
					bracketCount++;
				}
				else if (elsifM.matches()) {
					block.add(this.elsifBr(command));
					bracketCount++;
				}
				else if (elseM.matches()) {
					block.add(this.elseBr(command));
					bracketCount++;
				}
				else if (wholeWhileM.matches()) {
					block.add(this.whileLoop(command));
					bracketCount++;
				}
				else if (wholeForM.matches()) {
					block.add(this.forLoop(command));
					bracketCount++;
				}
				else if (command.equals("break;")) {
					block.add(this.breakLoop(command));
				}
				else if (command.equals("continue;")) {
					block.add(this.continueLoop(command));
				}
				else {
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

	private Command varDecl(String line) {
		return null;
	}

	private Command varAtr(String line) {
		return null;
	}

	private Command print(String line) {
		return null;
	}

	private Command scan(String line) {
		return null;
	}

	private Command scanln(String line) {
		return null;
	}

	private Command ifBr(String line) {
		return null;
	}

	private Command elsifBr(String line) {
		return null;
	}

	private Command elseBr(String line) {
		return null;
	}

	private Command whileLoop(String line) {
		return null;
	}

	private Command forLoop(String line) {
		return null;
	}

	private Command breakLoop(String line) {
		return null;
	}

	private Command continueLoop(String line) {
		return null;
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

	public static final String semicR = "( )*;";
	public static final String typeR = "int|double|string|bool";
	public static final String fixAtrTypeR = ":( )*(" + typeR + ")" + semicR;
	public static final String varNameR = "[A-Za-z_][A-Za-z_0-9]*";
	public static final String wholeDeclR = "(let)( )+(.+)( )*:( )*(\\w)+" + semicR;

	public static final String parenR = "\\(|\\)";
	public static final String boolOpR = "\\!|\\&\\&|\\|\\|";
	public static final String compOpR = "\\!\\=|\\<\\=|\\=\\=|\\>\\=|\\<|\\>";
	public static final String compOrBoolR = compOpR + "|" + boolOpR;
	public static final String mathOpR = "\\-|\\+|\\/|\\%|\\*|\\^";

	public static final String wholeOpR = parenR + "|" + mathOpR + "|" + compOpR + "|" + boolOpR;

	public static final String atrR = varNameR + "( )*=( )*.+";
	public static final String wholeAtrR = atrR + semicR;
	public static final String fixAtrR = ".+[,:]";
	public static final String stripAtrR = "( )*=( )*";

	public static final String fnR = "(fn)( )+(" + varNameR + ")( )*\\(.*\\)( )*\\{";

	public static final String fnParenR = "( )*\\(.*\\)" + semicR;
	public static final String printR = "(print|println)";
	public static final String wholePrintR = printR + fnParenR;
	public static final String scanContentR = "(" + varNameR + ")(( )*,( )*(" + varNameR + "))*";
	public static final String wholeScanR = "(scan)( )*\\(( )*" + scanContentR + "( )*\\)" + semicR;
	public static final String wholeScanlnR = "(scanln)( )*\\(( )*" + scanContentR + "( )*\\)" + semicR;

	public static final String ifR = "(if)( )*\\(( )*";
	public static final String ifEndR = "( )*\\)( )*\\{";
	public static final String elsifR = "(elsif)( )*\\(( )*";
	public static final String wholeElseR = "(else)( )*\\{";

	public static final String forSplitR = "(for)( )*\\(.+\\;(( )*)\\b";
	public static final String wholeForR = "(for)( )*\\((.+;){2}.+\\)( )*\\{";

	public static final String quotMarkR = "\\\"";
	public static final String quotInStrR = "\\\\" + quotMarkR;
	public static final String strBackR = quotMarkR + semicR;

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

		strAssignP = Pattern.compile(strR + semicR);
		upperCaseP = Pattern.compile("[A-Z]+");

		wholeDeclP = Pattern.compile(wholeDeclR);
		wholeAtrP = Pattern.compile(wholeAtrR);
		fixAtrP = Pattern.compile(fixAtrR);

		semicP = Pattern.compile(semicR);
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
