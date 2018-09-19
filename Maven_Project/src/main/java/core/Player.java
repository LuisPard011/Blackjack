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
	
	boolean bust;
	boolean stand;
	boolean win;
	String name;
	
	/**
	 * Constructor
	 * @param player_name
	 */
	public Player(String player_name)
	{
		this.default_hand = new Hand();
		this.split_hand_1 = new Hand();
		this.split_hand_2 = new Hand();
		this.bust = false;
		this.stand = false;
		this.win = false;
		this.name = player_name;
	}
	
	/**
	 * Draw x number of cards
	 * @param deck
	 * @param draw_times
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
	 */
	public void hit_or_stand(Stack<Card> deck, Hand hand)
	{
		Scanner scanner = new Scanner(System.in);
		
		hand.show_score();
		System.out.print("Hit or stand? (h/s): ");
		String hit_or_stand = scanner.next();
		
		if(hit_or_stand.equalsIgnoreCase("h"))
		{
			this.hit(deck, 1, hand);
			hand.show_cards(hand.cards.size());
		}
		else if(hit_or_stand.equalsIgnoreCase("s")){this.stand = true;}
		else
		{
			System.out.println("Invalid input");
			hit_or_stand(deck, hand);
		}
	}
	
	/**
	 * Determine if player/dealer is bust
	 * If yes, the other wins the game
	 * @param player_or_dealer
	 * @return
	 */
	public boolean bust(Player player_or_dealer, Hand hand)
	{
		if(hand.score > 21)
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
	 * @param player_hand
	 * @param dealer_hand
	 */
	public void determine_winner(Dealer dealer, Hand player_hand, Hand dealer_hand)
	{
		// If player's score is greater than dealer's
		if(player_hand.score > dealer_hand.score)
		{
			System.out.println("Player wins");
			this.win = true;
		}
		else
		{
			System.out.println("Dealer wins");
			dealer.win = true;
		}
	}
	
	/**
	 * Split hand
	 * @param deck
	 */
	public void split_check(Dealer dealer, Stack<Card> deck)
	{
		Scanner scanner = new Scanner(System.in);
		String choose_split;
		
		if(this.default_hand.cards.get(0).getRank() == this.default_hand.cards.get(1).getRank())
		{
			System.out.println("Would you like to split? (y/n): ");
			choose_split = scanner.next();
			
			if(choose_split.equalsIgnoreCase("y"))
			{
				this.split_hand_1.add(this.default_hand.cards.get(0));
				this.split_hand_2.add(this.default_hand.cards.get(1));
				
				this.hit(deck, 1, this.split_hand_1);
				this.split_hand_1.show_cards(split_hand_1.cards.size());
				this.player_turn(dealer, deck, split_hand_1);
				
				this.hit(deck, 1, this.split_hand_2);
				this.split_hand_2.show_cards(split_hand_2.cards.size());
				this.player_turn(dealer, deck, split_hand_2);
			}
		}
	}
	
	/**
	 * I should make this into a boolean function that returns true if the player has a blackjack
	 * @param dealer
	 * @param deck
	 * @param hand
	 */
	public boolean player_turn(Dealer dealer, Stack<Card> deck, Hand player_hand, Hand dealer_hand)
	{
		if(this.blackjack_Win(dealer, player_hand, dealer_hand)) {return true;}
		while(!this.bust(dealer, player_hand) && !this.stand)
		{
			this.hit_or_stand(deck, player_hand);
		}
		return false;
	}

	public boolean blackjack_Win(Dealer dealer, Hand player_hand, Hand dealer_hand)
	{
		if(player_hand.blackjack() && !dealer_hand.blackjack())
		{
			this.win = true;
			System.out.println("Player has blackjack and dealer does not. Player wins");
			return true;
		}
		else if(!player_hand.blackjack() && dealer_hand.blackjack())
		{
			System.out.println("Player does not have a blackjack, but dealer does. Dealer wins");
			dealer.win = true;
			return true;
		}
		else if(player_hand.blackjack() && dealer_hand.blackjack())
		{
			System.out.println("Both the player and dealer have a blackjack. Dealer wins");
			dealer.win = true;
			return true;
		}
		return false;
	}
} 
