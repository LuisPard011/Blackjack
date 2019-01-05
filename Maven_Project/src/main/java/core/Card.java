package core;

import java.util.HashMap;

/**
 * This class is partially based on code provided by professor Michael Jason Hinek for COMP 1406 during the summer of 2017.
 */
public class Card {

	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	protected final static String[] SUITS = { "D", "C", "H", "S"};
	protected final static String[] RANKS = { "None", "None", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private String suit;
	private String rank;
	private HashMap<String, Integer> rankValue;

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
		rankValue = new HashMap<String,Integer>(15);
		for(int r = 2; r < RANKS.length; r+=1) rankValue.put(RANKS[r], r);
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
	 * Jack = 11, Queen = 12, King = 13, Ace = 14.
	 * @return the numerical representation of the rank of the current card
	 */
	public int get_rank() {
		if(rank.equals(RANKS[0]) || rank.equals(RANKS[1])) return -1; // "no card"
		return rankValue.get(rank);
	}

	/** 
	 * The string representation of the rank of the current card. 
	 * @return the string representation of the rank of this card (must be a string from Card.RANKS) 
	 */
	public String get_rank_string() { return rank; }

	/********
	 * ELSE *
	 ********/
	@Override
	public final String toString() {
		int r = get_rank();

		switch(r) {
		case -1:
			return "No card";
		case 11: return get_suit() + "J";
		case 12: return get_suit() + "Q";
		case 13: return get_suit() + "K";
		case 14: return get_suit() + "A";
		default:
			return get_suit() + r;
		}
	}

}