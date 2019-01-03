package core;

import java.util.ArrayList;

public class Hand {
	
	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private int score;
	ArrayList<Card> cards;
	
	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Hand() {
		score = 0;
		cards = new ArrayList<Card>();
	}
	
	/*************
	 * GETTER(S) *
	 *************/
	public int get_score() { return score; }
	
	/*************
	 * SETTER(S) *
	 *************/
	public void set_score(int score) { this.score = score; }
	
	/**
	 * Add one card to hand and update score
	 * @param card
	 */
	public void add(Card card)
	{
		this.cards.add(card);
		this.count_score();
	}
	
	/**
	 * Interface output to show hand's score
	 */
	public void show_score()
	{
		System.out.println("Score is: " + this.score);
	}
	
	/**
	 * @return the player's total score, accounting for aces
	 */
	public void count_score()
	{	
		// Variables
		int new_score = 0;
		int aces = 0;
		
		// Count cards in hand	
		for(int i = 0; i < this.cards.size(); ++i)
		{
			if(this.cards.get(i).get_rank() < 11)
			{
				new_score += this.cards.get(i).get_rank();
			}
			else
			{
				switch(this.cards.get(i).get_rank())
				{
					case 11:
						new_score += 10;
						break;
					case 12:
						new_score += 10;
						break;
					case 13:
						new_score += 10;
						break;
					case 14:
						new_score += 11;
						aces += 1;
						break;
				}
			}
		}
		
		// Adjust score depending on aces in hand
		for(int i = 0; i < aces; ++i)
		{
			if(new_score >= 21)
			{
				new_score -= 10;
			}
		}
		
		// Update hand's score
		this.score = new_score;
	}
	
	/**
	 * Interface output to show specific number of cards in hand
	 * @param cards
	 */
	public void show_cards(int cards)
	{
		System.out.print("Cards in hand are: ");
		for(int i = 0; i < cards; ++i)
		{
			System.out.print(this.cards.get(i).toString() + " ");
		}
		System.out.println();
	}
	
	/**
	 * Determine if hand has a blackjack
	 * @return true if aces and cards worth 10 points are found
	 */
	public boolean blackjack()
	{
		// Variables
		boolean has_ace = false;
		boolean has_ten_value = false;
		
		// Iterate through hand to find aces, tens and face cards
		for(int i = 0; i < this.cards.size(); ++i)
		{
			if(this.cards.get(i).get_rank() == 10 || 
					this.cards.get(i).get_rank() == 11 ||
					this.cards.get(i).get_rank() == 12 ||
					this.cards.get(i).get_rank() == 13)
			{
				has_ten_value = true;
			}
			else if(this.cards.get(i).get_rank() == 14)
			{
				has_ace = true;
			}
		}
		
		// Determine whether or not hand has blackjack
		if(has_ace == true && has_ten_value == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean bust()
	{
		if(this.score > 21) {return true;}
		else {return false;}
	}
	
	@Override
	/**
	 * I got some of this code from StackOverflow
	 * https://stackoverflow.com/questions/5925420/how-to-create-a-string-from-string-array-or-arraylist
	 */
	public final String toString()
	{
		int hand_size = this.cards.size();
		String[] cards_array = new String[hand_size];
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < hand_size; ++i)
		{
			cards_array[i] = this.cards.get(i).toString();
		}

		for (String string : cards_array) {
		    if (builder.length() > 0) {
		        builder.append(" ");
		    }
		    builder.append(string);
		}
		
		return builder.toString();
	}
}
