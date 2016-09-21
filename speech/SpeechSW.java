package speech;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.SwingWorker;

import spellingAid.FileManager;
import spellingAid.Settings;

/**
 * This class holds the SwingWorker implementation for speaking. It is
 * called by the Speech class.
 * @author Daniel
 *
 */

public class SpeechSW extends SwingWorker<Void, Void> {
	private String _toSay;
	private FileManager _fm = new FileManager();
	
	public SpeechSW(String toSay) {	
		_toSay = toSay;
	}
	
	/**
	 * In order to have different voices, we must write to a .scm file. 
	 * The .scm file includes first, the voice, then the speech to say
	 * 
	 * So here we create a new hidden file, overwriting previous ones,
	 * insert the voice we want (if it is default, we leave it blank),
	 * then do the (SayText "This is a nightmare to figure out").
	 * 
	 * Then we call "festival -b .newSpeech.scm" which seems to work.
	 * So it's good enough :)
	 */
	@Override
	protected Void doInBackground() throws Exception {	
		PrintWriter writer;
		String filePath = _fm.getAbsolutePath(".newSpeech.scm");
		try {
			writer = new PrintWriter(filePath, "UTF-8");
			writer.println(Settings.getVoice());
			writer.println("(SayText \"" + _toSay + "\")");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ProcessBuilder builder = new ProcessBuilder ("/bin/bash", "-c", "festival -b " + filePath);
		Process process = builder.start();
		process.waitFor();
		
		return null;
	}
	
	/**
	 * This method tells the Manager that we have finished executing and we can continue to the
	 * next one
	 */
	protected void done() {
		Speech.getManager().setExecuting(false);
        Speech.getManager().executeNext();
	}
}
