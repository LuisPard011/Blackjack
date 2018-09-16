package core;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

// Perhaps I should change the class name from Table to Deck, unless I add players to the class

public class Deck_Maker
{	
	// Variables
	int deck_size = 52;
	Card[] source_deck = null;

	/**
	 * Constructor
	 */
	public Deck_Maker()
	{
		this.source_deck = new Card[deck_size];
	}
	
	/**
	 * 1406
	 * Fill the temporary deck with cards
	 */
	public void populate_source_deck()
	{
		int index = 0;
		for(int r=2; r<=14; r+=1)
		{
			for(int s=0; s<4; s+=1)
			{
				this.source_deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
			}
		}
	}
	
	/**
	 * 1406
	 * Shuffle the temporary deck, randomly
	 */
	public void shuffle_source_deck()
	{
		Random rnd = new Random();
		Card swap;
		for(int i = deck_size-1; i>=0; i=i-1)
		{
			int pos = rnd.nextInt(i+1);
			swap = this.source_deck[pos];
			this.source_deck[pos] = this.source_deck[i];
			this.source_deck[i] = swap;
		}
	}
	
	/**
	 * 1406
	 * Fill the draw pile with cards from the deck
	 * Cards in pile appear in the same order as they are in the deck
	 */
	public void make_deck(Stack<Card> deck_to_make)
	{
		this.populate_source_deck();
		this.shuffle_source_deck();
		for(int i = 0; i < deck_size; ++i){deck_to_make.push(this.source_deck[i]);}
	}
	
	/**
	 * Show all the cards in the deck within a specific range
	 * @param start
	 * @param end
	 */
	public void show_source_deck(int start, int end)
	{
		System.out.println("Cards in source deck are: " + Arrays.toString(Arrays.copyOfRange(this.source_deck, start, end)));
	}

}
