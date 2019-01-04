package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;
import junit.framework.TestCase;

public class Tests extends TestCase {	
	/*************
	 * VARIABLES * 
	 *************/
	Card ace_d = new Card("D", "A");
	Card ace_s = new Card("S", "A");
	Card two_c = new Card("C", "2");
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
	
	Guest guest = new Guest("Guest");
	Dealer dealer = new Dealer();
	
	Deck deck_maker = new Deck();
	Game_Controller game = new Game_Controller();
	Reader reader = new Reader();
	
	Stack<Card> deck_1 = new Stack<Card>();
	Stack<Card> deck_2 = new Stack<Card>();
	
	String[] commands_0;
	String[] commands_1;
	String[] commands_2;
	int[] arr_1;
	int[] arr_2;
	Card temp_card_1;
	Card temp_card_2;
	
	int counter = 0;
	
	String path_1 = "src\\main\\java\\text_files\\Input_File_1.txt";
	String path_2 = "src\\main\\java\\text_files\\Input_File_2.txt";
	String path_3 = "src\\main\\java\\text_files\\Input_File_3.txt";

	/*********
	 * TESTS * 
	 *********/
	/**
	 * R11
	 * Check there are 52 cards in the deck
	 */
	public void test_Cards_In_Deck() {	
		deck_maker.make_deck(deck_1);
		
		while(!deck_1.isEmpty()) {
			deck_1.pop();
			counter += 1;
		}
		
		assertEquals(52, counter);
	}
	
	/**
	 * R12
	 * Test shuffling procedure
	 * If shuffling is turned off in make_deck(Stack<Card>), test fails
	 * Else it passes
	 * I use the rank of cards to compare the order of decks
	 */
	public void test_Shuffling() {	
		deck_maker.make_deck(deck_1);
		deck_maker.make_deck(deck_2);
		
		arr_1 = new int[Deck.deck_size];
		arr_2 = new int[Deck.deck_size];
		
		for(int i = 0; i < Deck.deck_size; ++i) {
			arr_1[i] = deck_1.pop().get_rank();
			arr_2[i] = deck_2.pop().get_rank();
		}
		
		assertEquals(false, Arrays.equals(arr_1, arr_2));
	}
	
	/**
	 * R13-15 do not require tests
	 */
	
	/**
	 * R16
	 * Test support for file input
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void test_File_Input() throws FileNotFoundException, IOException {
		commands_0 = reader.read_file_input(path_1);
		assertEquals("SK", commands_0[0]);
		assertEquals("CA", commands_0[commands_0.length-1]);
		
		commands_1 = reader.read_file_input(path_2);
		assertEquals("SK", commands_1[0]);
		assertEquals("DJ", commands_1[commands_1.length-1]);
		
		commands_2 = reader.read_file_input(path_3);
		assertEquals("S10", commands_2[0]);
		assertEquals("D2", commands_2[commands_2.length-1]);
	}
	
	/**
	 * R17
	 * Test support for console input
	 * If play_console() did not work
	 * (e.g. try to draw 300 cards by changing draw_times to 300)
	 * Then it would not return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
//	public void test_Console_Input() throws FileNotFoundException, IOException {
//		System.out.println("\nR17 - Test support for console input");
//		View.divider();
//		game.play_console();
//	}
	
	/**
	 * R18
	 * Test option to choose between file and console input
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
//	public void test_Choose_FC() throws FileNotFoundException, IOException {
//		System.out.println("\nR18 - Test option to choose between file and console input");
//		View.divider();
//		game.choose_mode(View.scanner);
//	}
	
	/**
	 * R19
	 * Test if the player's first two cards are visible
	 * If I tried to show 3 cards in this test
	 * Then an IndexOutOfBounds would happen
	 */
	public void test_See_Two_Cards() {
		System.out.println("\nR19 - Test if the player's first two cards are visible");
		View.divider();
		
		guest.get_default_hand().add(ace_d);
		guest.get_default_hand().add(king_c);
		
		System.out.println(guest.get_default_hand());
	}
	
	/**
	 * R20
	 * Test that only one of the dealer's cards is visible at the start
	 */
	public void test_see_One_Card() {
		System.out.println("\nR20 - Test that only one of the dealer's cards is visible at the start");
		View.divider();
		
		dealer.get_default_hand().add(two_s);
		dealer.get_default_hand().add(nine_s);
		
		System.out.println(dealer.get_default_hand());
	}
	
	/**
	 * R21
	 * Test if the player can hit
	 */
	public void test_Player_Hit() {
		deck_maker.make_deck(deck_1);
		temp_card_1 = deck_1.peek();
		guest.hit(deck_1, 1, guest.get_default_hand());
		
		assertEquals(temp_card_1.get_rank(), guest.get_default_hand().get(0).get_rank());
		assertEquals(temp_card_1.get_suit(), guest.get_default_hand().get(0).get_suit());
	}
	
	/**
	 * R22
	 * Test player can hit repeatedly
	 */
	public void test_Player_Multi_Hits() {
		deck_maker.make_deck(deck_1);
		
		temp_card_1 = deck_1.peek();
		guest.hit(deck_1, 1, guest.get_default_hand());
		temp_card_2 = deck_1.peek();
		guest.hit(deck_1, 1, guest.get_default_hand());
		
		assertEquals(temp_card_1.get_rank(), guest.get_default_hand().get(0).get_rank());
		assertEquals(temp_card_1.get_suit(), guest.get_default_hand().get(0).get_suit());
		assertEquals(temp_card_2.get_rank(), guest.get_default_hand().get(1).get_rank());
		assertEquals(temp_card_2.get_suit(), guest.get_default_hand().get(1).get_suit());
	}
	
	/**
	 * R23
	 * Test that player can stand
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
//	public void test_Player_Stand() throws FileNotFoundException, IOException {
//		System.out.println("\nR23 - Test that player can stand");
//		View.divider();
//		game.play_console();
//	}
	
	/**
	 * R24
	 * Hand of the player is displayed at the end of the turn
	 */
	public void test_Display_Player_Hand_End() {
		System.out.println("\nR24 - Hand of player is displayed at end of turn");
		View.divider();
		
		guest.get_default_hand().add(ace_d);
		guest.get_default_hand().add(two_s);
		guest.get_default_hand().add(six_s);
		
		System.out.println(guest.get_default_hand());
	}
	
	/**
	 * R25
	 * Player can bust and dealer wins because of it
	 */
	public void test_Player_Bust() {
		System.out.println("\nR25 - Player can bust and dealer wins because of it");
		View.divider();
		
		guest.get_default_hand().add(king_c);
		guest.get_default_hand().add(king_d);
		guest.get_default_hand().add(king_s);
		
		dealer.get_default_hand().add(eight_c);
		
		game.determine_winner(guest, dealer);
		assertTrue(dealer.get_winner());
	}
	
	/**
	 * R26
	 * Dealer has <= 16, thus it hits
	 */
	public void test_dealer6() {
		System.out.println("\nR26 - Dealer has <= 16, thus it hits");
		View.divider();
		
		dealer.get_default_hand().add(ace_d);
		dealer.get_default_hand().add(two_s);
		System.out.println(dealer.get_default_hand());
		
		deck_maker.make_deck(deck_1);
		dealer.dealer_turn(deck_1, guest, dealer.get_default_hand());
		
		assertTrue(dealer.get_default_hand().get_score() >= 16);
	}
	
	/**
	 * R27
	 * Dealer has soft 17, thus it hits
	 */
	public void test_Dealer_Soft_17() {
		System.out.println("\nR27 - Dealer has soft 17, thus it hits");
		View.divider();
		
		dealer.get_default_hand().add(ace_d);
		dealer.get_default_hand().add(six_s);
		
		System.out.println(dealer.get_default_hand());
		
		deck_maker.make_deck(deck_1);
		temp_card_1 = deck_1.peek();
		
		dealer.dealer_turn(deck_1, guest, dealer.get_default_hand());
		
		assertFalse(temp_card_1 == deck_1.peek());
	}
	
	/**
	 * R28
	 * Dealer can hit repeatedly
	 * This tests shows that with calling only one function, a dealer can draw more than once in a row
	 * Dealer has a two, if it draws one more card, maximum value of the two cards combined is 13 (2+Ace)
	 * This means dealer must draw again to make it to 16 or soft 17
	 * Thus the number of cards in the hand must be equal or greater 3 (or 0, 1, 2)
	 */
	public void test_Dealer_Repeat_Hit() {
		System.out.println("\nR28 - Dealer can hit repeatedly");
		View.divider();
		
		deck_maker.make_deck(deck_1);
		dealer.get_default_hand().add(two_s);
		
		// Draw repeatedly until score >= 16 or soft 17
		dealer.dealer_turn(deck_1, guest, dealer.get_default_hand()); 
		
		assertTrue(dealer.get_default_hand().size() > 2);
	}
	
	/**
	 * R29 
	 * Dealer's cards are visible at the end of turn
	 */
	public void test_Display_Dealer_Hand_End() {
		System.out.println("\nR29 - Dealer's cards are visible at the end of turn");
		View.divider();
		
		dealer.get_default_hand().add(ace_d);
		dealer.get_default_hand().add(two_s);
		dealer.get_default_hand().add(six_s);
		
		System.out.println(dealer.get_default_hand());
	}
	
	/**
	 * R30
	 * If dealer busts, player wins
	 */
	public void test_Dealer_Bust() {
		System.out.println("\nR30 - If dealer busts, player wins");
		View.divider();
		
		guest.get_default_hand().add(five_d);
		
		dealer.get_default_hand().add(king_c);
		dealer.get_default_hand().add(king_d);
		dealer.get_default_hand().add(king_s);
		
		game.determine_winner(guest, dealer);
		
		assertTrue(dealer.get_default_hand().bust());
		assertTrue(guest.get_winner());
	}
	
	/**
	 * R31
	 * Ace can count as 1
	 */
	public void test_Ace_1() {
		guest.get_default_hand().add(two_s);
		guest.get_default_hand().add(king_c);
		guest.get_default_hand().add(ace_s);
		
		assertEquals(13, guest.get_default_hand().get_score());
		
		guest.get_default_hand().clear();
		
		guest.get_default_hand().add(seven_c);
		guest.get_default_hand().add(five_d);
		guest.get_default_hand().add(four_h);
		guest.get_default_hand().add(ace_d);
		guest.get_default_hand().add(six_s);
		
		assertEquals(23, guest.get_default_hand().get_score());
		
		dealer.get_default_hand().add(ace_d);
		dealer.get_default_hand().add(two_s);
		dealer.get_default_hand().add(three_s);
		dealer.get_default_hand().add(six_s);
		dealer.get_default_hand().add(ace_d);
		dealer.get_default_hand().add(ten_h);
		dealer.get_default_hand().add(jack_c);
		dealer.get_default_hand().add(eight_c);
		
		assertEquals(41, dealer.get_default_hand().get_score());
	}

	/**
	 * R32
	 * Ace can count as 11
	 */
	public void test_Ace_11() {
		guest.get_default_hand().add(two_s);
		guest.get_default_hand().add(ace_s);
		
		assertEquals(13, guest.get_default_hand().get_score());
	}
	
	/**
	 * R33
	 * Two aces in hand, one counts as 1 and the other as 11
	 */
	public void test_Aces_1_11() {
		guest.get_default_hand().add(ace_d);
		guest.get_default_hand().add(ace_s);
		
		assertEquals(12, guest.get_default_hand().get_score());
	}
	
	/**
	 * R34
	 * One ace can count as 11 and then 1
	 */
	public void test_Aces_11_1() {
		guest.get_default_hand().add(ace_d);
		guest.get_default_hand().add(two_s);
		assertEquals(13, guest.get_default_hand().get_score());
		
		guest.get_default_hand().add(king_c);
		guest.get_default_hand().add(six_s);
		assertEquals(19, guest.get_default_hand().get_score());
	}
	
	/**
	 * R35
	 * A hand can count two aces as 1 each
	 */
	public void test_Aces_1_1() {
		guest.get_default_hand().add(ace_d);
		guest.get_default_hand().add(ace_s);
		guest.get_default_hand().add(king_d);
		guest.get_default_hand().add(six_s);
		guest.get_default_hand().add(nine_s);
		
		assertEquals(27, guest.get_default_hand().get_score());
	}
	
	/**
	 * R36
	 * J, Q and K count as 10
	 */
	public void test_Face_Cards_Values() {
		guest.get_default_hand().add(jack_c);
		guest.get_default_hand().add(queen_c);
		guest.get_default_hand().add(king_c);
		
		assertEquals(30, guest.get_default_hand().get_score());
	}
	
	/**
	 * R37
	 * Player's initial blackjack is detected
	 */
	public void test_Player_Blackjack() {
		guest.get_default_hand().add(ace_d);
		guest.get_default_hand().add(jack_c);
		assertEquals(true, guest.get_default_hand().has_blackjack());
		guest.get_default_hand().clear();
		
		guest.get_default_hand().add(jack_c);
		guest.get_default_hand().add(ace_s);
		assertEquals(true, guest.get_default_hand().has_blackjack());
		guest.get_default_hand().clear();
		
		guest.get_default_hand().add(ace_s);
		guest.get_default_hand().add(queen_c);
		assertEquals(true, guest.get_default_hand().has_blackjack());
		guest.get_default_hand().clear();
		
		guest.get_default_hand().add(queen_c);
		guest.get_default_hand().add(ace_d);
		assertEquals(true, guest.get_default_hand().has_blackjack());
		guest.get_default_hand().clear();
		
		guest.get_default_hand().add(ace_s);
		guest.get_default_hand().add(king_c);
		assertEquals(true, guest.get_default_hand().has_blackjack());
		guest.get_default_hand().clear();
		
		guest.get_default_hand().add(king_d);
		guest.get_default_hand().add(ace_d);
		assertEquals(true, guest.get_default_hand().has_blackjack());
		guest.get_default_hand().clear();
		
		guest.get_default_hand().add(ace_s);
		guest.get_default_hand().add(ten_h);
		assertEquals(true, guest.get_default_hand().has_blackjack());
		guest.get_default_hand().clear();
	}
	
	/**
	 * R38
	 * Dealer's initial blackjack is detected
	 */
	public void test_Dealer_Blackjack() {
		dealer.get_default_hand().add(ace_d);
		dealer.get_default_hand().add(jack_c);
		assertEquals(true, dealer.get_default_hand().has_blackjack());
		dealer.get_default_hand().clear();
		
		dealer.get_default_hand().add(jack_c);
		dealer.get_default_hand().add(ace_s);
		assertEquals(true, dealer.get_default_hand().has_blackjack());
		dealer.get_default_hand().clear();
		
		dealer.get_default_hand().add(ace_s);
		dealer.get_default_hand().add(queen_c);
		assertEquals(true, dealer.get_default_hand().has_blackjack());
		dealer.get_default_hand().clear();
		
		dealer.get_default_hand().add(queen_c);
		dealer.get_default_hand().add(ace_d);
		assertEquals(true, dealer.get_default_hand().has_blackjack());
		dealer.get_default_hand().clear();
		
		dealer.get_default_hand().add(ace_s);
		dealer.get_default_hand().add(king_c);
		assertEquals(true, dealer.get_default_hand().has_blackjack());
		dealer.get_default_hand().clear();
		
		dealer.get_default_hand().add(king_d);
		dealer.get_default_hand().add(ace_d);
		assertEquals(true, dealer.get_default_hand().has_blackjack());
		dealer.get_default_hand().clear();
		
		dealer.get_default_hand().add(ace_s);
		dealer.get_default_hand().add(ten_h);
		assertEquals(true, dealer.get_default_hand().has_blackjack());
		dealer.get_default_hand().clear();
	}
	
	/**
	 * R39
	 * If player has Blackjack and the dealer doesn't, player wins
	 */
	public void test_Player_Blackjack_Win() {
		System.out.println("\nR39 - If player has Blackjack and the dealer doesn't, player wins");
		View.divider();
		
		guest.get_default_hand().add(ace_d);
		guest.get_default_hand().add(two_s);
		guest.get_default_hand().add(ten_h);
		
		dealer.get_default_hand().add(six_s);
		dealer.get_default_hand().add(nine_s);
		
		assertTrue(game.blackjack_win(guest, dealer));
		assertTrue(guest.get_winner());
	}
	
	/**
	 * R40
	 * If dealer has Blackjack it wins
	 */
	public void test_Dealer_Blackjack_Win() {
		System.out.println("\nR40 - If dealer has Blackjack it wins");
		View.divider();
		
		guest.get_default_hand().add(ace_d);
		guest.get_default_hand().add(nine_s);
		
		dealer.get_default_hand().add(ace_s); 	// ace
		dealer.get_default_hand().add(six_s);
		dealer.get_default_hand().add(ten_h); 	// ten
		
		game.blackjack_win(guest, dealer);
		assertTrue(dealer.get_winner());
		dealer.get_default_hand().clear();
		
		guest.get_default_hand().add(six_s);
		guest.get_default_hand().add(ace_d);
		guest.get_default_hand().add(jack_c);
		
		dealer.get_default_hand().add(ten_h);	// ten
		dealer.get_default_hand().add(ace_d);	// ace
		dealer.get_default_hand().add(nine_s);
		
		game.blackjack_win(guest, dealer);
		assertTrue(dealer.get_winner());
	}
	
	/**
	 * R41
	 * Player's hand score is displayed and it's correct
	 */
	public void test_Player_Score() {
		System.out.println("\nR41 - Player's hand score is displayed and it's correct");
		View.divider();
		
		guest.get_default_hand().add(king_c);
		System.out.println(guest.get_default_hand());
		assertEquals(10, guest.get_default_hand().get_score());
	}
	
	/**
	 * R42
	 * Dealer's hand score is displayed and it's correct
	 */
	public void test_Dealer_Score() {
		System.out.println("\nR42 - Dealer's hand score is displayed and it's correct");
		View.divider();
		
		dealer.get_default_hand().add(five_d);
		System.out.println(dealer.get_default_hand());
		
		assertEquals(5, dealer.get_default_hand().get_score());
		dealer.get_default_hand().clear();
		
		dealer.get_default_hand().add(eight_c);
		dealer.get_default_hand().add(ace_d);
		System.out.println(dealer.get_default_hand());
		
		assertEquals(19, dealer.get_default_hand().get_score());
	}
	
	/**
	 * R44
	 * If there are no busts and player's score > dealer's, then player wins
	 */
	public void test_Highest_Score_Player_Wins() {
		System.out.println("\nR44 - If there are no busts and player's score > dealer's, then player wins");
		View.divider();
		
		guest.get_default_hand().add(jack_c);
		guest.get_default_hand().add(king_d);
	
		dealer.get_default_hand().add(king_c);
		dealer.get_default_hand().add(six_s);
		
		game.determine_winner(guest, dealer);
		assertTrue(guest.get_winner());
	}
	
	/**
	 * R45
	 * If there are no busts and player's score is not greater than dealer's
	 * Dealer wins
	 */
	public void test_Highest_Score_Player_Loses() {
		System.out.println("\nR45 - If there are no busts and player's score is not greater than dealer's, then dealer wins");
		View.divider();
		guest.get_default_hand().add(two_s);
		
		dealer.get_default_hand().add(king_c);
		dealer.get_default_hand().add(six_s);
		
		game.determine_winner(guest, dealer);
		assertTrue(dealer.get_winner());
	}
	
	/**
	 * R47
	 * Test support for player splitting
	 * Chooses player's best hand and displays it
	 */
//	public void test_Player_Split() {
//		System.out.println("\nR47 - Test player splitting");
//		View.divider();
//		
//		guest.get_default_hand().add(two_c);
//		guest.get_default_hand().add(two_s);
//		
//		dealer.get_default_hand().add(five_d);
//		
//		deck_maker.make_deck(deck_1);
//		
//		if(game.choose_split(guest)) {
//			guest.split_hand();
//			game.split_turn(deck_1, guest);
//		}
//		
//		game.determine_winner(guest, dealer);
//	}
	
	/**
	 * R48
	 * Test support for player splitting
	 */
//	public void test_Dealer_Split() {
//		System.out.println("\nR48 - Test dealer splitting");
//		View.divider();
//		
//		dealer.get_default_hand().add(nine_s);
//		dealer.get_default_hand().add(nine_c);
//		
//		guest.get_default_hand().add(eight_c);
//		guest.get_default_hand().add(two_c);
//		
//		deck_maker.make_deck(deck_1);
//		
//		if(game.choose_split(dealer)) {
//			dealer.split_hand();
//			dealer.dealer_turn(deck_1, guest, dealer.get_split_hand_1());
//			dealer.dealer_turn(deck_1, guest, dealer.get_split_hand_2());
//		}
//		
//		game.determine_winner(guest, dealer);
//	}

}
