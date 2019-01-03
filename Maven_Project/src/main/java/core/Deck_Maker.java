package core;

import java.util.Random;
import java.util.Stack;

/**
 * This class is based on code provided by professor Michael Jason Hinek for COMP 1406 during the summer of 2017.
 */
public class Deck_Maker {
	
	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	protected static int deck_size = 52;
	private Card[] source_deck = new Card[deck_size];
	
	/********
	 * ELSE *
	 ********/
	/**
	 * Fill the temporary deck with cards.
	 */
	public void populate_source_deck() {
		int index = 0;
		
		for(int r=2; r<=14; r+=1) {
			for(int s=0; s<4; s+=1) {
				this.source_deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
			}
		}
	}
	
	/**
	 * Shuffle the temporary deck, randomly.
	 */
	public void shuffle_source_deck() {
		Random rnd = new Random();
		Card swap;
		int pos;
		
		for(int i = deck_size-1; i>=0; i=i-1) {
			pos = rnd.nextInt(i+1);
			swap = source_deck[pos];
			source_deck[pos] = source_deck[i];
			source_deck[i] = swap;
		}
	}
	
	/**
	 * Fill the draw pile with cards from the deck.
	 * Cards in pile appear in the same order as they are in the deck.
	 * @param deck to be used in game
	 */
	public void make_deck(Stack<Card> deck) {
		populate_source_deck();
		shuffle_source_deck();
		for(int i = 0; i < deck_size; ++i) deck.push(this.source_deck[i]);
	}

}
