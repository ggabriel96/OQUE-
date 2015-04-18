class Double_node{
  private String nomeDouble = new String("");
  private double valorDouble;
  private Double_node proximoDouble;

  public void setNomeDouble(String nome_var){
    this.nomeDouble = nome_var;
  }
  public String getNomeDouble(){
    return this.nomeDouble;
  }

  public void setValorDouble(double valor_var){
    this.valorDouble = valor_var;
  }
  public double getValorDouble(){
    return this.valorDouble;
  }

  public void setProximoDouble(Double_node prox){
    this.proximoDouble = prox;
  }
  public Double_node getProximoDouble(){
    return this.proximoDouble;
  }

}
