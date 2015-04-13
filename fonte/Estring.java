class Estring{

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


		int tamV = 0, auxi = 0, w = 0;
		char eh;
		auxi = 0;
		w = 0;

		// Conta o numero de "tarefas" (ponto e virgula)
		for(w = 0; w < Nlinha.length(); w++){
			eh = tokens.ehToken(Nlinha.charAt(w));

			if(eh == ';'){
				tamV++;
			}
		}

		// Reinstancia o vetor no tamanho certo
		linhas = new String[tamV];

		for(w = 0; w < tamV; w++){
			linhas[w] = new String("");
		}

		// Ate aqui esta certo <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		// Quebra a string novamente no vetor por tarefas (ponto e virgula)
		auxi = 0;
		w = 0;

		while(w < linhas.length){

			linhas[w] = linhas[w] + Nlinha.charAt(auxi);

			if(Nlinha.charAt(auxi) == ';'){
				w++;
			}

			auxi++;
		}

		return linhas;
	}
}
