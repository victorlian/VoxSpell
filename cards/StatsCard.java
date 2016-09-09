package cards;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import spellingAid.Statistics;
import spellingAid.StatsTableModel;

/**
 * This class populates the contents of a Card
 * Mainly to make the MainGUI class less cluttered and make it
 * easier to modify the card UI
 * @author Daniel
 *
 */
public class StatsCard extends Card {
	private JPanel _statsCard;
	Statistics _statistics = null;
	
	public StatsCard() {
		_statistics = Statistics.getInstance();
	}
	
	/**
	 * Populate the card with UI objects
	 */
	public JPanel createContents() {
		_statsCard = new JPanel();
		_statsCard.setLayout(new GridLayout(1,0));
		
		//Create the JTable and use the StatsTableModel
        final JTable table = new JTable(new StatsTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        _statsCard.add(scrollPane);
        
		return _statsCard;
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
		return _statsCard;
	}
}
