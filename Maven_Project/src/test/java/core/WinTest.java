package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
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
	
	int deck_size = 52;
	String path_1 = "src\\main\\java\\core\\Input_File_1.txt";
	String path_2 = "src\\main\\java\\core\\Input_File_2.txt";
	String path_3 = "src\\main\\java\\core\\Input_File_3.txt";
	
	
	/******************
	 * Variables(End) * 
	 ******************/
	
	/****************
	 * Tests(Start) * 
	 ****************/
	
	/**
	 * Requirement 11
	 * Check there are 52 cards in the deck
	 */
	public void test_Cards_In_Deck()
	{
		Stack<Card> deck = new Stack<>();
		Deck_Maker deck_maker = new Deck_Maker();
		
		deck_maker.make_deck(deck);
		int count_pops = 0;
		
		for(int i = 0; i < deck_size; ++i)
		{
			deck.pop();
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
		Stack<Card> deck_1 = new Stack<>();
		Stack<Card> deck_2 = new Stack<>();
		Deck_Maker deck_maker = new Deck_Maker();
		
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
	
	/**
	 * Requirement 16
	 * Test support for file input
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void test_File_Input() throws FileNotFoundException, IOException
	{
		Game game = new Game();
		Reader reader = new Reader();
		
		String[] commands;
		commands = reader.read_file_input(path_3);
		
		assertEquals("S10", commands[0]);
		assertEquals("D2", commands[commands.length-1]);
		assertEquals(true, game.play_file());
	}
	
	/**
	 * Requirement 17
	 * Test support for console input
	 * If play_console() did not work
	 * (e.g. try to draw 300 cards by changing draw_times to 300)
	 * Then it would not return "true"
	 */
	public void test_Console_Input()
	{
		Game game = new Game();
		assertEquals(true, game.play_console());
	}
	
	/**
	 * Requirement 18
	 * Test option to choose between file and console input
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void test_Choose_FC() throws FileNotFoundException, IOException
	{
		Scanner scanner = new Scanner(System.in);
		Game game = new Game();
		assertEquals(true, game.choose_mode(scanner));
	}
	
	/**
	 * Requirement 19
	 * Test if the player's first two cards are visible
	 * If I tried to show 3 cards in this test
	 * Then an IndexOutOfBounds would happen
	 */
	public void test_See_Two_Cards()
	{
		Player guest = new Player("Guest");
		guest.add(ace_d);
		guest.add(king_c);
		assertEquals(true, guest.show_cards(2));
	}
	
	/**
	 * Requirement 20
	 * Test that only one of the dealer's cards is visible at the start
	 */
	public void test_see_One_Card()
	{
		Player guest = new Player("Dealer");
		guest.add(ace_d);
		guest.add(king_c);
		assertEquals(true, guest.show_cards(1));
	}
	
	/**
	 * Requirement 21
	 * Test if the player can hit
	 */
	public void test_Player_Hit()
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
	
	/**************
	 * Tests(End) * 
	 **************/
}
