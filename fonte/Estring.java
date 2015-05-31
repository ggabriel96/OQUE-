package fonte;
import java.util.Random;

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

		while(true){
			if(l[w] != null){
				w++;
			}
			else{
				break;
			}
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


	public String removeEspaco(String linha)
	{
		int i = 0, j = 0, asp = 0;
		String nova = new String("");

		for(i = 0; i <  linha.length(); i++)
		{
			if(linha.charAt(i) == '"')
				asp++;

			if(asp%2 == 0)
			{
				if(linha.charAt(i) != ' ' && linha.charAt(i) != '\t')
					nova = nova + linha.charAt(i);
			}
			else
			{
				nova = nova + linha.charAt(i);
			}
		}
		return nova;
	}


	public String[] arrumavetor(String l[]){

		String linhas[] = new String[l.length];
		String Nlinha = new String("");
		Tokem tokens = new Tokem();

		// Deixa o vetor do tamanho certo, removendo os NULLs
		// linhas = Vremovenull(l); // dá falha de segmentação... o que seria remover os nulls?
		linhas = l;

		// Transforma o vetor em uma unica string
		Nlinha = concatenaVetor(linhas);

		// Remove os espacos
		Nlinha = removeEspaco(Nlinha);

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
		char a;

		if(pos > 0)
			a = linha.charAt(pos-1);
		else
			return "";

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


	public String condicional(String linha, int pos)
	{
		int i = 0;
		String nova = new String("");

		for(i = pos + 1; pos < linha.length(); i++)
		{
			if(linha.charAt(i) == ')')
				break;

			else
				nova = nova + linha.charAt(i);
		}
		return nova;
	}

	public boolean executaCondicional(String linha, int pos)
	{
		int i = 0, aux = 0, n = 0, flag = 0, flag2 = 0;
		boolean bool = true;
		double valor1 = 0, valor2 = 0;
		char tok = 'Q', tok2 = 'Q';
		String nova = new String("");
		String palavra1 = new String("");
		String palavra2 = new String("");
		String vale = new String("");
		String vale2 = new String("");
		Aritimeticos aritimetico = new Aritimeticos();

		nova = condicional(linha, pos);

		for(i = 0; i < nova.length(); i++)
		{
			if(tokem.ehToken(nova.charAt(i)) != 'N')
			{
				if(nova.charAt(i) == '-')
				{
					palavra1 += nova.charAt(i);
					continue;
				}

				tok = tokem.ehToken(nova.charAt(i));
				break;
			}

			else
				palavra1 += nova.charAt(i);
		}


		for(i = i + 1; i < nova.length(); i++)
		{
			if(tokem.ehToken(nova.charAt(i)) != 'N')
			{
				if(nova.charAt(i) == '-')
				{
					palavra2 += nova.charAt(i);
					continue;
				}
				tok2 = tokem.ehToken(nova.charAt(i));
				break;
			}

			else
				palavra2 += nova.charAt(i);
		}


		if(palavra2 != null && !palavra2.isEmpty())
		{
			if((palavra2.charAt(0) == '"') && (palavra2.charAt(palavra2.length()-1) == '"'))
			{
				palavra2 = palavra2.substring(1, palavra2.length()-1);
				flag = 0;
			}
			else
			{
				// É número ou variável inválida que começa com número.
				if("-1234567890".contains(palavra2.charAt(0) + ""))
				{
					flag = 1;
					valor2 = Double.parseDouble(palavra2);
				}

				// É variável
				else
				{
					palavra2 = aritimetico.pegaValor(palavra2);
					if(palavra2 != null)
					{
						if(palavra2.charAt(0) == '1'){
							palavra2 = palavra2.substring(1, palavra2.length());
							flag = 1;
							valor2 = Double.parseDouble(palavra2);
						}

						else if(palavra2.charAt(0) == '2'){
							palavra2 = palavra2.substring(1, palavra2.length());
							flag = 1;
							valor2 = Double.parseDouble(palavra2);
						}

						else if(palavra2.charAt(0) == '0'){
							palavra2 = palavra2.substring(2, palavra2.length()-1);
							flag = 0;
							vale = palavra2;
						}
					}
				}
			}
		}



		if(palavra1 != null && !palavra1.isEmpty())
		{
			if((palavra1.charAt(0) == '"') && (palavra1.charAt(palavra1.length()-1) == '"'))
			{
				palavra1 = palavra1.substring(1, palavra1.length()-1);
				flag2 = 0;
			}
			else
			{
				// É número ou variável inválida que começa com número.
				if("-1234567890".contains(palavra1.charAt(0) + ""))
				{
					valor1 = Double.parseDouble(palavra1);
					flag2 = 1;
				}

				// É variável
				else
				{
					palavra1 = aritimetico.pegaValor(palavra1);
					if(palavra1 != null)
					{
						if(palavra1.charAt(0) == '1'){
							palavra1 = palavra1.substring(1, palavra1.length());
							flag2 = 1;
							valor1 = Double.parseDouble(palavra1);
						}

						else if(palavra1.charAt(0) == '2'){
							palavra1 = palavra1.substring(1, palavra1.length());
							flag2 = 1;
							valor1 = Double.parseDouble(palavra1);
						}

						else if(palavra1.charAt(0) == '0'){
							palavra1 = palavra1.substring(2, palavra1.length()-1);
							flag2 = 0;
							vale2 = palavra1;
						}

					}
					else
					{
						Random gerador = new Random();
						System.out.println("ERRO: Variavel nao iniciada.	Numero aleatorio: " + gerador.nextInt());
						System.exit(0);
					}
				}
			}
		}


		if(tok == '@')
		{
			if(flag == 1 && flag == flag2)
			{
				if(valor1 == valor2)
					bool = true;
				else
					bool = false;
			}
			else if(flag == 0 && flag == flag2)
			{
				if(palavra1.equals(palavra2))
					bool = true;
				else
					bool = false;
			}

			else
			{
				System.out.println("Errrrrrrrrroooooooooooooooooooooooooooooooou.");
			}

		}

		else if(tok == '!')
		{
			if(flag == 1 && flag == flag2)
			{
				if(valor1 != valor2)
					bool = true;
				else
					bool = false;
			}
			else if(flag == 0 && flag == flag2)
			{
				if(!palavra1.equals(palavra2))
					bool = true;
				else
					bool = false;
			}

			else
			{
				System.out.println("Errrrrrrrrroooooooooooooooooooooooooooooooou.");
			}
		}

		else if(tok == '<')
		{
			if(valor1 < valor2)
				bool = true;
			else
				bool = false;
		}

		else if(tok == '>')
		{
			if(valor1 > valor2)
				bool = true;
			else
				bool = false;
		}

		return bool;
	}

	public void abreParenteses(String linha, int pos)
	{
		String nova = NantesTokem(linha, pos);
		if(nova.equals("SE"))
		{
			Interpretador.farol = executaCondicional(linha, pos);
			Interpretador.ELSE = true;
		}


		else if(nova.equals("repetix"))
		{

		}

		else if(nova.equals("IMPRIME"))
		{
			String maisNova = achaStrParen(linha, pos);
			String aux;

			for(int i = 0; i < maisNova.length(); i++)
			{
				if(maisNova.charAt(i) == '\\' && maisNova.charAt(i + 1) == 'n')
				{
					aux = maisNova.substring(0, i) + "\n" + maisNova.substring(i+2, maisNova.length());
					maisNova = aux;
				}
			}

			System.out.print(maisNova);
		}

		else
		{
			Random gerador = new Random();
			System.out.println("ERRO: WTF? o que você escreveu antes do parenteses?. Numero aleatório: " + gerador.nextInt());
			System.exit(0);
		}
	}


	public String achaStrParen(String linha, int pos)
	{
		Interpretador interpretador = new Interpretador();
		Aritimeticos aritimetico = new Aritimeticos();

		String nova = new String("");

		if(linha.charAt(linha.length()-2) == ')')
		{
			nova = linha.substring(pos + 1, linha.length()-2);

			if(nova.charAt(0) == '"' && nova.charAt(nova.length()-1) == '"')
			{
				return nova.substring(1, nova.length()-1);
			}
			else
			{
				nova = aritimetico.pegaValor(nova);
				if(nova.charAt(0) == '0')
					return nova.substring(2, nova.length() - 1);

				return nova.substring(1, nova.length());
			}
		}

		else
		{
			Random gerador = new Random();
			System.out.println("ERRO: Erro de sintaxe. Numero aleatório: " + gerador.nextInt());
			System.exit(0);
		}
		return nova;
	}

}
