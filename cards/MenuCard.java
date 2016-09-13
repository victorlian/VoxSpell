package cards;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class populates the contents of a Card
 * Mainly to make the MainGUI class less cluttered and make it
 * easier to modify the card UI
 * @author Daniel
 *
 */
public class MenuCard extends Card {
	private static JTextArea txtOutput = new JTextArea(10, 30);
	private static final String MENUTEXT = "===============================================\n"
			+ " SpellingAid  -  Daniel and Victor\n"
			+ "===============================================\n"
			+ "\n Welcome to the SpellingAid program, to begin, select one of the tabs above\n"
			+ "\n Quiz - Start a quiz"
			+ "\n Statistics - See statistics from this session"
			+ "\n Settings - Change your preferences";
	
	private JPanel _menuCard;
	
	public MenuCard() {}

	public JPanel createContents() {
		_menuCard = new JPanel();
		_menuCard.setLayout(new BorderLayout());

		JScrollPane scroll = new JScrollPane(txtOutput);
		txtOutput.setEditable(false);
		txtOutput.setText(MENUTEXT);
		
		_menuCard.add(scroll, BorderLayout.CENTER);
		
		return _menuCard;
	}

	public JPanel getPanel() {
		return _menuCard;
	}
}
