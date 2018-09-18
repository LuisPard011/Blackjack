package core;

import java.util.Stack;

public class Dealer extends Player {

	public Dealer(String name) {
		super(name);
	}
	
	public boolean blackjack_Win(Player player, Hand player_hand, Hand dealer_hand)
	{
		if(player.blackjack(player_hand) && !this.blackjack(dealer_hand))
		{
			player.win = true;
			System.out.println("Player has blackjack and dealer does not. Player wins");
			return true;
		}
		else if(!player.blackjack(player_hand) && this.blackjack(dealer_hand))
		{
			System.out.println("Player does not have a blackjack, but dealer does. Dealer wins");
			this.win = true;
			return true;
		}
		else if(player.blackjack(player_hand) && this.blackjack(dealer_hand))
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
		this.show_cards(dealer_hand.cards.size(), dealer_hand);
		this.show_score(dealer_hand);
		if(this.blackjack_Win(player, player_hand, dealer_hand)) {return true;}
		while(this.count_hand(dealer_hand) <= 16 || this.count_hand(dealer_hand) == 17 && this.count_aces(dealer_hand) > 0)
		{
			this.hit(deck, 1, dealer_hand);
			this.show_cards(dealer_hand.cards.size(), dealer_hand);
			this.show_score(dealer_hand);
		}
		this.bust(player, dealer_hand);
		return false;
	}

}
