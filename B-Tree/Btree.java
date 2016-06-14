package btreetest;

public class Btree {

    fileMngr nodo;
    public String fileName;
    public int quant;  //numero de nodos
    public int root;

    public Btree(String fileName, int t){
        nodo=new fileMngr(fileName,t);
        this.quant=nodo.getQuant();
        this.root=nodo.getRoot();
    }

    public void printTree() {    //imprime todos os nodos, usado em testes
        for (int i = 0; i < quant; i++) {
            nodo.get(i).printNode();
        }
    }

    public void close() {    //salva tudo no arquivo
        nodo.setQuant(quant);
        nodo.setRoot(root);
        nodo.close();
    }

    public int ondeInsere(int key) { //retorna indice do nodo onde deve
        int i, index = 0;
        if (quant <= 1) {
            return root;
        } else {
            if (quant == 0) {
                return 0;
            } else {
                Bnode alvo = nodo.get(root);     //ser inserida a chave
                while (alvo.altura != 0) {
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
    }
    public void insere(int key, int offset) {
        int spot = ondeInsere(key), i;
        if (quant==0) {                   //acho q n vai ser necessario
            Bnode raiz=new Bnode();
            raiz.papai=-1;
            raiz.altura=0;
            raiz.nchaves=1;
            raiz.chaves[0]=key;
            raiz.offset[0]=offset;
            nodo.set(0,raiz);
            quant++;
        } else {
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
    }

    public void insereFull(int key, int offset, int spot) {  //SPLIT         corrigir tirar spot
        Bnode novoNode = new Bnode();
        Bnode alvo = nodo.get(spot);
        int t = novoNode.t, i, chave, off;
        if (key > alvo.chaves[t]) {
            chave = alvo.chaves[t];
            off = alvo.offset[t];
            alvo.trocaCoisas(novoNode, t + 1);
            alvo.nchaves--;
            novoNode.papai = alvo.papai;
            novoNode.altura = alvo.altura;
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
            insereNoPai(chave, off, alvo.papai, quant - 1);

        } else if (key < alvo.chaves[t - 1]) {
            chave = alvo.chaves[t - 1];
            off = alvo.offset[t - 1];
            alvo.trocaCoisas(novoNode, t);
            alvo.nchaves--;
            novoNode.papai = alvo.papai;
            novoNode.altura = alvo.altura;
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
            insereNoPai(chave, off, alvo.papai, quant - 1);

        } else {
            alvo.trocaCoisas(novoNode, t);
            novoNode.papai = alvo.papai;
            novoNode.altura = alvo.altura;
            nodo.set(spot, alvo);   //salva alvo de volta para a lista
            nodo.add(novoNode);     //salva novoNode na lista
            quant++;
            insereNoPai(key, offset, alvo.papai, quant - 1);
        }

    }

    public void insereNoPai(int key, int offset, int spot, int filhoD) {
        int i, chave, off;
        if (spot == -1) {                   //se a raiz estava cheia
            Bnode novissimo = new Bnode();
            novissimo.papai = -1;
            
            novissimo.nchaves = 1;
            novissimo.pointer[0] = root;
            novissimo.pointer[1] = filhoD;
            novissimo.chaves[0] = key;
            novissimo.offset[0] = offset;
            Bnode alvo = nodo.get(root);
            novissimo.altura = alvo.altura+1;
            nodo.add(novissimo);
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
                novoNode = nodo.get(filhoD);
                novoNode.papai = spot;
                nodo.set(filhoD, novoNode);
                alvo.nchaves++;
                nodo.set(spot, alvo);   //salva alvo de volta para a lista
            } else if (key > alvo.chaves[t]) {
                chave = alvo.chaves[t];
                off = alvo.offset[t];
                alvo.trocaCoisas(novoNode, t + 1);
                alvo.nchaves--;
                novoNode.papai = alvo.papai;
                novoNode.altura = alvo.altura;
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
                setaFilhos(quant - 1);
                insereNoPai(chave, off, alvo.papai, quant - 1);

            } else if (key < alvo.chaves[t - 1]) {
                chave = alvo.chaves[t - 1];
                off = alvo.offset[t - 1];
                alvo.trocaCoisas(novoNode, t);
                alvo.nchaves--;
                novoNode.papai = alvo.papai;
                novoNode.altura = alvo.altura;
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
                setaFilhos(quant - 1);
                insereNoPai(chave, off, alvo.papai, quant - 1);

            } else {
                alvo.trocaCoisas(novoNode, t, filhoD);
                novoNode.papai = alvo.papai;
                novoNode.altura = alvo.altura;
                nodo.set(spot, alvo);   //salva alvo de volta para a lista
                nodo.add(novoNode);     //salva novoNode na lista
                quant++;
                setaFilhos(quant - 1);
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
                if (alvo.altura != 0) {
                    alvo = nodo.get(alvo.pointer[i]);
                } else {
                    return -1;
                }

            }
        }
    }

    public void setaFilhos(int spot) {
        Bnode pai = nodo.get(spot), filho;
        for (int i = 0; i <= pai.nchaves; i++) {
            filho = nodo.get(pai.pointer[i]);
            filho.papai = spot;
            nodo.set(pai.pointer[i], filho);
        }

    }

}//splita, passa chaves, insere nova, passa filhos de acordo com chave media,salvar