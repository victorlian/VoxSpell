package statistics;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import fileIO.FileManager;
import words.Word;

/**
 * This class keeps tracks of all the history of the session. Including all the
 * words that was spelled
 * 
 * Singleton Class as there can only be one instance of it.
 * 
 * Responsibilities: 1. Store all the words and their successStatus. (and their
 * level). 2. Allows a JTable model to fetch information from it
 * 
 * @author Daniel
 * 
 * 
 * Modified:
 * 1. Now will return numbers of failure/success on each word.
 * 2. Now will be able to clear all stats.
 * 3. Now will be able to read/write from/to history files.
 * 
 * @author Victor
 *
 */
public class Statistics {
	private static Statistics _statistics = null;
	private StatsTableModel _statsTableModel;
	private static List<List<WordStats>> _statsList = new ArrayList<>();
	private int _level = 0;
	private static final String[] columnNames = { "Word", Card.masteredString, Card.faultedString, Card.failedString };

	/**
	 * Private constructor as it is a singleton Initialise all the lists in the
	 * masterlist to prevent errors
	 */
	private Statistics() {
		for (int i = 0; i < 11; i++) {
			_statsList.add(new ArrayList<WordStats>());
		}
	}

	/**
	 * Singleton so passing an instance of itself to other people
	 * 
	 * @return
	 */
	public static Statistics getInstance() {
		if (_statistics == null) {
			_statistics = new Statistics();
		}
		return _statistics;
	}

	/**
	 * Takes a TableModel and stores it in a field for firing off changes
	 * 
	 * @param statsTableModel
	 */
	public void passModel(StatsTableModel statsTableModel) {
		_statsTableModel = statsTableModel;
	}

	/**
	 * Takes a list of Word objects and their corresponding level. Add the stats
	 * of the word into the master list.
	 * Deprecated: no longer being used, call the method that records a single word.
	 * 
	 * @param newWords
	 * @param level
	 * @deprecated
	 */
	public void recordQuizResults(List<Word> newWords, int level) {
		List<WordStats> currentLevel = _statsList.get(level - 1);

		// Loop through the wordlist and see if duplicates exist
		for (Word currentWord : newWords) {
			for (WordStats currentWordStat : currentLevel) {
				if (currentWordStat.getWord().equals(currentWord)) {
					// Get the result of the word and change the stat
					// the ordinal should correspond to the array index of the
					// result
					currentWordStat.changeStat(currentWord.getSuccessStatus().ordinal());
					currentWordStat.setRecentSuccess(currentWord.getSuccessStatus());
					break;
				}
			}

			// Create a new WordStats object, increment the corresponding stat
			// then add it
			// to the master list
			WordStats newWordStats = new WordStats(currentWord);
			newWordStats.changeStat(currentWord.getSuccessStatus().ordinal());
			newWordStats.setRecentSuccess(currentWord.getSuccessStatus());
			currentLevel.add(newWordStats);
		}

		_statsTableModel.fireTableDataChanged();
	}
	
	/**
	 * This function records the success of a word after it has been tested.
	 * It would also be written to the .stats file.
	 * 
	 * @param newWord
	 * @param level
	 */
	public void recordWordResult(Word newWord, int level) {
		boolean found = false;
		List<WordStats> currentLevel = _statsList.get(level - 1);
		
		for (WordStats currentWordStat : currentLevel) {
			if (currentWordStat.getWord().toString().equals(newWord.toString())) {
				//Word currently exists
				currentWordStat.changeStat(newWord.getSuccessStatus().ordinal());
				currentWordStat.setRecentSuccess(newWord.getSuccessStatus());
				
				found = true;
			}
		}
		
		if (!found) {
			// Does not currently exist
			// Create a new WordStats object, increment the corresponding stat
			// then add it to the master list
			WordStats newWordStats = new WordStats(newWord);
			newWordStats.changeStat(newWord.getSuccessStatus().ordinal());
			newWordStats.setRecentSuccess(newWord.getSuccessStatus());
			currentLevel.add(newWordStats);
		}
		
		new FileManager().updateStatsFile();
		_statsTableModel.fireTableDataChanged();
	}

	/**
	 * This function returns a list of the recently failed words in a given
	 * level
	 * 
	 * @param level
	 * @return failList
	 */
	public static List<Word> failList(int level) {
		List<Word> failList = new ArrayList<>();
		List<WordStats> currentLevel = _statsList.get(level - 1);

		for (WordStats currWordStats : currentLevel) {
			if (currWordStats.getRecentSuccess().equals(Word.SuccessStatus.FAILED)) {
				failList.add(currWordStats.getWord());
			}
		}

		return failList;
	}

	/**
	 * Return the number of failed words per level
	 * 
	 * @param level
	 * @return an integer
	 */
	public static int numberOfFailedWords(int level) {
		return failList(level).size();
	}

	/**
	 * This function calculates the total success number of spelling. 
	 * 
	 * @param level
	 */
	public int getMasteredNumber(int level) {
		List<WordStats> wordStats = _statsList.get(level - 1);

		int successes = 0;

		for (WordStats currWord : wordStats) {
			successes += currWord.getSuccess();
		}

		return successes;
	}
	
	/**
	 * This function calculates the amount of faulted words of spelling. 
	 * 
	 * @param level
	 */
	public int getFaultedNumber(int level) {
		List<WordStats> wordStats = _statsList.get(level - 1);

		int faulted = 0;

		for (WordStats currWord : wordStats) {
			faulted += currWord.getFault();
		}

		return faulted;
	}
	
	/**
	 * This function calculates the faulted words of spelling.
	 * 
	 * @param level
	 */
	public int getFailedNumber(int level) {
		List<WordStats> wordStats = _statsList.get(level - 1);

		int failed = 0;

		for (WordStats currWord : wordStats) {
			failed += currWord.getFail();
		}

		return failed;
	}

	/**
	 * Set the level to view in the Statistics table
	 * 
	 * @param level
	 */
	public void setLevel(int level) {
		_level = level - 1;
		_statsTableModel.fireTableDataChanged();
	}

	/**
	 * Method for the JTable method in StatsTableModel
	 * 
	 * @param index
	 * @return
	 */
	public String getColumnHeadings(int index) {
		return columnNames[index];
	}

	/**
	 * Method for the JTable method in StatsTableModel
	 * 
	 * @return count
	 */
	public int getRowCount() {
		int count = 0;

		for (List<WordStats> currentList : _statsList) {
			count += currentList.size();
		}

		return count;
	}

	/**
	 * Method for the JTable method in StatsTableModel. Reads the current level
	 * set by setLevel() method and returns the values in that level
	 * 
	 * @param rows
	 * @param columns
	 * @return
	 */
	public Object getValueAt(int rows, int columns) {
		int count = 0;
		List<WordStats> currentList = _statsList.get(_level);

		for (WordStats currentWord : currentList) {
			if (count == rows) {
				if (columns == 0) {
					return currentWord.getWord().toString();
				} else if (columns == 1) {
					return currentWord.getSuccess();
				} else if (columns == 2) {
					return currentWord.getFault();
				} else {
					return currentWord.getFail();
				}
			}
			count++;
		}

		return null;
	}
	
	/**
	 * This method will clear the statistics for all levels.
	 * Intended to be called by the settings card to remove all stats.
	 */
	public void clearStats(){
		for (List<WordStats> list: _statsList){
			list.clear();
		}
		_statsTableModel.fireTableDataChanged();
	}
	
	public List<List<WordStats>> getStatsList(){
		return _statsList;
	}
	
}
