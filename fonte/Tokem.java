import java.lang.*;

class Tokem
{
	public String tokens;
	
	public Tokem()
	{
		tokens = new String("={([+-*/]})");
	}
	
	public int temToken(String linha, int pivo)
	{
		int j = 0;
		
		for(int i = 0; i < this.tokens.length(); i++)
			for(j = pivo; j < linha.length(); j++)
				if(tokens.charAt(i) == linha.charAt(j))
					return j;
		return -1;		
	}
}
