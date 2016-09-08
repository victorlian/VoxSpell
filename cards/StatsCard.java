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
public class StatsCard extends Card {
	public StatsCard() {}

	public JPanel createContents() {
		JPanel statsCard = new JPanel();

		statsCard.add(new JTextField("StatsCard", 20));

		return statsCard;
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
}
