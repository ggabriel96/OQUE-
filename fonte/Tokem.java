import java.lang.*;

class Tokem{
// Atributos da classe ==========================
	public String tokens;

// Construtor (instancia objetos da classe) =====
	public Tokem(){
		tokens = new String("={([+-~$@!#<>]});&^");
	}

// Operações da Classe ==========================
	public int achaToken(String linha, int pivo){
		int i = 0;
		int j = 0;

		for(j = pivo; j < linha.length(); j++){
			for(i = 0; i < this.tokens.length(); i++){
				if(tokens.charAt(i) == linha.charAt(j)){
					return j;
				}
			}
		}

		return -1;
	}

	public char ehToken(char esse){

		int i = 0;

		for(i = 0; i < this.tokens.length(); i++){
			if(esse == tokens.charAt(i)){
				return tokens.charAt(i);
			}
		}

		return 'N';
	}
}
