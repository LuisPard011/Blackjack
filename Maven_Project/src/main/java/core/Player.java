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
	public void hit(Stack<Card> draw_pile)
	{
		this.add(draw_pile.pop());
		this.score = this.score();
	}
	
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
			this.show_hand("Player");
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
	public int score()
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
	
	/**
	 * Determine if a player is bust
	 * @return
	 */
	public boolean is_bust()
	{
		if(this.score > 21){return true;}
		return false;
	}
	
	/**
	 * Show all cards in hand
	 * @param player_or_dealer
	 */
	public void show_hand(String player_or_dealer)
	{
		System.out.print(player_or_dealer + "'s hand is: ");
		for(int i = 0; i < this.hand.size(); ++i)
		{
			System.out.print(this.hand.get(i).toString() + " ");
		}
		System.out.println();
	}
	
	/**
	 * Show score
	 * @param player_or_dealer
	 */
	public void show_count(String player_or_dealer)
	{
		System.out.println(player_or_dealer + "'s count is: " + this.score());
	}
	
	/**
	 * Add a card to hand
	 * @param card
	 */
	public void add(Card card)
	{
		this.hand.add(card);
		this.score = this.score();
	}
} 
