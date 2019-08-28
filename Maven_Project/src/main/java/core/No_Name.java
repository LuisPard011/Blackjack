package core;

public class No_Name {
	
	/**
	 * Routine for a turn after splitting initial hand.
	 * @param deck to draw from
	 * @param guest whose turn it is
	 */
	public static void split_turn(Deck deck, Guest guest) {
		guest.hit(deck, 1, guest.get_split_hand_1());
		View.hit_or_stand(deck, guest.get_split_hand_1(), guest);

		guest.hit(deck, 1, guest.get_split_hand_2());
		View.hit_or_stand(deck, guest.get_split_hand_2(), guest);
	}

}
