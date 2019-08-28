package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Game_File_Input extends Game {

	public Game_File_Input() {
		super();
	}

	/**
	 * Play using file input.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void play() throws FileNotFoundException, IOException {
		// Read file input
		Reader reader = new Reader();
		String[] input_line = reader.read_file_input(View.select_file_input_path());
		View.divider();

		// Draw player's first two cards
		for(int i = 0; i < draw_times; i++) reader.add_card_from_input(input_line[i], get_guest().get_default_hand());
		// Draw dealer's first two cards
		for(int i = draw_times; i < 4; i++) reader.add_card_from_input(input_line[i], get_dealer().get_default_hand());

		// Go through rest of the input
		boolean guest_turn = true;
		input:
			for(int i = 4; i < input_line.length; i++) {
				if(input_line[i].length() == 1) {
					switch(input_line[i].charAt(0)) {
					case 'S': // Stand
						guest_turn = guest_turn ? false : true;
						continue input;
					case 'H': // Hit
						continue input;
					case 'D': // Split
						continue input;
					}
				}

				if(guest_turn) reader.add_card_from_input(input_line[i], get_guest().get_default_hand());
				else reader.add_card_from_input(input_line[i], get_dealer().get_default_hand());
			}

		// End game
		Winner_Caller.determine_winner(get_guest(), get_dealer());
	}

}
