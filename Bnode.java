
package btreetest;

public class Bnode implements java.io.Serializable{
    public int t=2;    //n minimo de chaves                     //2 soh para fins de teste
    public int nchaves;
    public boolean folha;
    public int[] chaves= new int[2*t];  //para cada chave um offset no arquivo
    public int[] pointer=new int[2*t+1];//nodos filhos
    public int[] offset= new int[2*t];
    
    public void printNode(){
        System.out.println("nchaves="+nchaves+", folha:"+folha);
        for(int i=0; i<nchaves; i++){
            System.out.println(chaves[i]+" - "+offset[i]);
        }
    }
}
