package core;

import org.junit.Test;
import junit.framework.TestCase;

public class WinTest extends TestCase{
	
	public void testDraw()
	{
		Deck deck = new Deck();
		deck.populate_deck();
		deck.populate_draw_pile();
		
		assertEquals("SA", deck.draw_pile.peek().toString());
		Player guest = new Player();
		guest.draw(deck.draw_pile);
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
	}

}
