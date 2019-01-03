package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Game
{	
	public void welcome()
	{
		System.out.println("Hello, welcome to the Blackjack table");
		System.out.println("=== === === === ==== === === === ===");
	}
	
	/**
	 * Choose between console or file input modes
	 * @param scanner
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void choose_mode(Scanner scanner) throws FileNotFoundException, IOException
	{
		// Interface output
		System.out.println("Console or file input? (c/f): ");
		String mode = scanner.next();
		
		if(mode.equalsIgnoreCase("c"))
		{
			play_console();
		}
		else if(mode.equalsIgnoreCase("f"))
		{
			play_file();
		}
		else
		{
			System.out.println("Invalid input");
			choose_mode(scanner);
		}
	}
	
	/**
	 * Play in console mode
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void play_console() throws FileNotFoundException, IOException
	{	
		// Variables
		int draw_times = 2;
		
		// Create deck
		Deck_Maker deck_maker = new Deck_Maker();
		Stack<Card> deck = new Stack<Card>();
		deck_maker.make_deck(deck);
		
		// Create player
		Player player = new Player();
		player.hit(deck, draw_times, player.default_hand);
		player.default_hand.show_cards(2);
		
		// Create dealer
		Dealer dealer = new Dealer();
		dealer.hit(deck, draw_times, dealer.default_hand);
		dealer.default_hand.show_cards(1);
		
		if(player.blackjack_Win(dealer))
		{
			dealer.default_hand.show_cards(draw_times);
			continue_play();
		}
		
		// Player's turn
		if(player.can_split())
		{
			if(player.choose_split())
			{
				player.split_hand();
				player.split_turn(deck, dealer);
			}
			else
			{
				player.hit_or_stand(deck, player.default_hand, dealer);
			}
		}
		else
		{
			player.hit_or_stand(deck, player.default_hand, dealer);
		}
		
		// Dealer's turn
		dealer.default_hand.show_cards(draw_times);
		if(dealer.can_split())
		{
			if(dealer.choose_split())
			{
				dealer.split_hand();
				dealer.dealer_turn(deck, player, dealer.split_hand_1);
				dealer.dealer_turn(deck, player, dealer.split_hand_2);
			}
			else
			{
				dealer.dealer_turn(deck, player, dealer.default_hand);
			}
		}
		else
		{
			dealer.dealer_turn(deck, player, dealer.default_hand);
		}
		
		// End game
		player.determine_winner(dealer);
		continue_play();
	}
	
	/**
	 * Play in file input mode
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void play_file() throws FileNotFoundException, IOException
	{
		// Variables
		boolean stand = false;
		
		// Paths to files
		String path_1 = "src\\main\\java\\core\\Input_File_1.txt";
		String path_2 = "src\\main\\java\\core\\Input_File_2.txt";
		String path_3 = "src\\main\\java\\core\\Input_File_3.txt";
//		String path_4 = "src\\main\\java\\core\\Input_File_4.txt";
//		String path_5 = "src\\main\\java\\core\\Input_File_5.txt";
		
		// Read file input
		Reader reader = new Reader();
		String[] commands = reader.read_file_input(path_3);
		
		// Create players
		Player player = new Player();
		Dealer dealer = new Dealer();
		
		// Draw player's first two cards
		for(int i = 0; i < 2; ++i)
		{
			reader.add_card_from_input(player, commands, i, player.default_hand);
		}
		
		// Interface output
		player.default_hand.show_cards(2);
		player.default_hand.show_score();
		
		// Draw dealer's first two cards
		for(int i = 2; i < 4; ++i)
		{
			reader.add_card_from_input(dealer, commands, i, dealer.default_hand);
		}
		
		// Interface output
		dealer.default_hand.show_cards(2);
		dealer.default_hand.show_score();
		
		// Go through the rest of the input
		for(int i = 4; i < commands.length; ++i)
		{
			/*
			 * Add cards to player's default hand until arr[i].charAt(0) == 'S' and the same string is of length 1
			 * After this point, all cards are added to the dealer
			 */
			if(commands[i].charAt(0) == 'S' && commands[i].length() == 1)
			{
				stand = true;
				continue;
			}
			else if(commands[i].charAt(0) == 'H' && commands[i].length() == 1){continue;}
			else if(commands[i].charAt(0) == 'D' && commands[i].length() == 1)
			{
				// will have to add not to default hand
				// maybe continue might come in handy
			}
			
			if(!stand)
			{
				reader.add_card_from_input(player, commands, i, player.default_hand);
			}
			else
			{
				reader.add_card_from_input(dealer, commands, i, dealer.default_hand);	
			}
		}
		
		// Interface output
		player.default_hand.show_cards(player.default_hand.size());
		player.default_hand.show_score();
		dealer.default_hand.show_cards(dealer.default_hand.size());
		dealer.default_hand.show_score();
		
		// End game
		player.determine_winner(dealer);
		continue_play();
	}
	
	/**
	 * User menu to decide whether or not to continue playing
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void continue_play() throws FileNotFoundException, IOException
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Continue playing? (y/n): ");
		String continue_play = scanner.next();
		
		if(continue_play.equalsIgnoreCase("y"))
		{
			choose_mode(scanner);
		}
		else if(continue_play.equalsIgnoreCase("n"))
		{
			System.out.println("Thanks for playing");
		}
		else
		{
			System.out.println("Invalid input");
			continue_play();
		}
	}
}
