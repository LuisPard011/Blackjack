package core;

import java.util.Stack;

public class Dealer extends Player {

	public Dealer(String name) {
		super(name);
	}
	
	/**
	 * Hit while dealer's score is less than 16 or it has a soft 17
	 * @param deck
	 * @param player
	 */
	public boolean dealer_turn(Stack<Card> deck, Player player, Hand player_hand, Hand dealer_hand)
	{
		dealer_hand.show_cards(dealer_hand.cards.size());
		dealer_hand.show_score();
		
		/*
		 * Get rid of this player_hand blackjack param ???
		 */
		
		if(player.blackjack_Win(this, player_hand, dealer_hand)) {return true;}
		dealer_draw(deck, dealer_hand);
//		this.bust(player, dealer_hand);
		return false;
	}
	
	public void dealer_draw(Stack<Card> deck, Hand dealer_hand)
	{
		while(dealer_hand.score <= 16 || dealer_hand.score == 17 && dealer_hand.score > 0)
		{
			this.hit(deck, 1, dealer_hand);
			dealer_hand.show_cards(dealer_hand.cards.size());
			dealer_hand.show_score();
		}
	}

}
