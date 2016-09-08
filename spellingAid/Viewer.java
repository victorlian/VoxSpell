package spellingAid;

/**
 * This interface has all the methods that needs to be
 * implemented in the mainGUI class so that the 
 * functionality classes can update the GUI classes.
 * (The GUI class is developed by Daniel)
 * 
 * @author victor
 *
 */
public interface Viewer {
	
	/**
	 * This method will allow instructions/words to be
	 * appended to the JTextArea.
	 * @param text
	 */
	public void appendText(String text);
	
	/**
	 * Methods manipulating Buttons.
	 */
	public void disableStartButton();
	public void enableStartButton();
	//submission buttons include: repeat+submit.
	public void disableSubmissionButtons();
	public void enableSubmissionButton();
	
	/**
	 * This method will pop up an error message as a Dialogue/Option Pane.
	 */
	public void popErrorMessage(String errorMsg);
	
	/**
	 * This method will be called when a quiz has finished.
	 */
	public void displayMainMenu();
	
	/**
	 * This method will be called when the user can have a video reward.
	 * It should pop up an option pane to allow the user to select if they
	 * want to play the video.
	 */
	public void videoOption();
	
}
