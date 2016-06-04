
package btreetest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Btree {
    public Bnode[]  nodo;
    public String   FileName;
    public int      quant;  //numero de nodos
    
    //construtor: a ideia aqui eh abrir o arquivo (se nao conseguir, cria-lo) e
    //transferir o arranjo de Bnodo's para a memoria.
    public Btree(String FileName){
        this.FileName=FileName;
        Bnode tmp;
        try{
        FileInputStream fileIn = new FileInputStream(FileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        
        
         
        fileIn.close();
        in.close(); 
        }catch(IOException e){  //se n tem arquivo
            makeFile();
        }
    }
    private void makeFile(){
        try {
            FileOutputStream fileOut = new FileOutputStream(FileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.write(0);
            out.close();
            fileOut.close();
            quant=0;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
