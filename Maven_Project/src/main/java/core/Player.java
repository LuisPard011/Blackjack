package core;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Player
{
	ArrayList<Card> hand; 
	int score;
	boolean stand;
	String type;
	
	public Player(String player_type)
	{
		this.hand = new ArrayList<Card>();
		this.score = 0;
		this.stand = false;
		this.type = player_type;
	}
	
	/**
	 * Draw one card from the draw_pile
	 * @param draw_pile
	 */
	public void hit(Stack<Card> draw_pile)
	{
		this.add(draw_pile.pop());
		this.score = this.count_hand();
	}
	
	/**
	 * Give player the option to either hit or stand
	 * @param reader
	 * @param deck
	 */
	public void hit_or_stand(Scanner reader, Stack<Card> deck)
	{
		this.show_score();
		System.out.print("Hit or stand? (h/s): ");
		String hit_or_stand = reader.next();
		
		if(hit_or_stand.equalsIgnoreCase("h"))
		{
			this.hit(deck);
			this.show_hand();
		}
		else if(hit_or_stand.equalsIgnoreCase("s")){this.stand = true;}
		else
		{
			System.out.println("Invalid input");
			hit_or_stand(reader, deck);
		}
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
	public int count_hand()
	{
		// Count cards in hand, excluding aces
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
		
		// Take aces into account
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
	public boolean bust()
	{
		if(this.score > 21){return true;}
		return false;
	}
	
	/**
	 * Show all cards in hand
	 * @param player_or_dealer
	 */
	public void show_hand()
	{
		System.out.print(this.type + "'s hand is: ");
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
	public void show_score()
	{
		System.out.println(this.type + "'s count is: " + this.count_hand());
	}
	
	/**
	 * Add a card to hand
	 * @param card
	 */
	public void add(Card card)
	{
		this.hand.add(card);
		this.score = this.count_hand();
	}
	
	/**
	 * Show only one of the dealer's cards
	 */
	public void show_one_card()
	{
		System.out.println("One of " + this.type + "'s cards is: " + this.hand.get(0).toString());
	}
	
	/**
	 * Hit while player's score is less than 16 or it has a soft 17
	 */
	public void dealer_turn(Stack<Card> draw_pile)
	{
		this.show_hand();
		this.show_score();
		while(this.count_hand() <= 16 || this.count_hand() == 17 && this.count_aces() > 0)
		{
			this.hit(draw_pile);
			this.show_hand();
			this.show_score();
		}
	}
} 
