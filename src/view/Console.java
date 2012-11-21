package view;

import ground.Season;

import java.util.Scanner;

public class Console
{
	private boolean works;
	private boolean loaded;
	private Scanner scan;
	private String url;
	private Season season;
	
	/**
	 * Create a new console
	 */
	public Console() {
		works = true;
		loaded = false;
		scan = new Scanner(System.in);
		url = "";
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	private void chooseShow() throws Exception {
		System.out.println("\nPlease specify the URL of the TV show you want to compare episodes \n(http://www.imdb.fr/title/tt0460649/episodes?season=1)");
		prompt();
		url = fetch();
		System.out.println("\nLoading...");
		try {
			season = new Season(url);
			System.out.println("\n Ò" + season.getName() + "Ó (season " + season.getSeason() + ") successfull loaded"
					+ "\n There are " + season.getEpisodes().size() + " available episodes");
			loaded = true;
		}
		catch (Exception e) {
			System.out.println("Error: " + e + "\nPlease try again :/");
		}
		
	}
	
	private int chooseEpisode() {
		System.out.println("\nThere are "+ season.getEpisodes().size() + " available episodes. \nPlease choose one.");
		prompt();
		int e = Integer.parseInt(fetch());
		if (e >= 1 && e <= season.getEpisodes().size())
			return e;
		else {
			System.out.println("\nError: wrong number. Please try again.");
			return -1;
		}
	}
	
	private void displayEpisode() {
		int e = chooseEpisode();
		if (e > -1) System.out.println(season.getEpisode(e));
			
	}
	
	private void displaySimilitude() {
		int e = chooseEpisode();
		if (e > -1) season.displaySimilitude(season.getEpisode(e));
	}
	
	private void createHTML() {
		
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
		if (cmd.contentEquals("q") || cmd.contains("Q")) quit();
		else if (cmd.contentEquals("c") || cmd.contains("C")) chooseShow();
		if (loaded) {
			if (cmd.contentEquals("d") || cmd.contains("D")) System.out.println("\n" + season);
			else if (cmd.contentEquals("e") || cmd.contains("E")) displayEpisode();
			else if (cmd.contentEquals("s") || cmd.contains("S")) displaySimilitude();
			else if (cmd.contentEquals("t") || cmd.contains("t")) createHTML();
		}
	}
	
	/**
	 * Display the menu to choose actions to do
	 */
	private void displayMenu() {
		System.out.println("\n-- menu --");
		System.out.println("c > Choose a TV show");
		if(loaded) {
			System.out.println("d > Display all episodes of the season");
			System.out.println("e > Display a specificepisode");
			System.out.println("s > Display the similitude of an episode");
			System.out.println("t > Create an HTML page with tags of episodes");
		}
		System.out.println("q > quit");
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
		System.out.println("#------------------------------------#");
		System.out.println("|              TP BI 2A              |");
		System.out.println("|                                    |");
		System.out.println("|  github.com/nicolabricot/TP-BI-2A  |");
		System.out.println("#------------------------------------#");
		while(works) {
			this.displayMenu();
			prompt();
			decode(fetch());
		}
		System.out.println("#------------------------------------#");
		System.out.println("|         (c) 2012  Devenet          |");
		System.out.println("|                                    |");
		System.out.println("|  github.com/nicolabricot/TP-BI-2A  |");
		System.out.println("#------------------------------------#");
	}
}
