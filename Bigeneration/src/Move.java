
public class Move {
	int code;
	int type;
	int accuracy;
	int priority;
	int target;
	int damClass;
	int effect;
	int effectAcc;
	String name;
	@Override
	public String toString() {
		return "Move [code=" + code + ", type=" + type + ", name=" + name + "]";
	}	
}
