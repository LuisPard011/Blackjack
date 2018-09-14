package core;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

// Perhaps I should change the class name from Table to Deck, unless I add players to the class

public class Deck
{	
	int deck_size = 52;
	Card[] deck = null;
	Stack<Card> draw_pile = null;

	public Deck()
	{
		this.deck = new Card[deck_size]; //1406
		this.draw_pile = new Stack<Card>();
	}
	
	public void populate_deck()
	{
		int index = 0;
		for(int r=2; r<=14; r+=1)
		{
			for(int s=0; s<4; s+=1)
			{
				this.deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
			}
		}
	}
	
	public void shuffle_deck()
	{
		/* shuffle the deck, 1406 */
		Random rnd = new Random();
		Card swap;
		for(int i = deck_size-1; i>=0; i=i-1)
		{
			int pos = rnd.nextInt(i+1);
			swap = this.deck[pos];
			this.deck[pos] = this.deck[i];
			this.deck[i] = swap;
		}
	}
	
	public void populate_draw_pile()
	{
		for(int i = 0; i < deck_size; ++i){this.draw_pile.push(this.deck[i]);}
	}
	
	public void peek_draw_pile()
	{
		System.out.println("Top of draw pile is: " + this.draw_pile.peek().toString());
	}
	
	public void show_deck(int start, int end)
	{
		System.out.println("Deck is: " + Arrays.toString(Arrays.copyOfRange(this.deck, start, end)));
	}

}
