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
    if(linha.length() < 10)
      return "";
    if(linha.substring(0,10).contains("repetix(")){
      return  "repetix";
    }else if(!linha.contains("repetix(") && linha.contains("repetix")){
      return "\nrepetix e uma palavra reservada, nao pode usar no nome de variaveis\n";
    }
    return "";
    /*
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
    }

		return repetix;*/

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
      nova = "nao tem nada entre as parentes";
    }

		return nova;
	}

/*
  public Boolean Condicao(String linha){
    String palavra1 = new String();
    String palavra  = new String();
    int i, x;

    for(i = 0; i <= linha.length(); i++){
      if(linha.charAt(i) == tokens.charAt(8) || linha.charAt(i) == tokens.charAt(9) || linha.charAt(i) == tokens.charAt(11) || linha.charAt(i) == tokens.charAt(12)){
        x = i;
        for(; linha.charAt(i) != ";"; i++){
          palavra1 = palavra1 + linha.charAt(i);
        }
        for(; linha.charAt(i) != tokens.charAt(15); i--){
          palavra2 = palavra2 + linha.charAt(i);
        }
      }
    }
  }
  */

}
