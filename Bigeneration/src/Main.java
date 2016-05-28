import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
	public static void main(String args[]){
		try{
			FileReader pokedex = new FileReader("pokedex.txt");
			BufferedReader pokedex_buff = new BufferedReader(pokedex);
			FileReader types = new FileReader("pokemon_types.txt");
			BufferedReader typeBuff = new BufferedReader(types);
			FileReader moves = new FileReader("pokemon_moves.txt");
			BufferedReader movesBuff = new BufferedReader(moves);
			
			int cod;
			String line;
			String typeLine;
			String movesLine;
			
			ArrayList<String> pokeList = new ArrayList<String>();
			
			int typeequals = 0;
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
							pkm.moves.add(cod);	
							movesBuff.mark(100);					//Marca??? Nao esta dando certo, como da ultima vez
							System.out.println(cod);				// Imprimir agora pq eh mais facil
						}else{
							movesBuff.reset();						//Da um reset
							flag = 1;								//Sai do loop
						}
					}
					
				}
				
				
				System.out.println(pkm.code+" "+pkm.name+" "+pkm.type[0]+" "+pkm.type[1]);
				System.out.println();
			}
			pokedex_buff.close();
			typeBuff.close();
			movesBuff.close();
		}
		catch(IOException e){
			System.out.println("File not found!");
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
	
	
	
	}
}
