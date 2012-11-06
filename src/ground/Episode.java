package ground;

public class Episode {

	protected String title;
	protected int season;
	protected int number;
	protected String resume;
	
	public Episode (Episode e) {
		this.season = e.getSeason();
		this.number = e.getNumber();
		this.title = e.getTitle();
		this.resume = e.getResume();
	}
	
	public Episode (int season, int number, String title) {
		this.season = season;
		this.number = number;
		this.title = title;
	}
	
	public Episode (int season, int number, String title, String resume) {
		this.season = season;
		this.number = number;
		this.title = title;
		this.resume = resume;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getSeason() {
		return season;
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getResume() {
		return resume;
	}
	
	public void setResume(String resume) {
		this.resume = resume;
	}
	
	public String toString() {
		return "\n" + "S" + season + "E" + number + "\n" + "title: " + title + "\n" + "resume: " + resume;
	}
}
