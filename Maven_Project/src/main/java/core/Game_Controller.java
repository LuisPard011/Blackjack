package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

public class Game_Controller {

	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	private final int draw_times = 2;

	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private String mode;
	private String guest_name;

	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Game_Controller() {
		mode = "";
		guest_name = "Guest";
	}

	/********
	 * ELSE *
	 ********/
	/**
	 * Choose between console or file input modes.
	 * @param scanner
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void start() throws FileNotFoundException, IOException {
		View.welcome();

		System.out.println("Would you like to play using console or file input? (c/f): ");
		mode = View.scanner.next();

		switch(mode) {
		case "c":			
			System.out.println("What is your name?: ");
			guest_name = View.scanner.next();
			play_console();
			break;
		case "f":
			play_file();
			break;
		default:
			View.inavlid_input();
			start();
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
		Deck deck = new Deck();
		Guest guest = new Guest(guest_name);
		Dealer dealer = new Dealer();
		
		View.divider();

		// Guest setup
		guest.hit(deck, draw_times, guest.get_default_hand());
		View.hand(guest, guest.get_default_hand());

		// Dealer setup
		dealer.hit(deck, draw_times, dealer.get_default_hand());
		System.out.println("Dealer: [" + dealer.get_default_hand().get(0) + "]");

		blackjack_win(guest, dealer);
		
		game:
		if(!guest.get_winner() && !dealer.get_winner()) {
			// Guest's turn
			if(guest.can_split() && choose_split(guest)) {
				guest.split_hand();
				split_turn(deck, guest);
			}
			else hit_or_stand(deck, guest.get_default_hand(), guest);
			
			if(blackjack_win(guest, dealer)) break game;

			// Dealer's turn
			if(!guest.completely_busted()) {
				View.hand(dealer, dealer.get_default_hand());
				if(dealer.can_split() && choose_split(dealer)) {
					dealer.split_hand();
					dealer.dealer_turn(deck, dealer.get_split_hand_1());
					dealer.dealer_turn(deck, dealer.get_split_hand_2());
				}
				else dealer.dealer_turn(deck, dealer.get_default_hand());
			}
		}

		// End game
		determine_winner(guest, dealer);
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
		Guest guest = new Guest(guest_name);
		Dealer dealer = new Dealer();

		// File paths
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
			reader.add_card_from_input(guest, commands, i, guest.get_default_hand());
		}

		View.divider();
		View.hand(guest, guest.get_default_hand());

		// Draw dealer's first two cards
		for(int i = draw_times; i < 4; ++i) {
			reader.add_card_from_input(dealer, commands, i, dealer.get_default_hand());
		}

		View.hand(dealer, dealer.get_default_hand());

		// Go through the rest of the input
		for(int i = 4; i < commands.length; ++i) {

			// Add cards to player's default hand until arr[i].charAt(0) == 'S' and the same string is of length 1
			// After this point, all cards are added to the dealer
			if(commands[i].charAt(0) == 'S' && commands[i].length() == 1) {
				stand = true;
				continue;
			}
			else if(commands[i].charAt(0) == 'H' && commands[i].length() == 1) continue;
			else if(commands[i].charAt(0) == 'D' && commands[i].length() == 1) {
				// will have to add not to default hand
				// maybe continue might come in handy
			}

			if(!stand) reader.add_card_from_input(guest, commands, i, guest.get_default_hand());
			else reader.add_card_from_input(dealer, commands, i, dealer.get_default_hand());	
		}

		View.hand(guest, guest.get_default_hand());
		View.hand(dealer, dealer.get_default_hand());

		// End game
		determine_winner(guest, dealer);
		continue_play();
	}

	/**
	 * User menu to decide whether or not to continue playing
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void continue_play() throws FileNotFoundException, IOException {
		View.divider();
		System.out.print("Continue playing? (y/n): ");
		String continue_play = View.scanner.next();

		switch(continue_play) {
		case "y":
			if(mode.equals("c")) play_console();
			else play_file();
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
	public void determine_winner(Guest guest, Dealer dealer) {	
		// Variables
		Hand guest_best_hand = new Hand();
		Hand dealer_best_hand = new Hand();

		if(!guest.get_splitted()) guest_best_hand = guest.get_default_hand();
		else {
			// Not else-if, so that both split hands are checked if none of them busted
			if(!guest.get_split_hand_1().bust()) guest_best_hand = guest.get_split_hand_1();
			if(guest_best_hand.get_score() < guest.get_split_hand_2().get_score() && !guest.get_split_hand_2().bust()) guest_best_hand = guest.get_split_hand_2();
		}

		if(!dealer.get_splitted()) dealer_best_hand = dealer.get_default_hand();
		else {
			// Not else-if, so that both split hands are checked if none of them busted
			if(!dealer.get_split_hand_1().bust()) dealer_best_hand = dealer.get_split_hand_1();
			if(dealer_best_hand.get_score() < dealer.get_split_hand_2().get_score() && !dealer.get_split_hand_2().bust()) dealer_best_hand = dealer.get_split_hand_2();
		}

		// Interface output
		if(guest_best_hand.bust()) {
			System.out.print(guest_name + " busted -> ");
			View.hand(guest, guest_best_hand);
			System.out.print("Dealer wins -> ");
			View.hand(dealer, dealer_best_hand);
			dealer.set_winner(true);
		}
		else if(dealer_best_hand.bust()) {
			System.out.print("Dealer busted -> ");
			View.hand(dealer, dealer_best_hand); 
			System.out.print(guest_name + " wins -> ");
			View.hand(guest, guest_best_hand);
			guest.set_winner(true);
		}
		else if(guest_best_hand.get_score() > dealer_best_hand.get_score()) {
			System.out.print(guest_name + " -> ");
			View.hand(guest, guest_best_hand);
			System.out.print("Dealer -> ");
			View.hand(dealer, dealer_best_hand);
			System.out.println("Winner -> " + guest_name);
			guest.set_winner(true);
		}
		else {
			System.out.print(guest_name + " -> ");
			View.hand(guest, guest_best_hand);
			System.out.print("Dealer -> ");
			View.hand(dealer, dealer_best_hand);
			System.out.println("Winner -> Dealer");
			dealer.set_winner(true);
		}
	}

	/**
	 * Look for blackjacks in all of player's and dealer's hands.
	 * @param player whose hands will be checked for blackjacks
	 * @param dealer whose hands will be checked for blackjacks
	 * @return true if either the player, dealer or both have at least one blackjack
	 */
	public boolean blackjack_win(Player player, Player dealer) {
		if(player.get_default_hand().has_blackjack() || player.get_split_hand_1().has_blackjack() || player.get_split_hand_2().has_blackjack()) {
			player.set_has_blackjack(true);
		}
		if(dealer.get_default_hand().has_blackjack() || dealer.get_split_hand_1().has_blackjack() || dealer.get_split_hand_2().has_blackjack()) {
			dealer.set_has_blackjack(true);
		}

		if(player.get_has_blackjack() && !dealer.get_has_blackjack()) {
			System.out.println(guest_name + " has blackjack and dealer does not.\nWinner: " + guest_name);
			player.set_winner(true);
			return true;
		}
		else if(!player.get_has_blackjack() && dealer.get_has_blackjack()) {
			System.out.println(guest_name + " does not have a blackjack, but dealer does.\nWinner: Dealer");
			dealer.set_winner(true);
			return true;
		}
		else if(player.get_has_blackjack() && dealer.get_has_blackjack()) {
			System.out.println("Both " + guest_name + " and dealer have a blackjack.\nWinner: Dealer");
			dealer.set_winner(true);
			return true;
		}

		return false;
	}

	/**
	 * Give player the option to split hand.
	 * @return true if split has been chosen
	 */
	public boolean choose_split(Player guest_or_dealer) {
		String choose_split;

		System.out.println("Would you like to split? (y/n): ");
		choose_split = View.scanner.next();

		if(choose_split.equalsIgnoreCase("y")) {
			guest_or_dealer.set_splitted(true);
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
	public boolean hit_or_stand(Stack<Card> deck, Hand hand, Player guest_or_dealer) {
		// Local variables
		boolean stand = false;

		while(!hand.bust() && !stand && !hand.has_blackjack()) {
			View.hand(guest_or_dealer, hand);
			System.out.print("Hit or stand? (h/s): ");
			String hit_or_stand = View.scanner.next();

			// control structure for hit or stand
			switch(hit_or_stand) {
			case "h":
				guest_or_dealer.hit(deck, 1, hand);
				break;
			case "s":
				stand = true;	
				break;
			default:
				View.inavlid_input();
				hit_or_stand(deck, hand, guest_or_dealer);
				break;
			}
		}

		return stand;
	}

	/**
	 * Routine for a turn after splitting initial hand.
	 * @param deck to draw from
	 * @param guest_or_dealer whose turn it is
	 */
	public void split_turn(Stack<Card> deck, Player guest_or_dealer) {
		guest_or_dealer.hit(deck, 1, guest_or_dealer.get_split_hand_1());
		hit_or_stand(deck, guest_or_dealer.get_split_hand_1(), guest_or_dealer);

		guest_or_dealer.hit(deck, 1, guest_or_dealer.get_split_hand_2());
		hit_or_stand(deck, guest_or_dealer.get_split_hand_2(), guest_or_dealer);
	}
}
