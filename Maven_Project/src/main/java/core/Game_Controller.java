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

		System.out.println("Would you like to play using console or file input? (c/f): ");
		mode = View.scanner.next();
		
		boolean continue_playing = true;
		
		while(continue_playing) {
			switch(mode) {
			case "c":
				game = new Console_Game();
				break;
			case "f":
				game = new File_Game();
				break;
			default:
				View.inavlid_input();
				start();
				return;
			}
			
			game.play();
			continue_playing = continue_playing();
		}

	}

	/**
	 * User menu to decide whether or not to continue playing.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public boolean continue_playing() throws FileNotFoundException, IOException {
		View.divider();
		System.out.print("Continue playing? (y/n): ");
		String continue_play = View.scanner.next();

		switch(continue_play) {
		case "y":
			return true;
		case "n":
			System.out.println("Thanks for playing");
			break;
		default:
			View.inavlid_input();
			continue_playing();
			break;
		}
		
		return false;
	}
	
}
