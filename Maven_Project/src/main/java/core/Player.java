package core;

import java.util.Stack;

public class Player {

	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private Hand default_hand;
	private Hand split_hand_1;
	private Hand split_hand_2;

	private boolean has_blackjack;
	private boolean splitted;
	private boolean winner;

	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Player() {
		default_hand = new Hand();
		split_hand_1 = new Hand();
		split_hand_2 = new Hand();

		has_blackjack = false;
		splitted = false;
		winner = false;
	}

	/*************
	 * GETTER(S) *
	 *************/
	public Hand get_default_hand() { return default_hand; }
	public Hand get_split_hand_1() { return split_hand_1; }
	public Hand get_split_hand_2() { return split_hand_2; }

	public boolean get_has_blackjack() { return has_blackjack; }
	public boolean get_splitted() { return splitted; }
	public boolean get_winner() { return winner; }

	/*************
	 * SETTER(S) *
	 *************/
	public void set_has_blackjack(boolean true_or_false) { has_blackjack = true_or_false; }
	public void set_splitted(boolean true_or_false) { splitted = true_or_false; }
	public void set_winner(boolean true_or_false) { winner = true_or_false; }

	/********
	 * ELSE *
	 ********/
	/**
	 * Draw_times cards from deck and put them into hand.
	 * @param deck to draw from
	 * @param draw_times number of cards from deck
	 * @param hand to add drawn cards
	 * @return true if at least one card was drawn
	 */
	public boolean hit(Stack<Card> deck, int draw_times, Hand hand) {
		int start_hand_size = hand.size();

		for(int i = 0; i < draw_times; i++) {
			if(!deck.isEmpty()) hand.add(deck.pop());
			else break;
		}

		if(hand.size() > start_hand_size) return true;
		else return false;
	}

	/**
	 * Split hand.
	 */
	public void split_hand() {
		split_hand_1.add(default_hand.get(0));
		split_hand_2.add(default_hand.get(1));
	}

	/**
	 * @return true if initial hand can be split
	 */
	public boolean can_split() {
		if(default_hand.get(0).get_rank() == default_hand.get(1).get_rank()) return true;
		else return false;
	}

	/**
	 * @return true if all of this player's hands are bust
	 */
	public boolean completely_bust() {
		if(!can_split() && default_hand.bust()) return true;
		else if(can_split() && split_hand_1.bust() && split_hand_2.bust()) return true;
		else return false;
	}

} 
