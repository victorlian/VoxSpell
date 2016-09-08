package spellingAid;

import java.util.ArrayList;
import java.util.List;

/**
 * This class keeps tracks of all the history
 * of the session. Including all the words that
 * was spelled
 * 
 * Singleton Class as there can only be one instance of it.
 * 
 * Responsibilities:
 * 1. Store all the words and their successStatus. (and their level).
 * 2. Allows a JTable model to fetch information from it??? (or maybe use this as a JTable!)
 * @author Daniel and Victor
 *
 */
public class Statistics {
	private static Statistics _statistics = null;
	private List<List<WordStats>> _masterList = new ArrayList<>();
	private static final String[] columnNames = {"Level",
            									"Word",
									            "Success",
									            "Faults",
									            "Fails"};
										
	private Statistics() {
		Word newWord = new Word("Magical");
		newWord.setMastered();
		WordStats newWordStat = new WordStats(newWord);
		List<WordStats> newWordStatsList = new ArrayList<>();
		newWordStatsList.add(newWordStat);
		
		_masterList.add(newWordStatsList);
	}
	
	public static Statistics getInstance() {
		if (_statistics == null) {
			_statistics = new Statistics();
		}
		return _statistics;
	}
	
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
					//Get the result of the word and change the stat
					//the ordinal should correspond to the array index of the result
					currentWordStat.changeStat(currentWord.getSuccessStatus().ordinal());
					break;
				}
			}
			
			//Create a new WordStats object, increment the corresponding stat then add it
			//to the master list
			WordStats newWordStats = new WordStats(currentWord);
			newWordStats.changeStat(currentWord.getSuccessStatus().ordinal());
			currentLevel.add(new WordStats(currentWord));
		}
	}
	
	public String[] getColumnHeadings() {
		return columnNames;
	}
	
	/**
	 * This method converts the masterlist to a 2D array
	 * @return tableData;
	 */
	public String[][] getTableData() {
		String[][] tableData = new String[_masterList.size()][5];
		for (int i=0; i < _masterList.size(); i++) {
			List<WordStats> currentList = new ArrayList<>();
		    for (int j=0; j<currentList.size(); j++) {
		    	WordStats currentWord = currentList.get(j);
		    	
		    	//Level as string
		    	tableData[j][0] = String.valueOf(i);
		    	
		    	//Word as string
		    	tableData[j][1] = currentWord.getWord().toString();
		    	
		    	//Success stat as string
		    	tableData[j][2] = String.valueOf(currentWord.getSuccess());
		    	
		    	//Fault stat as string
		    	tableData[j][3] = String.valueOf(currentWord.getFault());
		    	
		    	//Fail stat as string
		    	tableData[j][4] = String.valueOf(currentWord.getFail());
		    }
		}
		
		return tableData;
	}
}
