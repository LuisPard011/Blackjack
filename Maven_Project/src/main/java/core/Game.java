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
	 * If neither the player nor the dealer busts
	 * Then, scores are compared to determine winner
	 * @param guest
	 * @param dealer
	 */
	public void determine_winner(Player guest, Player dealer)
	{
		if(guest.score > dealer.score)
		{
			System.out.println("Player wins");
		}
		else if(guest.score == dealer.score)
		{
			System.out.println("Player pushes");
		}
		else
		{
			System.out.println("Dealer wins");
		}
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
		
		// Create guest
		Player guest = new Player("Guest");
		guest.hit(deck, draw_times);
		guest.show_cards(2);
		
		// Create dealer
		Player dealer = new Player("Dealer");
		dealer.hit(deck, draw_times);
		dealer.show_cards(1);
		
		// Guest's turn
		guest.guest_turn(deck, dealer);
		if(guest.bust)
		{
			continue_play();
			return true;
		}
		
		// Dealer's turn
		dealer.dealer_turn(deck, guest);
		if(dealer.bust)
		{
			continue_play();
			return true;
		}
		
		// If there are no busts
		determine_winner(guest, dealer);
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
		Player guest = new Player("Guest");
		Player dealer = new Player("Dealer");
		
		// Guest's first two cards
		for(int i = 0; i < 2; ++i){reader.add_card_from_input(guest, commands, i);}
		guest.show_cards(2);
		guest.show_score();
		
		// Dealer's first two cards
		for(int i = 2; i < 4; ++i){reader.add_card_from_input(dealer, commands, i);}
		dealer.show_cards(2);
		dealer.show_score();
		
		for(int i = 4; i < commands.length; ++i)
		{
			/*
			 * All cards from now on are added to guest
			 * This is the case, until arr[i].charAt(0) == 'S' 
			 * And the same string is of length 1
			 * After this point, all cards are added to the dealer
			 */
			if(commands[i].charAt(0) == 'S' && commands[i].length() == 1)
			{
				guest.stand = true;
				continue;
			}
			else if(commands[i].charAt(0) == 'H' && commands[i].length() == 1){continue;}
			
			if(!guest.stand){reader.add_card_from_input(guest, commands, i);}
			else{reader.add_card_from_input(dealer, commands, i);}
		}
		
		guest.show_cards(guest.hand.size());
		guest.show_score();
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
