package fonte;
//                             OQUE
// Trabalho construido com o proposito de desenvolvimento de um interpretador para arquivos de textos .oq
// Autores: Cleiton A. Ambrosini        E-mail: cleito.am@gmail.com
//          Marco Aurélio Alves Puton   E-mail: marcoputon@gmail.com
//
//==================================================================================

import java.lang.*;
import java.util.Random;
import java.util.Scanner;

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

	// Arruma o vetor realocando, removendo espacos e separando por tarefas ========
    this.linhas = estring.arrumavetor(l);

  // printa a linha arrumado e joga dentro do controle e soma a qtd de caracteres da linha ======
    for(i = 0; i < this.linhas.length; i++){
      if(this.linhas[i] != null){

        //System.out.println("Linha " + (i + 1) + ": " + this.linhas[i]);
        controle(this.linhas[i], 0);
        tamanho_da_linha = linhas[i].length();
      }
    }
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
		String caracteresEspeciais = new String("={([+-~$@!#<>]});&^");
	  int aqui = pos, tipo;
		double int_ou_double;
    char tok, tipoTokem;

		// função de repetição ~repetix~
			repetix = lacorepeticao.achaRepetix(linha);
			if(!repetix.equals("'repetix' e uma palavra reservada pelo Imperio Galactico, \nso pode usar essa palavra (sem ser como laco) quem segue o lado negro da forca\nsinto muito =/") && repetix.equals("repetix")){

					if((pos + 7) <= linha.length()){
						if(linha.charAt(pos + 7) == caracteresEspeciais.charAt(2)){
							entreparentes = lacorepeticao.entreParenteses(linha, pos + 7);
							entreparentes = "(" + entreparentes + ")";
				  	}
					}

					while(estring.executaCondicional(entreparentes, pos)){
							controle(linha.substring(pos +7+ entreparentes.length()), 0);
					}
					return"0";
			}
			else{
				System.out.print(repetix);
			}
		// fim da função de repeticao ~repetix~

  // verefica o tipo do tokem ===============
    while(aqui < linha.length()){
      aqui = tokens.achaToken(linha, aqui);

      if(aqui == -1)
			     return "-1";

		  tok = linha.charAt(aqui);
			if(tok == ';'){

			}

			if(tok == '='){

				linha = aritimetico.simplifica(linha, aqui); // simplifica a linha
				valorVariavel = estring.entreTokem(linha, aqui); // passa valores após do "=" para valorVariavel
				nomeVariavel = estring.NantesTokem(linha, aqui); // passa valores antes do "=" para nomeVariavel

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
						}
						else{
							int decimal = (int) int_ou_double;
							lista_int.insere_ja_existente(nomeVariavel, decimal); // insere na lista int em uma variavel ja existente
						}
					}
					else if((int_ou_double % 1) == 0 && achouDouble != null ){
						System.out.println("A variavel " + "'" + nomeVariavel + "'" + " e do tipo double, voce esta tentando atribuir um valor do tipo 'int' para ela");
					}
					else if((int_ou_double % 1) != 0 && achouInteiro == null){
						if(achouDouble == null){
							double numDouble = int_ou_double;
							lista_double.insiraNaListaDouble(nomeVariavel, numDouble); // insere na lista double
						}
						else{
							double numDouble = int_ou_double;
							lista_double.insere_ja_existente(nomeVariavel, numDouble); // insere na lista double em uma variavel ja existente
						}
					}
					else if((int_ou_double % 1) != 0 && achouInteiro != null){
						System.out.println("A variavel " + "'" + nomeVariavel + "'" + " e do tipo inteiro, voce esta tentando atribuir um valor do tipo 'double' para ela");
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
						}
						else{
							lista_string.insere_ja_existente(nomeVariavel, valorVariavel);
						}
				}
			}


//====================================================================================


			if(tok == '['){

				achouInteiro = null;
				achouDouble  = null;
				achouString  = null;

				int posicaotoken = aqui;

				if(posicaotoken == 0){
					System.out.println("Voce precisa declarar uma variavel para receber o valor");
				}
				else if (posicaotoken != 0) {
					String variavelDaEntrada = new String("");
					variavelDaEntrada = estring.NantesTokem(linha, aqui); // passa valores antes do "[" para variavelDaEntrada

					Scanner entrada = new Scanner(System.in);
					String valorEntrada = entrada.nextLine();

					achouInteiro = lista_int.pesquisa_inteiro(variavelDaEntrada); // verefica se essa variavel ja esta na lista de inteiros
					achouDouble = lista_double.pesquisa_double(variavelDaEntrada); // verefica se essa variavel ja esta na lista de double
					achouString = lista_string.pesquisa_string(variavelDaEntrada); // verefica se essa variavel ja esta na lista de strings

					tipo = aritimetico.getTipo(valorEntrada); // verefica o tipo da atribuição se é numero ou string


					if(achouString == null && tipo == 1){// valorVariavel é um numero

						int_ou_double = Double.parseDouble(valorEntrada); // converte a string para numero

						if((int_ou_double % 1) == 0 && achouDouble == null){
							if(achouInteiro == null){
								int decimal = (int) int_ou_double;
								lista_int.insiraNaListaInt(variavelDaEntrada, decimal); // insere na lista int
							}
							else{
								int decimal = (int) int_ou_double;
								lista_int.insere_ja_existente(variavelDaEntrada, decimal); // insere na lista int em uma variavel ja existente
							}
						}
						else if((int_ou_double % 1) == 0 && achouDouble != null ){
							System.out.println("A variavel " + "'" + variavelDaEntrada + "'" + " e do tipo double, voce esta tentando atribuir um valor do tipo 'int' para ela");
						}
						else if((int_ou_double % 1) != 0 && achouInteiro == null){
							if(achouDouble == null){
								double numDouble = int_ou_double;
								lista_double.insiraNaListaDouble(variavelDaEntrada, numDouble); // insere na lista double
							}
							else{
								double numDouble = int_ou_double;
								lista_double.insere_ja_existente(variavelDaEntrada, numDouble); // insere na lista double em uma variavel ja existente
							}
						}
						else if((int_ou_double % 1) != 0 && achouInteiro != null){
							System.out.println("A variavel " + "'" + variavelDaEntrada + "'" + " e do tipo inteiro, voce esta tentando atribuir um valor do tipo 'double' para ela");
						}
					}
					else if(achouString != null && tipo == 1){
						System.out.println("\nA variavel " + "'" + variavelDaEntrada + "'" + " e uma string nao pode atribuir valor para ela!");
					}
					else if(achouInteiro == null && achouDouble != null && tipo == 0){
						System.out.println("\nA variavel " + "'" + variavelDaEntrada + "'" + " e do tipo double, nao pode atribuir uma palavra a ela");
					}
					else if(achouInteiro != null && achouDouble == null && tipo == 0){
						System.out.println("\nA variavel " + "'" + variavelDaEntrada + "'" + " e do tipo inteira, nao pode atribuir uma palavra a ela");
					}
					else if(achouInteiro == null && achouDouble == null && tipo == 0){
							valorEntrada = "'" + valorEntrada + "'";
							if(achouString == null){
								lista_string.insiraNaListaString(variavelDaEntrada, valorEntrada);
							}
							else{
								lista_string.insere_ja_existente(variavelDaEntrada, valorEntrada);
							}
					}

				}
			}





//===========================================================================================




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
					String ajuda = linha;
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
				System.out.println(linha);
				return linha;
			}

			if(tok == '(')
			{
				estring.abreParenteses(linha, aqui);
			}

			aqui++;
		}//fim do while

    return "0";
	}
}
