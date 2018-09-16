package core;

import junit.framework.TestCase;

public class WinTest extends TestCase{
	
	/**
	 * Variables to be used by multiple tests
	 */
	Card ace_d = new Card("D", "A");
	Card ace_s = new Card("S", "A");
	Card two_s = new Card("S", "2");
	Card nine_s = new Card("S", "9");
	Card king_c = new Card("C", "K");
	Card king_d = new Card("D", "K");
	Card king_s = new Card("S", "K");
	
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
		guest.add(ace_s);
		guest.add(ace_d);
		assertEquals(12, guest.score());
		
		// I can add different tests for multiple counting scenarios
	}
	
	/*
	 * This test is meant to identify when a player's count goes over 21
	 */
	public void testBust()
	{
		Player guest = new Player();
		guest.add(king_s);
		guest.add(king_d);
		guest.add(two_s);
		assertEquals(true, guest.is_bust());
		// might have to overwrite the ArrayList add method to include +count when adding cards manually
	}
	
	/*
	 * Compare scores to determine a winner
	 */
	public void testWinner()
	{
		Player guest = new Player();
		Player dealer = new Player();
		
		guest.add(king_s);
		guest.add(nine_s);
		dealer.add(king_d);
		dealer.add(king_c);
		
		assertEquals(false, guest.score > dealer.score);
	}
	
	public void testReadInput()
	{
//		Player guest = new Player();
//		//read input file
//		assertEquals("S", guest.hand.get(0).getSuit());
	}
	
	public void test()
	{
		
	}
	
	public void testMethodPath()
	{
		
	}
	
	public void testLoop()
	{
		
	}
	
	// Need to write tests for requirements 16-44
	
	public void testShuffling()
	{
		
	}
	
	public void testConsoleInput()
	{
		
	}
	
	/*
	 * Test if the option to choose between file and console works
	 */
	public void testChooseFC()
	{
		
	}
	
	/*
	 * Test if the player's first two cards are visible
	 */
	public void testVisibility_0()
	{
		
	}
	
	/**
	 * Test only one of the dealer's cards is visible at the start
	 */
	public void testDealerVisibility()
	{
		
	}
	
	/**
	 * Test the player can hit
	 */
	public void testPlayerHit()
	{
		
	}
	
	/**
	 * Test player can hit repeatedly
	 */
	public void testPlayerMultipleHits()
	{
		
	}
	
	/**
	 * 
	 */
	public void testPlayerStand()
	{
		
	}
	
	/**
	 * Hand of the player is displayed at the end of the turn
	 */
	public void testDisplayPlayerHandEnd()
	{
		
	}
	
	/**
	 * Player can bust and dealer wins because of it
	 */
	public void testPlayerBust()
	{
		
	}
	
	/**
	 * Dealer has <= 16, thus it hits
	 */
	public void testDealerHit16()
	{
		
	}
	
	/**
	 * Dealer has soft 17, thus it hits
	 */
	public void testDealerSoft17()
	{
		
	}
	
	/**
	 * Dealer can hit repeatedly
	 */
	public void testDealerRepeatHit()
	{
		
	}
	
	/**
	 * Dealer's cards are visible at the end of its turn
	 */
	public void testDealerVisibleEndTurn()
	{
		
	}
	
	/**
	 * If dealer busts player wins
	 */
	public void testDealerBust()
	{
		
	}
	
	/**
	 * Ace counts as 1
	 */
	public void testAce1()
	{
		
	}

	/**
	 * Ace counts as 11
	 */
	public void testAce11()
	{
		
	}
	
	/**
	 * Two aces in hand, one counts as 1 and the other as 11
	 */
	public void testTwoAces1_11()
	{
		
	}
	
	/**
	 * One ace can count as 11 and then 1
	 */
	public void testTwoAces11_1()
	{
		
	}
	
	/**
	 * A hand can count two aces as 1 each
	 */
	public void testTwoAces1_1()
	{
		
	}
	
	/**
	 * J, Q and K count as 10
	 */
	public void testFaceCardsValues()
	{
		
	}
	
	/**
	 * Player's initial blackjack is detected
	 */
	public void testPlayerBlackjack()
	{
		
	}
	
	/**
	 * 
	 */
	public void testDealerBlackjack()
	{
		
	}
	
	/**
	 * If player has Blackjack and the dealer doesn't, player wins
	 */
	public void testBlackjackWin()
	{
		
	}
	
	/**
	 * If dealer has Blackjack it wins
	 */
	public void testBlackjackWin_2()
	{
		
	}
	
	/**
	 * Player's hand score is displayed and it's correct
	 */
	public void testPlayerScore()
	{
		
	}
	
	/**
	 * Dealer's hand score is displayed and it's correct
	 */
	public void testDealerScore()
	{
		
	}
	
	/**
	 * If dealer doesn't bust, player with highest score wins
	 */
	public void testDetermineWinner()
	{
		
	}
	
}
