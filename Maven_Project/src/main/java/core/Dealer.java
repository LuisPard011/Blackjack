package core;

import java.util.Stack;

public class Dealer extends Player {

	public Dealer(String name) {
		super(name);
	}
	
	public boolean blackjack_Win(Player player)
	{
		if(!this.blackjack() && player.blackjack())
		{
			player.win = true;
			System.out.println("Player has blackjack and dealer does not. Player wins");
			return true;
		}
		else if(!player.blackjack() && this.blackjack())
		{
			System.out.println("Dealer has a blackjack and player does not. Dealer wins");
			this.win = true;
			return true;
		}
		else if(player.blackjack() && this.blackjack())
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
	public boolean dealer_turn(Stack<Card> deck, Player player)
	{
		this.show_cards(this.hand.size());
		this.show_score();
		if(this.blackjack_Win(player)) {return true;}
		while(this.count_hand() <= 16 || this.count_hand() == 17 && this.count_aces() > 0)
		{
			this.hit(deck, 1);
			this.show_cards(this.hand.size());
			this.show_score();
		}
		this.bust(player);
		return false;
	}

}
