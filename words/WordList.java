package words;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fileIO.FileManager;
import quiz.NewQuiz;
import quiz.Quiz;
import quiz.ReviewQuiz;
import spellingAid.Settings;
import statistics.Statistics;

/**
 * This class stores all the words that is used in 11 different 
 * levels of the spelling quiz.
 * (code reused from assignment2, modified)
 * 
 * Responsibility: 
 * 1. Read in the wordlist (using FileManger class);
 * 2. Generate the 10 words required for each quiz.
 * 
 * @author victor
 *
 */
public class WordList {
	private List<List<String>> _wordList = new ArrayList<List<String>>();
	private FileManager _fm = new FileManager();
	
	/**
	 * Constructor that will read in the wordList using the 
	 * FileManager class.
	 */
	public WordList (Quiz quiz){
		if (quiz instanceof NewQuiz){
			_wordList = _fm.readWordList(Settings.getWordlist());
		}
		else if (quiz instanceof ReviewQuiz){
			for (int i=1; i<=11; i++){
				List<Word> listOfWords = Statistics.failList(i);
				List<String> listOfStrings = convertListWordToString(listOfWords);
				_wordList.add(null);//for position 0;
				_wordList.add(i,listOfStrings);
			}
		}
		else {
			throw new RuntimeException("Invalid Quiz type for wordlist constructor.");
		}
	}
	
	/**
	 * This method generates a given number of randomWords for a particular level.
	 * @param level
	 * @param amount
	 * @return
	 */
	public List<Word> generateRandomWords(int level, int amount){
		if (level<=0 || level>11){
			throw new RuntimeException("Invalid level");
		}
		else{
			List<String> singleLevel = _wordList.get(level);
			
			int maxAmount = singleLevel.size();
			if (amount > maxAmount){
				throw new RuntimeException("Not enough words in list");
			}
			
			ArrayList<Integer> integerList = new ArrayList<Integer>(singleLevel.size());
			List<Word> randomWordList = new ArrayList<Word>(amount);
			for (int i=0; i<singleLevel.size(); i++){
				integerList.add(new Integer(i));
			}
			Collections.shuffle(integerList);
			for (int i=0; i<amount; i++){
				int randomIndex = integerList.get(i);
				randomWordList.add(new Word(singleLevel.get(randomIndex)));
			}
			return randomWordList;
		}
	}
	
	/**
	 * This method is intended for Review class.
	 * It counts the number of failed words in a given level.
	 */
	public int numberOfFailedWords(int level){
		return _wordList.get(level).size();
	}
	
	/**
	 * Private helper method that will convert List<Word> to List<String>
	 */
	private List<String> convertListWordToString (List <Word> wordList){
		List<String> stringList = new ArrayList<String>();
		for (Word word: wordList){
			stringList.add(word.toString());
		}
		return stringList;
	}
	
}
