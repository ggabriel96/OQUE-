class LacoRepeticao{
  // Atributos da classe ==========================
  	public String tokens;

  // Construtor (instancia objetos da classe) =====
  	public LacoRepeticao(){
  		tokens = new String("={([+-~$@!#<>]});&^");
  	}

  public String achaRepetix(String linha){
		String repetix = new String(" ");

    if(linha.length() < 10)
      return "";
    if(linha.substring(0,10).contains("repetix(")){
      return  "repetix";
    }else if(!linha.contains("repetix(") && linha.contains("repetix")){
      return "'repetix' e uma palavra reservada pelo Imperio Galactico, \nso pode usar essa palavra (sem ser como laco) quem segue o lado negro da forca\nsinto muito =/";
    }
    return "";

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
}
