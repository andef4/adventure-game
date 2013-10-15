package ch.andefgassm.adventuregame.game.ui;

public abstract class Console {
	public abstract void clear();
	
	public abstract void print(String str);
	
	public abstract int readInt();
	
	public abstract void close();
	
	public void println(String str) {
		print(str + "\n");
	}
	
	public int getInt(String prompt, int fromInclusive, int toInclusive) {
		int value = 0;
		while (true) {
			print(prompt);
			value = readInt();
			if (value >= fromInclusive && value <= toInclusive) {
				break;
			}
			println("");
		}
		return value;
	}
}
