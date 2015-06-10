package QUETEM;

import java.io.*;
import java.util.*;
import java.util.regex.*;

class OQUE {
    public static void main(String[] args) {
        File f;
        int ind = 0;
        boolean hasParam = true, validParam = true;
        Interpreter interpreter = new Interpreter();
        SourceScanner scanner = new SourceScanner();
        ArrayList<Line> input = new ArrayList<Line>();

        if (args.length > 0) {
            for (ind = 0; ind < args.length; ind++) {
                if (args[ind].endsWith(".oq")) {
                    validParam = true;
                    break;
                }
                else validParam = false;
            }
        }
        else {
            System.out.println("* No input file detected.\n");
            hasParam = false;
        }

        if (hasParam && validParam) {
            f = new File(args[ind]);

            if (f.exists() && !f.isDirectory()) {

				try {
					input = scanner.read(f);
                    interpreter.execute(input);
                }
                catch (IOException ioe) {
					ioe.toString();
				}
                catch (UatException ue) {
                    System.out.println("\n> " + ue.getMessage() + " @ line #" + ue.getNumber() + ":");
                    System.out.println(ue.getLine());
                    System.exit(1);
                }

            }
            else {
                System.out.println("# File \"" + args[ind] + "\" not found.");
            }
        }
        else if (!validParam) {
            System.out.println("# Invalid input file.");
        }
    }
}
