package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
	/**
	 * Main function
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException
	{	
		Scanner reader = new Scanner(System.in);
		Game game = new Game();
		game.choose_mode(reader);
		reader.close();
	}
}
