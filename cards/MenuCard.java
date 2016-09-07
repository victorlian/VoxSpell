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
public class MenuCard {
	private static JTextArea txtOutput = new JTextArea(10, 30);
	private static final String MENUTEXT = "Hi Victor";
	
	public MenuCard() {}

	public static JPanel createContents() {
		JPanel menuCard = new JPanel();
		menuCard.setLayout(new BorderLayout());

		JScrollPane scroll = new JScrollPane(txtOutput);
		txtOutput.setEditable(false);
		txtOutput.setText(MENUTEXT);
		
		menuCard.add(txtOutput, BorderLayout.CENTER);
		
		return menuCard;
	}
}
