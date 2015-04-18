class Double_lista{
    int qtd_elementos;
    Double_node primeiro;
    Double_node ultimo;

    //inicializa a lista vazia
    public void Double_lista(){
      this.qtd_elementos = 0;
      this.primeiro = null;
      this.ultimo = null;
    }

    //insere no inicio da lista
    public void insere_lista_double(String nome, double valor){
      if(qtd_elementos == 0){
        Double_node novo = new Double_node();
        novo.setNomeDouble(nome);
        novo.setValorDouble(valor);
        novo.setProximoDouble(primeiro);
        this.primeiro = novo;
      }
      else{
        Double_node novo2 = new Double_node();
        novo2.setNomeDouble(nome);
        novo2.setValorDouble(valor);
        novo2.setProximoDouble(primeiro);
        primeiro = novo2;
      }
      this.qtd_elementos++;
    }



      public void imprimir(){
        if(this.qtd_elementos == 0){
          System.out.println("Lista esta vazia bixo!");
        }
        else{
          Double_node aux2 = this.primeiro;
          for(int i = 0; i < this.qtd_elementos - 1; i++){
            System.out.println("Nome dessa variavel:" + aux2.getNomeDouble());
            System.out.println("O valor dela: " + aux2.getValorDouble());
            aux2 = aux2.getProximoDouble();
          }
          System.out.println("Nome dessa variavel:" + aux2.getNomeDouble());
          System.out.println("O valor dela: " + aux2.getValorDouble());
        }
      }



    //pesquisa elementos na lista retorna 1 se achou ou -1 se nao achou
    public int pesquisa_double(String nome){
      Double_node aux = this.primeiro;

      while(aux.getProximoDouble() != null){
        if(aux.getNomeDouble() == nome){
          return 1;
        }
        aux = aux.getProximoDouble();
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
