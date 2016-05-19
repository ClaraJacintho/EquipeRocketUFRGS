package btreetest;


public class Bnode {
    public int i;
    public int pai;         //indice no array do pai
    public Blmnt[] itens;   //array de elementos do nodo
    
    public Bnode(int i, int pai){
        itens=new Blmnt[2*i];       //cada item ja eh inicializado vazio
        this.pai=pai; 
        this.i=i;
    }
    public boolean folha(){
        if(itens[0].filhos[0]==-1)
            return true;
        else return false;
    }
    public boolean cheio(){
        if(itens.length==2*i)
            return true;
        else return false;
    }
}
