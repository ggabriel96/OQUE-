package fonte;
class Double_node{
  private String nome = new String("");
  private double valor;
  private Double_node proximo;

  public void setNome(String nome_var){
    this.nome = nome_var;
  }
  public String getNome(){
    return this.nome;
  }

  public void setValor(double valor_var){
    this.valor = valor_var;
  }
  public double getValor(){
    return this.valor;
  }

  public void setProximo(Double_node prox){
    this.proximo = prox;
  }
  public Double_node getProximo(){
    return this.proximo;
  }

}
