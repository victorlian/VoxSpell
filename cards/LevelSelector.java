package cards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import spellingAid.Settings;
import spellingAid.Statistics;

/**
 * This class handles the Level Selection for New Quiz and Review Quiz.
 * 
 * New Quiz is a normal dropdown box for level selection.
 * 
 * Review quiz is a custom UI which has the dropdown box for level selection along with a
 * JTextArea showing how many words have been failed for that level.
 * 
 * @author Daniel
 *
 */

public class LevelSelector implements ActionListener {
	private JPanel _quizCard;
	private String[] levelOptions = { "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7",
			"Level 8", "Level 9", "Level 10", "Level 11" };
	private JTextArea _textDisplay;
	private JComboBox<String> _levelComboBox;

	public LevelSelector(JPanel quizCard) {
		_quizCard = quizCard;
	}
	
	/**
	 * This method allows the user to select a level if we haven't started a quiz before.
	 * Otherwise it defaults to the last level a Quiz was on.
	 * @return
	 */
	public int NewQuizSelector() {
		// New Quiz - select starting level
		
		//If this is the users first time, prompt them to select a level
		if (Settings.isFirstTime()) {
			String level = (String) JOptionPane.showInputDialog(_quizCard, "Please select the starting level: ",
					"Select a starting level", JOptionPane.PLAIN_MESSAGE, null, // No Icon
					levelOptions, "Level 1");

			// Handling Cancel button
			if ((level != null) && (level.length() > 0)) {
				return Integer.valueOf(level.substring(6));
			} else {
				return -1;
			}
		} else {
			//If it isn't their first time, grab the level from Settings class
			return Settings.getLevel();
		}
	}

	/**
	 * This method handles the Review Quiz level selector. Has an additional textArea to display number
	 * of failed words. This value is generated in the Statistics class.
	 * 
	 * Code modified from
	 * http://stackoverflow.com/questions/789517/java-how-to-create-a-custom-dialog-box
	 * 
	 * @return
	 */
	public int ReviewQuizSelector() {
		_levelComboBox = new JComboBox<String>(levelOptions);
		_levelComboBox.addActionListener(this);

		_textDisplay = new JTextArea();
		_textDisplay.setEditable(false);
		
		//Get number of failed words for level 1 to populate textArea on first open
		_textDisplay.setText(Statistics.numberOfFailedWords(1) + " words");

		final JComponent[] inputs = new JComponent[] { new JLabel("Levels"), _levelComboBox,
				new JLabel("Words Incorrect"), _textDisplay };

		int result = JOptionPane.showConfirmDialog(_quizCard, inputs, "Select a Review level",
				JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			// Add one so it aligns with level numbering scheme
			return _levelComboBox.getSelectedIndex() + 1;
		} else {
			// Handling cancel
			return -1;
		}
	}
	
	/**
	 * Change the textArea as we change the level in the dropdown box
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int level = _levelComboBox.getSelectedIndex() + 1;
		_textDisplay.setText(Statistics.numberOfFailedWords(level) + " words");
	}
}
