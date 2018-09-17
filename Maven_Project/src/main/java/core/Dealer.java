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
	public boolean dealer_turn(Stack<Card> deck, Player player)
	{
		this.show_cards(this.hand.size());
		this.show_score();
		while(this.count_hand() <= 16 || this.count_hand() == 17 && this.count_aces() > 0)
		{
			this.hit(deck, 1);
			this.show_cards(this.hand.size());
			this.show_score();
		}
		this.bust(player);
		return true;
	}

}
