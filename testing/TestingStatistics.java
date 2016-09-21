package testing;

import java.util.ArrayList;
import java.util.List;

import statistics.Statistics;
import words.Word;

/**
 * Populate the Statistics Table Model
 * @author Daniel
 *
 */
public class TestingStatistics {
	/**
	 * Generate words for each level to ensure they display correctly
	 */
	public static void testStatisticsDisplay(Statistics _statistics) {
		List<Word> newWordsList = new ArrayList<>();
		Word newWord;
		int level = 1;
		
		//Level 1
		newWord = new Word("a");
		newWord.setMastered();
		newWordsList.add(newWord);
		
		newWord = new Word("I");
		newWord.setMastered();
		newWordsList.add(newWord);
		
		_statistics.recordQuizResults(newWordsList, level);
		newWordsList = new ArrayList<>();
		level++;
		
		//Level 2
		newWord = new Word("at");
		newWord.setMastered();
		newWordsList.add(newWord);
		
		newWord = new Word("had");
		newWord.setFailed();
		newWordsList.add(newWord);
		
		_statistics.recordQuizResults(newWordsList, level);
		newWordsList = new ArrayList<>();
		level++;
		
		//Level 3
		newWord = new Word("about");
		newWord.setMastered();
		newWordsList.add(newWord);
		
		newWord = new Word("be");
		newWord.setFaulted();
		newWordsList.add(newWord);
		
		_statistics.recordQuizResults(newWordsList, level);
		newWordsList = new ArrayList<>();
		level++;
	}
}
