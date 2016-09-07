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
public class StatsCard {
	public StatsCard() {}

	public static JPanel createContents() {
		JPanel statsCard = new JPanel();

		statsCard.add(new JTextField("StatsCard", 20));

		return statsCard;
	}
}
