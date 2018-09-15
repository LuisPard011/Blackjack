package core;

import junit.framework.TestCase;

public class WinTest extends TestCase{
	
	public void testDraw()
	{
		Deck deck = new Deck();
		deck.populate_deck();
		deck.populate_draw_pile();
		
		assertEquals("SA", deck.draw_pile.peek().toString());
		Player guest = new Player();
		guest.hit(deck.draw_pile);
		assertEquals("HA", deck.draw_pile.peek().toString());
	}
	
	public void testCount()
	{
		Player guest = new Player();
		Card ace_s = new Card("Spades", "Ace");
		Card ace_d = new Card("Diamonds", "Ace");
		guest.cards_on_table.add(ace_s);
		guest.cards_on_table.add(ace_d);
		assertEquals(12, guest.count_cards());
		
		// I can add different tests for multiple counting scenarios
	}
	
	/*
	 * This test is meant to identify when a player's count goes over 21
	 */
	public void testBust()
	{
		Player guest = new Player();
		Card king_s = new Card("Spades", "King");
		Card king_d = new Card("Diamonds", "King");
		Card two_s = new Card("Spades", "2");
		guest.cards_on_table.add(king_s);
		guest.cards_on_table.add(king_d);
		guest.cards_on_table.add(two_s);
		assertEquals(true, guest.is_bust());
		// might have to overwrite the ArrayList add method to include +count when adding cards manually
	}
	
	/*
	 * Compare scores to determine a winner
	 */
	public void testWinner()
	{
		Player guest = new Player();
		Card king_s = new Card("Spades", "King");
		Card nine_s = new Card("Spades", "9");
		
		Dealer dealer = new Dealer();
		Card king_d = new Card("Diamonds", "King");
		Card king_c = new Card("Clubs", "King");
		
		guest.cards_on_table.add(king_s);
		guest.cards_on_table.add(nine_s);
		dealer.cards_on_table.add(king_d);
		dealer.cards_on_table.add(king_c);
		
		assertEquals(false, guest.count_cards() > dealer.count_cards());
	}

}
