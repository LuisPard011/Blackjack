package core;

import java.util.Scanner;

public class Game
{	
	/*
	 * Collect cards and shuffle deck
	 * Deal cards
	 * Player turn
	 * Dealer's turn
	 * When win/lose give option to play again
	 * If play again == y, recursive call to play()
	 * Else, return
	 */
	public static void play()
	{
		Deck deck = new Deck();
		deck.populate_deck();
		deck.shuffle_deck();
		deck.populate_draw_pile();
		int draw_times = 2;
		
		Player guest = new Player();
		for(int i = 0; i < draw_times; ++i) {guest.hit(deck.draw_pile);}
		guest.show_cards();
		
		Dealer dealer = new Dealer();
		for(int i = 0; i < draw_times; ++i) {dealer.hit(deck.draw_pile);}
		dealer.show_one_card();
		
		Scanner reader = new Scanner(System.in);
		hit_or_stand(reader, guest, deck);
		
		//hit again
		
		// Dealer's turn
//		dealer.show_cards();
		
		/* 
		 * Have to use scanner as an argument
		 * Else continue_play function crashes 
		 */
		continue_play(reader);
		reader.close();
	}
	
	public static void hit_or_stand(Scanner reader, Player player, Deck deck)
	{
		System.out.print("Hit or stand? (h/s): ");
		String hit_or_stand = reader.next();
		
		if(hit_or_stand.equalsIgnoreCase("h"))
		{
			player.hit(deck.draw_pile);
			player.show_cards();
		}
		else if(hit_or_stand.equalsIgnoreCase("s"))
		{
			System.out.println("Standing");
			return;
		}
		else
		{
			System.out.println("Invalid input");
			hit_or_stand(reader, player, deck);
		}
	}
	
	public static void continue_play(Scanner reader)
	{
		System.out.print("Continue playing? (y/n): ");
		String continue_play = reader.next();
		
		if(continue_play.equalsIgnoreCase("y")) {play();}
		else if(continue_play.equalsIgnoreCase("n"))
		{
			System.out.println("Thanks for playing");
			return;
		}
		else
		{
			System.out.println("Invalid input");
			continue_play(reader);
		}
		
		return;
	}
		
	public static void main(String[] args)
	{	
		play();
	}
}
