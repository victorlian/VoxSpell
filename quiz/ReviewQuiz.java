package quiz;

import spellingAid.MessageType;
import spellingAid.Option;
import spellingAid.Viewer;
import statistics.Statistics;
import words.Word;
import words.WordList;

/**
 * This is a class represents a review quiz that user started.
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
 *  1.6 The number of times the current word has been spelled 
 *  	(to determine successStatus and if word should be spelled out.)
 * 2. Say instructions (using Speech Class)
 * 3. Append text instructions (using MainViewer Class)
 * 
 * New:
 * 1. Allow levels to be changed. (level up by one only)
 * 2. Update the record (all the fields).
 * 3. Set to move on to the next word.
 * 4. Singleton Class, use getInstance for object from this class.
 * 5. Once a review quiz has completed, send all the information to stats class.
 * 
 * @author victor
 *
 */
public class ReviewQuiz extends Quiz implements Option{
	//singleton.
	private static ReviewQuiz _instance = null;
	
	/**
	 * Use the same constructor as the default abstract one.
	 * @param viewer
	 * @param startingLevel
	 */
	private ReviewQuiz (Viewer viewer, int startingLevel){
		super(viewer, startingLevel);
		int numberFailed = _wordList.numberOfFailedWords(startingLevel);
		_numberOfTests = Math.min(numberFailed, WORDS_PER_LEVEL);
		if (_numberOfTests <=0){
			return;
		}
		else{
			_wordToTest = _wordList.generateRandomWords(startingLevel, _numberOfTests);
			_currentWord = _wordToTest.get(0);
		}
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
			_instance = new ReviewQuiz(viewer, startingLevel);
		}
		else {
			_instance.initializeQuiz(startingLevel);
		}
		return _instance;
	}
	
	/**
	 * Method that will do the following when all words in a quiz 
	 * have been spelt.
	 * 1. Display a summary of the current review session.
	 * 2. Pass the list of spelt words to Statistics Class.
	 * Method overriden from abstract class.
	 */
	@Override
	protected void endOfQuiz(){
		String msg = "End of review at level: " + _currentLevel + ".";
		msg += "You scored: " + _numberOfCorrectWords + " out of " + _numberOfTests + "!";
		_mainViewer.popMessage(msg, MessageType.INFORMATION);
		
		//Record stats
		Statistics stats = Statistics.getInstance();
		stats.recordQuizResults(_wordToTest, _currentLevel);
	}
	
	/**
	 * Method that will be called after a single word has been finished
	 * spelled. Intention is to allow for the word to be spelled out.
	 */
	@Override
	protected boolean endOfWord() {
		if (_currentWord.getSuccessStatus().equals(Word.SuccessStatus.FAILED) && _numberOfTimesSpelt==2) {
			if (_mainViewer.spellWord()) {
				_mainViewer.appendText("Follow the spelling and spell this word again: ");
				String sayWords = _currentWord.toString() + " ... " + "is spelled as ... " + _currentWord.StringToSpellWord();
				sayWords += "Now, please spell: " + _currentWord.toString() + " ... again"; 
				_speech.say(sayWords);
				_mainViewer.enableSubmissionButtons();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method will get called when there is no failed words to be 
	 * reviewed in a level. Should pop up an information message.
	 */
	@Override
	protected void emptyTest() {
		_mainViewer.popMessage("There are no failed words to be reviewed in this level.", MessageType.INFORMATION);
	}
	
	/**
	 * This method is replaced by initializeQuiz with int.
	 */
	@Override
	protected void initializeQuiz() {
		throw new RuntimeException("This Method should never be called.");

	}

	
	/**
	 * Reinitializes quiz fields to configure for a new starting quiz.
	 * (Based on the current level);
	 */
	protected void initializeQuiz(int startingLevel) {
		_currentLevel = startingLevel;
		_wordList = new WordList(this);
		int numberFailed = _wordList.numberOfFailedWords(_currentLevel);
		_numberOfTests = Math.min(numberFailed, WORDS_PER_LEVEL);
		_testNumber=0;
		_numberOfCorrectWords=0;
		_numberOfTimesSpelt=0;
		
		if (_numberOfTests<=0){
			return;
		}
		else{
			_wordToTest = _wordList.generateRandomWords(_currentLevel, _numberOfTests);
			_currentWord = _wordToTest.get(0);
		}
	}
}
