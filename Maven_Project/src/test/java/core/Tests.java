package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import junit.framework.TestCase;

public class Tests extends TestCase {
	
	/*********
	 * SETUP * 
	 *********/
	Dealer dealer = new Dealer();
	Deck deck = new Deck();
	Game_Controller game = new Game_Controller();
	Guest guest = new Guest("Guest");
	HashMap<String, Card> cards = new HashMap<>(52);
	
	private void create_cards() {
		for(int i = 0; i < Card.SUITS.length; i++) {
			for(int j = 0; j < Card.RANKS.length; j++) {
				String suit = Card.SUITS[i], rank = Card.RANKS[j];
				cards.put(suit+rank, new Card(suit, rank));
			}}}
	
	public Tests() {create_cards();}

	/*********
	 * TESTS * 
	 *********/
	
	/**
	 * R0-10 do not require tests
	 */
	
	
	/**
	 * R11
	 * Check there are 52 cards in the deck
	 */
	public void test_Cards_In_Deck() {
		int counter = 0;
		
		while(!deck.get_deck().isEmpty()) {
			deck.get_deck().pop();
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
		int[] arr_1  = new int[Deck.deck_size], arr_2 = new int[Deck.deck_size];
		Deck deck_2 = new Deck();

		for(int i = 0; i < Deck.deck_size; ++i) {
			arr_1[i] = deck.get_deck().pop().get_rank();
			arr_2[i] = deck_2.get_deck().pop().get_rank();
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
		Reader reader = new Reader();
		String[] commands_0, commands_1, commands_2, paths = new String[] {
				"src\\main\\java\\documents\\Input_File_1.txt",
				"src\\main\\java\\documents\\Input_File_2.txt",
				"src\\main\\java\\documents\\Input_File_3.txt",
				"src\\main\\java\\documents\\Input_File_4.txt",
				"src\\main\\java\\documents\\Input_File_5.txt"};
		
		commands_0 = reader.read_file_input(paths[0]);
		assertEquals("SK", commands_0[0]);
		assertEquals("CA", commands_0[commands_0.length-1]);

		commands_1 = reader.read_file_input(paths[1]);
		assertEquals("SK", commands_1[0]);
		assertEquals("DJ", commands_1[commands_1.length-1]);

		commands_2 = reader.read_file_input(paths[2]);
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

		guest.get_default_hand().get_cards().add(cards.get("DA"));
		guest.get_default_hand().get_cards().add(cards.get("CK"));

		System.out.println(guest.get_default_hand());
	}

	/**
	 * R20
	 * Test that only one of the dealer's cards is visible at the start
	 */
	public void test_see_One_Card() {
		System.out.println("\nR20 - Test that only one of the dealer's cards is visible at the start");
		View.divider();

		dealer.get_default_hand().get_cards().add(cards.get("S2"));
		dealer.get_default_hand().get_cards().add(cards.get("S9"));

		System.out.println(dealer.get_default_hand());
	}

	/**
	 * R21
	 * Test if the player can hit
	 */
	public void test_Player_Hit() {
		Card temp_card_1 = deck.get_deck().peek();
		guest.hit(deck, 1, guest.get_default_hand());

		assertEquals(temp_card_1.get_rank(), guest.get_default_hand().get_cards().get(0).get_rank());
		assertEquals(temp_card_1.get_suit(), guest.get_default_hand().get_cards().get(0).get_suit());
	}

	/**
	 * R22
	 * Test player can hit repeatedly
	 */
	public void test_Player_Multi_Hits() {
		Card temp_card_1 = deck.get_deck().peek();
		guest.hit(deck, 1, guest.get_default_hand());
		Card temp_card_2 = deck.get_deck().peek();
		guest.hit(deck, 1, guest.get_default_hand());

		assertEquals(temp_card_1.get_rank(), guest.get_default_hand().get_cards().get(0).get_rank());
		assertEquals(temp_card_1.get_suit(), guest.get_default_hand().get_cards().get(0).get_suit());
		assertEquals(temp_card_2.get_rank(), guest.get_default_hand().get_cards().get(1).get_rank());
		assertEquals(temp_card_2.get_suit(), guest.get_default_hand().get_cards().get(1).get_suit());
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

		guest.get_default_hand().get_cards().add(cards.get("DA"));
		guest.get_default_hand().get_cards().add(cards.get("S2"));
		guest.get_default_hand().get_cards().add(cards.get("S6"));

		System.out.println(guest.get_default_hand());
	}

	/**
	 * R25
	 * Player can bust and dealer wins because of it
	 */
	public void test_Player_Bust() {
		System.out.println("\nR25 - Player can bust and dealer wins because of it");
		View.divider();

		guest.get_default_hand().get_cards().add(cards.get("CK"));
		guest.get_default_hand().get_cards().add(cards.get("DK"));
		guest.get_default_hand().get_cards().add(cards.get("SK"));

		dealer.get_default_hand().get_cards().add(cards.get("C8"));

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

		dealer.get_default_hand().get_cards().add(cards.get("DA"));
		dealer.get_default_hand().get_cards().add(cards.get("S2"));
		System.out.println(dealer.get_default_hand());

		dealer.dealer_turn(deck, dealer.get_default_hand());

		assertTrue(dealer.get_default_hand().get_score() >= 16);
	}

	/**
	 * R27
	 * Dealer has soft 17, thus it hits
	 */
	public void test_Soft_17() {
		// Case 1
		guest.get_default_hand().get_cards().add(cards.get("DA"));
		assertFalse(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().add(cards.get("C2"));
		assertFalse(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().add(cards.get("H4"));
		assertTrue(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().clear();

		// Case 2
		guest.get_default_hand().get_cards().add(cards.get("DA"));
		assertFalse(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().add(cards.get("SA"));
		assertFalse(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().add(cards.get("C2"));
		assertFalse(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().add(cards.get("S3"));
		assertTrue(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().clear();

		// Case 3
		guest.get_default_hand().get_cards().add(cards.get("DA"));
		assertFalse(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().add(cards.get("SA"));
		assertFalse(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().add(cards.get("HA"));
		assertFalse(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().add(cards.get("H4"));
		assertTrue(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().clear();

		// Case 4
		guest.get_default_hand().get_cards().add(cards.get("CA"));
		assertFalse(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().add(cards.get("DA"));
		assertFalse(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().add(cards.get("HA"));
		assertFalse(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().add(cards.get("SA"));
		assertFalse(guest.get_default_hand().soft_17());

		guest.get_default_hand().get_cards().add(cards.get("S3"));
		assertTrue(guest.get_default_hand().soft_17());
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

		dealer.get_default_hand().get_cards().add(cards.get("S2"));

		// Draw repeatedly until score >= 16 or soft 17
		dealer.dealer_turn(deck, dealer.get_default_hand()); 

		assertTrue(dealer.get_default_hand().get_cards().size() > 2);
	}

	/**
	 * R29 
	 * Dealer's cards are visible at the end of turn
	 */
	public void test_Display_Dealer_Hand_End() {
		System.out.println("\nR29 - Dealer's cards are visible at the end of turn");
		View.divider();

		dealer.get_default_hand().get_cards().add(cards.get("DA"));
		dealer.get_default_hand().get_cards().add(cards.get("S2"));
		dealer.get_default_hand().get_cards().add(cards.get("S6"));

		System.out.println(dealer.get_default_hand());
	}

	/**
	 * R30
	 * If dealer busts, player wins
	 */
	public void test_Dealer_Bust() {
		System.out.println("\nR30 - If dealer busts, player wins");
		View.divider();

		guest.get_default_hand().get_cards().add(cards.get("D5"));

		dealer.get_default_hand().get_cards().add(cards.get("CK"));
		dealer.get_default_hand().get_cards().add(cards.get("DK"));
		dealer.get_default_hand().get_cards().add(cards.get("SK"));

		game.determine_winner(guest, dealer);

		assertTrue(dealer.get_default_hand().bust());
		assertTrue(guest.get_winner());
	}

	/**
	 * R31
	 * Ace can count as 1
	 */
	public void test_Ace_1() {
		guest.get_default_hand().get_cards().add(cards.get("S2"));
		guest.get_default_hand().get_cards().add(cards.get("CK"));
		guest.get_default_hand().get_cards().add(cards.get("SA"));

		assertEquals(13, guest.get_default_hand().get_score());

		guest.get_default_hand().get_cards().clear();

		guest.get_default_hand().get_cards().add(cards.get("C7"));
		guest.get_default_hand().get_cards().add(cards.get("D5"));
		guest.get_default_hand().get_cards().add(cards.get("H4"));
		guest.get_default_hand().get_cards().add(cards.get("DA"));
		guest.get_default_hand().get_cards().add(cards.get("S6"));

		assertEquals(23, guest.get_default_hand().get_score());

		dealer.get_default_hand().get_cards().add(cards.get("DA"));
		dealer.get_default_hand().get_cards().add(cards.get("S2"));
		dealer.get_default_hand().get_cards().add(cards.get("S3"));
		dealer.get_default_hand().get_cards().add(cards.get("S6"));
		dealer.get_default_hand().get_cards().add(cards.get("DA"));
		dealer.get_default_hand().get_cards().add(cards.get("H10"));
		dealer.get_default_hand().get_cards().add(cards.get("CJ"));
		dealer.get_default_hand().get_cards().add(cards.get("C8"));

		assertEquals(41, dealer.get_default_hand().get_score());
	}

	/**
	 * R32
	 * Ace can count as 11
	 */
	public void test_Ace_11() {
		guest.get_default_hand().get_cards().add(cards.get("S2"));
		guest.get_default_hand().get_cards().add(cards.get("SA"));

		assertEquals(13, guest.get_default_hand().get_score());
	}

	/**
	 * R33
	 * Two aces in hand, one counts as 1 and the other as 11
	 */
	public void test_Aces_1_11() {
		guest.get_default_hand().get_cards().add(cards.get("DA"));
		guest.get_default_hand().get_cards().add(cards.get("SA"));

		assertEquals(12, guest.get_default_hand().get_score());
	}

	/**
	 * R34
	 * One ace can count as 11 and then 1
	 */
	public void test_Aces_11_1() {
		guest.get_default_hand().get_cards().add(cards.get("DA"));
		guest.get_default_hand().get_cards().add(cards.get("S2"));
		assertEquals(13, guest.get_default_hand().get_score());

		guest.get_default_hand().get_cards().add(cards.get("CK"));
		guest.get_default_hand().get_cards().add(cards.get("S6"));
		assertEquals(19, guest.get_default_hand().get_score());
	}

	/**
	 * R35
	 * A hand can count two aces as 1 each
	 */
	public void test_Aces_1_1() {
		guest.get_default_hand().get_cards().add(cards.get("DA"));
		guest.get_default_hand().get_cards().add(cards.get("SA"));
		guest.get_default_hand().get_cards().add(cards.get("DK"));
		guest.get_default_hand().get_cards().add(cards.get("S6"));
		guest.get_default_hand().get_cards().add(cards.get("S9"));

		assertEquals(27, guest.get_default_hand().get_score());
	}

	/**
	 * R36
	 * J, Q and K count as 10
	 */
	public void test_Face_Cards_Values() {
		guest.get_default_hand().get_cards().add(cards.get("CJ"));
		guest.get_default_hand().get_cards().add(cards.get("CQ"));
		guest.get_default_hand().get_cards().add(cards.get("CK"));

		assertEquals(30, guest.get_default_hand().get_score());
	}

	/**
	 * R37
	 * Player's initial blackjack is detected
	 */
	public void test_Player_Blackjack() {
		guest.get_default_hand().get_cards().add(cards.get("DA"));
		guest.get_default_hand().get_cards().add(cards.get("CJ"));
		assertEquals(true, guest.get_default_hand().has_blackjack());
		guest.get_default_hand().get_cards().clear();

		guest.get_default_hand().get_cards().add(cards.get("CJ"));
		guest.get_default_hand().get_cards().add(cards.get("SA"));
		assertEquals(true, guest.get_default_hand().has_blackjack());
		guest.get_default_hand().get_cards().clear();

		guest.get_default_hand().get_cards().add(cards.get("SA"));
		guest.get_default_hand().get_cards().add(cards.get("CQ"));
		assertEquals(true, guest.get_default_hand().has_blackjack());
		guest.get_default_hand().get_cards().clear();

		guest.get_default_hand().get_cards().add(cards.get("CQ"));
		guest.get_default_hand().get_cards().add(cards.get("DA"));
		assertEquals(true, guest.get_default_hand().has_blackjack());
		guest.get_default_hand().get_cards().clear();

		guest.get_default_hand().get_cards().add(cards.get("SA"));
		guest.get_default_hand().get_cards().add(cards.get("CK"));
		assertEquals(true, guest.get_default_hand().has_blackjack());
		guest.get_default_hand().get_cards().clear();

		guest.get_default_hand().get_cards().add(cards.get("DK"));
		guest.get_default_hand().get_cards().add(cards.get("DA"));
		assertEquals(true, guest.get_default_hand().has_blackjack());
		guest.get_default_hand().get_cards().clear();

		guest.get_default_hand().get_cards().add(cards.get("SA"));
		guest.get_default_hand().get_cards().add(cards.get("H10"));
		assertEquals(true, guest.get_default_hand().has_blackjack());
		guest.get_default_hand().get_cards().clear();
	}

	/**
	 * R38
	 * Dealer's initial blackjack is detected
	 */
	public void test_Dealer_Blackjack() {
		dealer.get_default_hand().get_cards().add(cards.get("DA"));
		dealer.get_default_hand().get_cards().add(cards.get("CJ"));
		assertEquals(true, dealer.get_default_hand().has_blackjack());
		dealer.get_default_hand().get_cards().clear();

		dealer.get_default_hand().get_cards().add(cards.get("CJ"));
		dealer.get_default_hand().get_cards().add(cards.get("SA"));
		assertEquals(true, dealer.get_default_hand().has_blackjack());
		dealer.get_default_hand().get_cards().clear();

		dealer.get_default_hand().get_cards().add(cards.get("SA"));
		dealer.get_default_hand().get_cards().add(cards.get("CQ"));
		assertEquals(true, dealer.get_default_hand().has_blackjack());
		dealer.get_default_hand().get_cards().clear();

		dealer.get_default_hand().get_cards().add(cards.get("CQ"));
		dealer.get_default_hand().get_cards().add(cards.get("DA"));
		assertEquals(true, dealer.get_default_hand().has_blackjack());
		dealer.get_default_hand().get_cards().clear();

		dealer.get_default_hand().get_cards().add(cards.get("SA"));
		dealer.get_default_hand().get_cards().add(cards.get("CK"));
		assertEquals(true, dealer.get_default_hand().has_blackjack());
		dealer.get_default_hand().get_cards().clear();

		dealer.get_default_hand().get_cards().add(cards.get("DK"));
		dealer.get_default_hand().get_cards().add(cards.get("DA"));
		assertEquals(true, dealer.get_default_hand().has_blackjack());
		dealer.get_default_hand().get_cards().clear();

		dealer.get_default_hand().get_cards().add(cards.get("SA"));
		dealer.get_default_hand().get_cards().add(cards.get("H10"));
		assertEquals(true, dealer.get_default_hand().has_blackjack());
		dealer.get_default_hand().get_cards().clear();
	}

	/**
	 * R39
	 * If player has Blackjack and the dealer doesn't, player wins
	 */
	public void test_Player_Blackjack_Win() {
		System.out.println("\nR39 - If player has Blackjack and the dealer doesn't, player wins");
		View.divider();

		guest.get_default_hand().get_cards().add(cards.get("DA"));
		guest.get_default_hand().get_cards().add(cards.get("S2"));
		guest.get_default_hand().get_cards().add(cards.get("H10"));

		dealer.get_default_hand().get_cards().add(cards.get("S6"));
		dealer.get_default_hand().get_cards().add(cards.get("S9"));

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

		guest.get_default_hand().get_cards().add(cards.get("DA"));
		guest.get_default_hand().get_cards().add(cards.get("S9"));

		dealer.get_default_hand().get_cards().add(cards.get("SA")); 	// ace
		dealer.get_default_hand().get_cards().add(cards.get("S6"));
		dealer.get_default_hand().get_cards().add(cards.get("H10")); 	// ten

		game.blackjack_win(guest, dealer);
		assertTrue(dealer.get_winner());
		dealer.get_default_hand().get_cards().clear();

		guest.get_default_hand().get_cards().add(cards.get("S6"));
		guest.get_default_hand().get_cards().add(cards.get("DA"));
		guest.get_default_hand().get_cards().add(cards.get("CJ"));

		dealer.get_default_hand().get_cards().add(cards.get("H10"));	// ten
		dealer.get_default_hand().get_cards().add(cards.get("DA"));	// ace
		dealer.get_default_hand().get_cards().add(cards.get("S9"));

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

		guest.get_default_hand().get_cards().add(cards.get("CK"));
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

		dealer.get_default_hand().get_cards().add(cards.get("D5"));
		System.out.println(dealer.get_default_hand());

		assertEquals(5, dealer.get_default_hand().get_score());
		dealer.get_default_hand().get_cards().clear();

		dealer.get_default_hand().get_cards().add(cards.get("C8"));
		dealer.get_default_hand().get_cards().add(cards.get("DA"));
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

		guest.get_default_hand().get_cards().add(cards.get("CJ"));
		guest.get_default_hand().get_cards().add(cards.get("DK"));

		dealer.get_default_hand().get_cards().add(cards.get("CK"));
		dealer.get_default_hand().get_cards().add(cards.get("S6"));

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
		guest.get_default_hand().get_cards().add(cards.get("S2"));

		dealer.get_default_hand().get_cards().add(cards.get("CK"));
		dealer.get_default_hand().get_cards().add(cards.get("S6"));

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
	//		guest.get_default_hand().get_cards().add(cards.get("C2"));
	//		guest.get_default_hand().get_cards().add(cards.get("S2"));
	//
	//		dealer.get_default_hand().get_cards().add(cards.get("D5"));
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
	//		dealer.get_default_hand().get_cards().add(cards.get("S9"));
	//		dealer.get_default_hand().get_cards().add(cards.get("C9"));
	//
	//		guest.get_default_hand().get_cards().add(cards.get("C8"));
	//		guest.get_default_hand().get_cards().add(cards.get("C2"));
	//
	//		deck_maker.make_deck(deck_1);
	//
	//		if(game.choose_split(dealer)) {
	//			dealer.split_hand();
	//			dealer.dealer_turn(deck_1, dealer.get_split_hand_1());
	//			dealer.dealer_turn(deck_1, dealer.get_split_hand_2());
	//		}
	//
	//		game.determine_winner(guest, dealer);
	//	}

}
