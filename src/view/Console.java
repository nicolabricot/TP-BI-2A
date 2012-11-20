package view;

import ground.Season;

import java.util.Scanner;

public class Console
{
	private boolean works;
	private Scanner scan;
	private String url;
	private Season season;
	
	/**
	 * Create a new console
	 */
	public Console() {
		works = true;
		scan = new Scanner(System.in);
		url = "";
	}
	
	/**
	 * Bye bye
	 */
	private void quit() {
		works = false;
		System.out.println("\nBye bye... !");
	}
	
	
	/**
	 * Get what it's typing
	 * @return
	 */
	private String fetch() {
		return scan.next();
	}
	
	/**
	 * Process the executive command
	 * @param cmd
	 * @throws Exception 
	 */
	private void decode(String cmd) throws Exception {
		if (cmd.contentEquals("q")) quit();
		else if (cmd.contentEquals("tv")) chooseShow();
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	private void chooseShow() throws Exception {
		System.out.println("Please specify the URL of the TV show you want to compare episodes");
		prompt();
		url = fetch();
		season = new Season(url);
	}
	
	/**
	 * Display the menu to choose actions to do
	 */
	private void displayMenu() {
		System.out.println("");
		System.out.println("tv > choose a TV show");
		System.out.println("q > Quit");
	}
	
	/**
	 * Display the prompt symbol
	 */
	private void prompt() {
		System.out.print("-> ");
	}
	
	/**
	 * Let's play :)
	 * @throws Exception 
	 */
	public void play() throws Exception {
		System.out.println("#----------------------#");
		System.out.println("|      TP BI 2A        |");
		System.out.println("#----------------------#");
		while(works) {
			this.displayMenu();
			prompt();
			decode(fetch());
		}
		System.out.println("------------------------");
		System.out.println("    (c) 2012  Devenet   ");
		
	}
}
