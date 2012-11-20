package ground;

import analyzer.MyAnalyzer;

public class Episode {

	protected String title;
	protected int season;
	protected int number;
	protected String resume;
	protected String clearedResume = "";
	MyAnalyzer analyzer = new MyAnalyzer();
	
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
	}
	
	/**
	 * Get the cleared resume
	 * @return
	 */
	public String getClearedResume() {
		return this.clearedResume;
	}

	public void getSimilitude(Episode e) {
		if (e.equals(this)) {
			System.out.println("Similitude de 100%");
		}
		else {
			
		}
	}
	
	/**
	 * Nicer display of a episode
	 */
	public String toString() {
		return "\n" + "S" + season + "E" + number + "\n" + "title: " + title + "\n" + "resume: " + resume;
	}
}
