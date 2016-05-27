import java.io.*;
import java.util.ArrayList;


public class Main {
	public static void main(String args[]){
		try{
			FileReader pokedex = new FileReader("pokedex.txt");
			BufferedReader pokedex_buff = new BufferedReader(pokedex);
			FileReader types = new FileReader("pokemon_types.txt");
			BufferedReader typeBuff = new BufferedReader(types);
			int cod;
			String line;
			String typeLine;
			
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
						typeBuff.mark(0);
					}
					else{
						pkm.type[i] = -1;
						typeBuff.reset();
					}
				}
				
				
				System.out.println(pkm.code+" "+pkm.name+" "+pkm.type[0]+" "+pkm.type[1]);
				System.out.println();
			}
			pokedex_buff.close();
			typeBuff.close();
		}
		catch(IOException e){
			System.out.println("File not found!");
		}
	
	
	
	}
}
