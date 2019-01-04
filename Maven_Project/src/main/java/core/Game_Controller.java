package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Game_Controller {

	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	final int draw_times = 2;
	private Deck deck;
	private Player player;
	private Dealer dealer;

	/********
	 * ELSE *
	 ********/
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
		deck = new Deck();
		player = new Player();
		dealer = new Dealer();

		// Player setup
		player.hit(deck, draw_times, player.get_default_hand());
		System.out.println(player.get_default_hand());

		// Dealer setup
		dealer.hit(deck, draw_times, dealer.get_default_hand());
		System.out.println("Dealer's face-up card is: " + dealer.get_default_hand().get(0).toString());

		if(blackjack_win(player, dealer)) {
			System.out.println(dealer.get_default_hand());
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
		System.out.println(dealer.get_default_hand());
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
		determine_winner(player, dealer);
		continue_play();
	}

	/**
	 * Play in file input mode
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void play_file() throws FileNotFoundException, IOException {
		// Local variables
		boolean stand = false;
		player = new Player();
		dealer = new Dealer();

		// Paths to files
		//		String path_1 = "src\\main\\java\\text_files\\Input_File_1.txt";
		//		String path_2 = "src\\main\\java\\text_files\\Input_File_2.txt";
		String path_3 = "src\\main\\java\\text_files\\Input_File_3.txt";
		//		String path_4 = "src\\main\\java\\text_files\\Input_File_4.txt";
		//		String path_5 = "src\\main\\java\\text_files\\Input_File_5.txt";

		// Read file input
		Reader reader = new Reader();
		String[] commands = reader.read_file_input(path_3);

		// Draw player's first two cards
		for(int i = 0; i < draw_times; ++i) {
			reader.add_card_from_input(player, commands, i, player.get_default_hand());
		}

		// Interface output
		System.out.println(player.get_default_hand());
		View.score(player.get_default_hand());

		// Draw dealer's first two cards
		for(int i = draw_times; i < 4; ++i) {
			reader.add_card_from_input(dealer, commands, i, dealer.get_default_hand());
		}

		// Interface output
		System.out.println(dealer.get_default_hand());
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
			else if(commands[i].charAt(0) == 'H' && commands[i].length() == 1) continue;
			else if(commands[i].charAt(0) == 'D' && commands[i].length() == 1) {
				// will have to add not to default hand
				// maybe continue might come in handy
			}

			if(!stand) reader.add_card_from_input(player, commands, i, player.get_default_hand());
			else reader.add_card_from_input(dealer, commands, i, dealer.get_default_hand());	
		}

		// Interface output
		System.out.println(player.get_default_hand());
		View.score(player.get_default_hand());
		System.out.println(dealer.get_default_hand());
		View.score(dealer.get_default_hand());

		// End game
		determine_winner(player, dealer);
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
	
	/**
	 * If neither the player nor the dealer busts, scores are compared to determine winner
	 * @param dealer
	 */
	public static void determine_winner(Player player, Dealer dealer) {	
		// Variables
		int player_highest_score = 0;
		int dealer_highest_score = 0;
		Hand player_best_hand = new Hand();
		Hand dealer_best_hand = new Hand();

		// Check if player is bust and find its hand with the highest score under 22
		if(!player.get_default_hand().bust() && !player.get_splitted()) {
			player_highest_score = player.get_default_hand().get_score();
			player_best_hand = player.get_default_hand();
		}
		if(player_highest_score < player.get_split_hand_1().get_score() && !player.get_split_hand_1().bust()) {
			player_highest_score = player.get_split_hand_1().get_score();
			player_best_hand = player.get_split_hand_1();
		}
		if(player_highest_score < player.get_split_hand_2().get_score() && !player.get_split_hand_2().bust()) {
			player_highest_score = player.get_split_hand_2().get_score();
			player_best_hand = player.get_split_hand_2();
		}

		// Check if dealer is bust and find its hand with the highest score under 22
		if(!dealer.get_default_hand().bust() && !dealer.get_splitted()) {
			dealer_highest_score = dealer.get_default_hand().get_score();
			dealer_best_hand = dealer.get_default_hand();
		}
		if(dealer_highest_score < dealer.get_split_hand_1().get_score() && !dealer.get_split_hand_1().bust()) {
			dealer_highest_score = dealer.get_split_hand_1().get_score();
			dealer_best_hand = dealer.get_split_hand_1();
		}
		if(dealer_highest_score < dealer.get_split_hand_2().get_score() && !dealer.get_split_hand_2().bust()) {
			dealer_highest_score = dealer.get_split_hand_2().get_score();
			dealer_best_hand = dealer.get_split_hand_2();
		}

		// Interface output
		if(player_highest_score > dealer_highest_score) {
			System.out.println("Player: " + player_best_hand.toString() + " (" + player_highest_score + ") points.\n" +
					           "Dealer: " + dealer_best_hand.toString() + " (" + dealer_highest_score + ") points.\n" +
					           "Winner: Player");
			player.set_winner(true);
		}
		else if(player_highest_score == 0) {
			System.out.println("Player busted.\n" +
		                       "Dealer wins: " + dealer_best_hand.toString() + " (" + dealer_highest_score + ") points.");
			dealer.set_winner(true);
		}
		else if(dealer_highest_score == 0) {
			System.out.println("Dealer busted.\n" + 
		                       "Player wins: " + player_best_hand.toString() + " (" + player_highest_score + ") points.");
			player.set_winner(true);
		}
		else {
			System.out.println("Player: " + player_best_hand.toString() + " (" + player_highest_score + ") points.\n" + 
		                       "Dealer: " + dealer_best_hand.toString() + " (" + dealer_highest_score + ") points.\n" + 
					           "Winner: Dealer");
			dealer.set_winner(true);
		}
	}
	
	/**
	 * Look for blackjacks in all of player's and dealer's hands.
	 * Get rid of hand parameters and maybe dealer too.
	 * @param dealer
	 * @return true if anyone has a blackjack
	 */
	public static boolean blackjack_win(Player player, Dealer dealer) {
		if(player.get_default_hand().blackjack() || player.get_split_hand_1().blackjack() || player.get_split_hand_2().blackjack()) {
			player.set_has_blackjack(true);
		}
		if(dealer.get_default_hand().blackjack() || dealer.get_split_hand_1().blackjack() || dealer.get_split_hand_2().blackjack()) {
			dealer.set_has_blackjack(true);
		}

		if(player.get_has_blackjack() && !dealer.get_has_blackjack()) {
			System.out.println("Player has blackjack and dealer does not. Player wins");
			player.set_winner(true);
			return true;
		}
		else if(!player.get_has_blackjack() && dealer.get_has_blackjack()) {
			System.out.println("Player does not have a blackjack, but dealer does. Dealer wins");
			dealer.set_winner(true);
			return true;
		}
		else if(player.get_has_blackjack() && dealer.get_has_blackjack()) {
			System.out.println("Both the player and dealer have a blackjack. Dealer wins");
			dealer.set_winner(true);
			return true;
		}

		return false;
	}
}
