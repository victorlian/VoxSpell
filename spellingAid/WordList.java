package spellingAid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class stores all the words that is used in 11 different 
 * levels of the spelling quiz.
 * (code reused from assignment2, modified)
 * 
 * Responsibility: 
 * 1. Read in the wordlist　(using FileManger class);
 * 2. Generate the 10 words required for each quiz.
 * 
 * @author victor
 *
 */
public class WordList {
	private List<List<String>> _wordList;
	private FileManager _fm = new FileManager();
	
	/**
	 * Constructor that will read in the wordList using the 
	 * FileManager class.
	 */
	public WordList (Quiz quiz){
		if (quiz instanceof NewQuiz){
			_wordList = _fm.readWordList();
		}
		//else if (quiz instanceof Review){
		//
		//}
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
	
}
