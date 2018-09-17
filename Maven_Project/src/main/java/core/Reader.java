package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader
{
	public String[] read_file_input(String path) throws FileNotFoundException, IOException
	{
		// ArrayList where line from input file will be stored
		ArrayList<String> words = new ArrayList<>();
		
		/*
		 *  I got this block of code to read from file from StackOverflow
		 *  https://stackoverflow.com/questions/15695984/java-print-contents-of-text-file-to-screen
		 *  The comment from user Dariusz is where I got this block
		 *  I didn't even change some of the variable names
		 */
		try (BufferedReader br = new BufferedReader(new FileReader(path)))
		{
			String line = null;
			while ((line = br.readLine()) != null) {words.add(line);}
		}
		
		// Line from file is split into individual words and stored in this array
		String[] commands = words.get(0).split(" ");
		
		return commands;
	}
	
	public void add_card_from_input(Player player, String[] array, int index)
	{
		Card input_card;
		if(array[index].charAt(1) != '1')
		{
			input_card = new Card(Character.toString(array[index].charAt(0)), Character.toString(array[index].charAt(1)));
		}
		else
		{
			input_card = new Card(Character.toString(array[index].charAt(0)), "10");
		}
		player.add(input_card);
	}
}
