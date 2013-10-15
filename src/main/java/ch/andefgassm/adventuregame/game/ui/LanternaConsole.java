package ch.andefgassm.adventuregame.game.ui;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;

public class LanternaConsole extends Console {
	
	private Terminal terminal;
	private int currentLine = 0;
	
	public LanternaConsole() {
		terminal = new SwingTerminal();
		terminal.enterPrivateMode();
		terminal.moveCursor(0, 0);
	}

	@Override
	public void clear() {
		terminal.clearScreen();
		terminal.moveCursor(0, 0);
		currentLine = 0;
	}

	@Override
	public void print(String str) {
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '\n') {
				currentLine++;
				terminal.moveCursor(0, currentLine);
			} else {
				terminal.putCharacter(str.charAt(i));
			}
		}
	}

	@Override
	public int readInt() {
		Integer value = null;
		while (value == null) {
			Key key = null;
			while (key == null) {
				key = terminal.readInput();
			}
			char character = key.getCharacter();
			try {
				value = Integer.parseInt(String.valueOf(character));
			} catch(NumberFormatException ex) {
				System.out.println("nfe");
			}
		}
		return value;
	}

	@Override
	public void close() {
		terminal.exitPrivateMode();
	}
}
