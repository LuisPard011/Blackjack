package core;

import java.util.Stack;

public class Dealer extends Player
{	
	/**
	 * @param deck
	 * @param player
	 * @param dealer_hand
	 */
	public boolean dealer_turn(Stack<Card> deck, Player player, Hand dealer_hand)
	{
		// Interface output
		dealer_hand.show_cards(dealer_hand.cards.size());
		dealer_hand.show_score();
		
		// Check for blackjacks
		if(player.blackjack_Win(this)) {return true;}
		
		// Draw repeatedly until dealer's score is less than 16 or it has a soft 17
		while(dealer_hand.score <= 16 || dealer_hand.score == 17 && dealer_hand.score > 0)
		{
			this.hit(deck, 1, dealer_hand);
			dealer_hand.show_cards(dealer_hand.cards.size());
			dealer_hand.show_score();
			this.bust();
		}
		
		return false;
	}

}
