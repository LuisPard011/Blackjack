package core;

import java.util.Scanner;
import java.util.Stack;

public class Player
{
	// Variables
	Hand default_hand;
	Hand split_hand_1;
	Hand split_hand_2;
	boolean splitted;
	boolean has_blackjack;
	
	// Constructor
	public Player()
	{
		this.default_hand = new Hand();
		this.split_hand_1 = new Hand();
		this.split_hand_2 = new Hand();
		this.splitted = false;
		this.has_blackjack = false;
	}
	
	/**
	 * Draw_times cards from deck and put them into hand
	 * @param deck
	 * @param draw_times
	 * @param hand
	 */
	public boolean hit(Stack<Card> deck, int draw_times, Hand hand)
	{
		for(int i = 0; i < draw_times; ++i)
		{
			hand.add(deck.pop());
		}
		if(hand.blackjack())
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Give player the option to either hit or stand
	 * @param deck
	 * @param hand
	 * @return true if stand is chosen
	 */
	public void hit_or_stand(Stack<Card> deck, Hand hand, Dealer dealer)
	{
		// Local variables
		Scanner scanner = new Scanner(System.in);
		boolean stand = false;
		
		while(!hand.bust() && !stand)
		{
			// Interface output
			hand.show_cards(hand.cards.size());
			hand.show_score();
			System.out.print("Hit or stand? (h/s): ");
			String hit_or_stand = scanner.next();
			
			// If-Else control structure for hit or stand
			if(hit_or_stand.equalsIgnoreCase("h"))
			{
				if(this.hit(deck, 1, hand) && !dealer.default_hand.cards.isEmpty())
				{
					this.blackjack_Win(dealer);
				}
			}
			else if(hit_or_stand.equalsIgnoreCase("s"))
			{
				stand = true;	
			}
			else
			{
				System.out.println("Invalid input"); 
				hit_or_stand(deck, hand, dealer);
			}
			
			if(hand.bust())
			{
				hand.show_cards(hand.cards.size());
				this.bust();
			}
		}
	}
	
	
	/**
	 * If neither the player nor the dealer busts, scores are compared to determine winner
	 * @param dealer
	 */
	public void determine_winner(Dealer dealer)
	{	
		// Variables
		int player_highest_score = 0;
		int dealer_highest_score = 0;
		Hand player_best_hand = new Hand();
		Hand dealer_best_hand = new Hand();
		
		// Check if player is bust and find its hand with the highest score under 22
		if(!this.default_hand.bust() && !this.splitted)
		{
			player_highest_score = this.default_hand.get_score();
			player_best_hand = this.default_hand;
		}
		if(player_highest_score < this.split_hand_1.get_score() && !this.split_hand_1.bust())
		{
			player_highest_score = this.split_hand_1.get_score();
			player_best_hand = this.split_hand_1;
		}
		if(player_highest_score < this.split_hand_2.get_score() && !this.split_hand_2.bust())
		{
			player_highest_score = this.split_hand_2.get_score();
			player_best_hand = this.split_hand_2;
		}
		
		// Check if dealer is bust and find its hand with the highest score under 22
		if(!dealer.default_hand.bust() && !dealer.splitted)
		{
			dealer_highest_score = dealer.default_hand.get_score();
			dealer_best_hand = dealer.default_hand;
		}
		if(dealer_highest_score < dealer.split_hand_1.get_score() && !dealer.split_hand_1.bust())
		{
			dealer_highest_score = dealer.split_hand_1.get_score();
			dealer_best_hand = dealer.split_hand_1;
		}
		if(dealer_highest_score < dealer.split_hand_2.get_score() && !dealer.split_hand_2.bust())
		{
			dealer_highest_score = dealer.split_hand_2.get_score();
			dealer_best_hand = dealer.split_hand_2;
		}
		
		// Interface output
		if(player_highest_score > dealer_highest_score)
		{
			System.out.println("Player has [" + player_best_hand.toString() + "] (" + player_highest_score 
					+ ") points and dealer has [" + dealer_best_hand.toString() + "] (" + dealer_highest_score + 
					") points. Player wins");
		}
		else if(player_highest_score == 0)
		{
			System.out.println("Player busted. Dealer wins with [" + dealer_best_hand.toString() 
					+ "] (" + dealer_highest_score + ") points");
		}
		else if(dealer_highest_score == 0)
		{
			System.out.println("Dealer busted. Player wins with [" + player_best_hand.toString() 
			+ "] (" + player_highest_score + ") points");
		}
		else
		{
			System.out.println("Player has [" + player_best_hand.toString() + "] (" + player_highest_score 
					+ ") points and dealer has [" + dealer_best_hand.toString() + "] (" + dealer_highest_score + 
					") points. Dealer wins");
		}
	}
	
	/**
	 * Give player the option to split hand
	 * @return true if split has been chosen
	 */
	public boolean choose_split()
	{
		Scanner scanner = new Scanner(System.in);
		String choose_split;
		
		System.out.println("Would you like to split? (y/n): ");
		choose_split = scanner.next();
		
		if(choose_split.equalsIgnoreCase("y"))
		{
			this.splitted = true;
			return true;
		}
		else {return false;}
	}
	
	/**
	 * Split hand
	 */
	public void split_hand()
	{
		this.split_hand_1.add(this.default_hand.cards.get(0));
		this.split_hand_2.add(this.default_hand.cards.get(1));
	}
	
	/**
	 * @return true if initial hand can be split
	 */
	public boolean can_split()
	{
		if(this.default_hand.cards.get(0).get_rank() == this.default_hand.cards.get(1).get_rank())
		{
			return true;
		}
		return false;
	}

	/**
	 * Look for blackjacks in all of player's and dealer's hands
	 * Get rid of hand parameters and maybe dealer too
	 * @param dealer
	 * @return true if anyone has a blackjack
	 */
	public boolean blackjack_Win(Dealer dealer)
	{
		if(this.default_hand.blackjack() || this.split_hand_1.blackjack() || this.split_hand_2.blackjack())
		{
			this.has_blackjack = true;
		}
		if(dealer.default_hand.blackjack() || dealer.split_hand_1.blackjack() || dealer.split_hand_2.blackjack())
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
	
	/**
	 * @return true if player's default hand or both split hands have busted
	 */
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
	
	/**
	 * routine for a turn after splitting initial hand
	 * @param deck
	 */
	public void split_turn(Stack<Card> deck, Dealer dealer)
	{
		this.hit(deck, 1, this.split_hand_1);
		this.hit_or_stand(deck, this.split_hand_1, dealer);
		
		this.hit(deck, 1, this.split_hand_2);
		this.hit_or_stand(deck, this.split_hand_2, dealer);
	}
} 
