package quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import spellingAid.MessageType;
import spellingAid.Option;
import spellingAid.Viewer;
import words.Word;

/**
 * This class represents the submission of a spelling
 * after the submit button is clicked.
 * 
 * Responsibility:
 * 1. Get the current quiz undergoing (using Quiz Class)
 * 2. Check if the spelling is correct.
 * 3. set the successStatus of a word depending on it's correctness. (using Quiz, Word Class)
 * 4. set quiz to take the next word. (using Quiz Class).
 * 
 * Note that any interaction with the GUI is done through the quiz class. 
 * @author victor
 *
 */
public class Submission implements Option{
	private Quiz _quiz;
	private String _spelling;
	private Viewer _viewer;
	
	
	
	public Submission (Quiz quiz, String spelling, Viewer viewer){
		_quiz = quiz;
		_spelling = spelling;
		_viewer = viewer;
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
		_quiz.display(_spelling + _quiz.NL);
		_spelling = _spelling.toLowerCase();
		Word currentWord = _quiz.getCurrentWord();
		boolean correct = currentWord.isSpeltCorrect(_spelling);
		int numberOfTimesSpelt = _quiz.getNumberOfTimesSpelt();
		
		Word.SuccessStatus currentWordSS=currentWord.getSuccessStatus();
		//if the current word already have a successStatus
		if (!(currentWordSS == null)){
			//if the current word has failed then should be in review for spell out.
			if(currentWordSS.equals(Word.SuccessStatus.FAILED)){
				_quiz.incrementSpeltTimes();
				if (correct){
					_quiz.sayAndDisplay("Correct!" + _quiz.NL + _quiz.NL + _quiz.NL);
					_quiz.say(generateAudioReward());
					_viewer.displayTick();					
				}
				else {
					_quiz.sayAndDisplay("Incorrect!" + _quiz.NL + _quiz.NL + _quiz.NL);
					_quiz.say(generateAudioComfort());
					_quiz._mainViewer.popMessage("Correct spelling is: " + currentWord.toString(), MessageType.INFORMATION);
					_viewer.displayCross();
				}
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
					_quiz.sayAndDisplay("Correct!" + _quiz.NL + _quiz.NL);
					currentWord.setMastered();
					_quiz.say(generateAudioReward());
					_viewer.displayTick();	
				}
				else {
					_quiz.incrementSpeltTimes();
					_quiz.sayAndDisplay("Incorrect, try once more!" + _quiz.NL);
					_viewer.displayCross();	
					_quiz.execute();
					return;	
				}
				break;
			case 1:
				if (correct){
					_quiz.sayAndDisplay("Correct!" + _quiz.NL + _quiz.NL);
					_quiz.say(generateAudioReward());
					currentWord.setFaulted();
					_viewer.displayTick();
				}
				else {
					_quiz.incrementSpeltTimes();
					_quiz.sayAndDisplay("Incorrect!"+ _quiz.NL + _quiz.NL);
					_quiz.say(generateAudioComfort());
					currentWord.setFailed();
					_viewer.displayCross();
				}
				break;
			default:
				throw new RuntimeException("Unexpected behaviour. Should not be spelt>1");
			}
		}
		//Attempt to load the next word and start quiz.
		if(_quiz.nextWord()){
			_quiz.execute();
		}
		
		
	}
	
	/**
	 * Festival saying something when user gets something right.
	 * After saying "Correct!".
	 * @return
	 */
	public String generateAudioReward(){
		List<String> audioReward = new ArrayList<String>();
		audioReward.add("Well done?");
		audioReward.add("Nice work?");
		audioReward.add("Good job?");
		
		Collections.shuffle(audioReward);
		return audioReward.get(0);
	}
	
	/**
	 * Festival saying something when user gets something wrong.
	 * After saying "Incorrect!".
	 * @return
	 */
	public String generateAudioComfort(){
		List<String> audioComfort = new ArrayList<String>();
		audioComfort.add("Bad luck.");
		audioComfort.add("Keep going.");
		audioComfort.add("Let's move on?");
		
		Collections.shuffle(audioComfort);
		return audioComfort.get(0);
	}
	

}
