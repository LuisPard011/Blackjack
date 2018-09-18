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
	 * Show all cards in hand
	 * @param cards
	 */
	public boolean show_cards(int cards, Hand hand)
	{
		System.out.print(this.name + " has cards: ");
		for(int i = 0; i < cards; ++i)
		{
			System.out.print(hand.cards.get(i).toString() + " ");
		}
		System.out.println();
		return true;
	}
	
	/**
	 * Show score
	 */
	public boolean show_score(Hand hand)
	{
		System.out.println(this.name + "'s score is: " + this.count_hand(hand));
		return true;
	}
	
	/**
	 * @return the number of aces in the player's hand
	 */
	public int count_aces(Hand hand)
	{
		int aces_in_hand = 0;
		for(int i = 0; i < hand.cards.size(); ++i)
		{
			if(hand.cards.get(i).getRank() == 14) {aces_in_hand += 1;}
		}
		return aces_in_hand;
	}
	
	/**
	 * @return the player's total score, accounting for aces
	 */
	public int count_hand(Hand hand)
	{
		// Count cards in hand, excluding aces
		int sum = 0;
		for(int i = 0; i < hand.cards.size(); ++i)
		{
			if(hand.cards.get(i).getRank() < 11)
			{
				sum += hand.cards.get(i).getRank();
			}
			else
			{
				switch(hand.cards.get(i).getRank())
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
		
		/*
		 * The count and the number of aces matter
		 * 
		 * If I have more than two aces, only one of them, at most can count as 11
		 * 
		 * If I have one ace the score is more than 21, the ace's value becomes 1
		 * 
		 * If I have more than one ace, and the score value is more than 21, of one the ace's value becomes 1,
		 * if after turning the value to 1 the score still is greater than 21, repeat the process
		 * 
		 * 
		 */
		
		
		// Take aces into account
		int aces_in_hand = count_aces(hand);
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
	public void add(Card card, Hand hand)
	{
		hand.cards.add(card);
		hand.score = this.count_hand(hand);
	}
	
	/**
	 * Draw x number of cards
	 * @param deck
	 * @param draw_times
	 */
	public void hit(Stack<Card> deck, int draw_times, Hand hand)
	{
		for(int i = 0; i < draw_times; ++i){this.add(deck.pop(), hand);}
		hand.score = this.count_hand(hand);
	}
	
	/**
	 * Give player the option to either hit or stand
	 * @param deck
	 */
	public void hit_or_stand(Stack<Card> deck, Hand hand)
	{
		Scanner scanner = new Scanner(System.in);
		
		this.show_score(hand);
		System.out.print("Hit or stand? (h/s): ");
		String hit_or_stand = scanner.next();
		
		if(hit_or_stand.equalsIgnoreCase("h"))
		{
			this.hit(deck, 1, hand);
			this.show_cards(hand.cards.size(), hand);
		}
		else if(hit_or_stand.equalsIgnoreCase("s")){this.stand = true;}
		else
		{
			System.out.println("Invalid input");
			hit_or_stand(deck, hand);
		}
	}
	
	/**
	 * Check if player is bust
	 * If not, give option to hit or stand
	 * @param deck
	 * @param dealer
	 */
	public void player_turn(Stack<Card> deck, Dealer dealer, Hand hand)
	{
		while(!this.bust(dealer, hand) && !this.stand){this.hit_or_stand(deck, hand);}
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
			System.out.println(this.name + " wins");
			this.win = true;
		}
		else
		{
			System.out.println("Dealer wins");
			dealer.win = true;
		}
	}
	
	public boolean blackjack(Hand hand)
	{
		boolean has_ace = false;
		boolean has_ten_value = false;
		
		for(int i = 0; i < hand.cards.size(); ++i)
		{
			if(hand.cards.get(i).getRank() == 14) {has_ace = true;}
			
			if(hand.cards.get(i).getRank() == 10 || 
					hand.cards.get(i).getRank() == 11 ||
					hand.cards.get(i).getRank() == 12 ||
					hand.cards.get(i).getRank() == 13)
			{has_ten_value = true;}
		}
		
		if(has_ace == true && has_ten_value == true) {return true;}
		
		return false;
	}
	
	public void split(Hand hand)
	{
		if(hand.cards.get(0).getRank() == hand.cards.get(1).getRank())
		{
			/*
			 * Create two new hands
			 * Make a Hand class?
			 * Hand has score
			 */
			return;
		}
	}

} 
