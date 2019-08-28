package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Game_Console extends Game {
	
	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private Deck deck;
	
	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Game_Console() {
		super();
		deck = new Deck();
	}
	
	/**
	 * Play using console input.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Override
	public void play() throws FileNotFoundException, IOException {
		View.divider();

		// Guest setup
		get_guest().hit(deck, draw_times, get_guest().get_default_hand());
		View.hand(get_guest(), get_guest().get_default_hand());

		// Dealer setup
		get_dealer().hit(deck, draw_times, get_dealer().get_default_hand());
		System.out.println("Dealer: [" + get_dealer().get_default_hand().get_cards().get(0) + "]");

		blackjack_win(get_guest(), get_dealer());

		game:
			if(!get_guest().get_winner() && !get_dealer().get_winner()) {
				// Guest's turn
				if(get_guest().can_split() && choose_split(get_guest())) {
					get_guest().split_hand();
					split_turn(deck, get_guest());
				}
				else hit_or_stand(deck, get_guest().get_default_hand(), get_guest());

				if(blackjack_win(get_guest(), get_dealer())) break game;

				// Dealer's turn
				if(!get_guest().completely_bust()) {
					View.hand(get_dealer(), get_dealer().get_default_hand());
					if(get_dealer().can_split() && choose_split(get_dealer())) {
						get_dealer().split_hand();
						get_dealer().dealer_turn(deck, get_dealer().get_split_hand_1());
						get_dealer().dealer_turn(deck, get_dealer().get_split_hand_2());
					}
					else get_dealer().dealer_turn(deck, get_dealer().get_default_hand());
				}
			}

		// End game
		determine_winner(get_guest(), get_dealer());
	}

}
