package statistics;

import words.Word;

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
	private Word.SuccessStatus _latestSuccess = Word.SuccessStatus.FAILED;
	
	public WordStats(Word word) {
		_word = word;
	}
	
	/**
	 * Change the stat according to the index passed in
	 * @param index
	 */
	public void changeStat(int index) {
		_stats[index]++;
	}
	
	public int getSuccess() {
		return _stats[0];
	}
	
	public int getFault() {
		return _stats[1];
	}
	
	public int getFail() {
		return _stats[2];
	}
	
	public int getNumberofAttempts() {
		return _stats[0] + _stats[1] + _stats[2];
	}
	
	/**
	 * Get the word in this WordStats object.
	 * @return Word
	 */
	public Word getWord() {
		return _word;
	}
	
	public void setRecentSuccess(Word.SuccessStatus latestSuccess) {
		_latestSuccess = latestSuccess;
	}
	
	public Word.SuccessStatus getRecentSuccess() {
		return _latestSuccess;
	}
}
