package spellingAid;

/**
 * This is the class representing the speech synthesis.
 * Responsibilities:
 * 1. Pronounce words
 * 2. Read out instructions (txt to speech)
 * 3. Set the voice of festival.
 * 
 * @author Victor & Daniel
 *
 */
public class Speech {
	private String _toSay = "";
	
	/**
	 * This method will say whatever the input is.
	 * @param toSay
	 */
	public void say(String toSay){
		_toSay = toSay;
		festivalSayIt(toSay);
	}
	
	
	/**
	 * This method will set the voice of festival, run this method
	 * prior to saying anything.
	 * @param voice
	 */
	public void setVoice (String voice){
		
	}
	
	
	/**
	 * This is a private helper method that will
	 * allow bash-festival to say the input.
	 * 
	 * It calls the SwingWorker class SpeechSwingWorker
	 * @param toSay
	 * @return
	 */
	private void festivalSayIt (String toSay){
		SpeechSwingWorker ssw = new SpeechSwingWorker(_toSay);
		ssw.execute();
		return;
	}
}
