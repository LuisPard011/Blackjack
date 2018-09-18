package core;

import java.util.ArrayList;

public class Hand
{
	// Variables
	int score;
	ArrayList<Card> cards;
	// maybe, aces_in_hand variable can be in this class
	
	/*
	 * All functions four counting the score of a hand should be in the Hand class
	 */
	
	/**
	 * Constructor
	 */
	public Hand()
	{
		this.score = 0;
		this.cards = new ArrayList<Card>();
	}
	
	/**
	 * Add one card to hand
	 * Update score
	 * @param card
	 */
	public void add(Card card)
	{
		this.cards.add(card);
		this.score = this.count_hand();
	}
	

	
	/**
	 * @return the number of aces in the player's hand
	 */
	public int count_aces()
	{
		int aces_in_hand = 0;
		for(int i = 0; i < this.cards.size(); ++i)
		{
			if(this.cards.get(i).getRank() == 14) {aces_in_hand += 1;}
		}
		return aces_in_hand;
	}
	
	public int count_not_aces()
	{
		int sum = 0;
		// Count cards in hand, excluding aces
		for(int i = 0; i < this.cards.size(); ++i)
		{
			if(this.cards.get(i).getRank() < 11)
			{
				sum += this.cards.get(i).getRank();
			}
			else
			{
				switch(this.cards.get(i).getRank())
				{
					case 11:
						sum += 10;
						break;
					case 12:
						sum += 10;
						break;
					case 13:
						sum += 10;
						break;
				}
			}
		}
		return sum;
	}
	
	/**
	 * Show score
	 */
	public boolean show_score()
	{
		System.out.println("Score is: " + this.count_hand());
		return true;
	}
	
	/**
	 * @return the player's total score, accounting for aces
	 */
	public int count_hand()
	{
		int sum = this.count_not_aces();
		
		// Take aces into account
		int counter = 0;
		int aces_in_hand = this.count_aces();
		if(aces_in_hand > 0)
		{
			for(int i = 0; i < aces_in_hand; ++i)
			{
				if(sum < 11) {sum += 11;}
				else {sum += 1;}
			}
			
			if(sum > 21)
			{
				while(counter < aces_in_hand)
				{
					sum -= 10;
					++counter;
				}
			}
		}
		
		return sum;
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
}
