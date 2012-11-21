package view;

import ground.Season;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Scanner;

public class Console
{
	private boolean works;
	private boolean loaded;
	private boolean htmlFile;
	private Scanner scan;
	private String url;
	private Season season;
	
	/**
	 * Create a new console
	 */
	public Console() {
		works = true;
		loaded = false;
		htmlFile = false;
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
		try {
			if (! new File("html").exists()) new File("html").mkdir();
			
			String path = "html/" + season.getName().replace(" ", "") + "-S" + season.getSeason()  + ".html";
			String content = "";
			
			FileWriter file = new FileWriter(path, false);
			BufferedWriter stream = new BufferedWriter(file);
			headerHTML(stream);
			
			stream.write("<p class='nav'>Available episodes: ");
			for (int i=0; i<season.getEpisodes().size(); i++)
				content += "<a href='#e" + (i+1) + "'>" + (i+1) + "</a> | ";
			stream.write(content.substring(0, content.length()-2) + "</p>");
			content = "";
			for (int i=0; i<season.getEpisodes().size(); i++) {
				content =
					"<div class='episode' id='e" + (i+1) + "'>" +
					"<h3>Episode " + season.getEpisode(i+1).getNumber() + ": " + season.getEpisode(i+1).getTitle() + " <a href='#'>&uarr;</a></h3>" +
					"<p>" + season.getEpisode(i+1).getResume() + "</p>" +
					"<ul class='tags'>";
				stream.write(content);
				content = "";
				for (Entry<String, Integer> data : season.getEpisode(i+1).getMap().entrySet())
					content += "<li class='tag-" + data.getValue() + "'>" + data.getKey() + "</li>";
				stream.write(content);
				content =
					"</ul>" +
					"</div>";
				stream.write(content);
			}
			
			footerHTML(stream);
			stream.flush();
			stream.close();
			file.close();
			
			htmlFile = true;
			
			System.out.println("\nThe html page was successful created :");
			System.out.println("file://" + System.getProperty("user.dir") + "/" + path);
			
		}
		catch(IOException e){
			System.out.print("Error: ");
			e.printStackTrace();
		}
	}
	
	private void headerHTML(BufferedWriter stream) throws IOException {
		String html = 
			"<!DOCTYPE html>" +
			"<html lang=\"en\">" +
			"<head>" +
			"<meta charset=\"utf-8\" />" +
			"<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"//devenet.info/favicon.ico\" />" +
			"<link rel=\"icon\" type=\"image/png\" href=\"//devenet.info/favicon.png\" />" +
			"<title>" + season.getName() + "</title>";
		stream.write(html);
		html =
			"<style>" +
			"body {margin: 0 auto; font-family: 'Helvetica Neue', Helvatica, Arial, Ubuntu, sans-serif; font-size: 16px; font-weight: 300;}";
		stream.write(html);
		stream.write("#header, #footer {margin: 0 auto; padding: 10px; background: #333 url(data:image/png;base64,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);}");
		html =
			"a {color: #5DB1FF; border: none; text-decoration: none;}" +
			"a:hover {text-decoration: underline;}" +
			"h1, h2, #footer p {width: 800px; margin: auto; color: #fff;}" +
			"h1 {margin-bottom: 0;}" +
			"h2 {margin-top: 0; font-weight: 300;}" +
			"#content {width: 800px; margin: 0 auto;}" +
			"p.nav {color: #333; border-bottom: 1px solid #999; padding-bottom: 5px; margin-bottom: 30px;}" +
			".episode {border-bottom: 1px solid #333; padding-bottom: 20px; margin-bottom: 30px;}" +
			".episode:last-child {border: none;}" +
			".episode h3 a {font-weight: normal; vertical-align: top; display: block; margin-left: -20px; margin-top: -4px; float: left;}" +
			".episode p {text-align: justify; font-style: italic;}" +
			"ul.tags {list-style-type: none; margin: auto 20px; padding: 4px; line-height: 2em;}" +
			"ul.tags li {display: inline-block; margin: 0 5px 0 0; vertical-align: middle;}" +
			"ul.tags li:last-child {margin-right: 0;}" +
			".tag-1 {font-size: 1.0em;}" +
			".tag-2 {font-size: 1.4em;}" +
			".tag-3 {font-size: 1.8em;}" +
			".tag-4Ê{font-size: 2.2em;}" +
			".tag-5 {font-size: 2.6em;}" +
			".tag-6 {font-size: 3.0em;}" +
			".tag-7 {font-size: 3.4em;}" +
			".tag-8 {font-size: 3.8em;}" +
			".tag-9 {font-size: 4.2em;}" +
			".tag-10 {font-size: 4.6em;}" +
			"</style>";
		stream.write(html);
		html =
			"</head>" +
			"<body>" +
			"<div id='header'>" +
			"<h1><a href='" + url + "'>" + season.getName() + " &rarr;</a></h1>" +
			"<h2>Season " + season.getSeason() + "</h2>" +
			"</div>" +
			"<div id='content'>";
		stream.write(html);
	}
	
	private void footerHTML(BufferedWriter stream) throws IOException {
		stream.write("</div><div id='footer'><p>2012 &mdash; Nicolas Devenet &mdash; <a href='https://github.com/nicolabricot/TP-BI-2A'>TP-BI-2A &rarr;</a></p></div></body></html>");
	}
	
	private void removeHTML() {
		if (htmlFile) {
			File f = new File("html");
			f.deleteOnExit();
		}
		System.out.println("\nHtml files cleaned!");
		htmlFile = false;
	}
	
	/**
	 * Bye bye
	 */
	private void quit() {
		works = false;
		System.out.println("\nClean the html files and delete the direcorty html?\ny > yes");
		prompt();
		if(fetch().contentEquals("y")) removeHTML();
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
			else if (cmd.contentEquals("h") || cmd.contains("h")) createHTML();
		}
	}
	
	/**
	 * Display the menu to choose actions to do
	 */
	private void displayMenu() {
		System.out.println("\n-- menu --");
		if (!loaded) System.out.println("c > Choose a TV show");
		if (loaded) {
			System.out.println("c > Choose an other TV show");
			System.out.println("d > Display all episodes of the season");
			System.out.println("e > Display a specificepisode");
			System.out.println("s > Display the similitude of an episode");
			System.out.println("h > Create an html page with tags for each episode");
		}
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
