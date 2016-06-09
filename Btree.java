
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
    public String fileName;
    public int quant;  //numero de nodos
    public int root;

    //construtor: a ideia aqui eh abrir o arquivo (se nao conseguir, cria-lo) e
    //transferir o arranjo de Bnodo's para a memoria.
    public Btree(String fileName) throws ClassNotFoundException {
        this.fileName = fileName;
        Bnode tmp;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            quant = in.readInt();
            root = in.readInt();
            for (int i = 0; i < quant; i++) {
                nodo.add((Bnode) in.readObject());
            }
            fileIn.close();
            in.close();
        } catch (IOException e) {  //se n tem arquivo
            makeFile();
        }
    }

    private void makeFile() {        //cria um arquivo com uma raiz folha
        Bnode raiz = new Bnode();    //com nchaves==0
        raiz.nchaves = 0;
        raiz.folha = true;
        raiz.papai = -1;
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeInt(0);
            out.writeInt(0);
            out.writeObject(raiz);
            out.close();
            fileOut.close();
            quant = 0;
            root = 0;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void printTree() {    //imprime todos os nodos
        for (int i = 0; i < quant; i++) {
            nodo.get(i).printNode();
        }
    }

    public void close() {    //salva tudo no arquivo
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeInt(quant);
            out.writeInt(root);
            for (int i = 0; i < quant; i++) {
                out.writeObject(nodo.get(i));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Btree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int ondeInsere(int key) { //retorna indice do nodo onde deve
        Bnode alvo = nodo.get(root);     //ser inserida a chave
        int i, index = 0;
        if (quant == 0) {
            return 0;
        } else {
            while (alvo.folha == false) {
                i = 0;
                while (i < alvo.nchaves) {
                    if (key < alvo.chaves[i]) {
                        break;
                    } else {
                        i++;
                    }
                }
                index = alvo.pointer[i];
                alvo = nodo.get(alvo.pointer[i]);

            }
            return index;
        }

    }

    public void insere(int key, int offset) {
        int spot = ondeInsere(key), i;
        Bnode alvo = nodo.get(spot);
        if (alvo.isFull() == false) { //se tem espaço eh so fazer uma inserçao linear
            for (i = 0; i < alvo.nchaves; i++) //acha onde no nodo botar
            {
                if (key < alvo.chaves[i]) {
                    break;
                }
            }
            alvo.shiftData(i);      //libera espaco para inserir
            alvo.chaves[i] = key;
            alvo.offset[i] = offset;  //insere
            alvo.nchaves++;
            nodo.set(spot, alvo);   //salva alvo de volta para a lista
        } else {
            insereFull(key, offset, spot); //chama split
        }
    }

    public void insereFull(int key, int offset, int spot) {  //SPLIT         corrigir tirar spot
        Bnode novoNode = new Bnode();
        Bnode alvo = nodo.get(spot);
        int t = novoNode.t, i;
        if (key > alvo.chaves[t]) {
            alvo.trocaCoisas(novoNode, t + 1);
            novoNode.papai = alvo.papai;
            novoNode.folha = alvo.folha;
            for (i = 0; i < novoNode.nchaves; i++) //acha onde no nodo botar
            {
                if (key < novoNode.chaves[i]) {
                    break;
                }
            }
            novoNode.shiftData(i);      //libera espaco para inserir
            novoNode.chaves[i] = key;
            novoNode.offset[i] = offset;  //insere
            novoNode.nchaves++;
            nodo.set(spot, alvo);   //salva alvo de volta para a lista
            nodo.add(novoNode);     //salva novoNode na lista
            quant++;
            insereNoPai(alvo.chaves[t], alvo.offset[t], alvo.papai, quant - 1);

        } else if (key < alvo.chaves[t - 1]) {
            alvo.trocaCoisas(novoNode, t);
            novoNode.papai = alvo.papai;
            novoNode.folha = alvo.folha;
            for (i = 0; i < alvo.nchaves; i++) //acha onde no nodo botar
            {
                if (key < alvo.chaves[i]) {
                    break;
                }
            }
            alvo.shiftData(i);      //libera espaco para inserir
            alvo.chaves[i] = key;
            alvo.offset[i] = offset;  //insere
            alvo.nchaves++;
            nodo.set(spot, alvo);   //salva alvo de volta para a lista
            nodo.add(novoNode);     //salva novoNode na lista
            quant++;
            insereNoPai(alvo.chaves[t - 1], alvo.offset[t - 1], alvo.papai, quant - 1);

        } else {
            alvo.trocaCoisas(novoNode, t);
            novoNode.papai = alvo.papai;
            novoNode.folha = alvo.folha;
            nodo.set(spot, alvo);   //salva alvo de volta para a lista
            nodo.add(novoNode);     //salva novoNode na lista
            quant++;
            insereNoPai(key, offset, alvo.papai, quant - 1);
        }

    }

    public void insereNoPai(int key, int offset, int spot, int filhoD) {
        int i;
        if (spot == -1) {
            Bnode novissimo = new Bnode();
            novissimo.papai = -1;
            novissimo.folha = false;
            novissimo.nchaves = 1;
            novissimo.pointer[0] = root;
            novissimo.pointer[1] = filhoD;
            novissimo.chaves[0] = key;
            novissimo.offset[0] = offset;
            nodo.add(novissimo);
            Bnode alvo = nodo.get(root);
            alvo.papai = quant;
            nodo.set(root, alvo);
            alvo = nodo.get(filhoD);
            alvo.papai = quant;
            nodo.set(filhoD, alvo);
            root = quant;
            quant++;
        } else {
            Bnode novoNode = new Bnode(), alvo = nodo.get(spot);
            int t = alvo.t;

            if (alvo.isFull() == false) { //se tem espaço eh so fazer uma inserçao linear
                for (i = 0; i < alvo.nchaves; i++) //acha onde no nodo botar
                {
                    if (key < alvo.chaves[i]) {
                        break;
                    }
                }
                alvo.shiftData(i);      //libera espaco para inserir
                alvo.chaves[i] = key;
                alvo.offset[i] = offset;  //insere
                alvo.pointer[1 + i] = filhoD;
                alvo.nchaves++;
                nodo.set(spot, alvo);   //salva alvo de volta para a lista
            } else if (key > alvo.chaves[t]) {
                alvo.trocaCoisas(novoNode, t + 1);
                novoNode.papai = alvo.papai;
                novoNode.folha = alvo.folha;
                for (i = 0; i < novoNode.nchaves; i++) //acha onde no nodo botar
                {
                    if (key < novoNode.chaves[i]) {
                        break;
                    }
                }
                novoNode.shiftData(i);      //libera espaco para inserir
                novoNode.chaves[i] = key;
                novoNode.offset[i] = offset;  //insere
                novoNode.pointer[i + 1] = filhoD;
                novoNode.nchaves++;
                nodo.set(spot, alvo);   //salva alvo de volta para a lista
                nodo.add(novoNode);     //salva novoNode na lista
                quant++;
                insereNoPai(alvo.chaves[t], alvo.offset[t], alvo.papai, quant - 1);

            } else if (key < alvo.chaves[t - 1]) {
                alvo.trocaCoisas(novoNode, t);
                novoNode.papai = alvo.papai;
                novoNode.folha = alvo.folha;
                for (i = 0; i < alvo.nchaves; i++) //acha onde no nodo botar
                {
                    if (key < alvo.chaves[i]) {
                        break;
                    }
                }
                alvo.shiftData(i);      //libera espaco para inserir
                alvo.chaves[i] = key;
                alvo.offset[i] = offset;  //insere
                alvo.pointer[i + 1] = filhoD;
                alvo.nchaves++;
                nodo.set(spot, alvo);   //salva alvo de volta para a lista
                nodo.add(novoNode);     //salva novoNode na lista
                quant++;
                insereNoPai(alvo.chaves[t - 1], alvo.offset[t - 1], alvo.papai, quant - 1);

            } else {
                alvo.trocaCoisas(novoNode, t, filhoD);
                novoNode.papai = alvo.papai;
                novoNode.folha = alvo.folha;
                nodo.set(spot, alvo);   //salva alvo de volta para a lista
                nodo.add(novoNode);     //salva novoNode na lista
                quant++;
                insereNoPai(key, offset, alvo.papai, quant - 1);
            }
        }
    }

    public int pesquisa(int key) {
        Bnode alvo = nodo.get(root);     //ser inserida a chave
        int i;
        if (quant == 0) {
            return -1;
        } else {
            while (true) {
                i = 0;
                while (i < alvo.nchaves) {
                    if (key <= alvo.chaves[i]) {
                        if (key == alvo.chaves[i]) {
                            return alvo.offset[i];
                        } else {
                            break;
                        }
                    } else {
                        i++;
                    }
                }
                if (alvo.folha == false) {
                    alvo = nodo.get(alvo.pointer[i]);
                } else {
                    return -1;
                }

            }
        }

    }

}//splita, passa chaves, insere nova, passa filhos de acordo com chave media,salvar
