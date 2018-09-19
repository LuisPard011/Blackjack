package core;

import java.util.ArrayList;

public class Hand
{
	// Variables
	int score;
	ArrayList<Card> cards;
	
	// Constructor
	public Hand()
	{
		this.score = 0;
		this.cards = new ArrayList<Card>();
	}
	
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
	public boolean show_score()
	{
		System.out.println("Score is: " + this.score);
		return true;
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
			if(this.cards.get(i).getRank() < 11)
			{
				new_score += this.cards.get(i).getRank();
			}
			else
			{
				switch(this.cards.get(i).getRank())
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
		
		for(int i = 0; i < aces; ++i)
		{
			if(new_score >= 21)
			{
				new_score -= 10;
			}
		}
		
		this.score = new_score;
	}
	
	/**
	 * Show all cards in hand
	 * @param cards
	 */
	public boolean show_cards(int cards)
	{
		System.out.print("Cards in hand are: ");
		for(int i = 0; i < cards; ++i)
		{
			System.out.print(this.cards.get(i).toString() + " ");
		}
		System.out.println();
		return true;
	}
	
	public boolean blackjack()
	{
		boolean has_ace = false;
		boolean has_ten_value = false;
		
		// Iterate through hand to find either aces, tens and face cards
		for(int i = 0; i < this.cards.size(); ++i)
		{
			if(this.cards.get(i).getRank() == 10 || 
					this.cards.get(i).getRank() == 11 ||
					this.cards.get(i).getRank() == 12 ||
					this.cards.get(i).getRank() == 13)
			{
				has_ten_value = true;
			}
			else if(this.cards.get(i).getRank() == 14)
			{
				has_ace = true;
			}
		}
		
		if(has_ace == true && has_ten_value == true) {return true;}
		
		return false;
	}
	
	public boolean bust()
	{
		if(this.score > 21) {return true;}
		else {return false;}
	}
}
