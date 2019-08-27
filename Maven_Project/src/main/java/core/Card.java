package core;

import java.util.HashMap;

public class Card {

	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	protected final static String[] SUITS = { "D", "C", "H", "S"};
	protected final static String[] RANKS = { "None", "None", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
	
	protected static HashMap<String, Integer> rank_values = new HashMap<String,Integer>(13);
	
	private void assign_ranks() {
		for(int r = 2; r < RANKS.length; r+=1) {
			if(r <= 10) rank_values.put(RANKS[r], r);
			else if(r > 10 && r < 14) rank_values.put(RANKS[r], 10);
			else rank_values.put(RANKS[r], 11);
		}
	}
	
	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private String suit;
	private String rank;
	
	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	/** 
	 * Creates a card with specified suit and rank.
	 * @param suit is the suit of the card (must be a string from Card.SUITS)
	 * @param rank is the rank of the card (must be a string from Card.RANKS)
	 */
	public Card(String suit, String rank) {
		this.suit = suit; 
		this.rank = rank;
		assign_ranks();
	}

	/*************
	 * GETTER(S) *
	 *************/
	/** 
	 * The suit of the current card. 
	 * @return the suit of this card (must be a string from Card.SUITS) 
	 */
	public String get_suit() { return suit; }

	/** 
	 * Ranks have the numerical values.
	 * 2 = 2, 3 = 3, ..., 10 = 10.
	 * Jack = Queen = King = 10, Ace = 11.
	 * @return the numerical representation of the rank of the current card
	 */
	public int get_rank() { return rank_values.get(rank); }

	/** 
	 * The string representation of the rank of the current card. 
	 * @return the string representation of the rank of this card (must be a string from Card.RANKS) 
	 */
	public String get_rank_string() { return rank; }

	/********
	 * ELSE *
	 ********/
	@Override
	public String toString() { return get_suit() + rank; }

}