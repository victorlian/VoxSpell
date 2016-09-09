package cards;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import spellingAid.Statistics;
import spellingAid.StatsTableModel;
import spellingAid.TestingStatistics;

/**
 * This class populates the contents of a Card
 * Mainly to make the MainGUI class less cluttered and make it
 * easier to modify the card UI
 * @author Daniel
 *
 */
public class StatsCard extends Card implements ActionListener {
	private JPanel _statsCard;
	private static final String[] levelStrings = { "Level 1", 
			"Level 2", 
			"Level 3",
			"Level 4", 
			"Level 5",
			"Level 6",
			"Level 7",
			"Level 8",
			"Level 9",
			"Level 10",
			"Level 11" };
	
	private static Statistics _statistics = null;
	private static StatsTableModel _statsTableModel = new StatsTableModel();
	
	public StatsCard() {
		_statistics = Statistics.getInstance();
	}
	
	/**
	 * Populate the card with UI objects
	 */
	public JPanel createContents() {
		_statsCard = new JPanel();
		_statsCard.setLayout(new BorderLayout());
		
		JComboBox levelList = new JComboBox(levelStrings);
		levelList.addActionListener(this);
		
		//Create the JTable and use the StatsTableModel
        final JTable table = new JTable(_statsTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        _statistics.passModel(_statsTableModel);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        _statsCard.add(levelList, BorderLayout.NORTH);
        _statsCard.add(scrollPane, BorderLayout.CENTER);
        
        //Testing Input
        TestingStatistics.testStatisticsDisplay(_statistics);
        
		return _statsCard;
	}
	
	/**
	 * Updates the current level selected.
	 * Fires an update to the table.
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		JComboBox cb = (JComboBox) ae.getSource();
        String level = (String) cb.getSelectedItem();
        
		_statistics.setLevel(Integer.valueOf(level.substring(level.length() - 1)));
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
		return _statsCard;
	}
}
