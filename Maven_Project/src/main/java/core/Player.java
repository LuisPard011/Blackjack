package core;

import java.util.ArrayList;
import java.util.Stack;

public class Player
{
	ArrayList<Card> cards_on_table; //calculate max num of cards a player can have without busting and maybe make this into a simple array
	
	public Player(ArrayList<Card> cards_on_table)
	{
		this.cards_on_table = new ArrayList<Card>();
	}
	
	public void draw(Stack<Card> draw_pile)
	{
		cards_on_table.add(draw_pile.pop());
	}
	
	public int count_cards()
	{
		int sum = 0;
		for(int i = 0; i < this.cards_on_table.size(); ++i)
		{
			sum += this.cards_on_table.get(i).getRank();
		}
		return sum;
	}
	
	public void show_cards()
	{
		System.out.print("Cards on table are: ");
		for(int i = 0; i < cards_on_table.size(); ++i)
		{
			System.out.print(cards_on_table.get(i).toString() + " ");
		}
		System.out.println();
	}
} 
