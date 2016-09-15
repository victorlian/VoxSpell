package spellingAid;

import javax.swing.SwingWorker;

public class SpeechSwingWorker extends SwingWorker<Void, Void> {
	private String _cmd;
	
	public SpeechSwingWorker(String toSay) {
		_cmd = "echo \"" + toSay + "\" | festival --tts ";
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		ProcessBuilder builder = new ProcessBuilder ("/bin/bash", "-c", _cmd);
		Process process = builder.start();
		process.waitFor();
		
		return null;
	}
}
