class Inteiro{
  private String nome = new String("");
  private int valor;
  private Inteiro proximo;

  public void setNome(String nome_var){
    this.nome = nome_var;
  }
  public String getNome(){
    return this.nome;
  }

  public void setValor(int valor_var){
    this.valor = valor_var;
  }
  public int getValor(){
    return this.valor;
  }

  public void setProximo(Inteiro prox){
    this.proximo = prox;
  }
  public Inteiro getProximo(){
    return this.proximo;
  }

}
