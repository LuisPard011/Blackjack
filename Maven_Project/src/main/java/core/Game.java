package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Game
{	
	/**
	 * Play in console mode
	 */
	public static void play_console()
	{	
		// Create deck
		Deck_Maker deck_maker = new Deck_Maker();
		Stack<Card> deck = new Stack<Card>();
		deck_maker.make_deck(deck);
		int draw_times = 2;
		
		// Create guest
		Player guest = new Player("Guest");
		for(int i = 0; i < draw_times; ++i) {guest.hit(deck);}
		guest.show_hand();
		
		// Create dealer
		Player dealer = new Player("Dealer");
		for(int i = 0; i < draw_times; ++i) {dealer.hit(deck);}
		dealer.show_one_card();
		
		/*
		 * Check if player is bust
		 * If not, give option to hit or stand
		 */
		Scanner reader = new Scanner(System.in);
		while(!guest.bust() && !guest.stand){guest.hit_or_stand(reader, deck);}
		
		/*
		 * If bust, the dealer wins and game ends
		 * Option to play again is offered
		 */
		if(guest.bust())
		{
			System.out.println("Player busted, dealer wins");
			continue_play(reader);
			reader.close();
			return;
		}
		
		// Dealer's turn
		dealer.dealer_turn(deck);
		
		/*
		 * If dealer busts, player wins and game ends
		 * Option to play again is offered
		 */
		if(dealer.bust())
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
	
	/**
	 * User menu to decide whether or not to continue playing
	 * @param reader
	 */
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
	}
	
	/**
	 * Play in file input mode
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void play_file() throws FileNotFoundException, IOException
	{
		// Path to file
		String path = "src\\main\\java\\core\\Input_File_1.txt";
		
		// Array where cards to be played will be stored
		ArrayList<String> words = new ArrayList<>();
		
		// I got some of this code to read from file from StackOverflow
		try (BufferedReader br = new BufferedReader(new FileReader(path)))
		{
			   String line = null;
			   while ((line = br.readLine()) != null) {words.add(line);}
		}
		String[] arr = words.get(0).split(" ");
//		System.out.println(arr[0].charAt(0));
		
		Player player = new Player("Guest");
		Player dealer = new Player("Dealer");
		
		// Guest's first two cards
		for(int i = 0; i < 2; ++i)
		{
			Card input_card = new Card(Character.toString(arr[i].charAt(0)), Character.toString(arr[i].charAt(1)));
			player.add(input_card);
		}
		player.show_hand();
		player.show_score();
		
		// Dealer's first two cards
		for(int i = 2; i < 4; ++i)
		{
			Card input_card = new Card(Character.toString(arr[i].charAt(0)), Character.toString(arr[i].charAt(1)));
			dealer.add(input_card);
		}
		dealer.show_hand();
		dealer.show_score();
		
		/* Now, 
		 * I need to make meaning of characters
		 * D for split
		 * H for hit
		 * S for stand
		 */
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
