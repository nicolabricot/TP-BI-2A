package analyzer;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.LowerCaseTokenizer;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class MyAnalyzer extends Analyzer {

	final HashSet<String> stopWords;
	private Analyzer a;

	public MyAnalyzer() {
		String[] abc = { "when", "while", "who", "thi", "up", "have",
				"don", "ha", "hi", "him", "so", "out", "an", "that", "is",
				"in", "the", "he", "she", "her", "s", "i", "m", "t",
				"after", "from", "all", "can", "do", "which", "doesn", "go" };
		stopWords = new HashSet<String>(Arrays.asList(abc));
	}

	public final TokenStream tokenStream(String fieldName, Reader reader) {
		TokenStream ts = new LowerCaseTokenizer(Version.LUCENE_36, reader);
		ts = new StandardFilter(Version.LUCENE_36, ts);
		ts = new PorterStemFilter(ts);
		ts = new StopFilter(Version.LUCENE_36, ts, stopWords);
		return ts;
	}
	
	public String process(String summary) throws Exception {
		
		Reader reader = new StringReader(summary);
		a = new MyAnalyzer();

		TokenStream ts = a.tokenStream(summary, reader);
		boolean hasnext = ts.incrementToken();
		
		StringBuffer newSummary = new StringBuffer();
		while (hasnext) {
			CharTermAttribute ta = ts.getAttribute(CharTermAttribute.class);
			newSummary.append(ta.toString()+" ");
			hasnext = ts.incrementToken();
		}
		//System.out.println(summary);
		//System.out.println(newSummary);
		return newSummary.toString();

	}
}
