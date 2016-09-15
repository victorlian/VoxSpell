package spellingAid;

import java.util.List;

/**
 * This is an abstract class represents a quiz that user started.
 * The quiz can either be newQuiz or Review Mistakes.
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
 * 	1.5 The number of words spelled correctly.
 * 	1.6 The number of times the current word has been spelt (to determine successStatus/etc)
 * 2. Say instructions (using Speech Class)
 * 3. Append text instructions (using MainViewer Class)
 * 
 * @author victor
 *
 */
public abstract class Quiz implements Option{
	public final String NL = System.getProperty("line.separator");
	public final int WORDS_PER_LEVEL = 10;
	public final int MAX_LEVEL = 11;
	
	//Dependencies.
	protected WordList _wordList;
	protected Viewer _mainViewer;
	protected Speech _speech;
	
	//Fields (states).
	protected int _currentLevel;
	protected int _numberOfTests;
	protected int _testNumber;
	protected List<Word> _wordToTest;
	protected Word _currentWord;
	protected int _numberOfCorrectWords;
	protected int _numberOfTimesSpelt;
	
	/**
	 * Protected constructor for child class only.
	 * @param viewer
	 * @param level
	 */
	protected Quiz (Viewer viewer, int level){
		_mainViewer = viewer;
		_speech = new Speech();
		_wordList = new WordList(this);
		_currentLevel = level;
		_numberOfCorrectWords=0;
		_testNumber=0;
		
		//Fields to be initialized in child class:
		//_numberOfTests
		//_wordToTest
		//_currentWord
	}
	
	/**
	 * This method executes the quiz once. I.e a single test.
	 * It implements the option interface and is the one
	 * that gets called when the button is pressed.
	 * 
	 * Not intended to be overriden.
	 */
	@Override
	public final void execute(){
		if (_testNumber<_numberOfTests){
			spellTest();
		}
		else{
			_mainViewer.enableStartButton();
			_mainViewer.displayMainMenu();
		}
	}
	
	/**
	 * This method will say the word to spell and enable the submit button.
	 */
	protected void spellTest(){
		_mainViewer.appendText("Please spell word " + (_testNumber+1) + " of " + _numberOfTests + ": ");
		String sayWords = "Please Spell ... " + _currentWord.toString() + " ... " + _currentWord.toString();
		_speech.say(sayWords);
		_mainViewer.enableSubmissionButtons();
	}
	
	/**
	 * This method will be called to configure quiz to take the next word.
	 * If the currentWord is the last word, then launch end of quiz.
	 * 
	 * This is a template method. (hence final)
	 * 
	 * Methods to be implemented:
	 * endOfWord(), endOfQuiz();
	 */
	public final void nextWord (){
		if (endOfWord()){
			return;
		}
		_testNumber++;
		if (_testNumber==_numberOfTests-1){
			endOfQuiz();
		}
		else{
			if(_currentWord.getSuccessStatus().equals(Word.SuccessStatus.MASTERED)){
				_numberOfCorrectWords++;
			}
			_currentWord=_wordToTest.get(_testNumber);
		}
		_numberOfTimesSpelt=0;
	}
	
	/**
	 * This method will repeat the current word using festival.
	 */
	public void repeatWord(){
		_currentWord.sayWord();
	}
	
	/**
	 * This method will allow moving up the difficulty level by 1.
	 * If the quiz is at maximum level (11), then an information 
	 * message will pop up and tell the user that they are at the highest level
	 * possible.
	 */
	protected void nextLevel(){
		if (_currentLevel == MAX_LEVEL ){
			_mainViewer.popMessage("You are at the highest level (Level 11) already!", MessageType.INFORMATION);
		}
		else {
			_currentLevel++;
		}
	}
	
	/**
	 * Allowing children classes to do specific things after the completion of
	 * a test.
	 */
	protected abstract void endOfQuiz();
	
	/**
	 * Allowing children classes to do specific things after a word is spelled.
	 * Note that if returns true, it will skip the process of loading next word.
	 */
	protected abstract boolean endOfWord();
	
	/**
	 * This method should always be called when getting the instance of quiz.
	 * As it reinitializes all the fields based on the level.
	 */
	protected abstract void initializeQuiz();
	
	//++++++++++++++++++++++++++++++++++++++++++++++
	//Helper methods for other classes to call:
	
	/**
	 * This method will say and display the instruction input.
	 */
	public void sayAndDisplay(String s){
		_speech.say(s);
		_mainViewer.appendText(s);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will increment the count for the number of times the current
	 * word is spelt. Only to be called after submission.
	 */
	public void incrementSpeltTimes(){
		_numberOfTimesSpelt++;
	}
	
	/**
	 * Getter for number of spelt times.
	 */
	public int getNumberOfTimesSpelt(){
		return _numberOfTimesSpelt;
	}
	
	/**
	 * This method will display the input on the GUI.
	 */
	public void display (String s){
		_mainViewer.appendText(s);
	}
	
	public Word getCurrentWord(){
		return _currentWord;
	}

}
