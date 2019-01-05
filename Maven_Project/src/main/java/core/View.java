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
		if(guest_or_dealer instanceof Guest) System.out.println(((Guest) guest_or_dealer).get_name() + ": " + hand);
		else if(guest_or_dealer instanceof Dealer) System.out.println("Dealer: "  + hand);
	}

}
