package core;

import java.util.Scanner;
import java.util.Stack;

public class Player
{
	/*
	 * Variables
	 * Maybe I should make these variables protected 
	 * then use getters/setters to access them
	 */
	Hand default_hand;
	Hand split_hand_1;
	Hand split_hand_2;
	boolean has_blackjack;
	
	/**
	 * Constructor
	 */
	public Player()
	{
		this.default_hand = new Hand();
		this.split_hand_1 = new Hand();
		this.split_hand_2 = new Hand();
		this.has_blackjack = false;
	}
	
	/**
	 * Draw x number of cards
	 * @param deck
	 * @param draw_times
	 * @param hand
	 */
	public void hit(Stack<Card> deck, int draw_times, Hand hand)
	{
		for(int i = 0; i < draw_times; ++i)
		{
			hand.add(deck.pop());
		}
	}
	
	/**
	 * Give player the option to either hit or stand
	 * @param deck
	 * @param hand
	 */
	public boolean hit_or_stand(Stack<Card> deck, Hand hand)
	{
		Scanner scanner = new Scanner(System.in);
		boolean stand = false;
		
		while(!hand.bust() && !stand)
		{
			hand.show_cards(hand.cards.size());
			hand.show_score();
			System.out.print("Hit or stand? (h/s): ");
			String hit_or_stand = scanner.next();
			
			if(hit_or_stand.equalsIgnoreCase("h"))
			{
				this.hit(deck, 1, hand);
				hand.show_cards(hand.cards.size());
				hand.show_score();
			}
			else if(hit_or_stand.equalsIgnoreCase("s"))
			{
				stand = true;	
			}
			else
			{
				System.out.println("Invalid input");
				hit_or_stand(deck, hand);
			}
		}
		return stand;
	}
	
	
	
	/**
	 * If neither the player nor the dealer busts
	 * Then, scores are compared to determine winner
	 * @param dealer
	 */
	public void determine_winner(Dealer dealer)
	{	
		int player_highest_score = 0;
		int dealer_highest_score = 0;
		
		if(!this.default_hand.bust())
		{
			player_highest_score = this.default_hand.score;
		}
		else if(player_highest_score < this.split_hand_1.score && !this.split_hand_1.bust())
		{
			player_highest_score = this.split_hand_1.score;
		}
		else if(player_highest_score < this.split_hand_2.score && !this.split_hand_2.bust())
		{
			player_highest_score = this.split_hand_2.score;
		}
		
		if(!dealer.default_hand.bust())
		{
			dealer_highest_score = dealer.default_hand.score;
		}
		else if(dealer_highest_score < dealer.split_hand_1.score && !dealer.split_hand_1.bust())
		{
			dealer_highest_score = dealer.split_hand_1.score;
		}
		else if(dealer_highest_score < dealer.split_hand_2.score && !dealer.split_hand_2.bust())
		{
			dealer_highest_score = dealer.split_hand_2.score;
		}
		
		if(player_highest_score > dealer_highest_score)
		{
			System.out.println("Player has " + player_highest_score 
					+ " and dealer has " + dealer_highest_score + 
					". Player wins");
		}
		else
		{
			System.out.println("Player has " + player_highest_score 
					+ " and dealer has " + dealer_highest_score + 
					". Dealer wins");
		}
	}
	
	/**
	 * Split hand
	 * @param deck
	 */
	public boolean choose_split()
	{
		Scanner scanner = new Scanner(System.in);
		String choose_split;
		
		System.out.println("Would you like to split? (y/n): ");
		choose_split = scanner.next();
		
		if(choose_split.equalsIgnoreCase("y")) {return true;}
		else {return false;}
	}
	
	public void split_hand()
	{
		this.split_hand_1.add(this.default_hand.cards.get(0));
		this.split_hand_2.add(this.default_hand.cards.get(1));
	}
	
	public boolean can_split()
	{
		if(this.default_hand.cards.get(0).getRank() == this.default_hand.cards.get(1).getRank())
		{
			return true;
		}
		return false;
	}
	
	/**
	 * I should make this into a boolean function that returns true if the player has a blackjack
	 * @param dealer
	 * @param deck
	 * @param hand
	 */
	public void player_turn(Dealer dealer, Stack<Card> deck, Hand player_hand)
	{
		if(this.blackjack_Win(dealer)) {return;}
		this.hit_or_stand(deck, player_hand);
	}


	/**
	 * Look for blackjacks in all of player and dealer's hands
	 * Get rid of hand parameters and maybe dealer too
	 * @param dealer
	 * @return
	 */
	public boolean blackjack_Win(Dealer dealer)
	{
		if(this.default_hand.blackjack() || this.split_hand_1.blackjack() || this.split_hand_2.blackjack())
		{
			this.has_blackjack = true;
		}
		else if(dealer.default_hand.blackjack() || dealer.split_hand_1.blackjack() || dealer.split_hand_2.blackjack())
		{
			dealer.has_blackjack = true;
		}
		
		if(this.has_blackjack && !dealer.has_blackjack)
		{
			System.out.println("Player has blackjack and dealer does not. Player wins");
			return true;
		}
		else if(!this.has_blackjack && dealer.has_blackjack)
		{
			System.out.println("Player does not have a blackjack, but dealer does. Dealer wins");
			return true;
		}
		else if(this.has_blackjack && dealer.has_blackjack)
		{
			System.out.println("Both the player and dealer have a blackjack. Dealer wins");
			return true;
		}
		
		return false;
	}
	
	public boolean bust()
	{
		if(!this.can_split() && this.default_hand.bust())
		{
			System.out.println("Busted");
			return true;
		}
		else if(this.can_split() && this.split_hand_1.bust() && this.split_hand_2.bust())
		{
			System.out.println("Busted");
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void split_turn(Stack<Card> deck)
	{
		this.hit(deck, 1, this.split_hand_1);
		this.hit_or_stand(deck, this.split_hand_1);
		
		this.hit(deck, 1, this.split_hand_2);
		this.hit_or_stand(deck, this.split_hand_2);
	}
} 
