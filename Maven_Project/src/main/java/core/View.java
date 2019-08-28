package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class View {

	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	public static Scanner scanner = new Scanner(System.in);

	/********
	 * ELSE *
	 ********/
	/**
	 * Divider.
	 */
	public static void divider(){ System.out.println("=== === === === === === === === ==="); }

	/**
	 * Welcome message.
	 */
	public static void welcome() {
		System.out.println("Hello, welcome to the Blackjack table");
		divider();
	}

	/**
	 * Invalid input message.
	 */
	public static void inavlid_input() { System.out.println("Invalid input"); }

	/**
	 * Show hand and who it belongs to.
	 * @param guest_or_dealer whose hand will be shown
	 * @param hand to be shown
	 */
	public static void hand(Player guest_or_dealer, Hand hand) {
		if(guest_or_dealer instanceof Guest) System.out.println("Guest: " + hand);
		else if(guest_or_dealer instanceof Dealer) System.out.println("Dealer: "  + hand);
	}
	
	/**
	 * Winner console message.
	 * @param winner player
	 * @param loser player
	 * @param blackjack true if win by blackjack
	 * @param bust true if any player busts
	 */
	public static void winner_loser(Player winner, Player loser, boolean blackjack, boolean bust) {
		if(blackjack) {
			if(winner instanceof Guest) {
				System.out.println("Guest has blackjack, but dealer does not\nWinner: Guest");
			}
			else System.out.println("Dealer has blackjack\nWinner: Dealer");
		}
		else if(bust) {
			if(winner instanceof Guest) System.out.println("Dealer busts\nWinner: Guest");
			else System.out.println("Guest busts\nWinner: Dealer");
		}
		else {
			if(winner instanceof Guest) System.out.println("Winner: Guest");
			else System.out.println("Winner: Dealer");
		}
	}
	
	/***********
	* BOOLEANS *
	 ***********/
	
	/**
	 * Give guest or dealer the option to split hand.
	 * @return true if split has been chosen
	 */
	public static boolean choose_split(Player guest_or_dealer) {
		String choose_split;

		System.out.println("Would you like to split? (y/n): ");
		choose_split = View.scanner.next();

		if(choose_split.equalsIgnoreCase("y")) {
			guest_or_dealer.set_splitted(true);
			return true;
		}
		else return false;
	}
	
	/**
	 * Give guest the option to either hit or stand.
	 * @param deck to draw from
	 * @param hand to add card drawn
	 * @param guest who will choose to hit or stand
	 * @return true if stand is chosen
	 */
	public static boolean hit_or_stand(Deck deck, Hand hand, Guest guest) {
		// Local variables
		boolean stand = false;

		while(!hand.bust() && !stand && !hand.has_blackjack()) {
			View.hand(guest, hand);
			System.out.print("Hit or stand? (h/s): ");
			String hit_or_stand = View.scanner.next();

			// control structure for hit or stand
			switch(hit_or_stand) {
			case "h":
				guest.hit(deck, 1, hand);
				break;
			case "s":
				stand = true;	
				break;
			default:
				View.inavlid_input();
				hit_or_stand(deck, hand, guest);
				break;
			}
		}

		return stand;
	}
	
	/**
	 * User menu to decide whether or not to continue playing.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static boolean continue_playing() throws FileNotFoundException, IOException {
		View.divider();
		System.out.print("Continue playing? (y/n): ");
		String continue_play = View.scanner.next();

		switch(continue_play) {
		case "y":
			return true;
		case "n":
			System.out.println("Thanks for playing");
			break;
		default:
			View.inavlid_input();
			continue_playing();
			break;
		}
		
		return false;
	}

}
