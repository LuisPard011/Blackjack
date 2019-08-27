package core;

import java.util.ArrayList;

public class Hand {

	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private ArrayList<Card> cards;
	private int score;

	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Hand() {
		cards = new ArrayList<>();
		score = 0; 
	}

	/*************
	 * GETTER(S) *
	 *************/
	/**
	 * @return cards list
	 */
	public ArrayList<Card> get_cards() { return cards; }
	
	/**
	 * @return this hand's total score, accounting for aces
	 */
	public int get_score() {
		// Variables
		score = 0;
		int aces = 0;

		// Count cards in hand	
		for(Card card : cards) {
			score += card.get_rank();
			if(card.get_rank() == 11) aces += 1;
		}

		// Adjust score depending on aces in hand
		for(int i = 0; i < aces; i++) {
			if(score > 21) score -= 10;
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
		for(Card card : cards) {
			switch(card.get_rank()) {
			case 10:
				has_ten_value = true;
				break;
			case 11:
				has_ace = true;
				break;
			}
		}

		// Determine whether or not hand has blackjack
		if(has_ace && has_ten_value && !bust()) return true;
		else return false;
	}

	/**
	 * @return true if hand is bust
	 */
	public boolean bust() {
		if(get_score() > 21) return true;
		else return false;
	}
	
	/**
	 * @return true if this hand has a soft 17
	 */
	public boolean soft_17() {
		if(get_score() == 17) {
			int number_of_aces = 0;
			ArrayList<Integer> ace_indices = new ArrayList<>();
			
			for(int i = 0; i < cards.size(); i++) {
				if(cards.get(i).get_rank() == 11) {
					number_of_aces++;
					ace_indices.add(i);
				}
			}
			
			switch(number_of_aces) {
			case 1:
				if(sum_of_rest(ace_indices) == 6) return true;
				else return false;
			case 2:
				if(sum_of_rest(ace_indices) == 5) return true;
				else return false;
			case 3:
				if(sum_of_rest(ace_indices) == 4) return true;
				else return false;
			case 4:
				if(sum_of_rest(ace_indices) == 3) return true;
				else return false;
			default:
				return false;
			}
		}
		else return false;
	}
	
	/**
	 * Helper function for soft_17().
	 * @param ace_indices is a list with the indices of aces in this hand
	 * @return sum of card points other than the one ace worth 11 points
	 */
	private int sum_of_rest(ArrayList<Integer> ace_indices) {
		int sum = 0;
		
		for(int i = 0; i < cards.size(); i++) {
			if(!ace_indices.contains(i)) sum += cards.get(i).get_rank();
		}
		
		return sum;
	}

	@Override
	public final String toString() {
		String[] card_array = new String[cards.size()];
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < cards.size(); i++) card_array[i] = cards.get(i).toString();
		for (String string : card_array) {
			if (builder.length() > 0) builder.append(" ");
			builder.append(string);
		}
		return "[" + builder + "] (" + get_score() + " points)";
	}
}
