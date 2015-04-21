import java.util.Scanner;
import java.io.*;

class OQUE {
    public static void main(String args[]) throws Exception{
		
		Inteiro_lista lista_int = new Inteiro_lista();
		Double_lista lista_double = new Double_lista();
		String_lista lista_string = new String_lista();
		
        File f;
        Scanner s;
        Interpretador b;

        String linhas[] = new String[2000];
        f = new File(args[0]);
        s = new Scanner(f);

        b = new Interpretador();

        int i = 0;
        while(s.hasNext())
        {
            linhas[i] = s.nextLine();
            i++;
        }

        b.interpreta(linhas); // Agora o interpretador que se vire
        
        lista_int.imprimir();
		lista_string.imprimir();
		lista_double.imprimir();
    }
}
