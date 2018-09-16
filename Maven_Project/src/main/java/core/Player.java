package core;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Player
{
	ArrayList<Card> hand; 
	int score;
	boolean stand;
	
	public Player()
	{
		this.hand = new ArrayList<Card>();
		this.score = 0;
		this.stand = false;
	}
	
	/**
	 * Draw one card from the draw_pile
	 * @param draw_pile
	 */
	public void hit(Stack<Card> draw_pile){this.add(draw_pile.pop());}
	
	/**
	 * Give player the option to either hit or stand
	 * @param reader
	 * @param deck
	 */
	public void hit_or_stand(Scanner reader, Deck deck)
	{
		System.out.print("Hit or stand? (h/s): ");
		String hit_or_stand = reader.next();
		
		if(hit_or_stand.equalsIgnoreCase("h"))
		{
			this.hit(deck.draw_pile);
			this.show_cards();
		}
		else if(hit_or_stand.equalsIgnoreCase("s")){this.stand = true;}
		else
		{
			System.out.println("Invalid input");
			hit_or_stand(reader, deck);
		}
	}
	
	/**
	 * Count value of cards in hand, not accounting for aces
	 * @return the score without taking aces into account
	 */
	public int count_not_ace()
	{
		int sum = 0;
		for(int i = 0; i < this.hand.size(); ++i)
		{
			if(this.hand.get(i).getRank() < 11)
			{
				sum += this.hand.get(i).getRank();
			}
			else
			{
				switch(this.hand.get(i).getRank())
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
	 * Count the number of aces in the player's hand
	 * @return the number of aces in the player's hand
	 */
	public int count_aces()
	{
		int aces_in_hand = 0;
		for(int i = 0; i < this.hand.size(); ++i)
		{
			if(this.hand.get(i).getRank() == 14) {aces_in_hand += 1;}
		}
		return aces_in_hand;
	}
	
	/**
	 * Use the functions count_not_ace and count_aces 
	 * Calculate the player's total score
	 * @return the player's total score, accounting for aces
	 */
	public int calc_score()
	{
		int sum = count_not_ace();
		int aces_in_hand = count_aces();
		if(aces_in_hand > 0)
		{
			for(int i = 0; i < aces_in_hand; ++i)
			{
				if(sum < 11) {sum += 11;}
				else {sum += 1;}
			}
		}
		return sum;
	}
	
	public boolean is_bust()
	{
		int card_count = this.calc_score();
		if(card_count > 21) {return true;}
		return false;
	}
	
	public void show_cards()
	{
		System.out.print("Hand is: ");
		for(int i = 0; i < this.hand.size(); ++i)
		{
			System.out.print(this.hand.get(i).toString() + " ");
		}
		System.out.println();
	}
	
	public void show_one_card()
	{
		System.out.print("One card in hand is: ");
		for(int i = 0; i < 1; ++i)
		{
			System.out.print(this.hand.get(i).toString() + " ");
		}
		System.out.println();
	}
	
	/*
	 * Dealer must draw while count is <= 16 || soft 17
	 * Else stand
	 */
	public void dealer_hit(Stack<Card> draw_pile)
	{
		while(this.calc_score() <= 16 || this.calc_score() == 17 && this.count_aces() > 0)
		{
			this.hit(draw_pile);
		}
	}
	
	public void show_count() {System.out.println("Count is: " + this.calc_score());}
	
	public void add(Card card)
	{
		this.hand.add(card);
		this.score = this.calc_score();
	}
} 
