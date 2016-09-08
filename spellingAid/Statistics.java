package spellingAid;

import java.util.ArrayList;
import java.util.List;

/**
 * This class keeps tracks of all the history
 * of the session. Including all the words that
 * was spelled
 * 
 * Responsibilities:
 * 1. Store all the words and their successStatus. (and their level).
 * 2. Allows a JTable model to fetch information from it??? (or maybe use this as a JTable!)
 * @author Daniel and Victor
 *
 */
public class Statistics {
	List<List<WordStats>> _masterList = new ArrayList<>();
	
	/**
	 * Takes a list of Word objects and their corresponding level. 
	 * Add the stats of the word into the master list.
	 * 
	 * @param newWords
	 * @param level
	 */
	public void recordQuizResults(List<Word> newWords, int level) {
		List<WordStats> currentLevel = _masterList.get(level-1);
		
		//Loop through the wordlist and see if duplicates exist
		for (Word currentWord : newWords) {
			for (WordStats currentWordStat : currentLevel) {
				if (currentWordStat.getWord().equals(currentWord)) {
					//Equals
				}
			}
			
			//Not Equals
		}
	}
}
