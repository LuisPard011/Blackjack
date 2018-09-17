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
	Card six_s = new Card("S", "6");
	Card nine_s = new Card("S", "9");
	Card jack_c = new Card("C", "J");
	Card queen_c = new Card("C", "Q");
	Card king_c = new Card("C", "K");
	Card king_d = new Card("D", "K");
	Card king_s = new Card("S", "K");
	
	Player guest = new Player("Guest");
	Player dealer = new Player("Dealer");
	
	Deck_Maker deck_maker = new Deck_Maker();
	Scanner scanner = new Scanner(System.in);
	Game game = new Game();
	Reader reader = new Reader();
	
	Stack<Card> deck_1 = new Stack<Card>();
	Stack<Card> deck_2 = new Stack<Card>();
	
	String[] commands;
	int[] arr_1;
	int[] arr_2;
	Card temp_card_1;
	Card temp_card_2;
	
	int counter = 0;
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
	
//	/**
//	 * R11
//	 * Check there are 52 cards in the deck
//	 */
//	public void test_Cards_In_Deck()
//	{	
//		deck_maker.make_deck(deck_1);
//		
//		for(int i = 0; i < deck_size; ++i)
//		{
//			deck_1.pop();
//			counter += 1;
//		}
//		
//		assertEquals(52, counter);
//	}
//	
//	/**
//	 * R12
//	 * Test shuffling procedure
//	 * If shuffling is turned off in make_deck(Stack<Card>), test fails
//	 * Else it passes
//	 * I use the rank of cards to compare the order of decks
//	 */
//	public void test_Shuffling()
//	{	
//		deck_maker.make_deck(deck_1);
//		deck_maker.make_deck(deck_2);
//		
//		arr_1 = new int[deck_size];
//		arr_2 = new int[deck_size];
//		
//		for(int i = 0; i < deck_size; ++i)
//		{
//			arr_1[i] = deck_1.pop().getRank();
//			arr_2[i] = deck_2.pop().getRank();
//		}
//		
//		assertEquals(false, Arrays.equals(arr_1, arr_2));
//	}
//	
//	/**
//	 * R16
//	 * Test support for file input
//	 * @throws IOException 
//	 * @throws FileNotFoundException 
//	 */
//	public void test_File_Input() throws FileNotFoundException, IOException
//	{
//		System.out.println("\nR16\n==================");
//		commands = reader.read_file_input(path_3);
//		
//		assertEquals("S10", commands[0]);
//		assertEquals("D2", commands[commands.length-1]);
//		assertEquals(true, game.play_file());
//	}
//	
//	/**
//	 * R17
//	 * Test support for console input
//	 * If play_console() did not work
//	 * (e.g. try to draw 300 cards by changing draw_times to 300)
//	 * Then it would not return "true"
//	 */
//	public void test_Console_Input()
//	{
//		System.out.println("\nR17\n==================");
//		assertEquals(true, game.play_console());
//	}
//	
//	/**
//	 * R18
//	 * Test option to choose between file and console input
//	 * @throws IOException 
//	 * @throws FileNotFoundException 
//	 */
//	public void test_Choose_FC() throws FileNotFoundException, IOException
//	{
//		System.out.println("\nR18\n==================");
//		assertEquals(true, game.choose_mode(scanner));
//	}
//	
//	/**
//	 * R19
//	 * Test if the player's first two cards are visible
//	 * If I tried to show 3 cards in this test
//	 * Then an IndexOutOfBounds would happen
//	 */
//	public void test_See_Two_Cards()
//	{
//		System.out.println("\nR19\n==================");
//		guest.add(ace_d);
//		guest.add(king_c);
//		assertEquals(true, guest.show_cards(2));
//	}
//	
//	/**
//	 * R20
//	 * Test that only one of the dealer's cards is visible at the start
//	 */
//	public void test_see_One_Card()
//	{
//		System.out.println("\nR20\n==================");
//		dealer.add(two_s);
//		dealer.add(nine_s);
//		assertEquals(true, dealer.show_cards(1));
//	}
//	
//	/**
//	 * R21
//	 * Test if the player can hit
//	 */
//	public void test_Player_Hit()
//	{
//		deck_maker.make_deck(deck_1);
//		temp_card_1 = deck_1.peek();
//		guest.hit(deck_1, 1);
//		assertEquals(temp_card_1.getRank(), guest.hand.get(0).getRank());
//		assertEquals(temp_card_1.getSuit(), guest.hand.get(0).getSuit());
//	}
//	
//	/**
//	 * R22
//	 * Test player can hit repeatedly
//	 */
//	public void test_Player_Multi_Hits()
//	{
//		deck_maker.make_deck(deck_1);
//		
//		temp_card_1 = deck_1.peek();
//		guest.hit(deck_1, 1);
//		temp_card_2 = deck_1.peek();
//		guest.hit(deck_1, 1);
//		
//		assertEquals(temp_card_1.getRank(), guest.hand.get(0).getRank());
//		assertEquals(temp_card_1.getSuit(), guest.hand.get(0).getSuit());
//		assertEquals(temp_card_2.getRank(), guest.hand.get(1).getRank());
//		assertEquals(temp_card_2.getSuit(), guest.hand.get(1).getSuit());
//	}
//	
//	/**
//	 * R23
//	 * Test player can stand
//	 * If 's' is not chosen
//	 * Then test fails
//	 */
//	public void test_Player_Stand()
//	{
//		System.out.println("\nR23");
//		System.out.println("Choose 's' or test will fail");
//		System.out.println("==================");
//		guest.hit_or_stand(deck_1);
//		assertEquals(true, guest.stand);
//	}
//	
//	/**
//	 * R24
//	 * Hand of the player is displayed at the end of the turn
//	 */
////	public void test_Display_Player_Hand_End()
////	{
////		
////	}
//	
//	/**
//	 * R25
//	 * Player can bust and dealer wins because of it
//	 */
//	public void test_Player_Bust()
//	{
//		System.out.println("\nR25\n==================");
//		guest.score = 22;
//		guest.bust(dealer);
//		assertEquals(true, guest.bust);
//		assertEquals(true, dealer.win);
//	}
//	
//	
//	/**
//	 * R26
//	 * Dealer has <= 16, thus it hits
//	 */
//	public void test_Dealer_16()
//	{
//		System.out.println("\nR26\n==================");
//		dealer.add(ace_d);
//		dealer.add(two_s);
//		deck_maker.make_deck(deck_1);
//		assertEquals(true, dealer.dealer_turn(deck_1, guest));
//	}
//	
//	/**
//	 * R27
//	 * Dealer has soft 17, thus it hits
//	 */
//	public void test_Dealer_Soft_17()
//	{
//		System.out.println("\nR27\n==================");
//		dealer.add(ace_d);
//		dealer.add(six_s);
//		deck_maker.make_deck(deck_1);
//		assertEquals(true, dealer.dealer_turn(deck_1, guest));
//	}
//	
//	/**
//	 * R28
//	 * Dealer can hit repeatedly
//	 */
//	public void test_Dealer_Repeat_Hit()
//	{
//		System.out.println("\nR28\n==================");
//		deck_maker.make_deck(deck_1);
//		assertEquals(true, dealer.dealer_turn(deck_1, guest));
//	}
//	
//	/**
//	 * R29 
//	 * Dealer's cards are visible at the end of its turn
//	 */
////	public void test_Display_Player_Hand_End()
////	{
////		
////	}
//	
//	/**
//	 * R30
//	 * If dealer busts player wins
//	 */
//	public void test_Dealer_Bust()
//	{
//		System.out.println("\nR30\n==================");
//		dealer.score = 22;
//		dealer.bust(guest);
//		assertEquals(true, dealer.bust);
//		assertEquals(true, guest.win);
//	}
//	
//	/**
//	 * R31
//	 * Ace can count as 1
//	 */
//	public void test_Ace_1()
//	{
//		guest.add(two_s);
//		guest.add(king_c);
//		guest.add(ace_s);
//		assertEquals(13, guest.score);
//	}
//
//	/**
//	 * R32
//	 * Ace can count as 11
//	 */
//	public void test_Ace_11()
//	{
//		guest.add(two_s);
//		guest.add(ace_s);
//		assertEquals(13, guest.score);
//	}
//	
//	/**
//	 * R33
//	 * Two aces in hand, one counts as 1 and the other as 11
//	 */
//	public void test_Aces_1_11()
//	{
//		guest.add(ace_d);
//		guest.add(ace_s);
//		assertEquals(12, guest.score);
//	}
//	
//	/**
//	 * R34
//	 * One ace can count as 11 and then 1
//	 */
//	public void test_Aces_11_1()
//	{
//		guest.add(ace_d);
//		guest.add(two_s);
//		assertEquals(13, guest.score);
//		guest.add(king_c);
//		guest.add(six_s);
//		assertEquals(19, guest.score);
//	}
//	
//	/**
//	 * R35
//	 * A hand can count two aces as 1 each
//	 */
//	public void test_Aces_1_1()
//	{
//		guest.add(ace_d);
//		guest.add(ace_s);
//		guest.add(king_d);
//		assertEquals(12, guest.score);
//	}
	
	/**
	 * R36
	 * J, Q and K count as 10
	 */
	public void test_Face_Cards_Values()
	{
		guest.add(jack_c);
		guest.add(queen_c);
		guest.add(king_c);
		assertEquals(30, guest.score);
	}
	
	/**
	 * R37
	 * Player's initial blackjack is detected
	 */
//	public void test_Player_Blackjack()
//	{
//		
//	}
	
	/**
	 * R38
	 * Dealer's initial blackjack is detected
	 */
//	public void test_Dealer_Blackjack()
//	{
//		
//	}
	
	/**
	 * R39
	 * If player has Blackjack and the dealer doesn't, player wins
	 */
//	public void test_Player_Blackjack_Win()
//	{
//		
//	}
	
	/**
	 * R40
	 * If dealer has Blackjack it wins
	 */
//	public void test_Dealer_Blackjack_Win()
//	{
//		
//	}
	
	/**
	 * R41
	 * Player's hand score is displayed and it's correct
	 */
//	public void test_Player_Score()
//	{
//		
//	}
	
	/**
	 * R42
	 * Dealer's hand score is displayed and it's correct
	 */
//	public void test_Dealer_Score()
//	{
//		
//	}
	
	/**
	 * R43
	 * If dealer doesn't bust, player with highest score wins
	 */
//	public void test_Highest_Score_Winner()
//	{
//		
//	}
	
	/**************
	 * Tests(End) * 
	 **************/
}
