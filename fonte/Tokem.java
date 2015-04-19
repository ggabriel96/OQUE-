import java.lang.*;

class Tokem{
// Atributos da classe ==========================
	public String tokens;

// Construtor (instancia objetos da classe) =====
	public Tokem(){
		tokens = new String("={([+-*/]});");
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

	// essa classe eu criei para achar a palvra repetix no meio da linha (=
	public String achaRepetix(String linha){
		int i, tamanholinha = 0, y = 0, inicioRepetix = 0;
		String repetix = new String("");
		char x;

		for(x = linha.charAt(y); x != ';'; x = linha.charAt(y++)){
			tamanholinha++;
		}

		for(i = 0; i <= (tamanholinha - 1); i++){
			if(linha.charAt(i) == 'r'){
				for(int j = i; j <= (i + 7); j++){
					repetix = repetix + linha.charAt(j);
				}
				if(repetix.contains("repetix")){
					inicioRepetix = i;
					break;
				}
			}
		}

		if(repetix.contains("repetix"))
		{
			if(inicioRepetix - 1 != -1){
				if((linha.charAt(inicioRepetix - 1) != ' ') || (linha.charAt(inicioRepetix - 1) != ';') || (linha.charAt(inicioRepetix - 1) != tokens.charAt(10)))
				{
					repetix = "Cara repetix e uma palavra reservada, nao pode usar no nome de variaveis\n";
				}
			}
			if(linha.charAt(inicioRepetix + 7) != tokens.charAt(3))
			{
				repetix = "Cara voce esqueceu de abrir chaves";
			}
		}
		else
		{
			repetix = "nao existe essa palavra na linha";
		}
		return repetix;
	}
}
