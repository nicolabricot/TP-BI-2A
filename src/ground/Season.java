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
	
	
	public Season(String url) throws IOException {
		this.url = url;
		treatment();
	}
	
	public Season(Season season) {
		for (int i=0; i<season.getEpisodes().size(); i++) {
			data.add(new Episode(season.getEpisodes().get(i)));
		}
	}
	
	protected void initialize() throws IOException {
		URL oracle = new URL(url);
		URLConnection yc = oracle.openConnection();
		html = new BufferedReader(new InputStreamReader(yc.getInputStream()));
	}
	
	protected void analyse() throws IOException {
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
	
	protected void treatment() throws IOException {
		this.initialize();
		this.analyse();
		//System.out.println(data);
	}
	
	protected void printHTML(BufferedReader input) throws IOException {
		String inputLine;
		while ((inputLine = input.readLine()) != null)
			System.out.println(inputLine);
	}
	
	public ArrayList<Episode> getEpisodes() {
		return data;
	}
	
	public Episode getEpisode(int index) {
		return data.get(index);
	}

}
