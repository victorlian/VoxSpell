package speech;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Code modified from http://stackoverflow.com/questions/22412544/swingworker-queue-and-single-using
 * 
 * We are queuing up all the Speaking and executing them one at a time
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
