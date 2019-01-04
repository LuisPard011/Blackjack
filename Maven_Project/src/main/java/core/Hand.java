package core;

import java.util.ArrayList;

public class Hand extends ArrayList<Card>{

	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	private static final long serialVersionUID = 1L;

	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private int score;

	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Hand() { score = 0; }

	/*************
	 * GETTER(S) *
	 *************/
	/**
	 * @return this hand's total score, accounting for aces
	 */
	public int get_score() {
		// Variables
		score = 0;
		int aces = 0;

		// Count cards in hand	
		for(Card card : this) {
			switch(card.get_rank()) {
			case 11:
				score += 10;
				break;
			case 12:
				score += 10;
				break;
			case 13:
				score += 10;
				break;
			case 14:
				score += 11;
				aces += 1;
				break;
			default:
				score += card.get_rank();
				break;
			}
		}

		// Adjust score depending on aces in hand
		for(int i = 0; i < aces; i++) {
			if(score >= 21) score -= 10;
		}

		return score; 
	}

	/*************
	 * SETTER(S) *
	 *************/
	public void set_score(int score) { this.score = score; }

	/********
	 * ELSE *
	 ********/
	/**
	 * Determine if hand has a blackjack.
	 * @return true blackjack found and this hand is not bust
	 */
	public boolean has_blackjack() {
		// Variables
		boolean has_ace = false;
		boolean has_ten_value = false;

		// Iterate through hand to find aces, tens and face cards
		for(Card card : this) {
			switch(card.get_rank()) {
			case 10:
			case 11:
			case 12:
			case 13:
				has_ten_value = true;
				break;
			case 14:
				has_ace = true;
				break;
			}
		}

		// Determine whether or not hand has blackjack
		if(has_ace && has_ten_value && !bust()) return true;
		else return false;
	}

	/**
	 * @return true if hand busted
	 */
	public boolean bust() {
		if(get_score() > 21) return true;
		else return false;
	}

	@Override
	public final String toString() {
		String[] card_array = new String[size()];
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < size(); i++) card_array[i] = get(i).toString();
		for (String string : card_array) {
			if (builder.length() > 0) builder.append(" ");
			builder.append(string);
		}
		return "Cards in hand are [" + builder.toString() + "]";
	}
}
