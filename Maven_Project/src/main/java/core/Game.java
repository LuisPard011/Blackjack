package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Game
{	
	/**
	 * Choose between console or file input modes
	 * @param scanner
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public boolean choose_mode(Scanner scanner) throws FileNotFoundException, IOException
	{
		System.out.println("Console or file input? (c/f): ");
		String mode = scanner.next();
		
		if(mode.equalsIgnoreCase("c")) {play_console();}
		else if(mode.equalsIgnoreCase("f")) {play_file();}
		else
		{
			System.out.println("Invalid input");
			choose_mode(scanner);
		}
		
		return true;
	}
	
	/**
	 * Play in console mode
	 */
	public boolean play_console()
	{	
		// Variables
		int draw_times = 2;
		Scanner scanner = new Scanner(System.in);
		
		// Create deck
		Deck_Maker deck_maker = new Deck_Maker();
		Stack<Card> deck = new Stack<Card>();
		deck_maker.make_deck(deck);
		
		// Create player
		Player player = new Player("Player");
		player.hit(deck, draw_times);
		player.show_cards(2);
		
		// Create dealer
		Dealer dealer = new Dealer("Dealer");
		dealer.hit(deck, draw_times);
		dealer.show_cards(1);
		
		// Player's turn
		player.player_turn(deck, dealer);
		if(player.bust)
		{
			continue_play();
			return true;
		}
		
		// Dealer's turn
		dealer.dealer_turn(deck, player);
		if(dealer.bust)
		{
			continue_play();
			return true;
		}
		
		// If there are no busts
		player.determine_winner(dealer);
		continue_play();
		return true;
	}
	
	/**
	 * Play in file input mode
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public boolean play_file() throws FileNotFoundException, IOException
	{
		// Path to file
		String path_1 = "src\\main\\java\\core\\Input_File_1.txt";
		String path_2 = "src\\main\\java\\core\\Input_File_2.txt";
		String path_3 = "src\\main\\java\\core\\Input_File_3.txt";
		
		// Read file input
		Reader reader = new Reader();
		String[] commands = reader.read_file_input(path_3);
		
		// Create players
		Player player = new Player("Player");
		Dealer dealer = new Dealer("Dealer");
		
		// Player's first two cards
		for(int i = 0; i < 2; ++i){reader.add_card_from_input(player, commands, i);}
		player.show_cards(2);
		player.show_score();
		
		// Dealer's first two cards
		for(int i = 2; i < 4; ++i){reader.add_card_from_input(dealer, commands, i);}
		dealer.show_cards(2);
		dealer.show_score();
		
		for(int i = 4; i < commands.length; ++i)
		{
			/*
			 * All cards from now on are added to player
			 * This is the case, until arr[i].charAt(0) == 'S' 
			 * And the same string is of length 1
			 * After this point, all cards are added to the dealer
			 */
			if(commands[i].charAt(0) == 'S' && commands[i].length() == 1)
			{
				player.stand = true;
				continue;
			}
			else if(commands[i].charAt(0) == 'H' && commands[i].length() == 1){continue;}
			
			if(!player.stand){reader.add_card_from_input(player, commands, i);}
			else{reader.add_card_from_input(dealer, commands, i);}
		}
		
		player.show_cards(player.hand.size());
		player.show_score();
		dealer.show_cards(dealer.hand.size());
		dealer.show_score();
		return true;
	}
	
	/**
	 * User menu to decide whether or not to continue playing
	 * @param scanner
	 */
	public boolean continue_play()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Continue playing? (y/n): ");
		String continue_play = scanner.next();
		
		if(continue_play.equalsIgnoreCase("y"))
		{
			play_console();
			return true;
		}
		else if(continue_play.equalsIgnoreCase("n"))
		{
			System.out.println("Thanks for playing");
			return true;
		}
		else
		{
			System.out.println("Invalid input");
			continue_play();
			return true;
		}
	}
}
