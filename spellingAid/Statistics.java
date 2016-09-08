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
	private StatsTableModel _statsTableModel;
	private List<List<WordStats>> _masterList = new ArrayList<>();
	private static final String[] columnNames = {"Level",
            									"Word",
									            "Success",
									            "Faults",
									            "Fails"};
										
	private Statistics() {
		Word newWord = new Word("Magical");
		newWord.setMastered();
		WordStats newWordStats = new WordStats(newWord);
		newWordStats.changeStat(newWord.getSuccessStatus().ordinal());
		
		List<WordStats> newWordList = new ArrayList<>();
		newWordList.add(newWordStats);
		
		newWord = new Word("Sherlock");
		newWord.setFaulted();
		newWordStats = new WordStats(newWord);
		newWordStats.changeStat(newWord.getSuccessStatus().ordinal());
		
		newWordList.add(newWordStats);
		
		_masterList.add(newWordList);
		
		for (int i=1; i<11; i++) {
			_masterList.add(new ArrayList<>());
		}
	}
	
	public static Statistics getInstance() {
		if (_statistics == null) {
			_statistics = new Statistics();
		}
		return _statistics;
	}
	
	public void passModel(StatsTableModel statsTableModel) {
		_statsTableModel = statsTableModel;
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
			currentLevel.add(newWordStats);
		}
		
		_statsTableModel.fireTableDataChanged();
	}
	
	/**
	 * Method for the JTable method in StatsTableModel
	 * @param index
	 * @return
	 */
	public String getColumnHeadings(int index) {
		return columnNames[index];
	}
	
	/**
	 * This method converts the masterlist to a 2D array for the JTable
	 * @return tableData;
	 */
	public Object[][] getTableData() {
		int index = 0;
		Object[][] tableData = new Object[_masterList.size()][5];
		for (int i=0; i < _masterList.size(); i++) {
			List<WordStats> currentList = _masterList.get(i);
		    for (int j=0; j<currentList.size(); j++) {
		    	WordStats currentWord = currentList.get(j);
		    	
		    	//Level as string
		    	tableData[index][0] = i+1;
		    	
		    	//Word as string
		    	tableData[index][1] = currentWord.getWord().toString();
		    	System.out.println(currentWord.getWord().toString());
		    	
		    	//Success stat as string
		    	tableData[index][2] = currentWord.getSuccess();
		    	
		    	//Fault stat as string
		    	tableData[index][3] = currentWord.getFault();
		    	
		    	//Fail stat as string
		    	tableData[index][4] = currentWord.getFail();
		    	
		    	index++;
		    }
		}

		return tableData;
	}
	
	/**
	 * Method for the JTable method in StatsTableModel
	 * @return count
	 */
	public int getRowCount() {
		int count = 0;
		
		for (List<WordStats> currentList : _masterList) {
			for (WordStats currentWord : currentList) {
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * Method for the JTable method in StatsTableModel
	 * @param rows
	 * @param columns
	 * @return
	 */
	public Object getValueAt(int rows, int columns) {
		int count = 0;
		for (List<WordStats> currentList : _masterList) {
			for (WordStats currentWord : currentList) {
				if (count == rows) {
					if (columns == 0) {
						return _masterList.indexOf(currentList) + 1;
					} else if (columns == 1) {
						return currentWord.getWord().toString();
					} else if (columns == 2) {
						return currentWord.getSuccess();
					} else if (columns == 3) {
						return currentWord.getFault();
					} else {
						return currentWord.getFail();
					}
				}
				count++;
			}
		}
		
		return null;
	}
}
