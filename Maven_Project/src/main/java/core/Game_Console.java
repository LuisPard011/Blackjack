package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Game_Console extends Game{
	
	private Deck deck;
	
	public Game_Console() {
		super();
		deck = new Deck();
	}
	
	/**
	 * Play using console input.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void play() throws FileNotFoundException, IOException {
		View.divider();

		// Guest setup
		get_guest().hit(deck, draw_times, get_guest().get_default_hand());
		View.hand(get_guest(), get_guest().get_default_hand());

		// Dealer setup
		get_dealer().hit(deck, draw_times, get_dealer().get_default_hand());
		View.dealers_first_card(get_dealer());

		Winner_Caller.blackjack_win(get_guest(), get_dealer());

		game:
			if(!get_guest().get_winner() && !get_dealer().get_winner()) {
				// Guest's turn
				if(get_guest().can_split() && View.choose_split(get_guest())) {
					get_guest().split_hand();
					Game_Controller.split_turn(deck, get_guest());
				}
				else View.hit_or_stand(deck, get_guest().get_default_hand(), get_guest());

				if(Winner_Caller.blackjack_win(get_guest(), get_dealer())) break game;

				// Dealer's turn
				if(!get_guest().completely_bust()) {
					View.hand(get_dealer(), get_dealer().get_default_hand());
					if(get_dealer().can_split() && View.choose_split(get_dealer())) {
						get_dealer().split_hand();
						get_dealer().dealer_turn(deck, get_dealer().get_split_hand_1());
						get_dealer().dealer_turn(deck, get_dealer().get_split_hand_2());
					}
					else get_dealer().dealer_turn(deck, get_dealer().get_default_hand());
				}
			}

		// End game
		Winner_Caller.determine_winner(get_guest(), get_dealer());
	}

}
