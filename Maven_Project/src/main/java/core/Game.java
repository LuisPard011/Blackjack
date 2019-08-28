package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Game {
	
	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	protected final static int draw_times = 2;

	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private Guest guest;
	private Dealer dealer;

	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Game() {
		guest = new Guest();
		dealer = new Dealer();
	}
	
	public Guest get_guest() { return guest; }
	public Dealer get_dealer() { return dealer; }
	
	public void play() throws FileNotFoundException, IOException {}
	
	///////////////////////////////////// EVERYTHING AFTER THIS LINE SHOULD GO SOMEWHERE ELSE? /////////////////////////////////////////////////

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
	public boolean hit_or_stand(Deck deck, Hand hand, Guest guest) {
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
	public void split_turn(Deck deck, Guest guest) {
		guest.hit(deck, 1, guest.get_split_hand_1());
		hit_or_stand(deck, guest.get_split_hand_1(), guest);

		guest.hit(deck, 1, guest.get_split_hand_2());
		hit_or_stand(deck, guest.get_split_hand_2(), guest);
	}

}
