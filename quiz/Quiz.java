package quiz;

import java.util.List;

import speech.Speech;
import spellingAid.MessageType;
import spellingAid.Option;
import spellingAid.Settings;
import spellingAid.Viewer;
import statistics.Statistics;
import words.Word;
import words.WordList;

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
	protected int _score;
	
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
		_score=0;
		
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
		if (_numberOfTests == 0){
			emptyTest();
		}
		
		if (_testNumber<_numberOfTests){
			spellTest();
		}
		else{
			_mainViewer.enableStartButton();
			_mainViewer.disableSubmissionButtons();
			_mainViewer.displayMainMenu();
		}
	}
	
	/**
	 * This method will say the word to spell and enable the submit button.
	 */
	protected void spellTest(){
		_mainViewer.appendText("Please spell word " + (_testNumber+1) + " of " + _numberOfTests + ": ");
		_speech.say("Please Spell.");
		_currentWord.sayWord();
		_currentWord.sayWord();
		_mainViewer.enableSubmissionButtons();
	}
	
	/**
	 * This method will be called to configure quiz to take the next word.
	 * If the currentWord is the last word, then call end of quiz.
	 * 
	 * Return type is false when this method does not initialize the next word
	 * successfully, and so quiz.execute should not be called again.
	 * 
	 * This is a template method. (hence final)
	 * 
	 * Methods to be implemented:
	 * endOfWord(), endOfQuiz();
	 */
	public final boolean nextWord (){
		if (endOfWord()){
			return false;
		}
		
		Statistics.getInstance().recordWordResult(_currentWord, _currentLevel);
		Word.SuccessStatus currentSuccessStatus = _currentWord.getSuccessStatus();
		
		if(currentSuccessStatus.equals(Word.SuccessStatus.MASTERED)){
			_numberOfCorrectWords++;
			_score += 100;
		}
		else if (currentSuccessStatus.equals(Word.SuccessStatus.FAULTED)){
			_score += 20;
		}
		else if (currentSuccessStatus.equals(Word.SuccessStatus.FAILED)){
			_score -= 50;
		}
		else {
			throw new RuntimeException ("Should not be configuring for next word.");
		}
		
		_mainViewer.setScore(_score);
		_mainViewer.setProgess((_testNumber+1) * 100 / _numberOfTests );
		

		if (_testNumber==_numberOfTests-1){
			_testNumber++;
			endOfQuiz();

		}
		else{
			_testNumber++;
			_currentWord=_wordToTest.get(_testNumber);
		}
		_numberOfTimesSpelt=0;
		return true;
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
			//Increment current level in Settings
			Settings.setlevel(Settings.getLevel()+1);
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
	
	/**
	 * This method is going to get called when the number of test to do is 0.
	 */
	protected abstract void emptyTest();
	
	//++++++++++++++++++++++++++++++++++++++++++++++
	//Helper methods for other classes to call:
	
	/**
	 * This method will say and display the instruction input.
	 */
	public void sayAndDisplay(String s){
		_speech.say(s);
		_mainViewer.appendText(s);
	}
	
	/**
	 * This method will say the punishment/Reward
	 */
	public void say(String s){
		_speech.say(s);
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
	
	/**
	 * This method will return the current level.
	 */
	public int getCurrentLevel(){
		return _currentLevel;
	}
	
	//++++++++++++++++++++++++++++++
	//Debugging methods:
	/**
	 * This method will allow us to test word number X straight away.
	 * A list of words should be passed in as the spelt words. 
	 * (they need to have successStatus)
	 * If the reference passed in is not the correct size, then words will be 
	 * the old one generated and set to the given successStatus
	 */
	public void toNumberX (List<Word> wordsSpelt, int skipTo, int numberCorrect, Word.SuccessStatus ss){
		if (wordsSpelt != null && wordsSpelt.size()==skipTo-1){
			for (int i=0; i<wordsSpelt.size();i++){
				_wordToTest.remove(i);
				_wordToTest.add(i, wordsSpelt.get(i));
			}
		}
		else {
			for (int i=0; i<skipTo; i++){
				Word word = _wordToTest.get(i);
				if (ss.equals(Word.SuccessStatus.MASTERED)){
					word.setMastered();
				}
				else if (ss.equals(Word.SuccessStatus.FAULTED)){
					word.setFaulted();
				}
				else {
					word.setFailed();
				}
			}
		}

		_testNumber=skipTo;
		_currentWord=_wordToTest.get(skipTo);
		_numberOfTimesSpelt=0;
		_numberOfCorrectWords=numberCorrect;
	}
	
	/**
	 * This method will show the word in the txtOutput.
	 */
	public void cheat (){
		_mainViewer.appendText("  cheat: " + _currentWord.toString() + " ");
	}

}
