package core;

import java.util.Stack;

public class Dealer extends Player {

	public Dealer(String name) {
		super(name);
	}
	
	public boolean blackjack_Win(Player player, Hand player_hand, Hand dealer_hand)
	{
		if(player_hand.blackjack() && !dealer_hand.blackjack())
		{
			player.win = true;
			System.out.println("Player has blackjack and dealer does not. Player wins");
			return true;
		}
		else if(!player_hand.blackjack() && dealer_hand.blackjack())
		{
			System.out.println("Player does not have a blackjack, but dealer does. Dealer wins");
			this.win = true;
			return true;
		}
		else if(player_hand.blackjack() && dealer_hand.blackjack())
		{
			System.out.println("Both the player and dealer have a blackjack. Dealer wins");
			this.win = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Hit while dealer's score is less than 16 or it has a soft 17
	 * @param deck
	 * @param player
	 */
	public boolean dealer_turn(Stack<Card> deck, Player player, Hand player_hand, Hand dealer_hand)
	{
		dealer_hand.show_cards(dealer_hand.cards.size());
		dealer_hand.show_score();
		if(this.blackjack_Win(player, player_hand, dealer_hand)) {return true;}
		while(dealer_hand.score <= 16 || dealer_hand.score == 17 && dealer_hand.score > 0)
		{
			this.hit(deck, 1, dealer_hand);
			dealer_hand.show_cards(dealer_hand.cards.size());
			dealer_hand.show_score();
		}
		this.bust(player, dealer_hand);
		return false;
	}

}
