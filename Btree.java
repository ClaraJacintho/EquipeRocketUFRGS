
package btreetest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Btree {
    ArrayList<Bnode> nodo = new ArrayList<Bnode>();
    public String   fileName;
    public int      quant;  //numero de nodos
    public int      root;
    
    //construtor: a ideia aqui eh abrir o arquivo (se nao conseguir, cria-lo) e
    //transferir o arranjo de Bnodo's para a memoria.
    public Btree(String fileName) throws ClassNotFoundException{
        this.fileName=fileName;
        Bnode tmp;
        try{
        FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        
        quant=in.readInt();
        root=in.readInt();
        for(int i=0;i<quant; i++){
            nodo.add((Bnode) in.readObject());
        }
        fileIn.close();
        in.close(); 
        }catch(IOException e){  //se n tem arquivo
            makeFile();
        }
    }
    private void makeFile(){        //cria um arquivo com uma raiz folha
        Bnode raiz= new Bnode();    //com nchaves==0
        raiz.nchaves=0;
        raiz.folha=true;
        raiz.papai=-1;
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeInt(0);
            out.writeInt(0);
            out.writeObject(raiz);
            out.close();
            fileOut.close();
            quant=0;
            root=0;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void printTree(){    //imprime todos os nodos
        for(int i=0; i<quant; i++){
            nodo.get(i).printNode();
        }
    }
    public void close(){    //salva tudo no arquivo
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeInt(quant);
            out.writeInt(root);
            for(int i=0;i<quant;i++){
                out.writeObject(nodo.get(i));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int ondeInsere(int key){ //retorna indice do nodo onde deve
        Bnode alvo=nodo.get(root);     //ser inserida a chave
        int i, index=0;
        if(quant==0)
            return 0;
        else{
            while(alvo.folha==false){
                i=0;
                while(i<alvo.nchaves){
                    if(key<alvo.chaves[i])
                        break;
                    else i++;
                }
                index=alvo.pointer[i];
                alvo=nodo.get(alvo.pointer[i]);
                
            }
            return index;    
        }
        
    }
    public void insere(int key, int offset){
        int spot=ondeInsere(key), i;
        Bnode alvo=nodo.get(spot);
        if(alvo.isFull()==false){ //se tem espaço eh so fazer uma inserçao linear
            for(i=0; i<alvo.nchaves; i++)   //acha onde no nodo botar
                if(key<alvo.chaves[i])
                    break;
            alvo.shiftData(i);      //libera espaco para inserir
            alvo.chaves[i]=key;
            alvo.offset[i]=offset;  //insere
            alvo.nchaves++;
            nodo.set(spot, alvo);   //salva alvo de volta para a lista
        }
        else insereFull(key, offset, spot); //chama split
    }
    public void insereFull(int key, int offset, int spot){  //SPLIT
        Bnode novoNode=new Bnode();
        
    }
    
}
