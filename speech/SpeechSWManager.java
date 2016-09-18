package speech;

import java.util.LinkedList;
import java.util.Queue;

/**
 * What are we doing here? Good Question.
 * 
 * I think we are putting all the objects into a queue and then executing one by one to avoid
 * overlap of speaking. Adding 'synchronized' seems to make it work, something to do with Thread
 * safety or something.
 * 
 * Code modified from http://stackoverflow.com/questions/22412544/swingworker-queue-and-single-using
 *
 * @author daniel
 *
 */
public class SpeechSWManager {
	private static Queue<SpeechSW> _queue;
	private static boolean _executing;
	
	public SpeechSWManager() {
		_queue = new LinkedList<SpeechSW>();
	}
	
	public synchronized void queueExecution(SpeechSW newSpeech) {
        _queue.add(newSpeech);
        if (!_executing) {
        	executeNext();
        }
    }
	
	public synchronized void executeNext() {
        SpeechSW newSpeech = _queue.poll();
        if (newSpeech != null) {
            setExecuting(true);
            newSpeech.execute();
        }
    }

    public void setExecuting(boolean b) {
        _executing = b;
    }
}
