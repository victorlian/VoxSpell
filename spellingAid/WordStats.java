package spellingAid;

import java.util.ArrayList;
import java.util.List;

public class WordStats {
	private Word _word;
	private List<Integer> _stats = new ArrayList<>();
	
	public WordStats(Word word) {
		_word = word;
	}
	
	public boolean equal(Word word) {
		return _word.equals(word);
	}
	
	public Word getWord() {
		return _word;
	}
}
