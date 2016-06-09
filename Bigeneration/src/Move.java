import java.io.Serializable;

public class Move implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9191812752481609432L;
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
