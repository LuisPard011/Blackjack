package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Game_File_Input {
	
	/**
	 * Play using file input.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void play() throws FileNotFoundException, IOException {
		// Local variables
		Dealer dealer = new Dealer();
		Guest guest = new Guest();
		boolean stand = false;

		// Read file input
		Reader reader = new Reader();
		String[] input_line = reader.read_file_input(View.select_file_input_path());
		View.divider();

		// Draw player's first two cards
		for(int i = 0; i < Game_Controller.draw_times; i++) reader.add_card_from_input(input_line[i], guest.get_default_hand());

		// Draw dealer's first two cards
		for(int i = Game_Controller.draw_times; i < 4; i++) reader.add_card_from_input(input_line[i], dealer.get_default_hand());

		// Go through rest of the input
		input:
			for(int i = 4; i < input_line.length; i++) {
				if(input_line[i].length() == 1) {
					switch(input_line[i].charAt(0)) {
					case 'S': // Stand
						stand = true;
						continue input;
					case 'H': // Hit
						continue input;
					case 'D': // Split
						continue input;
					}
				}

				if(!stand) reader.add_card_from_input(input_line[i], guest.get_default_hand());
				else reader.add_card_from_input(input_line[i], dealer.get_default_hand());	
			}

		// End game
		Winner_Caller.determine_winner(guest, dealer);
	}

}
