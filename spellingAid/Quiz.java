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
	
	/**
	 * Protected constructor for child class only.
	 * @param viewer
	 * @param level
	 */
	protected Quiz (Viewer viewer, int level){
		_mainViewer = viewer;
		_speech = new Speech ();
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
			throw new RuntimeException("No more words to be tested.");
		}
	}
	
	/**
	 * This method will say the word to spell and enable the submit button.
	 */
	protected void spellTest(){
		_mainViewer.appendText("Please spell word " + _testNumber+1 + " of " + _numberOfTests+ ": ");
		_speech.saySentence("Please Spell...");
		_currentWord.sayWord();
		_currentWord.sayWord();
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
		endOfWord();
		if (_testNumber==_numberOfTests-1){
			endOfQuiz();
		}
		else{
			_testNumber++;
			if(_currentWord.getSuccessStatus().equals(Word.SuccessStatus.MASTERED)){
				_numberOfCorrectWords++;
			}
			_currentWord=_wordToTest.get(_testNumber);
		}
	}
	
	/**
	 * Allowing children classes to do specific things after the completion of
	 * a test.
	 */
	protected abstract void endOfQuiz();
	
	/**
	 * Allowing children classes to do specific things after a word is spelled.
	 */
	protected abstract void endOfWord();

}
