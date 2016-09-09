package cards;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class populates the contents of a Card
 * Mainly to make the MainGUI class less cluttered and make it
 * easier to modify the card UI
 * @author Daniel
 *
 */
public class SettingsCard extends Card {
	private JPanel _settingsCard;
	
	public SettingsCard() {}

	public JPanel createContents() {
		_settingsCard = new JPanel();

		_settingsCard.add(new JTextField("SettingsCard", 20));

		return _settingsCard;
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

	@Override
	public JPanel getPanel() {
		return _settingsCard;
	}
}
