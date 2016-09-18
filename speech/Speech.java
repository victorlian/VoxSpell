package speech;

/**
 * This is the class representing the speech synthesis.
 * 
 * Basically what happens is:
 * 1. We create a new SpeechSW with the correct words we want to say
 * 2. We then queue it's execution in SpeechSWManager
 * 3. Here be dragons (or follow through to SpeechSWManager to see how it works)
 * 4. Speech occurs one at a time without overlap and without the GUI freezing.
 * 
 * Additionally, I solved the pause issues by just creating a new SpeechSW after each pause,
 * it seems to work well enough since I couldn't find anything in Festival documentation for it
 * 
 * @author Daniel
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
	 * Package Visibility so that SpeechSW can get the manager
	 * @return NOTHING, cause, like, the JavaDoc commenting put this here, so like, I have to put something right?
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
	 * @return goto line 27
	 */
	private void festivalSayIt (String toSay){
		_manager.queueExecution(new SpeechSW(toSay));
		return;
	}
}
