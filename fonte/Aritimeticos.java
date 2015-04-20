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
		int flag = 0, flag2 = 0;

		for(i = pos + 1; i < linha.length(); i++){
			if(tokem.ehToken(linha.charAt(i)) == 'N')
				palavra1 = palavra1 + linha.charAt(i);

			else{
				op = linha.charAt(i);
				oper = op;
				aux = i;

				try {
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
					System.out.println("Tratando 2 tokens consecutivos porque deu a excecao: " + nfe.getMessage());
					// Colocar aqui a parte que confere no vetor de variaveis.
					System.out.println("ooooooooooooooooooooooooooooooooooooooop" + op);
					if(op == '-'){
						sinal = sinal*-1;
						continue;
					}
					if(op == ';'){
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
					catch(NumberFormatException nfe){
						
						System.out.println("Tratando 2 tokens consecutivos porque deu a excecao: " + nfe.getMessage());
						
						if(op == '-'){
						sinal = sinal*-1;
						continue;
					}
					if(op == ';')
						valor1 = -666;
						
					}
					break;
				}
			}
		}
		
		sinal = 1;

		if(oper == ';'){
			System.out.println("flag: " + flag + "\npalavra1: " + palavra1);
			if(flag == 0){
			valor1 = valor1 + valor2;
			linha = troca(linha, pos, valor1, i);
			}
			
			else
			{
				linha = troca(linha, pos, palavra1, i);
			}
			
			return linha;
		}


		else if(oper == '+'){
			valor1 = valor1 + valor2;

			linha = troca(linha, pos, valor1, i);

			linha = simplifica(linha, pos);
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
}
