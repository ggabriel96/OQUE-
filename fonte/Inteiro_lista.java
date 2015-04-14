class Inteiro_lista{
  int qtd_elementos;
  Inteiro primeiro;
  Inteiro ultimo;

  //inicializa a lista vazia
  public void Inteiro_lista(){
    this.qtd_elementos = 0;
    this.primeiro = null;
    this.ultimo = null;
  }

  //insere no inicio da lista
  public void insere_lista_int(String nome, int valor){
    if(quantidade_var == 0){
      Inteiro novo = new Inteiro();
      novo.setNome(nome);
      novo.setValor(valor);
      novo.setProximo(primeiro);
      this.primeiro = novo;
    }
    else{
      Inteiro novo2 = new Inteiro();
      novo2.setNome(nome);
      novo2.setValor(valor);
      novo2.setProximo(primeiro);
      primeiro = novo2;
    }
    this.qtd_elementos++;
  }

  /* SE QUISER IMPRIMIR A LISTA

    public void imprimir(){
      if(this.qtd_elementos == 0){
        System.out.println("Lista esta vazia bixo!");
      }
      else{
        Inteiro aux = this.primeiro;
        for(int i = 0; i < this.qtd_elementos - 1; i++){
          System.out.println(aux.getNome());
          System.out.println(aux.getValor());
          aux = aux.getProximo();
        }
        System.out.println(aux.getNome());
        System.out.println(aux.getValor());
      }
    }

  */

  //pesquisa elementos na lista retorna 1 se achou ou -1 se nao achou
  public int pesquisa_inteiro(String nome){
    Inteiro aux = this.primeiro;

    while(aux.getProximo() != null){
      if(aux.getNome() == nome){
        return 1;
      }
      aux = aux.getProximo();
    }

    return -1;
  }

}

/*
  exemplo de inserção:

  public static void main(String args[]){

    Inteiro_lista a = new Inteiro_lista();
    a.insere_lista_int(nomevariavel, valorvariavel);
    a.insere_lista_int(nomevariavel2, valorvariavel2);
    a.insere_lista_int(nomevariavel3, valorvariavel3);

    a.imprimir();
    a.pesquisainteiro(nomevariavel);
  }
*/
