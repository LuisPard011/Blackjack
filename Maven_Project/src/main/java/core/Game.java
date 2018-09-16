package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game
{	
	public static void play_console()
	{
		Deck deck = new Deck();
		deck.populate_deck();
		deck.shuffle_deck();
		deck.populate_draw_pile();
		int draw_times = 2;
		
		Player guest = new Player();
		for(int i = 0; i < draw_times; ++i) {guest.hit(deck.draw_pile);}
		guest.show_hand("Player");
		
		Dealer dealer = new Dealer();
		for(int i = 0; i < draw_times; ++i) {dealer.hit(deck.draw_pile);}
		dealer.show_one_card();
		
		/*
		 * Check if player is bust
		 * If not, give option to hit or stand
		 */
		Scanner reader = new Scanner(System.in);
		while(!guest.is_bust() && !guest.stand){guest.hit_or_stand(reader, deck);}
		
		/*
		 * If bust, the dealer wins and game ends
		 * Option to play again is offered
		 */
		if(guest.is_bust())
		{
			System.out.println("Player busted, dealer wins");
			continue_play(reader);
			reader.close();
			return;
		}
		
		/*
		 * Dealer's turn
		 */
		dealer.show_hand("Dealer");
		dealer.dealer_hit(deck.draw_pile);
		dealer.show_hand("Dealer");
		
		/*
		 * If dealer busts, player wins and game ends
		 * Option to play again is offered
		 */
		if(dealer.is_bust())
		{
			System.out.println("Dealer busted, player wins");
			continue_play(reader);
			reader.close();
			return;
		}
		
		/*
		 * If neither the player nor the dealer busts
		 * Then, scores are compared to determine winner
		 */
		if(guest.score > dealer.score)
		{
			System.out.println("Player wins");
		}
		else if(guest.score == dealer.score)
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
		
		for(int i = 0; i < 2; ++i)
		{
			Card input_card = new Card(Character.toString(arr[i].charAt(0)), Character.toString(arr[i].charAt(1)));
			player.add(input_card);
		}
		player.show_hand("Player");
		player.show_count("Player");
		
		for(int i = 2; i < 4; ++i)
		{
			Card input_card = new Card(Character.toString(arr[i].charAt(0)), Character.toString(arr[i].charAt(1)));
			dealer.add(input_card);
		}
		dealer.show_hand("Dealer");
		dealer.show_count("Dealer");
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
//		Player test = new Player();
//		test.cards_on_table.add(new Card("S", "K"));
	}
}
