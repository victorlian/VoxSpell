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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import statistics.Statistics;
import statistics.StatsTableModel;

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
	
    private static JLabel _accuracylabel = new JLabel();
	
	private static JComboBox<String> _levelList;
	
	public StatsCard() {
		_statistics = Statistics.getInstance();
		_statsTableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent arg0) {
				 updateAccuracy();
			}
		});
	}
	
	/**
	 * Populate the card with UI objects
	 */
	public JPanel createContents() {
		_statsCard = new JPanel();
		_statsCard.setLayout(new BorderLayout());
		
		
		//Create the panel that holds the level comboBox.
		_levelList = new JComboBox<String>(levelStrings);
		_levelList.addActionListener(this);
		_levelList.setFont(plain20);
		JPanel levelPanel = new JPanel();
		JLabel levelLabel = new JLabel("Select the level to view: ");
		levelLabel.setFont(bold20);
		levelLabel.setForeground(blueInstructionColor);
		levelPanel.add(levelLabel);
		levelPanel.add(_levelList);
		
		//Create the JTable and use the StatsTableModel
        _table = new JTable(_statsTableModel);
        _table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        _table.setFillsViewportHeight(true);
        _table.setFont(plain14);
        _table.getTableHeader().setFont(bold14);
        
        _statistics.passModel(_statsTableModel);
        
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(_table);
        
        //Create the panel for showing accuracy
        JPanel accuracyPanel = new JPanel();
        _accuracylabel.setText("Spelling Accuracy: " + _statistics.getAccuracy(1) + "%");



        //Add the scroll pane to this panel.
        _statsCard.add(levelPanel, BorderLayout.NORTH);
        _statsCard.add(scrollPane, BorderLayout.CENTER);
        _statsCard.add(_accuracylabel, BorderLayout.SOUTH);
        
        
        
		return _statsCard;
	}
	
	/**
	 * Updates the current level selected.
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		String levelString = (String) _levelList.getSelectedItem();
		
		levelString = levelString.substring(levelString.length() - 2);
        levelString = levelString.trim();
        int level = Integer.valueOf(levelString);
        
		_statistics.setLevel(level);
		_accuracylabel.setText("Spelling Accuracy: " + _statistics.getAccuracy(level) + "%");
	}
	
	public void updateAccuracy() {
		String levelString = (String) _levelList.getSelectedItem();
		
		levelString = levelString.substring(levelString.length() - 2);
        levelString = levelString.trim();
        int level = Integer.valueOf(levelString);
        
		_accuracylabel.setText("Spelling Accuracy: " + _statistics.getAccuracy(level) + "%");
	}

	@Override
	public JPanel getPanel() {
		return _statsCard;
	}
}
