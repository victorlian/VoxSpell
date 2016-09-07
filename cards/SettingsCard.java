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
public class SettingsCard {
	public SettingsCard() {}

	public static JPanel createContents() {
		JPanel settingsCard = new JPanel();

		settingsCard.add(new JTextField("SettingsCard", 20));

		return settingsCard;
	}
}
