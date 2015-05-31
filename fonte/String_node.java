package fonte;
class String_node{
  private String nome = new String("");
  private String valor = new String("");
  private String_node proximo;

  public void setNome(String nome_var){
    this.nome = nome_var;
  }
  public String getNome(){
    return this.nome;
  }

  public void setValor(String valor_var){
    this.valor = valor_var;
  }
  public String getValor(){
    return this.valor;
  }

  public void setProximo(String_node prox){
    this.proximo = prox;
  }
  public String_node getProximo(){
    return this.proximo;
  }

}
