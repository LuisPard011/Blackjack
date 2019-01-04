package core;

public class Guest extends Player{
	
	/************************
	 * INSTANCE VARIABLE(S) *
	 ************************/
	private String name;
	
	/******************
	 * CONSTRUCTOR(S) *
	 ******************/
	public Guest(String name) { this.name = name; }
	
	/*************
	 * GETTER(S) *
	 *************/
	public String get_name() { return name; }

}
