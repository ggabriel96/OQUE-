class Interpretador
{
    private String linhas[];
	private Tokem tokens;
	
	public Interpretador()
	{
		tokens = new Tokem();
	}
	
    public void interpreta(String l[])
    {
		char aux;
		char atual;
        int tamanho_da_linha;
        this.linhas = l;
        String Nlinha = new String();

        for(int i = 0; i < this.linhas.length; i++)
        {
            if(this.linhas[i] != null)
            {
                System.out.println("Linha " + (i + 1) + ":" + this.linhas[i]);
				Nlinha = this.linhas[i].replaceAll(" ","");
				System.out.println("Linha reescrita:" + Nlinha);
				
				controle(Nlinha, 0);
        
				tamanho_da_linha = Nlinha.length();
				System.out.println("a linha tem: " + tamanho_da_linha + " caracteres.\n\n");
            }
        }
		
        
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
