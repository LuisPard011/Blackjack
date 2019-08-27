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
	 * Start game.
	 * Choose between console or file input modes.
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
	 * Play using console input.
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
				if(!guest.completely_bust()) {
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
	 * Play using file input.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void play_file() throws FileNotFoundException, IOException {
		// Local variables
		boolean stand = false;

		// Players
		Guest guest = new Guest(guest_name);
		Dealer dealer = new Dealer();

		// Read file input
		Reader reader = new Reader();
		String[] input_line;

		// File paths
		int path_to_play;
		String[] paths = new String[] {
				"src\\main\\java\\text_files\\Input_File_1.txt",
				"src\\main\\java\\text_files\\Input_File_2.txt",
				"src\\main\\java\\text_files\\Input_File_3.txt",
				"src\\main\\java\\text_files\\Input_File_4.txt",
				"src\\main\\java\\text_files\\Input_File_5.txt"};

		System.out.println("Note: Features to support paths 4 and 5 have not been implemented.\nWhat path would you like to play? [1-5]: ");
		path_to_play = View.scanner.nextInt();

		switch(path_to_play) {
		case 1:
			input_line = reader.read_file_input(paths[0]);
			break;
		case 2:
			input_line = reader.read_file_input(paths[1]);
			break;
		case 3:
			input_line = reader.read_file_input(paths[2]);
			break;
		case 4:
			input_line = reader.read_file_input(paths[3]);
			break;
		case 5:
			input_line = reader.read_file_input(paths[4]);
			break;
		default:
			System.out.println("That path does not exist.\nPlease try again.\nThis time only choose a path within the range [1-5], inclusive");
			play_file();
			return;
		}

		View.divider();

		// Draw player's first two cards
		for(int i = 0; i < draw_times; i++) reader.add_card_from_input(input_line[i], guest.get_default_hand());

		// Draw dealer's first two cards
		for(int i = draw_times; i < 4; i++) reader.add_card_from_input(input_line[i], dealer.get_default_hand());

		// Go through rest of the input
		input:
			for(int i = 4; i < input_line.length; i++) {
				if(input_line[i].length() == 1) {
					switch(input_line[i].charAt(0)) {
					case 'S': // Stand
						stand = true;
						continue input;
					case 'H': // Hit
						continue input;
					case 'D': // Split
						continue input;
					}
				}

				if(!stand) reader.add_card_from_input(input_line[i], guest.get_default_hand());
				else reader.add_card_from_input(input_line[i], dealer.get_default_hand());	
			}

		// End game
		determine_winner(guest, dealer);
		continue_play();
	}

	/**
	 * User menu to decide whether or not to continue playing.
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
	 * Determine game's winner.
	 * @param guest of this game 
	 * @param dealer of this game
	 */
	public void determine_winner(Guest guest, Dealer dealer) {	
		// Variables
		Hand guest_best_hand = new Hand();
		Hand dealer_best_hand = new Hand();

		if(!guest.get_splitted()) guest_best_hand = guest.get_default_hand();
		else {
			// Not else-if, so that both split hands are checked if none of them is bust
			if(!guest.get_split_hand_1().bust()) guest_best_hand = guest.get_split_hand_1();
			if(guest_best_hand.get_score() < guest.get_split_hand_2().get_score() && !guest.get_split_hand_2().bust()) guest_best_hand = guest.get_split_hand_2();
		}

		if(!dealer.get_splitted()) dealer_best_hand = dealer.get_default_hand();
		else {
			if(!dealer.get_split_hand_1().bust()) dealer_best_hand = dealer.get_split_hand_1();
			if(dealer_best_hand.get_score() < dealer.get_split_hand_2().get_score() && !dealer.get_split_hand_2().bust()) dealer_best_hand = dealer.get_split_hand_2();
		}

		// Interface output
		if(guest_best_hand.bust()) {
			View.winner_loser(dealer, guest, false, true);
			View.hand(guest, guest_best_hand);
			View.hand(dealer, dealer_best_hand);
			dealer.set_winner(true);
		}
		else if(dealer_best_hand.bust()) {
			View.winner_loser(guest, dealer, false, true);
			View.hand(guest, guest_best_hand);
			View.hand(dealer, dealer_best_hand); 
			guest.set_winner(true);
		}
		else if(guest_best_hand.get_score() > dealer_best_hand.get_score()) {
			View.winner_loser(guest, dealer, false, false);
			View.hand(guest, guest_best_hand);
			View.hand(dealer, dealer_best_hand);
			guest.set_winner(true);
		}
		else {
			View.winner_loser(dealer, guest, false, false);
			View.hand(guest, guest_best_hand);
			View.hand(dealer, dealer_best_hand);
			dealer.set_winner(true);
		}
	}

	/**
	 * Look for blackjacks in all of guest's and dealer's hands.
	 * @param guest whose hands will be checked for blackjacks
	 * @param dealer whose hands will be checked for blackjacks
	 * @return true if either the guest, dealer or both have at least one blackjack
	 */
	public boolean blackjack_win(Player guest, Player dealer) {
		if(guest.get_default_hand().has_blackjack() || guest.get_split_hand_1().has_blackjack() || guest.get_split_hand_2().has_blackjack()) {
			guest.set_has_blackjack(true);
		}
		if(dealer.get_default_hand().has_blackjack() || dealer.get_split_hand_1().has_blackjack() || dealer.get_split_hand_2().has_blackjack()) {
			dealer.set_has_blackjack(true);
		}

		if(guest.get_has_blackjack() && !dealer.get_has_blackjack()) {
			View.winner_loser(guest, dealer, true, false);
			guest.set_winner(true);
			return true;
		}
		else if(dealer.get_has_blackjack()) {
			View.winner_loser(dealer, guest, true, false);
			dealer.set_winner(true);
			return true;
		}

		return false;
	}

	/**
	 * Give guest or dealer the option to split hand.
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
	 * Give guest the option to either hit or stand.
	 * @param deck to draw from
	 * @param hand to add card drawn
	 * @param guest who will choose to hit or stand
	 * @return true if stand is chosen
	 */
	public boolean hit_or_stand(Stack<Card> deck, Hand hand, Guest guest) {
		// Local variables
		boolean stand = false;

		while(!hand.bust() && !stand && !hand.has_blackjack()) {
			View.hand(guest, hand);
			System.out.print("Hit or stand? (h/s): ");
			String hit_or_stand = View.scanner.next();

			// control structure for hit or stand
			switch(hit_or_stand) {
			case "h":
				guest.hit(deck, 1, hand);
				break;
			case "s":
				stand = true;	
				break;
			default:
				View.inavlid_input();
				hit_or_stand(deck, hand, guest);
				break;
			}
		}

		return stand;
	}

	/**
	 * Routine for a turn after splitting initial hand.
	 * @param deck to draw from
	 * @param guest whose turn it is
	 */
	public void split_turn(Stack<Card> deck, Guest guest) {
		guest.hit(deck, 1, guest.get_split_hand_1());
		hit_or_stand(deck, guest.get_split_hand_1(), guest);

		guest.hit(deck, 1, guest.get_split_hand_2());
		hit_or_stand(deck, guest.get_split_hand_2(), guest);
	}
}
