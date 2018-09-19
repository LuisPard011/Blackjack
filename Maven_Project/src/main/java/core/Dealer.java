package core;

import java.util.Stack;

public class Dealer extends Player
{	
	/**
	 * 
	 * @param deck
	 * @param dealer_hand
	 */
	public void dealer_draw(Stack<Card> deck, Hand dealer_hand)
	{
		while(dealer_hand.score <= 16 || dealer_hand.score == 17 && dealer_hand.score > 0)
		{
			this.hit(deck, 1, dealer_hand);
			dealer_hand.show_cards(dealer_hand.cards.size());
			dealer_hand.show_score();
			this.bust();
		}
	}
	
	/**
	 * Hit while dealer's score is less than 16 or it has a soft 17
	 * @param deck
	 * @param player
	 */
	public boolean dealer_turn(Stack<Card> deck, Player player, Hand dealer_hand)
	{
		dealer_hand.show_cards(dealer_hand.cards.size());
		dealer_hand.show_score();
		
		/*
		 * Get rid of this player_hand blackjack param ???
		 * Go through all hands instead of having hand parameters
		 */
		
		if(player.blackjack_Win(this)) {return true;}
		dealer_draw(deck, dealer_hand);
		return false;
	}

}
