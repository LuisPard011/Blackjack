package core;

import java.util.Random;
import java.util.Stack;

/**
 * This class is based on code provided by professor Michael Jason Hinek for COMP 1406 during the summer of 2017.
 */
public class Deck extends Stack<Card> {
	
	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	private static final long serialVersionUID = 1L;
	
	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Deck() { make_deck(this); }
	
	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	public static final int deck_size = 52;
	private Card[] temp_deck = new Card[deck_size];
	
	/********
	 * ELSE *
	 ********/
	/**
	 * Fill the temporary deck with cards.
	 */
	public void populate_temp_deck() {
		int index = 0;
		
		for(int r=2; r<=14; r+=1) {
			for(int s=0; s<4; s+=1) {
				temp_deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
			}
		}
	}
	
	/**
	 * Shuffle the temporary deck, randomly.
	 */
	public void shuffle_temp_deck() {
		Random rnd = new Random();
		Card swap;
		int pos;
		
		for(int i = deck_size-1; i>=0; i=i-1) {
			pos = rnd.nextInt(i+1);
			swap = temp_deck[pos];
			temp_deck[pos] = temp_deck[i];
			temp_deck[i] = swap;
		}
	}
	
	/**
	 * Fill the actual deck with cards from the temporary deck.
	 * Cards in actual deck appear in the same order as they are in the temporary deck.
	 * @param deck to be used in game
	 */
	public void make_deck(Stack<Card> deck) {
		populate_temp_deck();
		shuffle_temp_deck();
		for(int i = 0; i < deck_size; ++i) deck.push(this.temp_deck[i]);
	}

}
