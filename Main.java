import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;


public class Main {
	public static void main(String args[]){
		moveFileMaker();/*
		try{
			FileReader pokedex = new FileReader("pokedex.txt");
			BufferedReader pokedex_buff = new BufferedReader(pokedex);
			
			FileReader types = new FileReader("pokemon_types.txt");
			BufferedReader typeBuff = new BufferedReader(types);
			
			FileReader moves = new FileReader("pokemon_moves.txt");
			BufferedReader movesBuff = new BufferedReader(moves);
			
			FileOutputStream fileOut =  new FileOutputStream("pokedex.ser"); 
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        
	        FileReader stats = new FileReader("stats.txt");
	        BufferedReader statsBuff = new BufferedReader(stats);
			
			int cod;
			String line;
			String typeLine;
			String movesLine;
			
			ArrayList<String> pokeList = new ArrayList<String>();
			
			while((line=pokedex_buff.readLine())!=null){
				Pokemon pkm = new Pokemon();
				pokeList.clear(); 
				for (String part : line.split(",")) {
					pokeList.add(part);					
				}
				pkm.code = Integer.valueOf(pokeList.get(0));
				pkm.name = pokeList.get(1);
			

				for(int i =0; i<2;i++){
					pokeList.clear(); //Limpa a lista
					typeLine = typeBuff.readLine();
					for (String part : typeLine.split(",")) {
						pokeList.add(part);					
					}
					cod = Integer.valueOf(pokeList.get(0));
					int tipo = Integer.valueOf(pokeList.get(1));
					
					if(cod == pkm.code){
						pkm.type[i]= tipo;
						typeBuff.mark(100);
					}
					else{
						pkm.type[i] = -1;
						typeBuff.reset();
					}
				}
				
				//Pra pegar os golpes -> mesma coisa dos outros
				int flag = 0;
				int version_id;
				int j = 0;
				while(flag == 0){
					pokeList.clear();
					movesLine = movesBuff.readLine();
					for (String part : movesLine.split(",")) {
						pokeList.add(part);					
					}
					
					cod = Integer.valueOf(pokeList.get(0));
					version_id = Integer.valueOf(pokeList.get(1)); //Version_id = geracao?
					if(version_id == 16){							//So pega se for a ultima geracao
						if(cod == pkm.code){
							cod = Integer.valueOf(pokeList.get(2));	//Reutilizando a variavel :V
							pkm.moves[j] = cod;
							j++;
							movesBuff.mark(50000);					// Imprimir agora pq eh mais facil
						}else{
							pkm.moves[j] = -1;
							movesBuff.reset();						//Da um reset
							flag = 1;								//Sai do loop
						}
					}
					
					
					
				}
				
				String statLine;
                for (int i = 0; i < 6; i++) {
                    pokeList.clear();
                    statLine = statsBuff.readLine();
                    pokeList.addAll(Arrays.asList(statLine.split(",")));
                    pkm.stats[i] = Integer.valueOf(pokeList.get(2));
                }
               
				  
			 out.writeObject(pkm);
			    
			}
			pokedex_buff.close();
			typeBuff.close();
			movesBuff.close();
			out.close();
			fileOut.close();
		}
		catch(IOException e){
			System.out.println("File not found!");
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
		
		
		try {
			Pokemon pkm = new Pokemon();
	        FileInputStream fileIn = new FileInputStream("pokedex.ser");
		    ObjectInputStream in = new ObjectInputStream(fileIn);
		    for(int i=0; i< 720; i++){
		     	 pkm = (Pokemon)in.readObject();
		       	 System.out.println(pkm.code+" "+pkm.name+" "+pkm.type[0]+" "+pkm.type[1]);
		       	 for(int l =0; l<190; l++){	
		       		 if(pkm.moves[l] == -1)
		       			 break;
		       	 	System.out.println(pkm.moves[l]);
		       	 }
		       	 for(int l = 0; l<6;l++){
		       		 System.out.println(pkm.stats[l]);
		       	 }
		       	 
		       	 System.out.println();
			}
		    in.close();
		    fileIn.close();
		 }
		catch(IOException e){
		    e.printStackTrace();
		    return;
		}
		catch(ClassNotFoundException c) {
		     System.out.println("Pokemon class not found");
		     c.printStackTrace();
		     return;
	   }*/
		
		
	
	}
	
	public static void moveFileMaker() {

        FileReader moves;
        try {
            moves = new FileReader("moves_semvirgula.txt");
            BufferedReader movesBuff = new BufferedReader(moves);
            FileOutputStream fileOut =  new FileOutputStream("moves.ser"); 
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            
            ArrayList<String> lista = new ArrayList<>();
            String moveLine = new String();
            
            while ((moveLine = movesBuff.readLine()) != null) {
            	Move golpe = new Move();
                lista.addAll(Arrays.asList(moveLine.split(",")));

                golpe.code = Integer.valueOf(lista.get(0));
                golpe.name = lista.get(1);
                golpe.type = Integer.valueOf(lista.get(3));
                golpe.power = Integer.valueOf(lista.get(4));
                golpe.accuracy = Integer.valueOf(lista.get(6));
                golpe.effectAcc = Integer.valueOf(lista.get(7));
                golpe.target = Integer.valueOf(lista.get(8));
                golpe.damClass = Integer.valueOf(lista.get(9));
                golpe.effect = Integer.valueOf(lista.get(10));
                golpe.effectAcc = Integer.valueOf(lista.get(11));
                
                
                lista.clear(); 
                out.writeObject(golpe);
                System.out.println(golpe.toString());
                
            }
              	moves.close();
              	movesBuff.close();
              	out.close();
              	fileOut.close();
        } catch (FileNotFoundException ex) {
        	 ex.printStackTrace();
        } catch (IOException ex) {
        	 ex.printStackTrace();
        }
        
	    try {
	    	FileInputStream fileIn = new FileInputStream("moves.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			
			for(int i =0; i<621 ;i++){
				Move mewve = new Move();
				mewve=(Move)in.readObject();
				System.out.println(mewve.toString());
			}
			
			in.close();
		    fileIn.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        

    }

}
