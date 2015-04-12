class Interpretador
{
    private String linhas[];
	private Tokem tokens;
	private Estring estring;
	
	public Interpretador()
	{
		tokens = new Tokem();
		estring = new Estring();
	}
	
    public void interpreta(String l[])
    {
		char aux;
		char atual;
		int i = 0, w = 0, auxi = 0;
        int tamanho_da_linha;		
		String Nlinha = new String("");




		this.linhas = estring.Vremovenull(l);

		Nlinha = estring.concatenaVetor(linhas);	
		Nlinha = Nlinha.replaceAll(" ","");


				int tamV = 0;
				char eh;
				auxi = 0;
				w = 0;
				
				for(w = 0; w < Nlinha.length(); w++)
				{
					eh = tokens.ehToken(Nlinha.charAt(w));
					
					if(eh == ';')
						tamV++;
				}
				
				linhas = new String[tamV];
				for(w = 0; w < tamV; w++)
				{
					linhas[w] = new String("");
				}
				
				auxi = 0;
				w = 0;
				while(w < linhas.length)
				{
					linhas[w] = linhas[w] + Nlinha.charAt(auxi);
					
					if(Nlinha.charAt(auxi) == ';')
						w++;
						
					auxi++;
				}



		Nlinha = new String("");				

        for(i = 0; i < this.linhas.length; i++)
        {
            if(this.linhas[i] != null)
            {
                System.out.println("Linha " + (i + 1) + ": " + this.linhas[i]);
				Nlinha = this.linhas[i].replaceAll(" ","");
				System.out.println("Linha reescrita: " + Nlinha);
				
				controle(Nlinha, 0);
        
				tamanho_da_linha = Nlinha.length();
				System.out.println("a linha tem: " + tamanho_da_linha + " caracteres.\n\n");
            }
        }
        
        /*	Teste de casting:
         
			char testi = '1';
			String test = new String("");
			test = test + testi;
			int foo = Integer.parseInt(test);
			System.out.println(foo + 10000);
		*/
    }
    
    public int controle(String linha, int pos)
			{
				int aqui = pos;
				while(aqui < linha.length())
				{
					aqui = tokens.achaToken(linha, aqui);
	
					if(aqui == -1)
						return -1;
	
					char tok = linha.charAt(aqui);
	
					
					if(tok == ';')
					{
						System.out.println("Achei um ponto e vírgula.");
					}
					
					if(tok == '=')
					{
						System.out.println("Achei um igual.");
					}
					
					else if(tok == '+')
					{
						// Funcao que trata o mais
						System.out.println("Achei um mais.");
					}
					
					else if(tok == '-')
					{
						// Funcao que trata o menos
						System.out.println("Achei um menos.");
					}
					
					else if(tok == '*')
					{
						// Funcao que trata o vezes
						System.out.println("Achei um vezes.");
					}
					
					else if(tok == '/')
					{
						// Funcao que trata o dividir
						System.out.println("Achei um dividir.");
					}
					aqui++;
					
				}
				return 0;
			}
    
}
