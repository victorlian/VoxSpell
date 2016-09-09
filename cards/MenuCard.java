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
	private static final String MENUTEXT = "Hi Victor";
	
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

	@Override
	public void appendText(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableStartButton() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableStartButton() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableSubmissionButtons() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableSubmissionButton() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMainMenu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean videoOption() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean levelUpOption() {
		// TODO Auto-generated method stub
		return false;
	}

	public JPanel getPanel() {
		return _menuCard;
	}
}
