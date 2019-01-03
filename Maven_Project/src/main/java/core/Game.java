package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Game {

	final int draw_times = 2;

	/**
	 * Choose between console or file input modes.
	 * @param scanner
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void choose_mode(Scanner scanner) throws FileNotFoundException, IOException {
		// Interface output
		System.out.println("Console or file input? (c/f): ");
		String mode = scanner.next();

		switch(mode) {
		case "c":
			play_console();
			break;
		case "f":
			play_file();
			break;
		default:
			System.out.println("Invalid input");
			choose_mode(scanner);
			break;
		}
	}

	/**
	 * Play in console mode.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void play_console() throws FileNotFoundException, IOException {	
		// Variables
		Deck deck = new Deck();
		Player player = new Player();
		Dealer dealer = new Dealer();

		// Player setup
		player.hit(deck, draw_times, player.get_default_hand());
		View.cards(draw_times, player.get_default_hand());

		// Dealer setup
		dealer.hit(deck, draw_times, dealer.get_default_hand());
		View.cards(1, dealer.get_default_hand());

		if(player.blackjack_Win(dealer)) {
			View.cards(draw_times, dealer.get_default_hand());
			continue_play();
		}

		// Player's turn
		if(player.can_split()) {
			if(player.choose_split()) {
				player.split_hand();
				player.split_turn(deck, dealer);
			}
			else player.hit_or_stand(deck, player.get_default_hand(), dealer);
		}
		else player.hit_or_stand(deck, player.get_default_hand(), dealer);

		// Dealer's turn
		View.cards(draw_times, dealer.get_default_hand());
		if(dealer.can_split()) {
			if(dealer.choose_split()) {
				dealer.split_hand();
				dealer.dealer_turn(deck, player, dealer.get_split_hand_1());
				dealer.dealer_turn(deck, player, dealer.get_split_hand_2());
			}
			else dealer.dealer_turn(deck, player, dealer.get_default_hand());
		}
		else dealer.dealer_turn(deck, player, dealer.get_default_hand());

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
		Player player = new Player();
		Dealer dealer = new Dealer();

		// Paths to files
		//		String path_1 = "src\\main\\java\\core\\Input_File_1.txt";
		//		String path_2 = "src\\main\\java\\core\\Input_File_2.txt";
		String path_3 = "src\\main\\java\\core\\Input_File_3.txt";
		//		String path_4 = "src\\main\\java\\core\\Input_File_4.txt";
		//		String path_5 = "src\\main\\java\\core\\Input_File_5.txt";

		// Read file input
		Reader reader = new Reader();
		String[] commands = reader.read_file_input(path_3);

		// Draw player's first two cards
		for(int i = 0; i < draw_times; ++i) {
			reader.add_card_from_input(player, commands, i, player.get_default_hand());
		}

		// Interface output
		View.cards(draw_times, player.get_default_hand());
		View.score(player.get_default_hand());

		// Draw dealer's first two cards
		for(int i = draw_times; i < 4; ++i) {
			reader.add_card_from_input(dealer, commands, i, dealer.get_default_hand());
		}

		// Interface output
		View.cards(draw_times, dealer.get_default_hand());
		View.score(dealer.get_default_hand());

		// Go through the rest of the input
		for(int i = 4; i < commands.length; ++i) {
			/*
			 * Add cards to player's default hand until arr[i].charAt(0) == 'S' and the same string is of length 1
			 * After this point, all cards are added to the dealer
			 */
			if(commands[i].charAt(0) == 'S' && commands[i].length() == 1) {
				stand = true;
				continue;
			}
			else if(commands[i].charAt(0) == 'H' && commands[i].length() == 1){continue;}
			else if(commands[i].charAt(0) == 'D' && commands[i].length() == 1) {
				// will have to add not to default hand
				// maybe continue might come in handy
			}

			if(!stand) {
				reader.add_card_from_input(player, commands, i, player.get_default_hand());
			}
			else {
				reader.add_card_from_input(dealer, commands, i, dealer.get_default_hand());	
			}
		}

		// Interface output
		View.cards(player.get_default_hand().size(), player.get_default_hand());
		View.score(player.get_default_hand());
		View.cards(dealer.get_default_hand().size(), dealer.get_default_hand());
		View.score(dealer.get_default_hand());

		// End game
		player.determine_winner(dealer);
		continue_play();
	}

	/**
	 * User menu to decide whether or not to continue playing
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void continue_play() throws FileNotFoundException, IOException {
		System.out.print("Continue playing? (y/n): ");
		String continue_play = View.scanner.next();

		switch(continue_play) {
		case "y":
			choose_mode(View.scanner);
			break;
		case "n":
			System.out.println("Thanks for playing");
			break;
		default:
			System.out.println("Invalid input");
			continue_play();
			break;
		}
	}
}
