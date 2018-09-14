package core;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Game
{	
	
	public static void play()
	{
		// Collect cards and shuffle deck
		// Deal cards
		// Player turn
		// Dealer's turn
		// When win/lose give option to play again
		// If play again == y, recursive call to play()
		// Else, return
	}
		
	public static void main(String[] args)
	{	
		Deck deck = new Deck();
		deck.populate_deck();
		deck.shuffle_deck();
		deck.populate_draw_pile();
		deck.show_deck(42, deck.deck_size);
		
		Player guest = new Player();
		guest.draw(deck.draw_pile);
		guest.draw(deck.draw_pile);
		guest.show_cards();
		guest.show_count();
		deck.peek_draw_pile();
		
		Dealer dealer = new Dealer();
		dealer.draw(deck.draw_pile);
		dealer.draw(deck.draw_pile);
		dealer.show_cards();
		dealer.show_count();
		deck.peek_draw_pile();
	}
}
