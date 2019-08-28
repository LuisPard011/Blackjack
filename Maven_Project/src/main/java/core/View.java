package core;

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

}
