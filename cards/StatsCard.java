package cards;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
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
	private static JTable _table;
	private static JLabel _label = new JLabel();
	
	public StatsCard() {
		_statistics = Statistics.getInstance();
	}
	
	/**
	 * Populate the card with UI objects
	 */
	public JPanel createContents() {
		_statsCard = new JPanel();
		_statsCard.setLayout(new BorderLayout());
		
		JComboBox<String> levelList = new JComboBox<String>(levelStrings);
		levelList.addActionListener(this);
		
		//Create the JTable and use the StatsTableModel
        _table = new JTable(_statsTableModel);
        _table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        _table.setFillsViewportHeight(true);
        
        _statistics.passModel(_statsTableModel);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(_table);

        //Add the scroll pane to this panel.
        _statsCard.add(levelList, BorderLayout.NORTH);
        _statsCard.add(scrollPane, BorderLayout.CENTER);
        _statsCard.add(_label, BorderLayout.SOUTH);
        
        _label.setText("Spelling Accuracy: " + _statistics.getAccuracy(1) + "%");
        
		return _statsCard;
	}
	
	/**
	 * Updates the current level selected.
	 * Fires an update to the table.
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		@SuppressWarnings("unchecked")
		JComboBox<String> cb = (JComboBox<String>) ae.getSource();
        String levelString = (String) cb.getSelectedItem();
        
        levelString = levelString.substring(levelString.length() - 2);
        levelString = levelString.trim();
        int level = Integer.valueOf(levelString);
        
		_statistics.setLevel(level);
		_label.setText("Spelling Accuracy: " + _statistics.getAccuracy(level) + "%");
	}

	@Override
	public JPanel getPanel() {
		return _statsCard;
	}
}
