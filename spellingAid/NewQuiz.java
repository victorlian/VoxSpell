package spellingAid;

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
 * 2. Say instructions (using Speech Class)
 * 3. Append text instructions (using MainViewer Class)
 * 
 * New:
 * 1. Allow levels to be changed. (level up by one only)
 * 2. Update the record (all the fields).
 * 3. Set to move on to the next word.
 * 4. Singleton Class, use getInstance for object from this class.
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
		_wordToTest=_wordList.generateRandomWords(_currentLevel, _currentLevel);
		_currentWord=_wordToTest.get(0);
	}
	
	
	/**
	 *　The method to call when requiring the quiz object.
	 * @param viewer
	 * @param startingLevel
	 * @return
	 */
	public static Quiz getInstance(Viewer viewer, int startingLevel){
		if (_instance==null){
			_instance = new NewQuiz(viewer, startingLevel);
		}
		return _instance;
	}
	
	
	/**
	 * Method that will do the following when all words in a quiz 
	 * have been spelt.
	 * 1. Pass the list of spelt words to Statistics Class.
	 * 2. Allow the play a video option.
	 * 3. Ask user if they want to level up or stay at current level.
	 * 
	 * Method overriden from abstract class.
	 */
	@Override
	protected void endOfQuiz(){
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
	protected void endOfWord() {
		// Nothing needs to be done.
	}
}
