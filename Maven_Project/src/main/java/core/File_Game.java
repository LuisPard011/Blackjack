package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class File_Game extends Game {
	
	public File_Game() {
		super();
	}
	
	/**
	 * Play using file input.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void play() throws FileNotFoundException, IOException {
		// Local variables
		boolean stand = false;

		// Read file input
		Reader reader = new Reader();
		String[] input_line;

		// File paths
		int path_to_play;
		String[] paths = new String[] {
				"src\\main\\java\\input_files\\Input_File_1.txt",
				"src\\main\\java\\input_files\\Input_File_2.txt",
				"src\\main\\java\\input_files\\Input_File_3.txt",
				"src\\main\\java\\input_files\\Input_File_4.txt",
				"src\\main\\java\\input_files\\Input_File_5.txt"};

		System.out.println("Note: Features to support paths 4 and 5 have not been implemented.\nWhat path would you like to play? [1-5]: ");
		path_to_play = View.scanner.nextInt();

		switch(path_to_play) {
		case 1:
			input_line = reader.read_file_input(paths[0]);
			break;
		case 2:
			input_line = reader.read_file_input(paths[1]);
			break;
		case 3:
			input_line = reader.read_file_input(paths[2]);
			break;
		case 4:
			input_line = reader.read_file_input(paths[3]);
			break;
		case 5:
			input_line = reader.read_file_input(paths[4]);
			break;
		default:
			System.out.println("That path does not exist.\nPlease try again.\nThis time only choose a path within the range [1-5], inclusive");
			play();
			return;
		}

		View.divider();

		// Draw player's first two cards
		for(int i = 0; i < draw_times; i++) reader.add_card_from_input(input_line[i], get_guest().get_default_hand());

		// Draw dealer's first two cards
		for(int i = draw_times; i < 4; i++) reader.add_card_from_input(input_line[i], get_dealer().get_default_hand());

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

				if(!stand) reader.add_card_from_input(input_line[i], get_guest().get_default_hand());
				else reader.add_card_from_input(input_line[i], get_dealer().get_default_hand());	
			}

		// End game
		determine_winner(get_guest(), get_dealer());
	}

}
