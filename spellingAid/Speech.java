package spellingAid;

import java.io.IOException;

/**
 * This is the class representing the speech synthesis.
 * Responsibilities:
 * 1. Pronounce words
 * 2. Read out instructions (txt to speech)
 * 3. Set the voice of festival.
 * 
 * @author victor
 *
 */
public class Speech {
	/**
	 * This method will read out a word.
	 * 
	 * @param word
	 */
	public void sayWord(Word word){
		festivalSayIt(word.toString());
	}
	
	/**
	 * This method will say whatever the input is.
	 * @param toSay
	 */
	public void saySentence(String toSay){
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
	 * @param toSay
	 * @return
	 */
	private void festivalSayIt (String toSay){
		String cmd = "echo \"" + toSay + "\" | festival --tts ";
		ProcessBuilder builder = new ProcessBuilder ("/bin/bash", "-c", cmd);
		try {
			Process process = builder.start();
			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return;
	}
}
