package view;

import java.io.Serializable;


public class Pokemon implements Serializable{
	private static final long serialVersionUID = 4640315127286444098L;
	
	String name;
	int code;	//Pokedex Code 1-720
	int stats[] = new int[6];
	int[] moves = new int[190];  //List of all possible moves this pokemon can have (max Mew-114) - Stores move's code
	int type[] = new int[2]; // There are 18 types, as of ORAS 
}
