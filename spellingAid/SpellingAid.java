package spellingAid;

/**
 * The main class. Entry point of the program.
 * @author Victor
 *
 */
public class SpellingAid {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainGUI().createAndShowGUI();
			}
		});
	}
}
