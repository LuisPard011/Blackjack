package core;

import java.util.Stack;

public class Dealer extends Player{
	
	/**
	 * Show only one of the dealer's cards
	 */
	public void show_one_card()
	{
		System.out.println("One of dealer's cards is: " + this.hand.get(0).toString());
	}
	
	/**
	 * Dealer must hit while its score is less than 16 or it has a soft 17
	 */
	public void dealer_hit(Stack<Card> draw_pile)
	{
		while(this.score() <= 16 || this.score() == 17 && this.count_aces() > 0)
		{
			this.hit(draw_pile);
		}
	}

}
