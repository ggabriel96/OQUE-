import java.lang.*;
import java.util.Random;

class Interpretador{

// Atributos da classe ==========================
	private String linhas[];
	private Tokem tokens;
	private Estring estring;
	private Aritimeticos aritimetico;
	private Blocos blocos;
	private Inteiro_lista lista_int;
	private Double_lista lista_double;
	private String_lista lista_string;
	public static boolean farol;
	public static boolean ELSE;
	private boolean encontrouInicioLaco, encontrouFimLaco;
	private LacoRepeticao lacorepeticao;

//==============================================

// Construtor das classes ======================
  public Interpretador(){
    tokens = new Tokem();
    estring = new Estring();
    aritimetico = new Aritimeticos();
	blocos = new Blocos();
	lista_int = new Inteiro_lista();
	lista_double = new Double_lista();
	lista_string = new String_lista();
	Interpretador.farol = true;
	Interpretador.ELSE = false;
	lacorepeticao = new LacoRepeticao();
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

  // printa a linha arrumado e joga dentro do controle e soma a qtd de caracteres da linha ======
    for(i = 0; i < this.linhas.length; i++){
      if(this.linhas[i] != null){
        System.out.println("Linha " + (i + 1) + ": " + this.linhas[i]);
        controle(this.linhas[i], 0);
        tamanho_da_linha = linhas[i].length();
      }
    }

  //========================================
  }

  // Tipo int para retorno de erros (ainda nao foi implementado).
  // Esta funcao serve para distribuir comandos, de acordo
  // com o toquem encontrado.
  //
  // Ela encontra tokem por tokem e chama sua determinada
  // funcao ate terminar de percorrer a linha recebida.
  public String controle(String linha, int pos){

  // variaveis do metodo CONTROLE ==========
    String valorVariavel = new String("");
		String nomeVariavel = new String("");
		String repetix = new String("");
		String entreparentes = new String("");
		String achouInteiro = new String("");
		String achouDouble = new String("");
		String achouString = new String("");
	  int aqui = pos, tipo;
		double int_ou_double;
    char tok, tipoTokem;

  //=========================================
	// função de repetição repetix
 /*

		repetix = lacorepeticao.achaRepetix(linha);
		if(!repetix.equals("\nrepetix e uma palavra reservada, nao pode usar no nome de variaveis\n") &&
				!repetix.equals("\nvoce esqueceu de abrir chaves no repetix") &&
				!repetix.equals("\nnao existe repetix na linha")){

				entreparentes = lacorepeticao.entreParenteses(linha, pos + 7);
				while( estring.executaCondicional(entreparentes, 0, true, '&') ){
					controle(linha, pos +7+entreparentes.length() + 3);
				}

		}
		else{
			System.out.println(repetix + "\n");
		}


 */
	// fim da função de repeticao repetix

  // verefica o tipo do tokem ===============
    while(aqui < linha.length()){
      aqui = tokens.achaToken(linha, aqui);

      if(aqui == -1)
			     return "-1";

		  tok = linha.charAt(aqui);
			if(tok == ';'){
			     System.out.println("\n\n~~~~ Achei um ponto e virgula. ~~~~\n\n");
			}

			if(tok == '='){

				linha = aritimetico.simplifica(linha, aqui); // simplifica a linha
				valorVariavel = estring.entreTokem(linha, aqui); // passa valores após do "=" para valorVariavel
				nomeVariavel = estring.NantesTokem(linha, aqui); // passa valores antes do "=" para nomeVariavel

				System.out.println("\nLinha simplificada: " + linha); // imprime a linha so p teste

				achouInteiro = lista_int.pesquisa_inteiro(nomeVariavel); // verefica se essa variavel ja esta na lista de inteiros
				achouDouble = lista_double.pesquisa_double(nomeVariavel); // verefica se essa variavel ja esta na lista de double
				achouString = lista_string.pesquisa_string(nomeVariavel); // verefica se essa variavel ja esta na lista de strings

				tipo = aritimetico.getTipo(valorVariavel); // verefica o tipo da atribuição se é numero ou string

				if(achouString == null && tipo == 1){// valorVariavel é um numero

					int_ou_double = Double.parseDouble(valorVariavel); // converte a string para numero

					if((int_ou_double % 1) == 0 && achouDouble == null){
						if(achouInteiro == null){
							int decimal = (int) int_ou_double;
							lista_int.insiraNaListaInt(nomeVariavel, decimal); // insere na lista int
							System.out.println("\n^^^^^^^^^^ Isso esta na lista de inteiros ^^^^^^^^^^");
							lista_int.imprimir();
						}
						else{
							int decimal = (int) int_ou_double;
							lista_int.insere_ja_existente(nomeVariavel, decimal); // insere na lista int em uma variavel ja existente
							System.out.println("\n^^^^^^^^^^ Isso esta na lista de inteiros ^^^^^^^^^^");
							lista_int.imprimir();
						}
					}
					else if((int_ou_double % 1) == 0 && achouDouble != null ){
						System.out.println("A variavel " + "'" + nomeVariavel + "'" + " e do tipo double, nao pode atribuir um valor inteiro para ela");
					}
					else if((int_ou_double % 1) != 0 && achouInteiro == null){
						if(achouDouble == null){
							double numDouble = int_ou_double;
							lista_double.insiraNaListaDouble(nomeVariavel, numDouble); // insere na lista double
							System.out.println("\n^^^^^^^^^^ Isso esta na lista de double ^^^^^^^^^^");
							lista_double.imprimir();
						}
						else{
							double numDouble = int_ou_double;
							lista_double.insere_ja_existente(nomeVariavel, numDouble); // insere na lista double em uma variavel ja existente
							System.out.println("\n^^^^^^^^^^ Isso esta na lista de double ^^^^^^^^^^");
							lista_double.imprimir();
						}
					}
					else if((int_ou_double % 1) != 0 && achouInteiro != null){
						System.out.println("A variavel " + "'" + nomeVariavel + "'" + " e do tipo inteiro, nao pode atribuir um valor double para ela");
					}
				}
				else if(achouString != null && tipo == 1){
					System.out.println("\nA variavel " + "'" + nomeVariavel + "'" + " e uma string nao pode atribuir valor para ela!");
				}
				else if(achouInteiro == null && achouDouble != null && tipo == 0){
					System.out.println("\nA variavel " + "'" + nomeVariavel + "'" + " e do tipo double, nao pode atribuir uma palavra a ela");
				}
				else if(achouInteiro != null && achouDouble == null && tipo == 0){
					System.out.println("\nA variavel " + "'" + nomeVariavel + "'" + " e do tipo inteira, nao pode atribuir uma palavra a ela");
				}
				else if(achouInteiro == null && achouDouble == null && tipo == 0){
						if(achouString == null){
							lista_string.insiraNaListaString(nomeVariavel, valorVariavel);
							System.out.println("\n^^^^^^^^^^ Isso esta na lista de string ^^^^^^^^^^");
							lista_string.imprimir();
						}
						else{
							lista_string.insere_ja_existente(nomeVariavel, valorVariavel);
							System.out.println("\n^^^^^^^^^^ Isso esta na lista de string ^^^^^^^^^^");
							lista_string.imprimir();
						}
				}
			}

			if(tok == '{'){
				if(Interpretador.farol)
				{
					String kl = estring.NantesTokem(linha, aqui);
					if(kl.equals("SENAO") )
					{
						if(Interpretador.ELSE)
							Interpretador.ELSE = false;

						else
						{
							Random gerador = new Random();
							System.out.println("ERRO: SENAO sem um SE. A maquina se autodestruira em 7 segundos. Numero aleatório: " + gerador.nextInt());
							System.exit(0);
						}
					}
					
					// Funcao que trata o escopo
					System.out.println("Achei um abre escopo.");
					linha = blocos.escopo(blocos.achaEscopo(linha, aqui));
					Interpretador.farol = false;
				}
				
				else
				{
					Interpretador.farol = true;
					break;
				}
			}

			if(tok == '}'){
				// Funcao que trata o escopo
				System.out.println("Achei um fecha escopo.");
				System.out.println(linha);
				return linha;
			}

			if(tok == '(')
			{
				System.out.println("Achei um abre escopo.");
				estring.abreParenteses(linha, aqui);
			}

			aqui++;
		}//fim do while

  //===================================================

    return "0";
	}
}
