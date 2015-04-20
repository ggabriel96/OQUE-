class String_lista{
    public static int qtd_elementos;
    public static String_node primeiro;
    public static String_node ultimo;

    //inicializa a lista vazia
    public void String_lista(){
      this.qtd_elementos = 0;
      this.primeiro = null;
      this.ultimo = null;
    }

    //insere no inicio da lista
    public void insiraNaListaString(String nome, String valor){
      if(qtd_elementos == 0){
        String_node novo = new String_node();
        novo.setNome(nome);
        novo.setValor(valor);
        novo.setProximo(primeiro);
        this.primeiro = novo;
      }
      else{
        String_node novo2 = new String_node();
        novo2.setNome(nome);
        novo2.setValor(valor);
        novo2.setProximo(primeiro);
        primeiro = novo2;
      }
      this.qtd_elementos++;
    }



      public void imprimir(){
        if(this.qtd_elementos == 0){
          System.out.println("Lista esta vazia bixo!");
        }
        else{
          String_node aux2 = this.primeiro;
          for(int i = 0; i < this.qtd_elementos - 1; i++){
            System.out.println("Nome dessa variavel:" + aux2.getNome());
            System.out.println("O valor dela: " + aux2.getValor());
            aux2 = aux2.getProximo();
          }
          System.out.println("Nome dessa variavel:" + aux2.getNome());
          System.out.println("O valor dela: " + aux2.getValor());
        }
      }



    //pesquisa elementos na lista retorna 1 se achou ou -1 se nao achou
    public int pesquisa_String(String nome){
      String_node aux = this.primeiro;

      while(aux.getProximo() != null){
        if(aux.getNome() == nome){
          return 1;
        }
        aux = aux.getProximo();
      }

      return -1;
    }

  }
