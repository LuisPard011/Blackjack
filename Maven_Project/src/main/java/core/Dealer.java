package core;

import java.util.Stack;

// Instead of dealer class I could have a player named "guest" and another "house"

public class Dealer extends Player
{
	public Dealer()
	{
		super();
	}
	
	public void show_one_card()
	{
		System.out.print("Cards on table are: ");
		for(int i = 0; i < 1; ++i)
		{
			System.out.print(this.cards_on_table.get(i).toString() + " ");
		}
		System.out.println();
	}
	
	/*
	 * Dealer must draw while count is <= 16 || soft 17
	 * Else stand
	 */
	public void dealer_hit(Stack<Card> draw_pile)
	{
		while(this.count_cards() <= 16 || this.count_cards() == 17 && this.find_aces() > 0)
		{
			this.hit(draw_pile);
		}
	}
}
