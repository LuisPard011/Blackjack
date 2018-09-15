package core;

import java.util.ArrayList;
import java.util.Stack;

public class Player
{
	ArrayList<Card> cards_on_table; //calculate max num of cards a player can have without busting and maybe make this into a simple array
	int count;
	boolean stand;
	
	public Player()
	{
		this.cards_on_table = new ArrayList<Card>();
		this.count = 0;
		this.stand = false;
	}
	
	public void hit(Stack<Card> draw_pile)
	{
		this.cards_on_table.add(draw_pile.pop());
		this.count = this.count_cards();
//		System.out.println("Count is: " + this.count);
	}
	
	public void stand()
	{
		// Must "continue", that is, pass the turn to the dealer
		this.stand = true;
	}
	
	public int count_not_ace()
	{
		int sum = 0;
		for(int i = 0; i < this.cards_on_table.size(); ++i)
		{
			if(this.cards_on_table.get(i).getRank() < 11)
			{
				sum += this.cards_on_table.get(i).getRank();
			}
			else
			{
				switch(this.cards_on_table.get(i).getRank())
				{
					case 11:
						sum += 10;
						break;
					case 12:
						sum += 10;
						break;
					case 13:
						sum += 10;
						break;
				}
			}
		}
		return sum;
	}
	
	public int find_aces()
	{
		int aces_in_hand = 0;
		for(int i = 0; i < this.cards_on_table.size(); ++i)
		{
			if(this.cards_on_table.get(i).getRank() == 14) {aces_in_hand += 1;}
		}
		return aces_in_hand;
	}
	
	public int count_cards()
	{
		int sum = count_not_ace();
		int aces_in_hand = find_aces();
		if(aces_in_hand > 0)
		{
			for(int i = 0; i < aces_in_hand; ++i)
			{
				if(sum < 11) {sum += 11;}
				else {sum += 1;}
			}
		}
		return sum;
	}
	
	public boolean is_bust()
	{
		int card_count = this.count_cards();
		if(card_count > 21) {return true;}
		return false;
	}
	
	public void show_cards()
	{
		System.out.print("Cards on table are: ");
		for(int i = 0; i < this.cards_on_table.size(); ++i)
		{
			System.out.print(this.cards_on_table.get(i).toString() + " ");
		}
		System.out.println();
	}
	
	public void show_count() {System.out.println("Count is: " + this.count_cards());}
} 
