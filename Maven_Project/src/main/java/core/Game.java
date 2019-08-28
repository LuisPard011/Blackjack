package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class Game {
	
	/*********************
	 * CLASS VARIABLE(S) *
	 *********************/
	protected final static int draw_times = 2;
	
	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private Dealer dealer;
	private Guest guest;
	
	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Game() {
		dealer = new Dealer();
		guest = new Guest();
	}
	
	/*************
	 * GETTER(S) *
	 *************/
	public Dealer get_dealer() { return dealer; }
	public Guest get_guest() { return guest; }
	
	/********
	 * ELSE *
	 ********/
	public abstract void play() throws FileNotFoundException, IOException;

}
