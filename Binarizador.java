package view;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.objects.NativeArray;

public class Binarizador {

    RandomAccessFile raf;
    Btree arvi;
    RandomAccessFile moveRaf;

    public void binarize() {
        try {
            long off;
            FileReader pokedex = new FileReader("pokedex.txt");
            BufferedReader pokedex_buff = new BufferedReader(pokedex);
            FileReader types = new FileReader("pokemon_types.txt");
            BufferedReader typeBuff = new BufferedReader(types);
            FileReader moves = new FileReader("pokemon_moves.txt");
            BufferedReader movesBuff = new BufferedReader(moves);
            FileReader stats = new FileReader("stats.txt");
            BufferedReader statsBuff = new BufferedReader(stats);

            raf = new RandomAccessFile("rocketdex.bin", "rw");
            arvi = new Btree("arvore.bin", 25);

            int cod;
            String line;
            String typeLine;
            String movesLine;

            ArrayList<String> pokeList = new ArrayList<String>();

            while ((line = pokedex_buff.readLine()) != null) {
                Pokemon pkm = new Pokemon();
                pokeList.clear();
                for (String part : line.split(",")) {
                    pokeList.add(part);
                }
                pkm.code = Integer.valueOf(pokeList.get(0));
                pkm.name = pokeList.get(1);

                for (int i = 0; i < 2; i++) {
                    pokeList.clear(); //Limpa a lista
                    typeLine = typeBuff.readLine();
                    for (String part : typeLine.split(",")) {
                        pokeList.add(part);
                    }
                    cod = Integer.valueOf(pokeList.get(0));
                    int tipo = Integer.valueOf(pokeList.get(1));

                    if (cod == pkm.code) {
                        pkm.type[i] = tipo;
                        typeBuff.mark(100);
                    } else {
                        pkm.type[i] = -1;
                        typeBuff.reset();
                    }
                }

                //Pra pegar os golpes -> mesma coisa dos outros
                int flag = 0;
                int version_id;
                int j = 0;
                while (flag == 0) {
                    pokeList.clear();
                    movesLine = movesBuff.readLine();
                    for (String part : movesLine.split(",")) {
                        pokeList.add(part);
                    }

                    cod = Integer.valueOf(pokeList.get(0));
                    version_id = Integer.valueOf(pokeList.get(1)); //Version_id = geracao?
                    if (version_id == 16) {							//So pega se for a ultima geracao
                        if (cod == pkm.code) {
                            cod = Integer.valueOf(pokeList.get(2));	//Reutilizando a variavel :V
                            pkm.moves[j] = cod;
                            j++;
                            movesBuff.mark(50000);					// Imprimir agora pq eh mais facil
                        } else {
                            pkm.moves[j] = -1;
                            movesBuff.reset();						//Da um reset
                            flag = 1;								//Sai do loop
                        }
                    }

                }
                int stat; //1- hp 2- attack 3- defence 4- spc attk 5- spc def 6- speed
                String statLine;
                for (int i = 0; i < 6; i++) {
                    pokeList.clear();
                    statLine = statsBuff.readLine();
                    pokeList.addAll(Arrays.asList(statLine.split(",")));
                    pkm.stats[i] = Integer.valueOf(pokeList.get(2));
                }
                //System.out.println(pkm.code);
                //System.out.println(pkm.code+" "+pkm.name+" "+pkm.type[0]+" "+pkm.type[1]);
                /*for (int i = 0; i < 6; i++) {
                    System.out.println(pkm.stats[i]);
                }*/
                //System.out.println();

                off = write(pkm);
                arvi.insere(pkm.code, off);

            }
            raf.close();
            arvi.close();
            pokedex_buff.close();
            typeBuff.close();
            movesBuff.close();
            
            //System.out.println("Começa moveFileMaker");
            
            moveFileMaker();

        } catch (IOException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }
    }

    public long write(Pokemon pkm) {
        try {
            long ptr = raf.getFilePointer();
            raf.writeBytes(pkm.name + '\n');
            raf.writeInt(pkm.code);
            for (int i = 0; i < 190; i++) {
                raf.writeInt(pkm.moves[i]);
            }
            for (int i = 0; i < 6; i++) {
                raf.writeInt(pkm.stats[i]);
            }
            raf.writeInt(pkm.type[0]);
            raf.writeInt(pkm.type[1]);

            return ptr;
        } catch (IOException ex) {
            Logger.getLogger(Binarizador.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public void moveFileMaker() {

        FileReader moves;
        try {
            moveRaf=new RandomAccessFile("moves.bin","rw");
            moves = new FileReader("moves_semvirgula.txt");
            BufferedReader movesBuff = new BufferedReader(moves);
            Move golpe = new Move();
            ArrayList<String> lista = new ArrayList<>();
            String moveLine = new String();
            while ((moveLine = movesBuff.readLine()) != null) {
                
                lista.addAll(Arrays.asList(moveLine.split(",")));
                golpe.code = Integer.valueOf(lista.get(0));
                golpe.name = lista.get(1)+'\n';
                golpe.type = Integer.valueOf(lista.get(3));
                golpe.power = Integer.valueOf(lista.get(4));
                golpe.accuracy = Integer.valueOf(lista.get(6));
                golpe.effectAcc = Integer.valueOf(lista.get(7));
                golpe.target = Integer.valueOf(lista.get(8));
                golpe.damClass = Integer.valueOf(lista.get(9));
                golpe.effect = Integer.valueOf(lista.get(10));
                golpe.effectAcc = Integer.valueOf(lista.get(11));
                lista.clear();

                try {
                    
                    //long ptr = moveRaf.getFilePointer();
                    moveRaf.writeBytes(golpe.name);
                    moveRaf.writeInt(golpe.code);
                    moveRaf.writeInt(golpe.type);
                    moveRaf.writeInt(golpe.power);
                    moveRaf.writeInt(golpe.accuracy);
                    moveRaf.writeInt(golpe.priority);
                    moveRaf.writeInt(golpe.target);
                    moveRaf.writeInt(golpe.damClass);
                    moveRaf.writeInt(golpe.effect);
                    moveRaf.writeInt(golpe.effectAcc);
                } catch (IOException ex) {
                    Logger.getLogger(Binarizador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            moves.close();
            movesBuff.close();
            moveRaf.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Binarizador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Binarizador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
