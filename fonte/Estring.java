import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;


// 	A função para remover null do meio do vetor foi pega em:
//	http://stackoverflow.com/questions/4150233/remove-null-value-from-string-array-in-java
//


class Estring
{

	public Tokem tokem;


	public Estring()
	{
		tokem  = new Tokem();
	}

// Operações da Classe ==========================
	public String[] Vremovenull(String l[]){
		int w = 0;
		String linhas[];

		while(w < l.length){
			if(l[w] != null){
				System.out.println("w vale " + w);
				w++;
			}
			else{
				break;
			}
			System.out.println(w);
		}

		linhas = new String[w];
		w--;

		while((w + 1) != 0){
			linhas[w] = l[w];
			w--;
		}

		return linhas;
	}

	public String concatenaVetor(String linhas[]){
		int i;
		String Nlinha = new String("");

		for(i = 0; i < linhas.length; i++){
			Nlinha = Nlinha + linhas[i];
		}

		return Nlinha;
	}

	public String[] arrumavetor(String l[]){

		String linhas[] = new String[l.length];
		String Nlinha = new String("");
		Tokem tokens = new Tokem();

		// Deixa o vetor do tamanho certo, removendo os NULLs
		linhas = Vremovenull(l);

		// Transforma o vetor em uma unica string
		Nlinha = concatenaVetor(linhas);

		// Remove os espacos
		Nlinha = Nlinha.replaceAll(" ","");
		Nlinha = Nlinha.replaceAll("	","");


		int tamV = 0, auxi = 0, w = 0;
		char eh;
		int flag = 0;
		auxi = 0;
		w = 0;

		// Conta o numero de "tarefas" (ponto e virgula)
		for(w = 0; w < Nlinha.length(); w++){
			eh = tokens.ehToken(Nlinha.charAt(w));

			if(eh == '{')
			{
				flag++;
			}

			if(eh == '}')
			{
				flag--;
			}

			if(flag == 0)
			{
				if(eh == ';' || eh == '}')
				{
					tamV++;
				}
			}
		}
		
		// Reinstancia o vetor no tamanho certo
		linhas = new String[tamV];
		
		System.out.println(tamV);
		
		for(w = 0; w < tamV; w++){
			linhas[w] = new String("");
		}


		// Quebra a string novamente no vetor por tarefas (ponto e virgula ou escopo)
		auxi = 0;
		w = 0;
		int flag1 = 0, Nesc = 0;;
		while(w < linhas.length){

			linhas[w] = linhas[w] + Nlinha.charAt(auxi);

			if(Nlinha.charAt(auxi) == '{')
			{
				flag1++;
			}

			if(Nlinha.charAt(auxi) == '}')
			{
				flag1--;
				if(flag1 == 0)
					w++;
			}

			if(flag1 == 0)
			{
				if(Nlinha.charAt(auxi) == ';'){
					w++;
				}
			}

			auxi++;
		}
		
		
		
		// /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		for(w = 0; w < linhas.length; w++)
		{
			if(linhas[w].contains("SE(") && linhas[w + 1].contains("SENAO{"))
			{
				if(w < (linhas.length - 1))
				{
					System.out.println("SEEEEEEEEEEEE");
					linhas[w] = linhas[w] + linhas[w + 1];
					linhas[w + 1] = null;
					w = w + 1;
				}
			}
		}
		int y = 0;
		
		
		linhas = Arrays.stream(linhas)
                     .filter(s -> (s != null && s.length() > 0))
                     .toArray(String[]::new);
		
		
		linhas = Vremovenull(linhas);
		for(w = 0; w < linhas.length; w++)
			System.out.println(linhas[w]);
			
		// */
		
		return linhas;
	}

	public String entreTokem(String linha, int pos) // pega o que esta depois do "="
	{
		String nova = new String("");
		char a = linha.charAt(tokem.achaToken(linha, pos+1));

		// Se houver dois tokens consecutivos (Para tratar numeros negativos, ou quase).
		if(linha.charAt(1) == '-')
			a = linha.charAt(tokem.achaToken(linha, pos+2));

		int i;
		for(i = pos + 1; linha.charAt(i) != ';'; i++){
			nova = nova + linha.charAt(i);
		}
		return nova;
	}
	
	public String NantesTokem(String linha, int pos)
	{
		String antesTokem = new String("");
		char a = linha.charAt(pos-1);
		int i;
		for(i = pos - 1; (a != '{' && a != ';' && i >= 0); i--)
		{
			antesTokem = antesTokem + linha.charAt(i);
			if(i > 0)
				a = linha.charAt(i - 1);
			
		}
		
		antesTokem = new StringBuffer(antesTokem).reverse().toString();
		return antesTokem;
		
	}

	public String antesTokem(String linha, int pos) // pega o que esta antes do "="
	{
		String antesTokem = new String("");
		char a = linha.charAt(pos-1);
		int i;
		for(i = 0; i != pos; i++){
			antesTokem = antesTokem + linha.charAt(i);
		}
		return antesTokem;
	}
}
