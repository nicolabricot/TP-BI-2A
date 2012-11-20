package ground;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import analyzer.MyAnalyzer;

public class Episode {

	protected String title;
	protected int season;
	protected int number;
	protected String resume;
	protected String clearedResume = "";
	protected MyAnalyzer analyzer = new MyAnalyzer();
	protected HashMap<String, Integer> map = new HashMap<String, Integer>();
	
	/**
	 * Constructor by copy
	 * @param e
	 */
	public Episode (Episode e) {
		this.season = e.getSeason();
		this.number = e.getNumber();
		this.title = e.getTitle();
		this.resume = e.getResume();
		this.clearedResume = e.getClearedResume();
	}
	
	/**
	 * Constructor for a episode without resume
	 * @param season
	 * @param number
	 * @param title
	 */
	public Episode (int season, int number, String title) {
		this.season = season;
		this.number = number;
		this.title = title;
	}
	
	/**
	 * Default constructor
	 * @param season
	 * @param number
	 * @param title
	 * @param resume
	 * @throws Exception 
	 */
	public Episode (int season, int number, String title, String resume) throws Exception {
		this.season = season;
		this.number = number;
		this.title = title;
		this.setResume(resume);
	}
	
	/**
	 * Title of the episode
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Get the number of the season
	 * @return
	 */
	public int getSeason() {
		return season;
	}
	
	/**
	 * Get the number of the current episode
	 * @return
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * Get resume of the episode
	 * @return
	 */
	public String getResume() {
		return resume;
	}
	
	/**
	 * Set the resume
	 * @param resume
	 * @throws Exception 
	 */
	public void setResume(String resume) throws Exception {
		this.resume = resume;
		this.clearedResume = this.analyzer.process(resume);
		this.split();
	}
	
	/**
	 * Get the cleared resume
	 * @return
	 */
	public String getClearedResume() {
		return this.clearedResume;
	}

	/**
	 * Split into a HashMap<Word, count of occurrence> for the cleared resume
	 * @return
	 */
	protected void split() {
		StringTokenizer tk = new StringTokenizer(this.clearedResume, " ");
		while (tk.hasMoreElements()) {
			String s = tk.nextToken().toLowerCase();
			if (map.get(s) == null) {
				map.put(s, 1);
			} else {
				map.put(s, map.get(s) + 1);
			}
		}
	}
	
	/**
	 * Get the HashMap<word, number of occurrence> for the cleared resume
	 * @return
	 */
	public HashMap<String, Integer> getMap() {
		return map;
	}
	
	/**
	 * Get the similitude (0-1) between the current episode and an ohter
	 * @param e
	 * @return
	 */
	public double getSimilitude(Episode e) {
		// On commence par faire une liste avec les mots communs
		Set<String> both = new HashSet<String>();
		both.addAll(map.keySet());
		both.retainAll(e.getMap().keySet());
		
		// On peut ensuite calculer la similite avec la jolie formule (merci @gforestier !)
		double sclar = 0, norm1 = 0, norm2 = 0;
		for (String k : both)
			sclar += map.get(k) * e.getMap().get(k);
		for (String k : map.keySet())
			norm1 += map.get(k) * map.get(k);
		for (String k : e.getMap().keySet())
			norm2 += e.getMap().get(k) * e.getMap().get(k);
		
		return sclar / Math.sqrt(norm1 * norm2);
	}
	
	/**
	 * Nicer display of an episode
	 */
	public String toString() {
		return "\n" + "S" + season + "E" + number + "\n" + "title: " + title + "\n" + "resume: " + resume;
	}
}
