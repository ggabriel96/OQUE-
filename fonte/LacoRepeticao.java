class LacoRepeticao{
  // Atributos da classe ==========================
  	public String tokens;

  // Construtor (instancia objetos da classe) =====
  	public LacoRepeticao(){
  		tokens = new String("={([+-*/@!%<>]});");
  	}

  public String achaRepetix(String linha){
		int i, tamanholinha = 0, y = 0, inicioRepetix = 0;
		String repetix = new String(" ");
		char x;

		for(x = linha.charAt(y); x != ';'; x = linha.charAt(y++)){
			tamanholinha++;
		}


		for(i = 0; i <= (tamanholinha - 1); i++){
			if(linha.charAt(i) == 'r'){
				for(int j = i; j <= (i + 6); j++){
					repetix = repetix + linha.charAt(j);
				}
				if(repetix.contains("repetix")){
					inicioRepetix = i;
					break;
				}
			}
		}

    if(!repetix.equals(" ")){

  		if(repetix.contains("repetix"))
  		{
  			if(inicioRepetix - 1 != -1){
  				if(linha.charAt(inicioRepetix - 1) != tokens.charAt(14))
  				{
  					repetix = "\nrepetix e uma palavra reservada, nao pode usar no nome de variaveis\n";
  				}
  			}
        else{
          if(linha.charAt(inicioRepetix + 7) != tokens.charAt(2))
    			{
    				repetix = "\nvoce esqueceu de abrir chaves no repetix";
    			}
          else{
            repetix = "repetix";
          }
        }
  		}
      else
  		{
  			repetix = "\nnao existe repetix na linha";
  		}
    }
		else
		{
			repetix = "\nnao existe repetix na linha";
		}
		return repetix;
}

  public String entreParenteses(String linha, int pos)
	{
		String nova = new String("");

		int i;
    if(linha.charAt(pos) == tokens.charAt(2)){
  		for(i = pos + 1; linha.charAt(i) != tokens.charAt(15) && linha.charAt(i) != tokens.charAt(1) && linha.charAt(i) != tokens.charAt(14); i++){
  			nova = nova + linha.charAt(i);
  		}
    }
    else{
      nova = "nao tem nada entre as chaves";
    }
		return nova;
	}
  

}
