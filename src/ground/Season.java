package ground;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Season {
	
	protected String url;
	protected BufferedReader html;
	protected ArrayList<Episode> data = new ArrayList<Episode>();
	protected int season = 0;
	
	/**
	 * Default constructor
	 * @param url
	 * @throws Exception 
	 */
	public Season(String url) throws Exception {
		this.url = url;
		treatment();
	}
	
	/**
	 * Constructor by copy
	 * @param season
	 */
	public Season(Season season) {
		for (int i=0; i<season.getEpisodes().size(); i++)
			data.add(new Episode(season.getEpisodes().get(i)));
		this.season = season.getSeason();
	}
	
	/**
	 * Get html from the website
	 * @throws IOException
	 */
	protected void initialize() throws IOException {
		URL oracle = new URL(url);
		URLConnection yc = oracle.openConnection();
		html = new BufferedReader(new InputStreamReader(yc.getInputStream()));
	}
	
	/**
	 * Get resume and title foreach episode
	 * @throws Exception 
	 */
	protected void analyse() throws Exception {
		String inputLine;
		int season;
		int episode = 1;
		while ((inputLine = html.readLine()) != null) {
			if (inputLine.contains("episode_top")) {
				inputLine = inputLine.replace("</h3>", "").substring(inputLine.indexOf(";")+1);
				season = Integer.parseInt(inputLine);
				while ((inputLine = html.readLine()) != null) {
					if (inputLine.contains("itemprop=\"name\"")) {
						int index = inputLine.indexOf("=");
						inputLine = inputLine.substring(index+8, inputLine.length()-13);
						data.add(new Episode(season, episode, inputLine));
					}
					if (inputLine.contains("item_description")) {
						inputLine = inputLine.replace("<div class=\"item_description\" itemprop=\"description\">", "").replace("</div>", "").replace("    ",  "");
						data.get(episode-1).setResume(inputLine);
						episode++;
					}
				}
			}
		}
	}
	
	/**
	 * Process to get connection and resumes
	 * @throws Exception 
	 */
	protected void treatment() throws Exception {
		this.initialize();
		this.analyse();
		//System.out.println(data);
		this.season = data.get(0).getSeason();
	}
	
	/*
	protected void printHTML(BufferedReader input) throws IOException {
		String inputLine;
		while ((inputLine = input.readLine()) != null)
			System.out.println(inputLine);
	}
	*/
	
	/**
	 * Get list of Episodes in this Season
	 * @return ArrayList<Episode>
	 */
	public ArrayList<Episode> getEpisodes() {
		return data;
	}	
	/**
	 * Get a specific Episode from a Season
	 * @param index
	 * @return Episode index
	 */
	public Episode getEpisode(int index) {
		return data.get(index-1);
	}
	
	/**
	 * Return the number of the current season
	 * @return
	 */
	public int getSeason() {
		return this.season;
	}
	
	/**
	 * Nicer display of a season
	 */
	public String toString() {
		String tmp = "----------\n" + "Season: " + this.season + "\n----------";
		for (int i=0; i<data.size(); i++)
			tmp += "\nE" + (i+1) + ": " + data.get(i).getTitle() + "\nresume: " + data.get(i).getResume() + "\ncleared: " + data.get(i).getClearedResume() ;
		return tmp;
	}
}
