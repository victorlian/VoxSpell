package spellingAid;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the Word object along with a list of stats
 * index:
 * 	0 - Success
 * 	1 - Fault
 * 	2 - Fail
 * @author Daniel
 *
 */
public class WordStats {
	private Word _word;
	private int[] _stats = new int[3];
	
	public WordStats(Word word) {
		_word = word;
	}
	
	public boolean equal(Word word) {
		return _word.equals(word);
	}
	
	/**
	 * Change the stat according to the index passed in
	 * @param index
	 */
	public void changeStat(int index) {
		_stats[index]++;
	}
	
	/**
	 * Get the word in this WordStats object.
	 * @return Word
	 */
	public Word getWord() {
		return _word;
	}
}
