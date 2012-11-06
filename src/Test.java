import java.io.IOException;

import ground.Season;


public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//Season data = new Season("http://www.imdb.fr/title/tt0460649/episodes?season=1");
		Season data = new Season("http://www.imdb.fr/title/tt0364845/episodes?season=1");
		Season s = new Season(data);
		
		
	}

}
