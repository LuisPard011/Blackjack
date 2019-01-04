package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Game_Controller {

	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	private final int draw_times = 2;
	private Deck deck;
	private Player player;
	private House dealer;

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
			View.inavlid_input();
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
		// Local variables
		deck = new Deck();
		player = new Player();
		dealer = new House();

		// Player setup
		player.hit(deck, draw_times, player.get_default_hand());
		System.out.println(player.get_default_hand());

		// Dealer setup
		dealer.hit(deck, draw_times, dealer.get_default_hand());
		System.out.println("Dealer's face-up card is: [" + dealer.get_default_hand().get(0).toString() + "]");

		blackjack_win(player, dealer);

		if(!player.get_winner() && !dealer.get_winner()) {
			// Player's turn
			if(player.can_split()) {
				if(choose_split(player)) {
					player.split_hand();
					split_turn(deck, player);
				}
				else hit_or_stand(deck, player.get_default_hand(), player);
			}
			else hit_or_stand(deck, player.get_default_hand(), player);

			// Dealer's turn
			System.out.println(dealer.get_default_hand());
			if(dealer.can_split()) {
				if(choose_split(dealer)) {
					dealer.split_hand();
					dealer.dealer_turn(deck, player, dealer.get_split_hand_1());
					dealer.dealer_turn(deck, player, dealer.get_split_hand_2());
				}
				else dealer.dealer_turn(deck, player, dealer.get_default_hand());
			}
			else dealer.dealer_turn(deck, player, dealer.get_default_hand());

			// End game
			determine_winner(player, dealer);
		}

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
		dealer = new House();

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
			View.inavlid_input();
			continue_play();
			break;
		}
	}
	
	/**
	 * If neither the player nor the dealer busts, scores are compared to determine winner
	 * @param dealer
	 */
	public static void determine_winner(Player player, House dealer) {	
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
	 * @param player whose hands will be checked for blackjacks
	 * @param dealer whose hands will be checked for blackjacks
	 * @return true if either the player, dealer or both have at least one blackjack
	 */
	public static boolean blackjack_win(Player player, House dealer) {
		if(player.get_default_hand().has_blackjack() || player.get_split_hand_1().has_blackjack() || player.get_split_hand_2().has_blackjack()) {
			player.set_has_blackjack(true);
		}
		if(dealer.get_default_hand().has_blackjack() || dealer.get_split_hand_1().has_blackjack() || dealer.get_split_hand_2().has_blackjack()) {
			dealer.set_has_blackjack(true);
		}

		if(player.get_has_blackjack() && !dealer.get_has_blackjack()) {
			System.out.println("Player has blackjack and dealer does not.\nWinner: Player" );
			player.set_winner(true);
			return true;
		}
		else if(!player.get_has_blackjack() && dealer.get_has_blackjack()) {
			System.out.println("Player does not have a blackjack, but dealer does.\nWinner: Dealer");
			dealer.set_winner(true);
			return true;
		}
		else if(player.get_has_blackjack() && dealer.get_has_blackjack()) {
			System.out.println("Both the player and dealer have a blackjack.\nWinner: Dealer");
			dealer.set_winner(true);
			return true;
		}

		return false;
	}
	
	/**
	 * Give player the option to split hand.
	 * @return true if split has been chosen
	 */
	public static boolean choose_split(Player guest_or_house) {
		String choose_split;

		System.out.println("Would you like to split? (y/n): ");
		choose_split = View.scanner.next();

		if(choose_split.equalsIgnoreCase("y")) {
			guest_or_house.set_splitted(true);
			return true;
		}
		else return false;
	}
	
	/**
	 * Give player the option to either hit or stand.
	 * @param deck to draw from
	 * @param hand to add card drawn
	 * @return true if stand is chosen
	 */
	public boolean hit_or_stand(Stack<Card> deck, Hand hand, Player guest_or_house) {
		// Local variables
		boolean stand = false;

		while(!hand.bust() && !stand) {
			// Interface output
			System.out.println(hand);
			View.score(hand);
			System.out.print("Hit or stand? (h/s): ");
			String hit_or_stand = View.scanner.next();

			// control structure for hit or stand
			switch(hit_or_stand) {
			case "h":
				guest_or_house.hit(deck, 1, hand);
				break;
			case "s":
				stand = true;	
				break;
			default:
				View.inavlid_input();
				hit_or_stand(deck, hand, guest_or_house);
				break;
			}

			if(hand.bust()) {
				System.out.println(hand);
				guest_or_house.bust();
			}
		}
		
		return stand;
	}
	
	/**
	 * Routine for a turn after splitting initial hand.
	 * @param deck to draw from
	 * @param guest_or_house
	 */
	public void split_turn(Stack<Card> deck, Player guest_or_house) {
		guest_or_house.hit(deck, 1, guest_or_house.get_split_hand_1());
		hit_or_stand(deck, guest_or_house.get_split_hand_1(), guest_or_house);

		guest_or_house.hit(deck, 1, guest_or_house.get_split_hand_2());
		hit_or_stand(deck, guest_or_house.get_split_hand_2(), guest_or_house);
	}
}
