package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Game_Controller {

	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private String mode;
	private Game game;

	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Game_Controller() {
		mode = "";
	}

	/********
	 * ELSE *
	 ********/
	/**
	 * Start game.
	 * Choose between console or file input modes.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void start() throws FileNotFoundException, IOException {
		View.welcome();
		mode = View.console_or_file_input();
		
		boolean continue_playing = true;
		
		while(continue_playing) {
			switch(mode) {
			case "c":
				game = new Game_Console();
				break;
			case "f":
				game = new Game_File_Input();
				break;
			default:
				View.inavlid_input();
				start();
				return;
			}
			
			game.play();
			continue_playing = View.continue_playing();
		}

	}
	
}
