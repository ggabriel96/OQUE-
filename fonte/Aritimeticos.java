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
				System.out.println("palavra1: " + palavra1 + " >>>>>>>>>>>>>>>>>>>>>>>>>> palavra2: " + palavra2);
				op = linha.charAt(i);
				oper = op;
				aux = i;

				try 
				{
					flag = 0;
					if(palavra1 != "")
						valor1 = sinal * (Double.parseDouble(palavra1));
					else
						{
							if(op == '-')
								sinal = -1;

							else if(op == '+')
								sinal = 1;

							else
								System.out.println("Erro de sintaxe");

							continue;
						}
				}
				catch(NumberFormatException nfe){
					// Colocar aqui a parte que confere no vetor de variaveis.
					if(op == '-'){
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
		
		if(op != ';'){
			for(i = aux + 1; i < linha.length(); i++){
				if(tokem.ehToken(linha.charAt(i)) == 'N')
					palavra2 = palavra2 + linha.charAt(i);

				else{
					op = linha.charAt(i);
					aux = i;

					try{

						if(palavra2 != "")
							valor2 = sinal * (Double.parseDouble(palavra2));

						else
						{
							if(op == '-')
								sinal = -1;

							else if(op == '+')
								sinal = 1;

							else
								System.out.println("Erro de sintaxe");

							continue;
						}
					}
					catch(NumberFormatException nfe)
					{
						if(op == '-')
						{
							sinal = sinal*-1;
							continue;
						}
					
					if(op == ';' || op == '+')
						{
						valor1 = -666;
						flag2 = 1;
						}
					}
					break;
				}
			}
		}
		
		//
		// Essa parte confere se é uma string ou uma variável.
		//
		if(palavra1 != null && !palavra1.isEmpty())
		{
			
			if((palavra1.charAt(0) == '"') && (palavra1.charAt(palavra1.length()-1) == '"'))
			{
				
				palavra1 = palavra1.substring(1, palavra1.length()-1);
				flag = 1;
			}
			
			else
			{
				// Procurar na lista aqui e já mudar o valor de flag, para string ou numero.
			}
		}
		
		if(palavra2 != null && !palavra2.isEmpty()){
			if(palavra2.charAt(0) == '"' && palavra2.charAt(palavra2.length()-1) == '"')
			{
				palavra2 = palavra2.substring(1, palavra2.length()-1);
				flag2 = 1;
			}
			
			else
			{
				// Procurar na lista aqui e já mudar o valor de flag, para string ou numero.
			}
		}
		System.out.println("palavra1: " + palavra1 + " >>>>>>>>>>>>>>>>>>>>>>>>>> palavra2: " + palavra2);


		sinal = 1;

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
			if(flag == 0 && flag2 == 0){
				valor1 = valor1 + valor2;

				linha = troca(linha, pos, valor1, i);
				linha = simplifica(linha, pos);
			}

			else
			{
				palavra1 = palavra1 + palavra2;
				
				// Concatena para colocar entre aspas a resposta.
				palavra1 = "\"" + palavra1 + "\"";
				System.out.println("paaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaalavra = " + palavra1);
				linha = troca(linha, pos, palavra1, i);
				linha = simplifica(linha, pos);
			}

			return linha;
		}

		else if(oper == '-'){
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
		String nova = new String("");
		int ind = 0, i = 0;

		for(i = 0; i <= igual; i++)
			nova = nova + linha.charAt(i);

		nova = nova + valor;

		for(i = termina; i < linha.length(); i++)
				nova = nova + linha.charAt(i);

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
	
	
	public String pegaValor()
	{
		
		return "0";
	}
	
}
