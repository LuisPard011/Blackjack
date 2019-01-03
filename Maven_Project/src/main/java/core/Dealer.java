package core;

import java.util.Stack;

public class Dealer extends Player {	
	/**
	 * @param deck
	 * @param player
	 * @param dealer_hand
	 * @return true if blackjack found    (???)
	 */
	public void dealer_turn(Stack<Card> deck, Player player, Hand dealer_hand) {
		// Check for blackjacks
		if(player.blackjack_Win(this)) {
			return;
		}
		
		// Draw repeatedly until dealer's score is less than 16 or it has a soft 17
		while(dealer_hand.get_score() <= 16 || dealer_hand.get_score() == 17 && dealer_hand.get_score() > 0) {
			hit(deck, 1, dealer_hand);
			dealer_hand.show_cards(dealer_hand.size());
			bust();
		}
	}

}
