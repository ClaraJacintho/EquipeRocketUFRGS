
package btreetest;

public class BtreeTest {
    //Regra Geral: se eh -1 entao n existe
    public static void main(String[] args) throws ClassNotFoundException {
        
        Btree arvi=new Btree("arq6", 2);
        arvi.insere(61, 610);
        
        arvi.printTree();
        arvi.close();
        
    }
}