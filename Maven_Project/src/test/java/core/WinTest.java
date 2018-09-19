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
	Card three_s = new Card("S", "3");
	Card four_h = new Card("H", "4");
	Card five_d = new Card("D", "5");
	Card six_s = new Card("S", "6");
	Card seven_c = new Card("C", "7");
	Card eight_c = new Card("C", "8");
	Card nine_s = new Card("S", "9");
	Card nine_c = new Card("C", "9");
	Card ten_h = new Card("H", "10");
	Card jack_c = new Card("C", "J");
	Card queen_c = new Card("C", "Q");
	Card king_c = new Card("C", "K");
	Card king_d = new Card("D", "K");
	Card king_s = new Card("S", "K");
	
	Player player_0 = new Player("Player");
	Player player_1 = new Player("Player");
	Player player_2 = new Player("Player");
	Player player_3 = new Player("Player");
	Player player_4 = new Player("Player");
	Player player_5 = new Player("Player");
	Player player_6 = new Player("Player");
	
	Dealer dealer_0 = new Dealer("Dealer");
	Dealer dealer_1 = new Dealer("Dealer");
	Dealer dealer_2 = new Dealer("Dealer");
	Dealer dealer_3 = new Dealer("Dealer");
	Dealer dealer_4 = new Dealer("Dealer");
	Dealer dealer_5 = new Dealer("Dealer");
	Dealer dealer_6 = new Dealer("Dealer");
	
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
	
	/*******************
	 * Functions(Start)* 
	 *******************/
	public void divider(){System.out.println("==========================");}
	/*******************
	 * Functions(End)* 
	 *******************/
	
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
//		while(!deck_1.isEmpty())
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
//		System.out.println("\nR16 - Test support for file input");
//		divider();
//		
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
//		System.out.println("\nR17 - Test support for console input");
//		divider();
//		
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
//		System.out.println("\nR18 - Test option to choose between file and console input");
//		divider();
//		
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
//		System.out.println("\nR19 - Test if the player's first two cards are visible");
//		divider();
//		
//		player_0.add(ace_d);
//		player_0.add(king_c);
//		
//		assertEquals(true, player_0.show_cards(2));
//	}
//	
//	/**
//	 * R20
//	 * Test that only one of the dealer's cards is visible at the start
//	 */
//	public void test_see_One_Card()
//	{
//		System.out.println("\nR20 - Test that only one of the dealer's cards is visible at the start");
//		divider();
//		
//		dealer_0.add(two_s);
//		dealer_0.add(nine_s);
//		
//		assertEquals(true, dealer_0.show_cards(1));
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
//		player_0.hit(deck_1, 1);
//		
//		assertEquals(temp_card_1.getRank(), player_0.hand.get(0).getRank());
//		assertEquals(temp_card_1.getSuit(), player_0.hand.get(0).getSuit());
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
//		player_0.hit(deck_1, 1);
//		temp_card_2 = deck_1.peek();
//		player_0.hit(deck_1, 1);
//		
//		assertEquals(temp_card_1.getRank(), player_0.hand.get(0).getRank());
//		assertEquals(temp_card_1.getSuit(), player_0.hand.get(0).getSuit());
//		assertEquals(temp_card_2.getRank(), player_0.hand.get(1).getRank());
//		assertEquals(temp_card_2.getSuit(), player_0.hand.get(1).getSuit());
//	}
//	
//	/**
//	 * R23
//	 * Test that player can stand
//	 * If 's' is not chosen
//	 * Then test fails
//	 */
//	public void test_Player_Stand()
//	{
//		System.out.println("\nR23 - Test that player can stand. Choose 's' or test will fail");
//		divider();
//		
//		player_0.hit_or_stand(deck_1);
//		
//		assertEquals(true, player_0.stand);
//	}
//	
//	/**
//	 * R24
//	 * Hand of the player is displayed at the end of the turn
//	 */
//	public void test_Display_Player_Hand_End()
//	{
//		System.out.println("\nR24 - Hand of player is displayed at end of turn");
//		divider();
//		
//		player_0.add(ace_d);
//		player_0.add(two_s);
//		player_0.add(six_s);
//		
//		assertEquals(true, player_0.show_cards(player_0.hand.size()));
//	}
//	
//	/**
//	 * R25
//	 * Player can bust and dealer wins because of it
//	 */
//	public void test_Player_Bust()
//	{
//		System.out.println("\nR25 - Player can bust and dealer wins because of it");
//		divider();
//		
//		player_0.score = 22;
//		player_0.bust(dealer_0);
//		
//		assertEquals(true, player_0.bust);
//		assertEquals(true, dealer_0.win);
//	}
//	
//	
//	/**
//	 * R26
//	 * Dealer has <= 16, thus it hits
//	 */
//	public void test_Dealer_16()
//	{
//		System.out.println("\nR26 - Dealer has <= 16, thus it hits");
//		divider();
//		
//		dealer_0.add(ace_d);
//		dealer_0.add(two_s);
//		deck_maker.make_deck(deck_1);
//		
//		assertEquals(false, dealer_0.dealer_turn(deck_1, player_0));
//	}
//	
//	/**
//	 * R27
//	 * Dealer has soft 17, thus it hits
//	 */
//	public void test_Dealer_Soft_17()
//	{
//		System.out.println("\nR27 - Dealer has soft 17, thus it hits");
//		divider();
//		
//		dealer_0.add(ace_d);
//		dealer_0.add(six_s);
//		deck_maker.make_deck(deck_1);
//		
//		assertEquals(false, dealer_0.dealer_turn(deck_1, player_0));
//	}
//	
//	/**
//	 * R28
//	 * Dealer can hit repeatedly
//	 */
//	public void test_Dealer_Repeat_Hit()
//	{
//		System.out.println("\nR28 - Dealer can hit repeatedly");
//		divider();
//		
//		deck_maker.make_deck(deck_1);
//		
//		assertEquals(false, dealer_0.dealer_turn(deck_1, player_0));
//	}
//	
//	/**
//	 * R29 
//	 * Dealer's cards are visible at the end of turn
//	 */
//	public void test_Display_Dealer_Hand_End()
//	{
//		System.out.println("\nR29 - Dealer's cards are visible at the end of turn");
//		divider();
//		
//		dealer_0.add(ace_d);
//		dealer_0.add(two_s);
//		dealer_0.add(six_s);
//		
//		assertEquals(true, dealer_0.show_cards(dealer_0.hand.size()));
//	}
//	
//	/**
//	 * R30
//	 * If dealer busts, player wins
//	 */
//	public void test_Dealer_Bust()
//	{
//		System.out.println("\nR30 - If dealer busts, player wins");
//		divider();
//		
//		dealer_0.score = 22;
//		dealer_0.bust(player_0);
//		
//		assertEquals(true, dealer_0.bust);
//		assertEquals(true, player_0.win);
//	}
//	
//	/**
//	 * R31
//	 * Ace can count as 1
//	 */
//	public void test_Ace_1()
//	{
//		player_0.default_hand.add(two_s);
//		player_0.default_hand.add(king_c);
//		player_0.default_hand.add(ace_s);
//		
//		assertEquals(13, player_0.default_hand.score);
//		
//		player_1.default_hand.add(seven_c);
//		player_1.default_hand.add(five_d);
//		player_1.default_hand.add(four_h);
//		player_1.default_hand.add(ace_d);
//		player_1.default_hand.add(six_s);
//		
//		assertEquals(23, player_1.default_hand.score);
//		
//		dealer_1.default_hand.add(ace_d);
//		dealer_1.default_hand.add(two_s);
//		dealer_1.default_hand.add(three_s);
//		dealer_1.default_hand.add(six_s);
//		dealer_1.default_hand.add(ace_d);
//		dealer_1.default_hand.add(ten_h);
//		dealer_1.default_hand.add(jack_c);
//		dealer_1.default_hand.add(eight_c);
//		
//		assertEquals(41, dealer_1.default_hand.score);
//	}
//
//	/**
//	 * R32
//	 * Ace can count as 11
//	 */
//	public void test_Ace_11()
//	{
//		player_0.default_hand.add(two_s);
//		player_0.default_hand.add(ace_s);
//		
//		assertEquals(13, player_0.default_hand.score);
//	}
//	
//	/**
//	 * R33
//	 * Two aces in hand, one counts as 1 and the other as 11
//	 */
//	public void test_Aces_1_11()
//	{
//		player_0.default_hand.add(ace_s);
////		player_0.default_hand.add(four_h);
//		player_0.default_hand.add(ace_s);
////		player_0.default_hand.add(ace_s);
////		player_0.default_hand.add(ace_s);
//		
//		assertEquals(12, player_0.default_hand.score);
//	}
//	
//	/**
//	 * R34
//	 * One ace can count as 11 and then 1
//	 */
//	public void test_Aces_11_1()
//	{
//		player_0.default_hand.add(ace_d);
//		player_0.default_hand.add(two_s);
//		assertEquals(13, player_0.default_hand.score);
//		
//		player_0.default_hand.add(king_c);
//		player_0.default_hand.add(six_s);
//		assertEquals(19, player_0.default_hand.score);
//	}
//	
//	/**
//	 * R35
//	 * A hand can count two aces as 1 each
//	 */
//	public void test_Aces_1_1()
//	{
//		player_0.default_hand.add(ace_d);
//		player_0.default_hand.add(ace_s);
//		player_0.default_hand.add(king_d);
//		player_0.default_hand.add(six_s);
////		player_0.default_hand.add(jack_c);
//		player_0.default_hand.add(nine_s);
////		player_0.default_hand.add(ace_d);
////		player_0.default_hand.add(ace_s);
//		
//		assertEquals(27, player_0.default_hand.score);
//	}
//	
//	/**
//	 * R36
//	 * J, Q and K count as 10
//	 */
//	public void test_Face_Cards_Values()
//	{
//		player_0.add(jack_c);
//		player_0.add(queen_c);
//		player_0.add(king_c);
//		
//		assertEquals(30, player_0.score);
//	}
//	
//	/**
//	 * R37
//	 * Player's initial blackjack is detected
//	 */
//	public void test_Player_Blackjack()
//	{
//		player_0.default_hand.add(ace_d);
//		player_0.default_hand.add(jack_c);
//		assertEquals(true, player_0.default_hand.blackjack());
//		
//		player_1.default_hand.add(jack_c);
//		player_1.default_hand.add(ace_s);
//		assertEquals(true, player_1.default_hand.blackjack());
//		
//		player_2.default_hand.add(ace_s);
//		player_2.default_hand.add(queen_c);
//		assertEquals(true, player_2.default_hand.blackjack());
//		
//		player_3.default_hand.add(queen_c);
//		player_3.default_hand.add(ace_d);
//		assertEquals(true, player_3.default_hand.blackjack());
//		
//		player_4.default_hand.add(ace_s);
//		player_4.default_hand.add(king_c);
//		assertEquals(true, player_4.default_hand.blackjack());
//		
//		player_5.default_hand.add(king_d);
//		player_5.default_hand.add(ace_d);
//		assertEquals(true, player_5.default_hand.blackjack());
//		
//		player_6.default_hand.add(ace_s);
//		player_6.default_hand.add(ten_h);
//		assertEquals(true, player_6.default_hand.blackjack());
//	}
//	
//	/**
//	 * R38
//	 * Dealer's initial blackjack is detected
//	 */
//	public void test_Dealer_Blackjack()
//	{
//		dealer_0.default_hand.add(ace_d);
//		dealer_0.default_hand.add(jack_c);
//		assertEquals(true, dealer_0.default_hand.blackjack());
//		
//		dealer_1.default_hand.add(jack_c);
//		dealer_1.default_hand.add(ace_s);
//		assertEquals(true, dealer_1.default_hand.blackjack());
//		
//		dealer_2.default_hand.add(ace_s);
//		dealer_2.default_hand.add(queen_c);
//		assertEquals(true, dealer_2.default_hand.blackjack());
//		
//		dealer_3.default_hand.add(queen_c);
//		dealer_3.default_hand.add(ace_d);
//		assertEquals(true, dealer_3.default_hand.blackjack());
//		
//		dealer_4.default_hand.add(ace_s);
//		dealer_4.default_hand.add(king_c);
//		assertEquals(true, dealer_4.default_hand.blackjack());
//		
//		dealer_5.default_hand.add(king_d);
//		dealer_5.default_hand.add(ace_d);
//		assertEquals(true, dealer_5.default_hand.blackjack());
//		
//		dealer_6.default_hand.add(ace_s);
//		dealer_6.default_hand.add(ten_h);
//		assertEquals(true, dealer_6.default_hand.blackjack());
//	}
//	
//	/**
//	 * R39
//	 * If player has Blackjack and the dealer doesn't, player wins
//	 */
//	public void test_Player_Blackjack_Win()
//	{
//		System.out.println("\nR39 - If player has Blackjack and the dealer doesn't, player wins");
//		divider();
//		
//		player_0.default_hand.add(ace_d);
//		player_0.default_hand.add(two_s);
//		player_0.default_hand.add(ten_h);
//		
//		dealer_0.default_hand.add(six_s);
//		dealer_0.default_hand.add(nine_s);
//		
//		player_0.blackjack_Win(dealer_0, player_0.default_hand, dealer_0.default_hand);
//		
//		assertEquals(true, player_0.win);
//		assertEquals(false, dealer_0.win);
//	}
//	
//	/**
//	 * R40
//	 * If dealer has Blackjack it wins
//	 */
//	public void test_Dealer_Blackjack_Win()
//	{
//		System.out.println("\nR40 - If dealer has Blackjack it wins");
//		divider();
//		
//		player_0.default_hand.add(ace_d);
//		player_0.default_hand.add(nine_s);
//		
//		dealer_0.default_hand.add(ace_s);
//		dealer_0.default_hand.add(six_s);
//		dealer_0.default_hand.add(nine_s);
//		dealer_0.default_hand.add(ten_h);
//		
//		player_0.blackjack_Win(dealer_0, player_0.default_hand, dealer_0.default_hand);
//		
//		assertEquals(false, player_0.win);
//		assertEquals(true, dealer_0.win);
//		
//		player_1.default_hand.add(six_s);
//		player_1.default_hand.add(ace_d);
//		player_1.default_hand.add(jack_c);
//		dealer_1.default_hand.add(ten_h);
//		dealer_1.default_hand.add(ace_d);
//		dealer_1.default_hand.add(nine_s);
//		
//		player_1.blackjack_Win(dealer_1, player_1.default_hand, dealer_1.default_hand);
//		
//		assertEquals(false, player_1.win);
//		assertEquals(true, dealer_1.win);
//	}
//	
//	/**
//	 * R41
//	 * Player's hand score is displayed and it's correct
//	 */
//	public void test_Player_Score()
//	{
//		System.out.println("\nR41 - Player's hand score is displayed and it's correct");
//		divider();
//		
//		player_0.add(king_c);
//		
//		assertEquals(true, player_0.show_score());
//	}
//	
//	/**
//	 * R42
//	 * Dealer's hand score is displayed and it's correct
//	 */
//	public void test_Dealer_Score()
//	{
//		System.out.println("\nR42 - Dealer's hand score is displayed and it's correct");
//		divider();
//		
//		dealer_0.add(king_c);
//		
//		assertEquals(true, dealer_0.show_score());
//	}
//	
//	/**
//	 * R44
//	 * If there are no busts and player's score > dealer's, then player wins
//	 */
//	public void test_Highest_Score_Player_Wins()
//	{
//		System.out.println("\nR44 - If there are no busts and player's score > dealer's, then player wins");
//		divider();
//		
//		player_0.add(jack_c);
//		player_0.add(king_d);
//		dealer_0.add(king_c);
//		dealer_0.add(six_s);
//		player_0.determine_winner(dealer_0);
//		
//		assertEquals(true, player_0.win);
//	}
//	
//	/**
//	 * R45
//	 * If there are no busts and player's score is not greater than dealer's
//	 * Dealer wins
//	 */
//	public void test_Highest_Score_Player_Loses()
//	{
//		player_0.add(two_s);
//		dealer_0.add(king_c);
//		dealer_0.add(six_s);
//		player_0.determine_winner(dealer_0);
//		
//		assertEquals(false, player_0.win);
//	}
	
	/**
	 * R47
	 * 
	 * Test support for player splitting
	 */
	public void test_Player_Split()
	{
		player_0.default_hand.add(nine_s);
		player_0.default_hand.add(nine_c);
		
		dealer_0.default_hand.add(king_d);
		dealer_0.default_hand.add(king_s);
		
		deck_maker.make_deck(deck_1);
		
		if(player_0.can_split())
		{
			if(player_0.choose_split())
			{
				player_0.split_hand();
				player_0.split_turn(deck_1);
			}
			else
			{
				player_0.hit_or_stand(deck_1, player_0.default_hand);
			}
		}
		else
		{
			player_0.hit_or_stand(deck_1, player_0.default_hand);
		}
		
		if(dealer_0.can_split())
		{
			if(dealer_0.choose_split())
			{
				dealer_0.split_hand();
				dealer_0.dealer_turn(deck_1, player_0, player_0.default_hand, dealer_0.split_hand_1);
				dealer_0.dealer_turn(deck_1, player_0, player_0.default_hand, dealer_0.split_hand_2);
			}
			else
			{
				dealer_0.dealer_turn(deck_1, player_0, player_0.default_hand, dealer_0.default_hand);
			}
		}
		
		assertEquals(18, player_0.default_hand.score);
	}
	/**************
	 * Tests(End) * 
	 **************/
}
