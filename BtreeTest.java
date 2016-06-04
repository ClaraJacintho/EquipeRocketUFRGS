
package btreetest;
        
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BtreeTest {
    //Regra Geral: se eh -1 entao n existe
    public static void main(String[] args) {
        generateEx("arq3");
        //Btree arvi=new Btree("arq3");
        
    }
    public static void generateEx(String fName){
        Bnode itens[];
        //itens = new Bnode[];
        itens[0].chaves[0]=10;          //raiz
        itens[0].chaves[1]=20;
        itens[0].chaves[2]=30;
        itens[0].chaves[3]=40;
        itens[0].folha=false;
        itens[0].nchaves=4;
        itens[0].offset[0]=100;
        itens[0].offset[1]=200;
        itens[0].offset[2]=300;
        itens[0].offset[3]=400;
        itens[0].pointer[0]=1;
        itens[0].pointer[1]=2;
        itens[0].pointer[2]=3;
        itens[0].pointer[3]=4;
        itens[0].pointer[4]=5;
        
        itens[1].chaves[0]=3;
        itens[1].chaves[1]=5;
        itens[1].chaves[2]=8;
        itens[1].folha=true;
        itens[1].nchaves=3;
        itens[1].offset[0]=30;
        itens[1].offset[1]=50;
        itens[1].offset[2]=80;

        itens[2].chaves[0]=13;
        itens[2].chaves[1]=15;
        itens[2].chaves[2]=18;
        itens[2].folha=true;
        itens[2].nchaves=3;
        itens[2].offset[0]=130;
        itens[2].offset[1]=150;
        itens[2].offset[2]=180;
        
        itens[3].chaves[0]=23;
        itens[3].chaves[1]=25;
        itens[3].chaves[2]=28;
        itens[3].folha=true;
        itens[3].nchaves=3;
        itens[3].offset[0]=230;
        itens[3].offset[1]=250;
        itens[3].offset[2]=280;
        
        itens[4].chaves[0]=33;
        itens[4].chaves[1]=35;
        itens[4].chaves[2]=38;
        itens[4].folha=true;
        itens[4].nchaves=3;
        itens[4].offset[0]=330;
        itens[4].offset[1]=350;
        itens[4].offset[2]=380;
        
        itens[5].chaves[0]=43;
        itens[5].chaves[1]=45;
        itens[5].chaves[2]=48;
        itens[5].folha=true;
        itens[5].nchaves=3;
        itens[5].offset[0]=430;
        itens[5].offset[1]=450;
        itens[5].offset[2]=480;
        
        try{
            //1 - Crie um objeto FileOutputStream
            FileOutputStream fileStream = new FileOutputStream(fName);
            //2 - Crie um ObjectOutputStream
            ObjectOutputStream os = new ObjectOutputStream(fileStream);
            //3 - Grava quantos tem
            os.writeInt(6);
            //4 - Grave os objetos
            os.writeObject(itens[0]); 
            os.writeObject(itens[1]);
            os.writeObject(itens[2]);
            os.writeObject(itens[3]);
            os.writeObject(itens[4]);
            os.writeObject(itens[5]);
            //5 - Feche ObjectOutputStream
            os.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BtreeTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BtreeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
