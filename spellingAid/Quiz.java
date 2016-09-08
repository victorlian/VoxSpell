package spellingAid;

import java.util.List;

/**
 * This class represents a quiz that user started.
 * (code reused from assignment 2)
 * 
 * Singleton Class.
 * 
 * Responsibilities:
 * 1. Keep track of:
 * 	1.1	The current level
 * 	1.2 The current word that is under test.
 * 	1.3	All the words that was spelled or is going to be spelled in this quiz.
 * 	1.4 The list of words to be spelled. (Generated using wordList class)
 * 	1.4 The number of words spelled correctly.
 * 2. Allow levels to be changed. (level up by one only)
 * 3. Say instructions (using Speech Class)
 * 4. Append text instructions (using MainViewer Class)
 * 5. Update the record (all the fields).
 * 6. Set to move on to the next word.
 * 
 * @author victor
 *
 */
public class Quiz {
	public final String NL = System.getProperty("line.separator");
	public final int WORDS_PER_LEVEL = 10;
	public final int MAX_LEVEL = 11;
	
	//singleton.
	private static Quiz _instance = null;
	
	//Dependencies.
	private WordList _wordList;
	private Viewer _mainViewer;
	private Speech _speech;
	
	//Fields (states).
	private int _currentLevel;
	private int _numberOfTests;
	private int _testNumber;
	private List<Word> _wordToTest;
	private Word _currentWord;
	private int _numberOfCorrectWords;
	
	/**
	 * Private constructor for singleton class.
	 * @param viewer
	 * @param level
	 */
	private Quiz (Viewer viewer, int level){
		_mainViewer = viewer;
		_speech = new Speech ();
		_wordList = new WordList();
		_currentLevel = level;
		_numberOfTests = WORDS_PER_LEVEL;
		_wordToTest=_wordList.generateRandomWords(_currentLevel, _currentLevel);
		_numberOfCorrectWords=0;
		_testNumber=0;
		_currentWord=_wordToTest.get(0);
	}
	
	/**
	 * This method will be called to configure quiz to take the next word.
	 * If the currentWord is the last word, then launch end of quiz.
	 */
	public void nextWord (){
		if (_testNumber==_numberOfTests-1){
			endOfQuiz();
		}
		else{
			
		}
	}
	
	/**
	 * Private method that will do the following when all words in a quiz 
	 * have been spelt.
	 * 1. Pass the list of spelt words to Statistics Class.
	 * 2. Allow the play a video option.
	 */
	private void endOfQuiz(){
		
	}
	
	/**
	 *　The method to call when requiring the quiz object.
	 * @param viewer
	 * @param startingLevel
	 * @return
	 */
	public static Quiz getInstance(Viewer viewer, int startingLevel){
		if (_instance==null){
			_instance = new Quiz(viewer, startingLevel);
		}
		return _instance;
	}
	
	/**
	 * This method will allow moving up the difficulty level by 1.
	 * If the quiz is at maximum level (11), then an information 
	 * message will pop up and tell the user that they are at the highest level
	 * possible.
	 */
	public void nextLevel(){
		if (_currentLevel == MAX_LEVEL ){
			_mainViewer.popMessage("You are at the highest level (Level 11) already!", MessageType.INFORMATION);
		}
		else {
			_currentLevel++;
		}
	}
	
	

}
