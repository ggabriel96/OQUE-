class Interpretador{

// Atributos da classe ==========================
	private String linhas[];
	private Tokem tokens;
	private Estring estring;
	private Aritimeticos aritimetico;
	private Blocos blocos;
	private Inteiro_lista lista_int;
//==============================================

// Construtor das classes ======================
  public Interpretador(){
    tokens = new Tokem();
    estring = new Estring();
    aritimetico = new Aritimeticos();
	  blocos = new Blocos();
		lista_int = new Inteiro_lista();
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
  public String controle(String linha, int pos){

  // variaveis do metodo CONTROLE ==========
    String valorVariavel = new String("");
		String nomeVariavel = new String("");
	  int aqui = pos, tipo, int_ou_double;
    char tok;

  //=========================================

  // verefica o tipo do tokem ===============
    while(aqui < linha.length()){
      aqui = tokens.achaToken(linha, aqui);

      if(aqui == -1)
			     return "-1";

		  tok = linha.charAt(aqui);
			if(tok == ';'){
			     System.out.println("\n\n\n\nAchei um ponto e vírgula.");
			}

			if(tok == '='){
				System.out.println("\nAchei um igual.");

				linha = aritimetico.simplifica(linha, aqui); // simplifica a linha
				valorVariavel = estring.entreTokem(linha, aqui); // passa valores após do "=" para valorVariavel
				nomeVariavel = estring.antesTokem(linha, aqui); // passa valores antes do "=" para nomeVariavel

				System.out.println("\nLinha simplificada: " + linha); // imprime a linha so p teste
				System.out.println("\nvalorVariavel: " + valorVariavel); // imprime o que tem dps do "=" so p teste
				System.out.println("\nnomeVariavel: " + nomeVariavel); // imprime o que tem antes do "=" so p teste

				tipo = aritimetico.getTipo(valorVariavel);
				System.out.println("\ntipo da variavel: " + tipo);

				if(tipo == 1){// valorVariavel é um numero

					int_ou_double = (int)Double.parseDouble(valorVariavel); // converte a string para numero

					System.out.println("\nvalor da variavel em numero: "  + int_ou_double); // imprime numero
					if((int_ou_double % 1) == 0){
						lista_int.insere_lista_int(nomeVariavel, int_ou_double); // insere na lista int
						System.out.println("\nIsso esta na lista. Deu certo essa bagaça graças ao café!!!");
						lista_int.imprimir();
					}
					else{
						// é um double
					}

				}
				else{
						// a palavra é uma string
				}
			}

			if(tok == '+'){
				// Funcao que trata o mais
				System.out.println("Achei um mais.");
			}

			if(tok == '-'){
				// Funcao que trata o menos
				System.out.println("Achei um menos.");
			}

			if(tok == '*'){
				// Funcao que trata o vezes
				System.out.println("Achei um vezes.");
			}

			if(tok == '/'){
				// Funcao que trata o dividir
				System.out.println("Achei um dividir.");
			}

			if(tok == '{'){
				// Funcao que trata o escopo
				System.out.println("Achei um abre escopo.");
				linha = blocos.escopo(blocos.achaEscopo(linha, aqui));
			}

			if(tok == '}'){
				// Funcao que trata o escopo
				System.out.println("Achei um fecha escopo.");
				return linha;
			}

			aqui++;
		}//fim do while

  //===================================================

    return "0";
	}
}
