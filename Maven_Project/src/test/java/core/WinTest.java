package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import junit.framework.TestCase;

public class WinTest extends TestCase
{	
	/********************
	 * Variables(Start) * 
	 ********************/
	
	Card ace_d = new Card("D", "A");
	Card ace_s = new Card("S", "A");
	Card two_s = new Card("S", "2");
	Card nine_s = new Card("S", "9");
	Card king_c = new Card("C", "K");
	Card king_d = new Card("D", "K");
	Card king_s = new Card("S", "K");
	
	Deck_Maker deck_maker = new Deck_Maker();
	
	Stack<Card> deck_1 = new Stack<>();
	Stack<Card> deck_2 = new Stack<>();
	
	int deck_size = 52;
	
	/******************
	 * Variables(End) * 
	 ******************/
	
	/**
	 * Requirement 11
	 * Check there are 52 cards in the deck
	 */
	public void test_Cards_In_Deck()
	{
		deck_maker.make_deck(deck_1);
		int count_pops = 0;
		for(int i = 0; i < deck_size; ++i)
		{
			deck_1.pop();
			count_pops += 1;
		}
		assertEquals(52, count_pops);
	}
	
	/**
	 * Requirement 12
	 * Test shuffling procedure
	 * If shuffling is turned off in make_deck(Stack<Card>), test fails
	 * Else it passes
	 * I use the rank of cards to compare the order of decks
	 */
	public void test_Shuffling()
	{
		deck_maker.make_deck(deck_1);
		deck_maker.make_deck(deck_2);
		int[] arr_1 = new int[deck_size];
		int[] arr_2 = new int[deck_size];
		for(int i = 0; i < deck_size; ++i)
		{
			arr_1[i] = deck_1.pop().getRank();
			arr_2[i] = deck_2.pop().getRank();
		}
		assertEquals(false, Arrays.equals(arr_1, arr_2));
	}
	
	public void test_Method_Path()
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
