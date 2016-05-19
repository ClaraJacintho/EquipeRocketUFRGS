
package btreetest;
        
public class BtreeTest {
    //Regra Geral: se eh -1 entao n existe
    public static void main(String[] args) {    
        Btree arvi = new Btree(2, "arquivoteste");  //2=min. chaves; arquivoteste=nome do arquivo (n implementado)
        arvi.insert("pikachu", 0);
        arvi.insert("ash", 1);
        arvi.insert("digimon", 2);
        arvi.insert("misty", 3);
        arvi.insert("brock", 4);                //NAO RODAR!            --eu avisei...
        arvi.insert("tokepee", 5);
        arvi.insert("mewtwo", 6);
        arvi.insert("charizard", 7);
        arvi.insert("megazord", 8);
        arvi.insert("tinkywinky", 9);
        arvi.insert("autobot", 10);
        arvi.insert("marilene", 11);
        arvi.insert("pokebolas", 12);   //casos de teste
        arvi.print();                   //imprime tudo oq inseriu
    }
    
}
