import java.io.Serializable;
import java.util.ArrayList;

public class Pokemon implements Serializable{
	private static final long serialVersionUID = 4640315127286444098L;
	
	String name;
	int code;	//Pokedex Code 1-720
	int evolution;	//Code of this Pokemons evolution
	int prevolution;	//The Pokemon that evolves into this 
	
	ArrayList<Integer> moves = new ArrayList<>();  //List of all possible moves this pokemon can have (max Mew-114) - Stores move's code
	int type[] = new int[2]; // There are 18 types, as of ORAS 
}

