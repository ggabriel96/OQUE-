class Estring
{
	
	public String[] Vremovenull(String l[])
	{
		int w = 0;	
		String linhas[];
		
		while(true)
		{
			if(l[w] != null)
				w++;
			else
				break;
		}
		linhas = new String[w];
		
		w--;
		while(w+1 != 0)
		{
			linhas[w] = l[w];
			w--;
		}
		return linhas;
	}
	
	public String concatenaVetor(String linhas[])
	{
		int i;
		String Nlinha = new String("");
		
		for(i = 0; i < linhas.length; i++)
			Nlinha = Nlinha + linhas[i];
			
		return Nlinha;
	}
	
	
	
}
