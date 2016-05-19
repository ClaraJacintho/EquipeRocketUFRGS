package btreetest;              //  ||
                                //  \/ para serializar
public class Btree implements java.io.Serializable{
    private int i=1;        //min chaves
    public String arq;     //arquivo
    public Bnode[] tree;   //arvore eh um array de nodos
    
    public Btree(int i, String arq){    //construtor, n insere
        this.i=i;
        this.arq=arq;
        tree[0]=new Bnode(i, -1);    //inicializa o primeiro nodo(raiz)
    }
    
    public void insert(String chave, int offset){   //super incompleto, nao sei o q eu to fazendo
        Bdados dados=new Bdados(chave, offset);
        Bnode nodo=tree[0];
        Blmnt tmp;
        Boolean search=true;
        int k=0;
        while(search){                 
            if(tree[k].folha()){    //se achou uma folha
                if(tree[k].itens.length!=2*i){  //se tem espaço
                    for(int l=tree[k].itens.length-1; l>=0;l--){
                        if(chave.compareToIgnoreCase(tree[k].itens[l].dados.chave)<0){
                            tmp=tree[k].itens[l];
                            
                        }
                    }
                }
            }
            //ve primeiro se deve ficar como filho do primeiro elemento (que tem dois filhos)
            if(chave.compareToIgnoreCase(nodo.itens[k].dados.chave)<0)    //se chave eh menor q chave da arvore
            {
                
            }

            for(k=1;k<2*i;k++){
                if(chave.compareToIgnoreCase(nodo.itens[k].dados.chave)<0)
            }
        }
        
        
    }
    public void split(Bnode nodo, Bdados dados){    //entrada: nodo cheio e dados a serem inseridos
        
        
    }
    //diz se esta vazia
    public boolean vazia(){
        if(tree[0].itens[0].dados.offset==-1)
            return true;
        else return false;
    }
    //imprime tudo sobre tudo
    public void print(){
        for(int j=0;j<tree.length;j++){
            for(int k=0; k<2*i; k++)
                if(tree[j].itens[k].dados.offset!=-1)
                    System.out.println("Chave: "+tree[j].itens[k].dados.chave+" Offset: "+
                            tree[j].itens[k].dados.offset+" Filhos: "+tree[j].itens[k].filhos[0]+
                            ", "+tree[j].itens[k].filhos[1]+" Nodo: "+j);
        }
    }
    
}
