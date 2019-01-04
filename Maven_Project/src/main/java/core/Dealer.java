package core;

import java.util.Stack;

public class Dealer extends Player {	
	
	/********
	 * ELSE *
	 ********/
	/**
	 * @param deck to draw from
	 * @param player to compare with when deciding win by blackjack
	 * @param dealer_hand hand to draw to (e.g. default, split_1, split_2)
	 * @return true if blackjack found
	 */
	public boolean dealer_turn(Stack<Card> deck, Player player, Hand dealer_hand) {
		if(Game_Controller.blackjack_win(player, this)) return true; // Check for blackjacks
		
		// Draw repeatedly until dealer's score is less than 16 or it has a soft 17
		while(dealer_hand.get_score() <= 16 || dealer_hand.get_score() == 17 && dealer_hand.get_score() > 0) {
			hit(deck, 1, dealer_hand);
			completely_busted();
		}
		
		return false;
	}

}
