package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Game_File_Input extends Game {

	public Game_File_Input() { super(); }

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

		boolean guest_turn = true;
		int dealer_counter = 0, guest_counter = 0;

		input:
			for(int i = 4; i < input_line.length; i++) { // Go through rest of the input
				if(dealer_counter > 0) {
					if(dealer_counter == input_line.length) {
						Winner_Caller.determine_winner(get_guest(), get_dealer());
						return;
					}
					else {
						dealer_counter--;
						continue;
					}
				}

				if(guest_counter > 0) {
					if(guest_counter == input_line.length) {
						Winner_Caller.determine_winner(get_guest(), get_dealer());
						return;
					}
					else {
						guest_counter--;
						continue;
					}
				}

				if(input_line[i].length() == 1) {
					switch(input_line[i]) {
					case "S": // Stand
						guest_turn = guest_turn ? false : true;
						continue input;
					case "H": // Hit
						continue input;
					case "D": // Split
						get_guest().split_hand();
						guest_counter = i+1;
						reader.add_card_from_input(input_line[guest_counter], get_guest().get_split_hand_1());
						guest_counter += 1;

						// input_line[guest_counter] now is either H or S
						if(input_line[guest_counter].equalsIgnoreCase("h")) {
							guest_counter += 1; // split hand 1
							
							while(!input_line[guest_counter].equalsIgnoreCase("s") || get_guest().get_split_hand_1().get_score() > 21) { // Until bust or stand
								reader.add_card_from_input(input_line[guest_counter], get_guest().get_split_hand_1());
								guest_counter += 1;
							}
							
							if(get_guest().get_split_hand_1().bust()) { // Exited while loop because of a bust
								reader.add_card_from_input(input_line[guest_counter], get_guest().get_split_hand_2());
							}
							else { // Exited while loop because player chose to stand, move on to split hand 2
								guest_counter += 1; // Skip the S and draw
								reader.add_card_from_input(input_line[guest_counter], get_guest().get_split_hand_2()); // draw
								guest_counter += 1; // Move on to next command
								if(input_line[guest_counter].equalsIgnoreCase("h")) {
									guest_counter += 1;
									
									
									while(!input_line[guest_counter].equalsIgnoreCase("s") || get_guest().get_split_hand_2().get_score() > 21) { // Until bust or stand
										reader.add_card_from_input(input_line[guest_counter], get_guest().get_split_hand_2());
										guest_counter += 1;
									}
								}		
							}
						}

						continue input;
					}
				}

				if(guest_turn) reader.add_card_from_input(input_line[i], get_guest().get_default_hand());
				else {
					if(get_dealer().can_split()) {
						get_dealer().split_hand();
						dealer_counter = i;
						
						
						while (get_dealer().get_split_hand_1().get_score() <= 16 || get_dealer().get_split_hand_1().soft_17()) {
							reader.add_card_from_input(input_line[dealer_counter], get_dealer().get_split_hand_1());
							dealer_counter++;
						}
						while (get_dealer().get_split_hand_2().get_score() <= 16 || get_dealer().get_split_hand_2().soft_17()) {
							reader.add_card_from_input(input_line[dealer_counter], get_dealer().get_split_hand_2());
							dealer_counter++;
						}
						
						
					}
					else reader.add_card_from_input(input_line[i], get_dealer().get_default_hand());
				}

			}

		// End game
		Winner_Caller.determine_winner(get_guest(), get_dealer());
	}

}
