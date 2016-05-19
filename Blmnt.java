package btreetest;

public class Blmnt {
    public Bdados dados;
    public int[] filhos={-1, -1};   //indices dos filhos na arvore, se -1 nao tem filho
    
    public Blmnt(String chave, int offset){
        
    }
    
    //insere elemento sem filhos
    public void insert(String chave, int offset){
        dados=new Bdados(chave, offset);
    }
    
    //escreve o indice do filho(tem no maximo 2)
    public void setFilho(int indice){
        if(filhos[0]==-1){
            filhos[0]=indice;
        }else{
            filhos[1]=indice;
        }
    }
    
    
}
