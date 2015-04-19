class Blocos
{	
	public String achaEscopo(String linha, int pos)
	{
		String nova = new String("");
		int w = 0;
		int aux = pos;
		int flag1 = 0;
		int Nesc = 0;
		
		while(aux < linha.length()){
			if(linha.charAt(aux) == '{'){
				flag1 = 1;
				Nesc++;
			}
				
			if(linha.charAt(aux) == '}'){
				Nesc--;
				if(Nesc == 0){
					nova = nova + linha.charAt(aux);
					break;
				}
			}
			
			if(flag1 != 0){
				nova = nova + linha.charAt(aux);
			}	
			aux++;
		}
		System.out.println("nova: " + nova);
		return nova;
	}
	
	
	
	public String escopo(String linha)
	{
		Interpretador interpretador = new Interpretador();
		return interpretador.controle(linha, 1);
	}	
	
}
