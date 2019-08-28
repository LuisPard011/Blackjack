package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Game_Console {
	
	/**
	 * Play using console input.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void play() throws FileNotFoundException, IOException {
		Dealer dealer = new Dealer();
		Deck deck = new Deck();
		Guest guest = new Guest();
		
		View.divider();

		// Guest setup
		guest.hit(deck, Game_Controller.draw_times, guest.get_default_hand());
		View.hand(guest, guest.get_default_hand());

		// Dealer setup
		dealer.hit(deck, Game_Controller.draw_times, dealer.get_default_hand());
		View.dealers_first_card(dealer);

		Winner_Caller.blackjack_win(guest, dealer);

		game:
			if(!guest.get_winner() && !dealer.get_winner()) {
				// Guest's turn
				if(guest.can_split() && View.choose_split(guest)) {
					guest.split_hand();
					No_Name.split_turn(deck, guest);
				}
				else View.hit_or_stand(deck, guest.get_default_hand(), guest);

				if(Winner_Caller.blackjack_win(guest, dealer)) break game;

				// Dealer's turn
				if(!guest.completely_bust()) {
					View.hand(dealer, dealer.get_default_hand());
					if(dealer.can_split() && View.choose_split(dealer)) {
						dealer.split_hand();
						dealer.dealer_turn(deck, dealer.get_split_hand_1());
						dealer.dealer_turn(deck, dealer.get_split_hand_2());
					}
					else dealer.dealer_turn(deck, dealer.get_default_hand());
				}
			}

		// End game
		Winner_Caller.determine_winner(guest, dealer);
	}

}
