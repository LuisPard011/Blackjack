package core;

public class Dealer extends Player {	

	/**
	 * Draw repeatedly while dealer's score is less than or equal to 16, or dealer has a soft 17.
	 * @param deck to draw from
	 * @param dealer_hand hand to draw to (e.g. default, split_1, split_2)
	 * @return true if at least one card was drawn
	 */
	public boolean dealer_turn(Deck deck, Hand dealer_hand) {
		boolean return_code = false;
		while(dealer_hand.get_score() <= 16 || dealer_hand.soft_17()) return_code = return_code | hit(deck, 1, dealer_hand);
		return return_code;
	}

}
