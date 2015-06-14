package QUETEM;
/*******************************************************************************
Name: OQUE.java
Authors: Acácia dos Campos da Terra - terra.acacia@gmail.com
         Gabriel Batista Galli - g7.galli96@gmail.com
         Vladimir Belinski - vlbelinski@gmail.com

Description: Main class of a second version of OQUE, a programming language
             based on Java.
*******************************************************************************/

import java.io.*;
import java.util.*;
import java.util.regex.*;

class OQUE {
    public static void main(String[] args) {
        File f;
        int ind = 0;
        HashMap<String, ArrayList<Command>> code;
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
                    code = scanner.compile(f);

                    for(Map.Entry<String, ArrayList<Command>> entry: code.entrySet()) {
                        String name = entry.getKey();
                        ArrayList<Command> block = entry.getValue();

                        System.out.println("fn " + name);
                        scanner.printCommandBlock(block);
                        System.out.println();
                    }

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
