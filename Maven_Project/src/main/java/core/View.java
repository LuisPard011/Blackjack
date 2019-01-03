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
	 * Interface output to display hand's score.
	 */
	public static void score(Hand hand) { System.out.println("Score is: " + hand.get_score()); }

	/**
	 * Display specific number of cards in hand.
	 * @param cards number of cards to show
	 */
	public static void cards(int cards, Hand hand) {
		System.out.print("Cards in hand are: ");
		for(int i = 0; i < cards; i++) System.out.print(hand.get(i).toString() + " ");
		System.out.println();
	}

}
