package btreetest;

import java.io.Serializable;

public class Bnode implements Serializable{
    public int t=2;    //n minimo de chaves                     //2 soh para fins de teste
    public int nchaves=0;
    public int altura;
    public int papai;
    public int[] chaves= new int[2*t];  //para cada chave um offset no arquivo
    public int[] pointer=new int[2*t+1];//nodos filhos
    public int[] offset= new int[2*t];
    
    public void printNode(){
        System.out.println("nchaves="+nchaves+", altura:"+altura+" papai="+papai);
        for(int i=0; i<nchaves; i++){
            System.out.println(chaves[i]+" - "+offset[i]);
        }
    }
    public boolean isFull(){
        return nchaves==2*t;
    }
    public void shiftData(int position){       //shifta chaves e offset, abrindo
        int i;
        if(altura==0){
            for(i=nchaves-1;i>=position;i--){  //espaco em position
            chaves[i+1]=chaves[i];
            offset[i+1]=offset[i];
        }
        }else{
            for(i=nchaves-1;i>=position;i--){  //espaco em position
            chaves[i+1]=chaves[i];
            offset[i+1]=offset[i];
            pointer[i+2]=pointer[i+1];
        }
        }
        
    }
    public void trocaCoisas(Bnode dest, int m){
        int j=0, i, n=nchaves;
        for(i=m;i<n;i++,j++){
            dest.chaves[j]=chaves[i];
            dest.offset[j]=offset[i];
            dest.pointer[j]=pointer[i];
            dest.nchaves++;
            nchaves--;
        }
        dest.pointer[j]=pointer[i];
    }
    public void trocaCoisas(Bnode dest, int m, int filhoD){
        int j=0, i, n=nchaves;
        for(i=m;i<n;i++,j++){
            dest.chaves[j]=chaves[i];
            dest.offset[j]=offset[i];
            dest.pointer[j+1]=pointer[i+1];
            dest.nchaves++;
            nchaves--;
        }
        dest.pointer[0]=filhoD;
        
    }
}