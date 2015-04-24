import java.util.Random;

class Aritimeticos
{
	public Tokem tokem;

	public Aritimeticos()
	{
		tokem = new Tokem();
	}


	public String simplifica(String linha, int pos)
	{
		String palavra1 = new String("");
		String palavra2 = new String("");
		int aux = 0, i = 0, sinal = 1;
		double valor1 = 0, valor2 = 0;
		char op = 'Q', oper = 'Q';
		int flag = 0, flag2 = 0, ter = 0, poder = 13;
		boolean vai = true;
		
		for(i = pos + 1; i < linha.length(); i++)
		{
			if(linha.charAt(i) == '"')
			{
				vai = !vai;
			}
			
			if(!vai)
				palavra1 = palavra1 + linha.charAt(i);
				
			else if(vai)
			{
				if(tokem.ehToken(linha.charAt(i)) == 'N')
					palavra1 = palavra1 + linha.charAt(i);
				else
					 ter = 13;
			}

			if(ter == poder)
			{	
				vai = true;
				ter = 0;
				op = linha.charAt(i);
				oper = op;
				aux = i;

				try 
				{
					flag = 0;
					if(!palavra1.equals("")){
						valor1 = sinal * (Double.parseDouble(palavra1));

					}
					else
					{
							
						if(op == '-')
							sinal = -1;

						else if(op == '+')
							sinal = 1;

						else
						{
							Random gerador = new Random();
							System.out.println("ERRO: Sintaxe invalida.		Número aleatório: " + gerador.nextInt());
							System.exit(0);
						}

						continue;
					}
				}
				catch(NumberFormatException nfe){
					// Colocar aqui a parte que confere no vetor de variaveis.
					if(op == '-' && palavra1.equals("")){
						sinal = sinal*-1;
						continue;
					}
					if(op == ';' || op == '+'){
						valor1 = -666;
						flag = 1;
					}
				}
				break;
			}
		}
		sinal = 1;
		// Arrumar esse for também	OQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUEOQUE
		
		if(op != ';')
		{
			System.out.println("i = " + i + "	QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			for(i = aux + 1; i < linha.length(); i++){
				if(linha.charAt(i) == '"')
				{
					vai = !vai;
				}
				
				if(!vai)
					palavra2 = palavra2 + linha.charAt(i);
					
				else if(vai)
				{
					if(tokem.ehToken(linha.charAt(i)) == 'N')
						palavra2 = palavra2 + linha.charAt(i);
					else
						 ter = 13;
				}
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


				if(ter == poder)
				{
					op = linha.charAt(i);
					aux = i;

					try{

						if(palavra2 != "")
						{
							valor2 = sinal * (Double.parseDouble(palavra2));
							System.out.println("Conseguiu   PPPPPPPPPPPPPPP");
						}
						else
						{
							if(op == '-')
								sinal = -1;

							else if(op == '+')
								sinal = 1;

							else
							{
								Random gerador = new Random();
								System.out.println("ERRO: Sintaxe inválida.		Número aleatório: " + gerador.nextInt());
								System.exit(0);
							}
							continue;
						}
					}
					catch(NumberFormatException nfe)
					{
						// Colocar aqui a parte que confere no vetor de variaveis.
						if(op == '-' && palavra2.equals(""))
						{
							sinal = sinal*-1;
							continue;
						}
						if(op == ';' || op == '+')
						{
							valor2 = -666;
							flag2 = 1;
						}
					}
					break;
				}
			}
		}
		
		
		// Essa parte confere se é uma string ou uma variável.
		if(palavra1 != null && !palavra1.isEmpty())
		{	
			if((palavra1.charAt(0) == '"') && (palavra1.charAt(palavra1.length()-1) == '"'))
			{			
				palavra1 = palavra1.substring(1, palavra1.length()-1);
				flag = 1;
			}
			else
			{
				// É número ou variável inválida que começa com número. 
				if("1234567890".contains(palavra1.charAt(0) + ""))
				{
					
				}
				
				// É variável
				else
				{
					palavra1 = pegaValor(palavra1);
					if(palavra1 != null)
					{
						if(palavra1.charAt(0) == '1'){
							palavra1 = palavra1.substring(1, palavra1.length());
							flag = 0;
							valor1 = Integer.parseInt(palavra1);	
						}
						
						else if(palavra1.charAt(0) == '2'){
							palavra1 = palavra1.substring(1, palavra1.length());
							flag = 0;
							valor1 = Double.parseDouble(palavra1);	
						}
						
						else if(palavra1.charAt(0) == '0'){
							palavra1 = palavra1.substring(2, palavra1.length()-1);
							flag = 1;
						}
						
					}	
					else
					{
						Random gerador = new Random();
						System.out.println("ERRO: Variável não iniciada.	Número aleatório: " + gerador.nextInt());
						System.exit(0);
					}
				}
			}
		}
		
		// Essa parte confere se é uma string ou uma variável.
		if(palavra2 != null && !palavra2.isEmpty())
		{	
			if((palavra2.charAt(0) == '"') && (palavra2.charAt(palavra2.length()-1) == '"'))
			{			
				palavra2 = palavra2.substring(1, palavra2.length()-1);
				flag = 1;
			}
			else
			{
				// É número ou variável inválida que começa com número. 
				if("1234567890".contains(palavra2.charAt(0) + ""))
				{
					
				}
				
				// É variável
				else
				{
					palavra2 = pegaValor(palavra2);
					if(palavra2 != null)
					{
						if(palavra2.charAt(0) == '1'){
							palavra2 = palavra2.substring(1, palavra2.length());
							flag2 = 0;
							valor2 = Integer.parseInt(palavra2);	
						}
						
						else if(palavra2.charAt(0) == '2'){
							palavra2 = palavra2.substring(1, palavra2.length());
							flag2 = 0;
							valor2 = Double.parseDouble(palavra2);	
						}
						
						else if(palavra2.charAt(0) == '0'){
							palavra2 = palavra2.substring(2, palavra2.length()-1);
							flag2 = 1;
						}
					}	
				}
			}
		}
		
		

		sinal = 1;
		System.out.println(oper + "OOOOOOPPPPPEEEEEERRRRR");
		if(oper == ';'){
			if(flag == 0 && flag2 == 0){
			valor1 = valor1 + valor2;
			linha = troca(linha, pos, valor1, i);
			}

			else
			{
				// Concatena para colocar entre aspas a resposta.
				palavra1 = "\"" + palavra1 + "\"";
				linha = troca(linha, pos, palavra1, i);
			}

			return linha;
		}


		else if(oper == '+'){
			System.out.println("Valor1: " + valor1 + "  valor2: " + valor2 + "  palavra1: " + palavra1 + "  palavra2: " + palavra2);
			if(flag == 0 && flag2 == 0){
				valor1 = valor1 + valor2;
				
				System.out.println("Vvvalor1: " + valor1 + "   i = " + i);
				
				linha = troca(linha, pos, valor1, i);
				
				System.out.println("Valor1: " + valor1 + "  valor2: " + valor2 + "  palavra1: " + palavra1 + "  palavra2: " + palavra2);
				System.out.println("Linha simplificada: " + linha);
				System.out.println("pos: " + pos);
				linha = simplifica(linha, pos);
			}

			else
			{
				palavra1 = palavra1 + palavra2;
				
				// Concatena para colocar entre aspas a resposta.
				palavra1 = "\"" + palavra1 + "\"";
				linha = troca(linha, pos, palavra1, i);
				linha = simplifica(linha, pos);
			}

			return linha;
		}

		else if(oper == '-'){
			System.out.println("Caiu na operação de --------");
			if(flag == 1 && flag2 == 1)
			{
				Random gerador = new Random();
				System.out.println("ERRO: Tipo incopatível para a operação. Numero aleatório: " + gerador.nextInt());
				System.exit(0);
			}
			valor1 = valor1 - valor2;
			linha = troca(linha, pos, valor1, i);

			linha = simplifica(linha, pos);
			return linha;
		}

		else if(oper == '*'){
			valor1 = valor1 * valor2;

			linha = troca(linha, pos, valor1, i);

			linha = simplifica(linha, pos);
			return linha;
		}

		else if(oper == '/'){
			valor1 = valor1 / valor2;

			linha = troca(linha, pos, valor1, i);

			linha = simplifica(linha, pos);
			return linha;
		}

		else if(oper == '%'){
			valor1 = valor1 % valor2;

			linha = troca(linha, pos, valor1, i);

			linha = simplifica(linha, pos);
			return linha;
		}

		return linha;
	}



	public String troca(String linha, int igual, double valor, int termina){
		System.out.println(valor + "ESSE VALOR    tamanho: " + linha.length());
		String nova = new String("");
		int ind = 0, i = 0;

		for(i = 0; i <= igual; i++)
			nova = nova + linha.charAt(i);

		while(tokem.ehToken(linha.charAt(termina)) == 'N')
			termina++;
		
		nova = nova + valor;
		for(i = termina; i < linha.length(); i++)
				nova = nova + linha.charAt(i);
		
		System.out.println(nova + " NOVAAA");
		return nova;
	}

	public String troca(String linha, int igual, String valor, int termina){
		String nova = new String("");
		int ind = 0, i = 0;

		for(i = 0; i <= igual; i++)
			nova = nova + linha.charAt(i);

		nova = nova + valor;

		for(i = termina; i < linha.length(); i++)
				nova = nova + linha.charAt(i);

		return nova;
	}



	public int getTipo(String palavra){
		double numero;
		try{
			numero = Double.parseDouble(palavra);
			return 1;
		}
		catch(NumberFormatException nfe){
			return 0;
		}
	}
	
	// Retorna null se não achar, ou "1" + valor se for numero ou "0" + valor se for string.
	// Cara, que função mais linda :P
	public String pegaValor(String palavra)
	{
		Inteiro_lista lista_int = new Inteiro_lista();
		Double_lista lista_double = new Double_lista();
		String_lista lista_string = new String_lista();
		
		String nova = lista_double.pesquisa_double(palavra);
		if(nova == null)
		{
			nova = lista_int.pesquisa_inteiro(palavra);
			if(nova == null)
			{
				nova = lista_string.pesquisa_string(palavra);
				if(nova == null)
				{
					System.out.println("Warning: Variavel \"" + palavra + "\" não iniciada.");
					return null;
				}
				else
					return "0" + lista_string.retornaValor(palavra);
			}
			else
				return "1" + lista_int.retornaValor(palavra);
		}
		else
			return "2" + lista_double.retornaValor(palavra);
	}
	
}
