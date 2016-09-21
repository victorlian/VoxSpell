package quiz;

import spellingAid.MessageType;
import spellingAid.Option;
import spellingAid.Viewer;

/**
 * This is a class represents a new quiz that user started.
 * (code reused from assignment 2)
 * 
 * 
 * Responsibilities (inherited):
 * 1. Keep track of:
 * 	1.1	The current level
 * 	1.2 The current word that is under test.
 * 	1.3	All the words that was spelled or is going to be spelled in this quiz.
 * 	1.4 The list of words to be spelled. (Generated using wordList class)
 * 	1.5 The number of words spelled correctly.
 *  1.6 The number of times the current word has been spelt (to determine successStatus/etc)
 * 2. Say instructions (using Speech Class)
 * 3. Append text instructions (using MainViewer Class)
 * 
 * New:
 * 1. Allow levels to be changed. (level up by one only)
 * 2. Update the record (all the fields).
 * 3. Set to move on to the next word.
 * 4. Singleton Class, use getInstance for object from this class.
 * 5. Once a quiz has completed, send all the information to stats class.
 * 
 * @author victor
 *
 */
public class NewQuiz extends Quiz implements Option{
	//singleton.
	private static NewQuiz _instance = null;
	
	/**
	 * Use the same constructor as the default abstract one.
	 * @param viewer
	 * @param startingLevel
	 */
	private NewQuiz (Viewer viewer, int startingLevel){
		super(viewer, startingLevel);
		_numberOfTests = WORDS_PER_LEVEL;
		_wordToTest=_wordList.generateRandomWords(_currentLevel, _numberOfTests);
		_currentWord=_wordToTest.get(0);
	}
	
	
	/**
	 *　The method to call when requiring the quiz object.
	 * @param viewer
	 * @param startingLevel
	 * @return
	 */
	public static Quiz getInstance(Viewer viewer, int startingLevel){
		//FOR DEBUG USE:
		if (startingLevel==12345){
			return _instance;
		}
		//===============
		
		if (_instance==null){
			_instance = new NewQuiz(viewer, startingLevel);
		}
		else {
			_instance.initializeQuiz();
		}
		return _instance;
	}
	
	
	/**
	 * Method that will do the following when all words in a quiz 
	 * have been spelt.
	 * 1. Display a summary of the current quiz session.
	 * 2. Pass the list of spelt words to Statistics Class.
	 * 3. Allow the play a video option.
	 * 4. Ask user if they want to level up or stay at current level.
	 * 
	 * Method overriden from abstract class.
	 */
	@Override
	protected void endOfQuiz(){
		String msg = "End of current Quiz at level: " + _currentLevel + ".";
		msg += "You scored: " + _numberOfCorrectWords + " out of " + _numberOfTests + "!";
		_mainViewer.popMessage(msg, MessageType.INFORMATION);
		
		//Video and level up options.
		if (_numberOfCorrectWords>=9){
			if(_mainViewer.videoOption()){
				_mainViewer.playVideo();
			}
			if(_mainViewer.levelUpOption()){
				nextLevel();
			}
		}
		
	}

	/**
     * Unsupported operation in this class.
     */
	@Override
	protected boolean endOfWord() {
		// Nothing needs to be done.
		return false;
	}
	
	/**
	 * Unsupported operation in this class.
	 */
	@Override
	protected void emptyTest() {
		//Should never be called.
		throw new RuntimeException ("Unsupported operation. ");
	}

	/**
	 * Reinitializes quiz fields to configure for a new starting quiz.
	 */
	@Override
	protected void initializeQuiz() {
		_wordToTest=_wordList.generateRandomWords(_currentLevel, _numberOfTests);
		_currentWord=_wordToTest.get(0);
		_testNumber=0;
		_numberOfCorrectWords=0;
		_numberOfTimesSpelt=0;
	}
}
