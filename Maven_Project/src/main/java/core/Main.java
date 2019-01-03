package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {	
		Game game = new Game();
		View.welcome();
		game.choose_mode(View.scanner);
	}
	
}
