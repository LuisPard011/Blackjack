package core;

public class Handler_Win {
	
	/**
	 * Determine game's winner.
	 * @param guest of this game 
	 * @param dealer of this game
	 */
	public static void determine_winner(Guest guest, Dealer dealer) {	
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
	public static boolean blackjack_win(Player guest, Player dealer) {
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

}
