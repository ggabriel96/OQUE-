package fonte;
import java.util.Scanner;
import java.io.*;

class OQUE {
    public static void main(String[] args) throws Exception {
        File f;
        Scanner s;
        int ind = 0;
        Interpretador oq;
        boolean hasParam = true, validParam = true;
        SourceScanner oqScanner = new SourceScanner();
		Inteiro_lista lista_int = new Inteiro_lista();
		Double_lista lista_double = new Double_lista();
		String_lista lista_string = new String_lista();

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
                oq = new Interpretador();
                oq.interpreta(oqScanner.scanToStringArray(f));
            }
        }
        else if (!validParam) {
            System.out.println("# Invalid input file.");
        }
    }
}
