package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class fileMngr {

    RandomAccessFile raf;
    String fileName;
    int t, size;

    public fileMngr(String fileName, int t) {
        this.t = t;
        this.size = (4 * t + 4)*4+(2*t)*8;
        this.fileName = fileName;
        try {
            File f = new File(fileName);
            if (f.createNewFile()) {
                raf = new RandomAccessFile(f, "rw");
                raf.writeInt(0);
                raf.writeInt(0);
                Bnode raiz = new Bnode(this.t);
                raiz.nchaves = 0;
                raiz.altura = 0;
                raiz.papai = -1;
                add(raiz);
                raf.seek(0);

            } else {
                raf = new RandomAccessFile(f, "rw");
                raf.seek(0);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Erro ao abrir arquivo "+fileName);
            System.out.println("(não encontrado)");
            Logger.getLogger(fileMngr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Erro ao abrir arquivo "+fileName);
            System.out.println("(IOException)");
            Logger.getLogger(fileMngr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void set(int index, Bnode alvo) {
        try {
            long ptr = raf.getFilePointer();
            raf.seek(8 + index *size);
            raf.writeInt(alvo.nchaves);
            raf.writeInt(alvo.altura);
            raf.writeInt(alvo.papai);
            for (int i = 0; i < 2 * t; i++) {
                raf.writeInt(alvo.chaves[i]);
            }
            for (int j = 0; j < 2 * t + 1; j++) {
                raf.writeInt(alvo.pointer[j]);
            }
            for (int k = 0; k < 2 * t; k++) {
                raf.writeLong(alvo.offset[k]);
            }
            raf.seek(ptr);

        } catch (IOException ex) {
            System.out.println("Erro ao salvar nodo "+index);
            Logger.getLogger(fileMngr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void add(Bnode alvo) {    //add bnode ao fim
        try {
            long ptr = raf.getFilePointer();
            raf.seek(raf.length());
            raf.writeInt(alvo.nchaves);
            raf.writeInt(alvo.altura);
            raf.writeInt(alvo.papai);
            for (int i = 0; i < 2*alvo.t; i++) {
                raf.writeInt(alvo.chaves[i]);
            }
            for (int j = 0; j < 2 * t + 1; j++) {
                raf.writeInt(alvo.pointer[j]);
            }
            for (int k = 0; k < 2 * t; k++) {
                raf.writeLong(alvo.offset[k]);
            }
            raf.seek(ptr);

        } catch (IOException ex) {
            System.out.println("Erro ao adicionar nodo:");
            alvo.printNode();
            Logger.getLogger(fileMngr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Bnode get(int index) {
        try {
            long ptr = raf.getFilePointer();
            Bnode alvo = new Bnode(t);
            raf.seek(8 + index *size);
            alvo.nchaves = raf.readInt();
            alvo.altura = raf.readInt();
            alvo.papai = raf.readInt();
            for (int i = 0; i < 2 * t; i++) {
                alvo.chaves[i] = raf.readInt();
            }
            for (int j = 0; j < 2 * t + 1; j++) {
                alvo.pointer[j] = raf.readInt();
            }
            for (int k = 0; k < 2 * t; k++) {
                alvo.offset[k] = raf.readLong();
            }
            raf.seek(ptr);
            return alvo;
        } catch (IOException ex) {
            System.out.println("Erro ao buscar nodo "+index);
            Logger.getLogger(fileMngr.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public int getRoot() {
        try {
            long ptr=raf.getFilePointer();
            raf.seek(4);
            int root=raf.readInt();
            raf.seek(ptr);
            return root;
        } catch (IOException ex) {
            System.out.println("Erro ao buscar root");
            Logger.getLogger(fileMngr.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public void setRoot(int root) {
        try {
            long ptr=raf.getFilePointer();
            raf.seek(4);
            raf.writeInt(root);
            raf.seek(ptr);
        } catch (IOException ex) {
            System.out.println("Erro ao salvar root = "+root);
            Logger.getLogger(fileMngr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getQuant() {
        try {
            long ptr=raf.getFilePointer();
            raf.seek(0);
            int quant=raf.readInt();
            raf.seek(ptr);
            return quant;
        } catch (IOException ex) {
            System.out.println("Erro ao buscar quant");
            Logger.getLogger(fileMngr.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    public void setQuant(int quant) {
        try {
            long ptr=raf.getFilePointer();
            raf.seek(0);
            raf.writeInt(quant);
            raf.seek(ptr);
        } catch (IOException ex) {
            System.out.println("Erro ao salvar quant = "+quant);
            Logger.getLogger(fileMngr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void close(){
        try {
            raf.close();
        } catch (IOException ex) {
            System.out.println("Erro ao fechar "+this.fileName);
            Logger.getLogger(fileMngr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
