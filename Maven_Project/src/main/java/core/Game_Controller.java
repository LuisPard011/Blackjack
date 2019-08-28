package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Game_Controller {
	
	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	protected final static int draw_times = 2;

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
		String mode = View.console_or_file_input();
		
		boolean continue_playing = true;
		
		while(continue_playing) {
			switch(mode) {
			case "c":
				Game_Console game_console = new Game_Console();
				game_console.play();
				break;
			case "f":
				Game_File_Input game_file_input = new Game_File_Input();
				game_file_input.play();
				break;
			default:
				View.inavlid_input();
				start();
				return;
			}

			continue_playing = View.continue_playing();
		}

	}
	
}
