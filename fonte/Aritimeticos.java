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
		int aux = 0, i = 0;
		double valor1 = 0, valor2 = 0;
		char op = 'Q', oper = 'Q';
		
		for(i = pos + 1; i < linha.length(); i++)
		{
			if(tokem.ehToken(linha.charAt(i)) == 'N')
					palavra1 = palavra1 + linha.charAt(i);
			
			else
			{
				op = linha.charAt(i);
				oper = op;
				aux = i;
				
				try 
				{
						valor1 = Double.parseDouble(palavra1); 
				}
				catch(NumberFormatException nfe) 
				{
					System.out.println("Erro tentando converter String para double: " + nfe.getMessage());
					// Colocar aqui a parte que confere no vetor de variaveis.
				}
				
				break;
			}
		}

		if(op != ';')
		{
			for(i = aux + 1; i < linha.length(); i++)
			{
				if(tokem.ehToken(linha.charAt(i)) == 'N')
						palavra2 = palavra2 + linha.charAt(i);
				
				else
				{
					op = linha.charAt(i);
					aux = i;
					
					try
					{ 
						valor2 = Double.parseDouble(palavra2); 
					}
					
					catch(NumberFormatException nfe) 
					{
						
						//System.out.println("Erro tentando converter String para double: " + nfe.getMessage());
					}
					break;
				}
			}
		}
		
		
		if(oper == ';')
		{
			valor1 = valor1 + valor2;
			linha = troca(linha, pos, valor1, i);
			
			return linha;
		}
		
		
		else if(oper == '+')
		{
			valor1 = valor1 + valor2;

			linha = troca(linha, pos, valor1, i);
						
			linha = simplifica(linha, pos);
			return linha;
		}
		
		else if(oper == '-')
		{
			valor1 = valor1 - valor2;
			System.out.println("valor1 = " + valor1);
			linha = troca(linha, pos, valor1, i);
						
			linha = simplifica(linha, pos);
			return linha;
		}
		
		else if(oper == '*')
		{
			valor1 = valor1 * valor2;

			linha = troca(linha, pos, valor1, i);
						
			linha = simplifica(linha, pos);
			return linha;
		}
		
		else if(oper == '/')
		{
			valor1 = valor1 / valor2;

			linha = troca(linha, pos, valor1, i);
						
			linha = simplifica(linha, pos);
			return linha;
		}
		
		System.out.println("nao fez nada");
		return linha;
	}
	
	
	
	public String troca(String linha, int igual, double valor, int termina)
	{
		System.out.println(valor);
		String nova = new String("");
		int ind = 0, i = 0;
		
		
		for(i = 0; i <= igual; i++)
			nova = nova + linha.charAt(i);
			
		nova = nova + valor;
		// Ate aqui esta certo.
		
		
		for(i = termina; i < linha.length(); i++)
				nova = nova + linha.charAt(i);
		
		return nova;
	}
	
	public int getTipo(String palavra)
	{
		double numero;
		try
		{
			numero = Double.parseDouble(palavra);
			return 1;
		}
		catch(NumberFormatException nfe)
		{
			return 0;
		}
		
	}
	
	
}
