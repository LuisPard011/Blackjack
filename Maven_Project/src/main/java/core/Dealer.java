package core;

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
	public void dealer_draw()
	{
		
	}
}
