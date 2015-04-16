class Interpretador{

// Atributos da classe ==========================
  private String linhas[];
  private Tokem tokens;
  private Estring estring;
  private Aritimeticos aritimetico;
//==============================================

// Construtor das classes ======================
  public Interpretador(){
    tokens = new Tokem();
    estring = new Estring();
    aritimetico = new Aritimeticos();
  }

//===============================================

// Metodos da classe ====================

  public void interpreta(String l[]){

  //variaveis do metodo INTERPRETA ======
    int i = 0;
    int tamanho_da_linha;

  //======================================

	// Arruma o vetor realocando, removendo espacos e separando por tarefas ========
    this.linhas = estring.arrumavetor(l);

  //======================================

  // printa a linha arrumado e joga dentro do controle e soma a qtd de caracteres da linha ======
    for(i = 0; i < this.linhas.length; i++){
      if(this.linhas[i] != null){
        System.out.println("Linha " + (i + 1) + ": " + this.linhas[i]);
        controle(this.linhas[i], 0);
        tamanho_da_linha = linhas[i].length();
				System.out.println("a linha tem: " + tamanho_da_linha + " caracteres.\n\n");
      }
    }
  //========================================

  }

  // ipo int para retorno de erros (ainda nao foi implementado).
  // Esta funcao serve para distribuir comandos, de acordo
  // com o toquem encontrado.
  //
  // Ela encontra tokem por tokem e chama sua determinada
  // funcao ate terminar de percorrer a linha recebida.
  public int controle(String linha, int pos){

  // variaveis do metodo CONTROLE ==========
    String palavra = new String("");
		int aqui = pos;
    char tok;

  //=========================================

  // verefica o tipo do tokem ===============
    while(aqui < linha.length()){
      aqui = tokens.achaToken(linha, aqui);

      if(aqui == -1)
			     return -1;

      tok = linha.charAt(aqui);

			if(tok == ';'){
			     System.out.println("Achei um ponto e vírgula.");
			}

			else if(tok == '='){
				System.out.println("Achei um igual.");
				linha = aritimetico.simplifica(linha, aqui);
				palavra = estring.entreTokem(linha, aqui);
				System.out.println("Linha simplificada: " + linha);
			}

			else if(tok == '+'){
			     // Funcao que trata o mais
					 System.out.println("Achei um mais.");
			}

			else if(tok == '-'){
			     // Funcao que trata o menos
					 System.out.println("Achei um menos.");
			}

			else if(tok == '*'){
			     // Funcao que trata o vezes
					 System.out.println("Achei um vezes.");
			}

			else if(tok == '/'){
			     // Funcao que trata o dividir
					 System.out.println("Achei um dividir.");
			}
			
			else if(tok == '{'){
			     // Funcao que trata o escopo
					 System.out.println("Achei um dividir.");
			}

			aqui++;
		}//fim do while

  //===================================================

    return 0;
	}
}
