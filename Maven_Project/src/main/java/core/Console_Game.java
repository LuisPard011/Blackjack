package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Console_Game extends Game {
	
	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private Deck deck;
	
	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Console_Game() {
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
		// Local variables
		Guest guest = new Guest();
		Dealer dealer = new Dealer();

		View.divider();

		// Guest setup
		guest.hit(deck, draw_times, guest.get_default_hand());
		View.hand(guest, guest.get_default_hand());

		// Dealer setup
		dealer.hit(deck, draw_times, dealer.get_default_hand());
		System.out.println("Dealer: [" + dealer.get_default_hand().get_cards().get(0) + "]");

		blackjack_win(guest, dealer);

		game:
			if(!guest.get_winner() && !dealer.get_winner()) {
				// Guest's turn
				if(guest.can_split() && choose_split(guest)) {
					guest.split_hand();
					split_turn(deck, guest);
				}
				else hit_or_stand(deck, guest.get_default_hand(), guest);

				if(blackjack_win(guest, dealer)) break game;

				// Dealer's turn
				if(!guest.completely_bust()) {
					View.hand(dealer, dealer.get_default_hand());
					if(dealer.can_split() && choose_split(dealer)) {
						dealer.split_hand();
						dealer.dealer_turn(deck, dealer.get_split_hand_1());
						dealer.dealer_turn(deck, dealer.get_split_hand_2());
					}
					else dealer.dealer_turn(deck, dealer.get_default_hand());
				}
			}

		// End game
		determine_winner(guest, dealer);
	}

}
