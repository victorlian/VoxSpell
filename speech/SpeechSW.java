package speech;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.SwingWorker;

import spellingAid.Settings;

/**
 * This class holds the SwingWorker implementation for speaking. It is
 * called by the Speech class.
 * @author Daniel
 *
 */

public class SpeechSW extends SwingWorker<Void, Void> {
	private String _toSay;
	
	public SpeechSW(String toSay) {	
		_toSay = toSay;
	}
	
	@Override
	protected Void doInBackground() throws Exception {	
		PrintWriter writer;
		try {
			writer = new PrintWriter(".newSpeech.scm", "UTF-8");
			writer.println(Settings.getVoice());
			writer.println("(SayText \"" + _toSay + "\")");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ProcessBuilder builder = new ProcessBuilder ("/bin/bash", "-c", "festival -b .newSpeech.scm");
		Process process = builder.start();
		process.waitFor();
		
		return null;
	}
	
	/**
	 * This method tells the Manager that we have finished executing and we can continue
	 */
	protected void done() {
		Speech.getManager().setExecuting(false);
        Speech.getManager().executeNext();
	}
}
