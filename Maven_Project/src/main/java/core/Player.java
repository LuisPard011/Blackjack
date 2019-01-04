package core;

import java.util.Stack;

public class Player {
	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private Hand default_hand;
	private Hand split_hand_1;
	private Hand split_hand_2;
	private boolean splitted;
	private boolean has_blackjack;

	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Player() {
		default_hand = new Hand();
		split_hand_1 = new Hand();
		split_hand_2 = new Hand();
		splitted = false;
		has_blackjack = false;
	}

	/*************
	 * GETTER(S) *
	 *************/
	public Hand get_default_hand() { return default_hand; }
	public Hand get_split_hand_1() { return split_hand_1; }
	public Hand get_split_hand_2() { return split_hand_2; }
	public boolean get_splitted() { return splitted; }
	public boolean get_has_blackjack() { return has_blackjack; }

	/*************
	 * SETTER(S) *
	 *************/
	public void set_has_blackjack(boolean true_or_false) { has_blackjack = true_or_false; }

	/********
	 * ELSE *
	 ********/
	/**
	 * Draw_times cards from deck and put them into hand.
	 * @param deck
	 * @param draw_times
	 * @param hand
	 */
	public boolean hit(Stack<Card> deck, int draw_times, Hand hand) {
		for(int i = 0; i < draw_times; i++) hand.add(deck.pop());
		if(hand.blackjack()) return true;
		else return false;
	}

	/**
	 * Give player the option to either hit or stand
	 * @param deck
	 * @param hand
	 * @return true if stand is chosen
	 */
	public void hit_or_stand(Stack<Card> deck, Hand hand, Dealer dealer) {
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
				if(hit(deck, 1, hand) && !dealer.get_default_hand().isEmpty()) {
					blackjack_Win(dealer);
				}
				break;
			case "s":
				stand = true;	
				break;
			default:
				System.out.println("Invalid input"); 
				hit_or_stand(deck, hand, dealer);
				break;
			}

			if(hand.bust()) {
				System.out.println(hand);
				bust();
			}
		}
	}


	/**
	 * If neither the player nor the dealer busts, scores are compared to determine winner
	 * @param dealer
	 */
	public void determine_winner(Dealer dealer) {	
		// Variables
		int player_highest_score = 0;
		int dealer_highest_score = 0;
		Hand player_best_hand = new Hand();
		Hand dealer_best_hand = new Hand();

		// Check if player is bust and find its hand with the highest score under 22
		if(!default_hand.bust() && !splitted) {
			player_highest_score = default_hand.get_score();
			player_best_hand = default_hand;
		}
		if(player_highest_score < split_hand_1.get_score() && !split_hand_1.bust()) {
			player_highest_score = split_hand_1.get_score();
			player_best_hand = split_hand_1;
		}
		if(player_highest_score < split_hand_2.get_score() && !split_hand_2.bust()) {
			player_highest_score = split_hand_2.get_score();
			player_best_hand = split_hand_2;
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
		}
		else if(player_highest_score == 0) {
			System.out.println("Player busted.\n" +
		                       "Dealer wins: " + dealer_best_hand.toString() + " (" + dealer_highest_score + ") points.");
		}
		else if(dealer_highest_score == 0) {
			System.out.println("Dealer busted.\n" + 
		                       "Player wins: " + player_best_hand.toString() + " (" + player_highest_score + ") points.");
		}
		else {
			System.out.println("Player: " + player_best_hand.toString() + " (" + player_highest_score + ") points.\n" + 
		                       "Dealer: " + dealer_best_hand.toString() + " (" + dealer_highest_score + ") points.\n" + 
					           "Winner: Dealer");
		}
	}

	/**
	 * Give player the option to split hand.
	 * @return true if split has been chosen
	 */
	public boolean choose_split() {
		String choose_split;

		System.out.println("Would you like to split? (y/n): ");
		choose_split = View.scanner.next();

		if(choose_split.equalsIgnoreCase("y")) {
			splitted = true;
			return true;
		}
		else return false;
	}

	/**
	 * Split hand.
	 */
	public void split_hand() {
		split_hand_1.add(default_hand.get(0));
		split_hand_2.add(default_hand.get(1));
	}

	/**
	 * @return true if initial hand can be split
	 */
	public boolean can_split() {
		if(default_hand.get(0).get_rank() == default_hand.get(1).get_rank()) return true;
		else return false;
	}

	/**
	 * Look for blackjacks in all of player's and dealer's hands.
	 * Get rid of hand parameters and maybe dealer too.
	 * @param dealer
	 * @return true if anyone has a blackjack
	 */
	public boolean blackjack_Win(Dealer dealer) {
		if(default_hand.blackjack() || split_hand_1.blackjack() || split_hand_2.blackjack()) {
			has_blackjack = true;
		}
		if(dealer.get_default_hand().blackjack() || dealer.get_split_hand_1().blackjack() || dealer.get_split_hand_2().blackjack()) {
			dealer.set_has_blackjack(true);
		}

		if(has_blackjack && !dealer.get_has_blackjack()) {
			System.out.println("Player has blackjack and dealer does not. Player wins");
			return true;
		}
		else if(!has_blackjack && dealer.get_has_blackjack()) {
			System.out.println("Player does not have a blackjack, but dealer does. Dealer wins");
			return true;
		}
		else if(has_blackjack && dealer.get_has_blackjack()) {
			System.out.println("Both the player and dealer have a blackjack. Dealer wins");
			return true;
		}

		return false;
	}

	/**
	 * @return true if player's default hand or both split hands have busted
	 */
	public boolean bust() {
		if(!can_split() && default_hand.bust()) {
			System.out.println("Busted");
			return true;
		}
		else if(can_split() && split_hand_1.bust() && split_hand_2.bust()) {
			System.out.println("Busted");
			return true;
		}
		else return false;
	}

	/**
	 * routine for a turn after splitting initial hand
	 * @param deck
	 */
	public void split_turn(Stack<Card> deck, Dealer dealer) {
		hit(deck, 1, get_split_hand_1());
		hit_or_stand(deck, get_split_hand_1(), dealer);

		hit(deck, 1, get_split_hand_2());
		hit_or_stand(deck, get_split_hand_2(), dealer);
	}
} 
