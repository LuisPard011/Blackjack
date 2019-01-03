package core;

import java.util.Scanner;

public class View {
	
	static Scanner scanner = new Scanner(System.in);
	
	static void divider(){ System.out.println("=== === === === === === === === ==="); }
	
	static void welcome() {
		System.out.println("Hello, welcome to the Blackjack table");
		divider();
	}
	
	/**
	 * Interface output to show hand's score.
	 */
	static void score(Hand hand) { System.out.println("Score is: " + hand.get_score()); }

	/**
	 * Interface output to show specific number of cards in hand.
	 * @param cards number of cards to show
	 */
	static void cards(int cards, Hand hand) {
		System.out.print("Cards in hand are: ");
		for(int i = 0; i < cards; i++) System.out.print(hand.get(i).toString() + " ");
		System.out.println();
	}

}
