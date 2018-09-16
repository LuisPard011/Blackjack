package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	public static void play_console()
	{
		Deck deck = new Deck();
		deck.populate_deck();
		deck.shuffle_deck();
		deck.populate_draw_pile();
		int draw_times = 2;
		
		Player guest = new Player();
		for(int i = 0; i < draw_times; ++i) {guest.hit(deck.draw_pile);}
		guest.show_cards();
		
		Player dealer = new Player();
		for(int i = 0; i < draw_times; ++i) {dealer.hit(deck.draw_pile);}
		dealer.show_one_card();
		
		// bust check
		// hit again option
		Scanner reader = new Scanner(System.in);
		while(!guest.is_bust() && !guest.stand){hit_or_stand(reader, guest, deck);}
		
		// if bust, the dealer wins and game ends
		if(guest.is_bust())
		{
			System.out.println("Dealer wins");
			continue_play(reader);
			reader.close();
			return;
		}
		
		// Dealer's turn
		dealer.show_cards();
		dealer.dealer_hit(deck.draw_pile);
		dealer.show_cards();
		
		// if bust, player wins and game ends
		// else, player with higher count wins and game ends
		// this means comparing scores
		if(dealer.is_bust())
		{
			System.out.println("Player wins");
			continue_play(reader);
			reader.close();
			return;
		}
		
		if(guest.count > dealer.count)
		{
			System.out.println("Player wins");
		}
		else if(guest.count == dealer.count)
		{
			System.out.println("Player pushes");
		}
		else
		{
			System.out.println("Dealer wins");
		}
		
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
			player.stand = true;
//			player.stand();
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
		
		if(continue_play.equalsIgnoreCase("y")) {play_console();}
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
	
	public static void play_file() throws FileNotFoundException, IOException
	{
		String path = "src\\main\\java\\core\\Input_File_1.txt";
		
		ArrayList<String> words = new ArrayList<>();
		
		/* I got some of this code to read from file from StackOverflow */
		try (BufferedReader br = new BufferedReader(new FileReader(path)))
		{
			   String line = null;
			   while ((line = br.readLine()) != null) {words.add(line);}
		}
		String[] arr = words.get(0).split(" ");
//		System.out.println(arr[0].charAt(0));
		
		Player player = new Player();
		Player dealer = new Player();
		
		// I need to make meaning of characters like S for stand and H for hit
		
		Card first_card = new Card(Character.toString(arr[0].charAt(0)), Character.toString(arr[0].charAt(1)));
		player.cards_on_table.add(first_card);
		player.show_cards();
	}
	
	public static void choose_mode(Scanner reader) throws FileNotFoundException, IOException
	{
		System.out.println("Console or file input? (c/f): ");
		String mode = reader.next();
		
		if(mode.equalsIgnoreCase("c")) {play_console();}
		else if(mode.equalsIgnoreCase("f")) {play_file();}
		else
		{
			System.out.println("Invalid input");
			choose_mode(reader);
		}
	}
		
	public static void main(String[] args) throws FileNotFoundException, IOException
	{	
		Scanner reader = new Scanner(System.in);
		choose_mode(reader);
		reader.close();
	}
}
