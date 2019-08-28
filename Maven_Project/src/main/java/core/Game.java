package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Game {
	
	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	protected final static int draw_times = 2;

	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private Guest guest;
	private Dealer dealer;

	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Game() {
		guest = new Guest();
		dealer = new Dealer();
	}
	
	/*************
	 * GETTER(S) *
	 *************/
	public Guest get_guest() { return guest; }
	public Dealer get_dealer() { return dealer; }
	
	/********
	 * ELSE *
	 ********/
	public void play() throws FileNotFoundException, IOException {}

}
