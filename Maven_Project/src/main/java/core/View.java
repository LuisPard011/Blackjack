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
	 * Invalid input message.
	 */
	public static void inavlid_input() { System.out.println("Invalid input"); }

}
