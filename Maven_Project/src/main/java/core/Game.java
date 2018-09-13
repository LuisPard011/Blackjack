package core;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Game
{	
	public static void create_deck(Card[] deck, int index)
	{
		/* create the deck, 1406 */
		for(int r=2; r<=14; r+=1)
		{
			for(int s=0; s<4; s+=1)
			{
				deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
			}
		}
	}
	
	public static void shuffle_deck(Card[] deck)
	{
		/* shuffle the deck, 1406 */
		Random rnd = new Random();
		Card swap;
		for(int i = deck.length-1; i>=0; i=i-1)
		{
			int pos = rnd.nextInt(i+1);
			swap = deck[pos];
			deck[pos] = deck[i];
			deck[i] = swap;
		}
	}
	
	public static void populate_draw_pile(Stack<Card> draw_pile, Card[] deck, int deck_size)
	{
		for(int i = 0; i < deck_size; ++i){draw_pile.push(deck[i]);}
	}
		
	public static void main(String[] args)
	{
		int deck_size = 52; 
		Card[] deck = new Card[deck_size]; //1406
		int index = 0; //1406
		boolean endGame = false; //1406, should change and depend one player win or loss

		create_deck(deck, index);
		shuffle_deck(deck);
		
		Stack<Card> draw_pile = null; //1406
		draw_pile = new Stack<Card>();
		populate_draw_pile(draw_pile, deck, deck_size);
		
		System.out.println("deck is: " + Arrays.toString(Arrays.copyOfRange(deck, 42, deck_size)));
		
//		while(!draw_pile.isEmpty()){System.out.println("Card is " + draw_pile.pop().toString());}
//		while(endGame = false){}
//		for(int i = 0; i < deck_size; ++i){System.out.println("Card " + i + " is " + deck[i]);}
		
		Player guest = new Player(null);
		guest.draw(draw_pile);
		guest.draw(draw_pile);
		guest.show_cards();
		System.out.println("Guest's count is: " + guest.count_cards());
//		System.out.println("Top of draw pile is: " + draw_pile.peek().toString());
		
		Dealer dealer = new Dealer(null);
		dealer.draw(draw_pile);
		dealer.draw(draw_pile);
		dealer.show_cards();
		System.out.println("Dealer's count is: " + dealer.count_cards());
//		System.out.println("Top of draw pile is: " + draw_pile.peek().toString());
	}
}
