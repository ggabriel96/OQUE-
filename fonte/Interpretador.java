class Interpretador
{
    private String linhas[];

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

                // Faz a mesma coisa que a função que eu tinha feito.
				        Nlinha = this.linhas[i].replaceAll("\\s+"," ");

				        System.out.println("Linha reescrita:" + Nlinha);

            }
        }

        tamanho_da_linha = Nlinha.length();

        System.out.println("a linha tem: " + tamanho_da_linha + " caracteres.");

        


    }
}
