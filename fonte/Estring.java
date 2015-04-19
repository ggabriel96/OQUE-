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
		int flag = 1;
		auxi = 0;
		w = 0;

		// Conta o numero de "tarefas" (ponto e virgula)
		for(w = 0; w < Nlinha.length(); w++){
			eh = tokens.ehToken(Nlinha.charAt(w));

			if(eh == '{')
			{
				flag = 0;
			}

			if(eh == '}')
			{
				flag = 1;
			}

			if(flag == 1)
			{
				if(eh == ';')
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
		int flag1 = 1, Nesc = 0;;
		while(w < linhas.length){

			linhas[w] = linhas[w] + Nlinha.charAt(auxi);

			if(Nlinha.charAt(auxi) == '{')
			{
				flag1 = 0;
				Nesc++;
			}

			if(Nlinha.charAt(auxi) == '}')
			{
				Nesc--;
				if(Nesc == 0)
					w++;
			}

			if(flag1 != 0)
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

	public String antesTokem(String linha, int pos) // pega o que esta antes do "="
	{
		String antesTokem = new String("");
		int i = 0, x = pos;
		int y = linha.charAt(tokem.achaToken(linha, i));

		if(y >= 0){
			i = 1;
		}

		for(; i != pos; i++){
			antesTokem = antesTokem + linha.charAt(i);
			x--;
		}
		return antesTokem;
	}
}
