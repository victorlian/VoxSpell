package spellingAid;

import java.io.IOException;

import javax.swing.SwingWorker;

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
public class Speech extends SwingWorker<Void, Void>{
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
	 * @param toSay
	 * @return
	 */
	private void festivalSayIt (String toSay){
		String cmd = "echo \"" + _toSay + "\" | festival --tts ";
		ProcessBuilder builder = new ProcessBuilder ("/bin/bash", "-c", cmd);
		try {
			Process process = builder.start();
			this.execute();
			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return;
	}


	@Override
	protected Void doInBackground() throws Exception {
		System.out.println(_toSay + "\n");
		return null;
	}
}
