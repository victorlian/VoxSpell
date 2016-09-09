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
	}

	@Override
	public void disableStartButton() {
	}

	@Override
	public void enableStartButton() {
	}

	@Override
	public void disableSubmissionButtons() {
	}

	@Override
	public void enableSubmissionButton() {
	}
	
	@Override
	public void displayMainMenu() {
	}

	@Override
	public JPanel getPanel() {
		return _settingsCard;
	}
}
