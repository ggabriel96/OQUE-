class Interpretador
{
    private String linhas[];
	private Tokem tokens;
	private Estring estring;
	private Aritimeticos aritimetico;
	
	// Construtor
	public Interpretador()
	{
		tokens = new Tokem();
		estring = new Estring();
		aritimetico = new Aritimeticos();
	}
	
	
	
	
    public void interpreta(String l[])
    {
		int i = 0;
        int tamanho_da_linha;		
		String Nlinha = new String("");

		// Arruma o vetor realocando, removendo espacos e separando por tarefas. 
		this.linhas = estring.arrumavetor(l);			


		// ######
        for(i = 0; i < this.linhas.length; i++)
        {
            if(this.linhas[i] != null)
            {
                System.out.println("Linha " + (i + 1) + ": " + this.linhas[i]);
				
				controle(this.linhas[i], 0);
        
				tamanho_da_linha = linhas[i].length();
				System.out.println("a linha tem: " + tamanho_da_linha + " caracteres.\n\n");
            }
        }
        // ######
        
    }
    
    // Tipo int para retorno de erros (ainda nao foi implementado).
    public int controle(String linha, int pos)
			{
				
				// Esta funcao serve para distribuir comandos, de acordo
				// com o toquem encontrado.
				//
				// Ela encontra tokem por tokem e chama sua determinada 
				// funcao ate terminar de percorrer a linha recebida.
				
				
				int aqui = pos;
				while(aqui < linha.length())
				{
					
					aqui = tokens.achaToken(linha, aqui);
					if(aqui == -1)
						return -1;
					char tok = linha.charAt(aqui);
	
		
		
					// Testes de tokens para trata-los:
					
					if(tok == ';')
					{
						System.out.println("Achei um ponto e vírgula.");
					}
					
					else if(tok == '=')
					{
						System.out.println("Achei um igual.");
						System.out.println(aritimetico.simplifica(linha, aqui));
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
