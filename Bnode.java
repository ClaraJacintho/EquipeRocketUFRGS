
package btreetest;

public class Bnode implements java.io.Serializable{
    public int t=2;    //n minimo de chaves                     //2 soh para fins de teste
    public int nchaves;
    public boolean folha;
    public int papai;
    public int[] chaves= new int[2*t];  //para cada chave um offset no arquivo
    public int[] pointer=new int[2*t+1];//nodos filhos
    public int[] offset= new int[2*t];
    
    public void printNode(){
        System.out.println("nchaves="+nchaves+", folha:"+folha);
        for(int i=0; i<nchaves; i++){
            System.out.println(chaves[i]+" - "+offset[i]);
        }
    }
    public boolean isFull(){
        return nchaves==2*t;
    }
    public void shiftData(int position){       //shifta chaves e offset, abrindo
        for(int i=nchaves-1;i>=position;i--){  //espaco em position
            chaves[i+1]=chaves[i];
            offset[i+1]=offset[i];
        }
    }
}
