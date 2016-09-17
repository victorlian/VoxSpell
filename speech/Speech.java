package speech;

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
	private static SpeechSWManager _manager = new SpeechSWManager();
	/**
	 * This method will say whatever the input is.
	 * @param toSay
	 */
	public void say(String toSay){
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
	 * Package Visibility so that SpeechSW can get the manager
	 * @return
	 */
	static SpeechSWManager getManager() {
		return _manager;
	}
	
	/**
	 * This is a private helper method that will
	 * allow bash-festival to say the input.
	 * 
	 * It queues a new SpeechSW. This is to prevent multiple things being read out
	 * @param toSay
	 * @return
	 */
	private void festivalSayIt (String toSay){
		_manager.queueExecution(new SpeechSW(toSay));
		return;
	}
}
