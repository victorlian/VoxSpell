package spellingAid;

/**
 * This class represents the submission of a spelling
 * after the submit button is clicked.
 * 
 * Responsibility:
 * 1. Get the current quiz undergoing (using Quiz Class)
 * 2. Check if the spelling is correct.
 * 3. set the successStatus of a word depending on it's correctness. (using Quiz, Word Class)
 * 4. set quiz to take the next word. (using Quiz Class).
 * @author victor
 *
 */
public class Submission implements Option{
	private Quiz _quiz;
	private String _spelling;
	
	public Submission (Quiz quiz, String spelling){
		_quiz = quiz;
		_spelling = spelling;
	}
	
	/**
	 * This method implements the option interface and is the method 
	 * to be called when the submission button is clicked.
	 * 
	 * It's logic is dependent on the number of times a word 
	 * has been spelt, and whether or not the user spelt it correctly
	 * this time.
	 */
	@Override
	public void execute() {
		_quiz.display(_spelling);
		_spelling = _spelling.toLowerCase();
		Word currentWord = _quiz.getCurrentWord();
		boolean correct = currentWord.isSpeltCorrect(_spelling);
		int numberOfTimesSpelt = _quiz.getNumberOfTimesSpelt();
		
		Word.SuccessStatus currentWordSS=currentWord.getSuccessStatus();
		//if the current word already have a successStatus
		if (!currentWordSS.equals(null)){
			//if the current word has failed then should be in review for spell out.
			if(currentWordSS.equals(Word.SuccessStatus.FAILED)){
				//TODO
			}
			else {
				throw new RuntimeException("Should not be in submitting if word has status.");
			}
		}
		//for a normal quiz this part should execute.
		else {
			switch (numberOfTimesSpelt){
			case 0: 
				if (correct){
					_quiz.sayAndDisplay("Correct!");
					currentWord.setMastered();
				}
				else {
					_quiz.incrementSpeltTimes();
					_quiz.sayAndDisplay("Incorrect, try once more!");
					_quiz.execute();
				}
				break;
			case 1:
				if (correct){
					_quiz.sayAndDisplay("Correct!");
					currentWord.setFaulted();
				}
				else {
					_quiz.sayAndDisplay("Incorrect!");
					currentWord.setFailed();
				}
				break;
			default:
				throw new RuntimeException("Unexpected behaviour. Should not be spelt>1");
			}
		}
		//Attempt to load the next word and start quiz.
		_quiz.nextWord();
		_quiz.execute();
		
	}
	

}
