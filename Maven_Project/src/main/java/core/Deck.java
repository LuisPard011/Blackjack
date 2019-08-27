package core;

import java.util.Random;
import java.util.Stack;

public class Deck {

	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	private static final long serialVersionUID = 1L;
	public static final int deck_size = 52;
	private Card[] all_cards = new Card[deck_size];
	
	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	Stack<Card> deck;

	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Deck() { 
		deck = new Stack<>();
		make_shuffled_deck(); 
	}

	/*************
	 * GETTER(S) *
	 *************/
	public Stack<Card> get_deck(){ return deck; }

	/********
	 * ELSE *
	 ********/
	/**
	 * Fill the temporary deck with cards.
	 */
	private void populate_all_cards() {
		int index = 0;

		for(int r=2; r<=14; r+=1) {
			for(int s=0; s<4; s+=1) {
				all_cards[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
			}
		}
	}

	/**
	 * Shuffle the temporary deck, randomly.
	 */
	private void shuffle_all_cards() {
		Random rnd = new Random();
		Card card;
		int position;

		for(int i = deck_size-1; i>=0; i-=1) {
			position = rnd.nextInt(i+1);
			card = all_cards[position];
			all_cards[position] = all_cards[i];
			all_cards[i] = card;
		}
	}
	
	

	/**
	 * Fill the actual deck with cards from the temporary deck.
	 * Cards in actual deck appear in the same order as they are in the temporary deck.
	 * @param deck to be used in game
	 */
	private void make_shuffled_deck() {
		populate_all_cards();
		shuffle_all_cards();
		for(int i = 0; i < deck_size; i++) deck.push(all_cards[i]);
	}

}
