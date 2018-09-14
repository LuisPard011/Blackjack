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

}
