package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {

	/**
	 * Read game input from a text file.
	 * @param path of where the file is located
	 * @return array of cards obtained from file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String[] read_file_input(String path) throws FileNotFoundException, IOException {
		ArrayList<String> strings = new ArrayList<>(); // ArrayList where line from input file will be stored

		// I got this try-catch block from https://stackoverflow.com/questions/15695984/java-print-contents-of-text-file-to-screen
		try (BufferedReader buffered_reader = new BufferedReader(new FileReader(path))) {
			String line = null;
			while((line = buffered_reader.readLine()) != null) { strings.add(line); }
		}
		catch(FileNotFoundException e) {
			System.err.println("* File not found\n* Program has been terminated");
			System.exit(0);
		}

		String[] commands = strings.get(0).split(" "); // Line from file is split into individual cards or commands and stored in this array
		return commands;
	}

	/**
	 * Add card from input file to player's hand.
	 * @param commands from input
	 * @param index of command
	 * @param hand to add card to
	 */
	public void add_card_from_input(String[] commands, int index, Hand hand) {
		Card input_card;

		if(commands[index].charAt(1) != '1') {
			input_card = new Card(Character.toString(commands[index].charAt(0)), Character.toString(commands[index].charAt(1)));
		}
		else {
			input_card = new Card(Character.toString(commands[index].charAt(0)), "10");
		}

		hand.add(input_card);
	}

}
