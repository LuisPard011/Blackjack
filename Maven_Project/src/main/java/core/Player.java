package core;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Player
{
	/*
	 * Variables
	 * Maybe I should make these variables protected 
	 * then use getters/setters to access them
	 */
	ArrayList<Card> hand; 
	boolean bust;
	boolean stand;
	boolean win;
	int score;
	String name;
	
	/**
	 * Constructor
	 * @param player_name
	 */
	public Player(String player_name)
	{
		this.hand = new ArrayList<Card>();
		this.bust = false;
		this.stand = false;
		this.win = false;
		this.score = 0;
		this.name = player_name;
	}
	
	/**
	 * Show all cards in hand
	 * @param cards
	 */
	public boolean show_cards(int cards)
	{
		System.out.print(this.name + " has cards: ");
		for(int i = 0; i < cards; ++i)
		{
			System.out.print(this.hand.get(i).toString() + " ");
		}
		System.out.println();
		return true;
	}
	
	/**
	 * Show score
	 */
	public boolean show_score()
	{
		System.out.println(this.name + "'s score is: " + this.count_hand());
		return true;
	}
	
	/**
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
		
		if(sum > 21 && aces_in_hand > 1)
		{
			while(sum > 21) {sum -= 10;}
		}
		
		return sum;
	}
	
	/**
	 * Add a card to hand and update score
	 * @param card
	 */
	public void add(Card card)
	{
		this.hand.add(card);
		this.score = this.count_hand();
	}
	
	/**
	 * Draw x number of cards
	 * @param deck
	 * @param draw_times
	 */
	public void hit(Stack<Card> deck, int draw_times)
	{
		for(int i = 0; i < draw_times; ++i){this.add(deck.pop());}
		this.score = this.count_hand();
	}
	
	/**
	 * Give player the option to either hit or stand
	 * @param deck
	 */
	public void hit_or_stand(Stack<Card> deck)
	{
		Scanner scanner = new Scanner(System.in);
		
		this.show_score();
		System.out.print("Hit or stand? (h/s): ");
		String hit_or_stand = scanner.next();
		
		if(hit_or_stand.equalsIgnoreCase("h"))
		{
			this.hit(deck, 1);
			this.show_cards(this.hand.size());
		}
		else if(hit_or_stand.equalsIgnoreCase("s")){this.stand = true;}
		else
		{
			System.out.println("Invalid input");
			hit_or_stand(deck);
		}
	}
	
	/**
	 * Check if player is bust
	 * If not, give option to hit or stand
	 * @param deck
	 * @param dealer
	 */
	public void player_turn(Stack<Card> deck, Dealer dealer)
	{
		while(!this.bust(dealer) && !this.stand){this.hit_or_stand(deck);}
	}
	
	/**
	 * Determine if player/dealer is bust
	 * If yes, the other wins the game
	 * @param player_or_dealer
	 * @return
	 */
	public boolean bust(Player player_or_dealer)
	{
		if(this.score > 21)
		{
			System.out.println(this.name + " busts, " + player_or_dealer.name + " wins");
			this.bust = true;
			player_or_dealer.win = true;
			return true;
		}
		return false;
	}
	
	/**
	 * If neither the player nor the dealer busts
	 * Then, scores are compared to determine winner
	 * @param dealer
	 */
	public void determine_winner(Dealer dealer)
	{
		// If player's score is greater than dealer's
		if(this.score > dealer.score)
		{
			System.out.println(this.name + " wins");
			this.win = true;
		}
		else
		{
			System.out.println("Dealer wins");
			dealer.win = true;
		}
	}
} 
