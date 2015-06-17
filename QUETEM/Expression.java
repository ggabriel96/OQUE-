package QUETEM;

import java.util.*;
import java.util.regex.*;

class Expression {
	public String original, value;
	public static final Character SEP = 96; // 31, 96 to test
	public static final Character VSEP = 39; // 29, 39 to test
    private static final Map<String, Integer> precedence = mapPrecedence();

	public Expression(String value) throws UatException {
		this.original = this.value = value.trim();
		// System.out.println("Expression 1: " + this.value);
		this.value = this.fixSpaces(this.value);
		// System.out.println("Expression 2: " + this.value);
		this.value = this.fixSignals(this.value);
		// System.out.println("Expression 3: " + this.value);
    }

	public String toString() {
        return this.value;
    }

	private String fixSpaces(String value) throws UatException {
		String[] output;
        boolean foundFn, foundArr;
		int index = 0, index1, index2, j, k;
		TreeMap<Integer, String> tokens = new TreeMap<Integer, String>();
		String aux = value, tmp = "", tmp1 = "", tmp2 = "", fixed = "", inv;
		Matcher notEmptyM, opGroupM, wholeOpM, ufpM, strM, varNameM, arrayM, fnCallM, invalidFpM;

		invalidFpM = SourceScanner.invalidFpP.matcher(aux);
		if (invalidFpM.find()) {
			throw new UatException("invalidExp", aux);
		}

		// if the last token is an operator, but not a ")"
		inv = aux.substring(aux.length() - 1);
		wholeOpM = SourceScanner.wholeOpP.matcher(inv);
		if (wholeOpM.find() && !inv.equals(")")) {
			throw new UatException("invalidExp", aux);
		}

		// Fixing signs that aren't a substring
		// output is just temporary here...
		output = aux.split(SourceScanner.strR);
		for (String s: output) {
			opGroupM = SourceScanner.opGroupP.matcher(s);
			if (opGroupM.find()) {
				tmp = opGroupM.group();
				// gets aux from start until the repeating signs I found then
				// appends the result of fixing those signs and then
				// appends what was left in aux after the repeating signs
				aux = aux.substring(0, aux.indexOf(tmp)) +
									this.fixRepSign(tmp) +
									aux.substring(aux.indexOf(tmp) + tmp.length());
			}
		}

		arrayM = SourceScanner.arrayP.matcher(aux);
        fnCallM = SourceScanner.fnCallP.matcher(aux);

		index1 = index2 = -1;
		foundFn = fnCallM.find();
        foundArr = arrayM.find();
        while (foundFn || foundArr) {
			index1 = index2 = -1;

            if (foundFn) {
                tmp1 = fnCallM.group();
                index1 = fnCallM.start();
            }
			if (foundArr) {
				tmp2 = arrayM.group();
				index2 = arrayM.start();
			}

            if ((index1 >= 0 && index2 >= 0 && index1 <= index2) || (index1 >= 0 && index2 < 0)) {
                j = tmp1.indexOf("(");
                k = tmp1.lastIndexOf(")");
                if (j > 0 && k > 0) {
                    inv = tmp1.substring(j + 1, k);
					if (!inv.isEmpty()) {
						tmp1 = tmp1.substring(0, j + 1) + new Expression(inv).toPostfix() + ")";
					}
                }

				tmp1 = tmp1.replaceAll(SEP.toString(), VSEP.toString());

                tokens.put(index1, tmp1);
                aux = fnCallM.replaceFirst(this.spacenize(tmp1));
            }
            else if (index2 >= 0) {
                j = tmp2.indexOf("[");
                k = tmp2.lastIndexOf("]");
                if (j > 0 && k > 0) {
                    inv = tmp2.substring(j + 1, k);
                    tmp2 = tmp2.substring(0, j + 1) + new Expression(inv).toPostfix() + "]";
                }

				tmp2 = tmp2.replaceAll(SEP.toString(), VSEP.toString());

                tokens.put(index2, tmp2);
                aux = arrayM.replaceFirst(this.spacenize(tmp2));
            }

			arrayM = SourceScanner.arrayP.matcher(aux);
            fnCallM = SourceScanner.fnCallP.matcher(aux);

            foundFn = fnCallM.find();
			foundArr = arrayM.find();
        }

        strM = SourceScanner.strP.matcher(aux);
		ufpM = SourceScanner.jufpP.matcher(aux);
		wholeOpM = SourceScanner.wholeOpP.matcher(aux);
		varNameM = SourceScanner.varNameP.matcher(aux);
		notEmptyM = SourceScanner.strNotEmptyP.matcher(aux);

		while (notEmptyM.find()) {

			if (strM.find()) {
				tmp = strM.group();
				index = strM.start();

				tokens.put(index, tmp);
				aux = strM.replaceFirst(this.spacenize(tmp));
			}
			else if (wholeOpM.find()) {
				tmp = wholeOpM.group();
				index = wholeOpM.start();

				tokens.put(index, tmp);
				aux = wholeOpM.replaceFirst(this.spacenize(tmp));
			}
			else if (varNameM.find()) {
				tmp = varNameM.group();
				index = varNameM.start();

				tokens.put(index, tmp);
				aux = varNameM.replaceFirst(this.spacenize(tmp));
			}
			else if (ufpM.find()) {
				tmp = ufpM.group();
				index = ufpM.start();

				tokens.put(index, tmp);
				aux = ufpM.replaceFirst(this.spacenize(tmp));
			}
			else {
				throw new UatException("invalidExp", this.value);
			}

			strM = SourceScanner.strP.matcher(aux);
			ufpM = SourceScanner.jufpP.matcher(aux);
			wholeOpM = SourceScanner.wholeOpP.matcher(aux);
			varNameM = SourceScanner.varNameP.matcher(aux);
			notEmptyM = SourceScanner.strNotEmptyP.matcher(aux);
		}

		output = new String[tokens.size()];
		tokens.values().toArray(output);

		for (String s: output) {
			fixed += s + SEP;
		}

		return fixed;
	}

	public String spacenize(String token) {
		int i, max = token.length();
		String repl = "";

		for (i = 0; i < max; i++) {
			repl += " ";
		}

		return repl;
	}

	private String fixRepSign(String value) {
        String aux;
        do {
            aux = value.replaceAll("\\+( )*\\-|\\-( )*\\+", "-").replaceAll("\\-( )*\\-", "+").replaceAll("\\+( )*\\+", "+");

            if (!value.equals(aux)) {
                value = aux;
                aux = "";
            }
        } while (aux.isEmpty());

		return value;
    }

	private String fixSignals(String value) {
        int i, max;
        boolean next;
        String t = new String("");
        Matcher wholeOpM, numBuildM;
        ArrayList<String> ts = new ArrayList<String>();
        String[] tokens = value.split(SEP.toString());

        for (i = 0; i < tokens.length; i++) {
            wholeOpM = SourceScanner.wholeOpP.matcher(tokens[i]);

            if (this.isSignal(tokens, i)) {
				ts.add(tokens[i] + tokens[i + 1]);
				i++;
            }
            else {
                ts.add(tokens[i]);
            }
        }

        // building the resulting string from the ArrayList
        t = "";
        max = ts.size();
        for (i = 0; i < max; i++) {
            t += ts.get(i);
            if (i < max - 1) t += SEP;
        }

        return t;
    }

	private boolean isSignal(String[] tokens, int i) {
        boolean result = false;
        Matcher numBuildM, signM, opBeforeM;

        // (if the current token is + or -) && (the next token is a
        // number) && (either we are looking at the first token ||
        // we are looking at a token that comes right after a '(' ||
        // we are looking at a token that comes right after another
        // token that is not a + or -)...
        // then, it's a signal, not an operation :)
        signM = SourceScanner.signP.matcher(tokens[i]);
        if (signM.matches() && i + 1 < tokens.length) {
            numBuildM = SourceScanner.ufpP.matcher(tokens[i + 1]);

            if (numBuildM.matches()) {
                if (i == 0) {
					if (i + 2 < tokens.length && tokens[i + 2].equals("^")) {
						result = false;
					}
					else {
						result = true;
					}
                }
                else if (i - 1 >= 0) {
                    // as we treated all the cases with duplicated +/- signs,
                    // is there's any operation token right before a +/-, it
                    // is obviously not a + or - and thus this operator is indeed
                    // a number sign. Because if it was a normal operator, the
                    // previous token would be a number.
                    opBeforeM = SourceScanner.wholeOpP.matcher(tokens[i - 1]);

                    if (opBeforeM.matches()) {
                        result = true;
                    }
                }
            }
        }

        return result;
    }

    // our implementation of Dijkstra's Shunting Yard algorithm
	public String toPostfix() throws UatException {
        String rpn = new String("");
        Matcher wholeOpM, parenM, opNoParM;
        int i, pt, ps, opCount = 0, tCount = 0;
        Stack<String> op = new Stack<String>();
        String[] tokens = this.value.split(SEP.toString());

        for (i = 0; i < tokens.length; i++) {
            wholeOpM = SourceScanner.wholeOpP.matcher(tokens[i]);
			opNoParM = SourceScanner.opNoParP.matcher(tokens[i]);

			if (opNoParM.matches()) {
				opCount++;
			}

            if (tokens[i].equals("(")) {
                op.push(tokens[i]);
            }
            else if (tokens[i].equals(")")) {
                // pop everything until you find the '('
                while (!op.peek().equals("(")) {
                    rpn += op.pop() + SEP;
                }

                op.pop(); // discard the "("
            }
            else if (wholeOpM.matches()) {
                while (!op.isEmpty()) {

					pt = precedence.get(tokens[i]);
                    ps = precedence.get(op.peek());

                    if ((!tokens[i].equals("^") && pt <= ps) || (tokens[i].equals("^") && pt < ps)) {
                        rpn += op.pop() + SEP;
                    }
                    else break;
                }

                op.push(tokens[i]);
            }
            else {
				tCount++;
                rpn += tokens[i] + SEP;
            }
        }

		if (opCount != tCount - 1) {
			throw new UatException("invalidExp", this.original);
		}

        while (!op.isEmpty()) {
            parenM = SourceScanner.parenP.matcher(op.peek());

            if (parenM.matches()) {
                throw new UatException("missingParen", this.original);
            }

            rpn += op.pop() + SEP;
        }

        // the last SEP
        rpn = rpn.substring(0, rpn.length() - 1);

		// System.out.println("[INFO_LOG]: TO_POSTFIX_RPN = {" + rpn + "}");

        return rpn;
    }

    private static Map<String, Integer> mapPrecedence() {
        Map<String, Integer> result = new HashMap<String, Integer>();
        result.put("^", 5);
		result.put("!", 5);

        result.put("*", 4);
        result.put("/", 4);
        result.put("%", 4);

        result.put("+", 3);
        result.put("-", 3);

        result.put("<", 2);
        result.put(">", 2);
        result.put("<=", 2);
        result.put("==", 2);
        result.put(">=", 2);
        result.put("!=", 2);

        result.put("&&", 1);
        result.put("||", 1);

        result.put("(", 0);
        result.put(")", 0);

        return Collections.unmodifiableMap(result);
    }
}
