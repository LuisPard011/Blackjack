package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {
	
	/********
	 * ELSE *
	 ********/
	/**
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String[] read_file_input(String path) throws FileNotFoundException, IOException {
		ArrayList<String> words = new ArrayList<>(); // ArrayList where line from input file will be stored
		
		// I got this try-catch block from https://stackoverflow.com/questions/15695984/java-print-contents-of-text-file-to-screen
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = null;
			while((line = br.readLine()) != null) { words.add(line); }
		}
		catch(FileNotFoundException e) {
			System.err.println("* File not found\n* Program has been terminated");
			System.exit(0);
		}
		
		String[] commands = words.get(0).split(" "); // Line from file is split into individual words and stored in this array
		return commands;
	}
	
	/**
	 * 
	 * @param player
	 * @param array
	 * @param index
	 * @param hand
	 */
	public void add_card_from_input(Player player, String[] array, int index, Hand hand) {
		Card input_card;
		
		if(array[index].charAt(1) != '1') {
			input_card = new Card(Character.toString(array[index].charAt(0)), Character.toString(array[index].charAt(1)));
		}
		else {
			input_card = new Card(Character.toString(array[index].charAt(0)), "10");
		}
		
		hand.add(input_card);
	}
	
}
