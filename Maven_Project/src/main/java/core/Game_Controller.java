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
			continue_playing = continue_playing();
		}

	}
	
	/**
	 * Routine for a turn after splitting initial hand.
	 * @param deck to draw from
	 * @param guest whose turn it is
	 */
	public void split_turn(Deck deck, Guest guest) {
		guest.hit(deck, 1, guest.get_split_hand_1());
		hit_or_stand(deck, guest.get_split_hand_1(), guest);

		guest.hit(deck, 1, guest.get_split_hand_2());
		hit_or_stand(deck, guest.get_split_hand_2(), guest);
	}
	
}
