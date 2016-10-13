package cards;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
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
 * Updates on UI: 
 * JLabels for selecting the level to view.
 * Accuracy Chart.
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
	
    JLabel masteredLabel = new JLabel();
    JLabel faultedLabel = new JLabel();
    JLabel failedLabel = new JLabel();
	
	private static JComboBox<String> _levelList;
	
	private static AccuracyChart _chart = new AccuracyChart(0,0,0);
	
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
        accuracyPanel.setPreferredSize(new Dimension(710, 110));
        accuracyPanel.add(_chart);
        

        JPanel labelPanel = new JPanel(new GridLayout(1,0,150,0));
        
        masteredLabel.setFont(plain16);
        faultedLabel.setFont(plain16);
        failedLabel.setFont(plain16);
        
        labelPanel.add(masteredLabel);
        labelPanel.add(faultedLabel);
        labelPanel.add(failedLabel);
        
        accuracyPanel.add(labelPanel);
        
        int[] numbers = getAllThreeNumbers(1);
        _chart.redrawAccuracyChart(numbers[0], numbers[1], numbers[2]);
        
        //Add the scroll pane to this panel.
        _statsCard.add(levelPanel, BorderLayout.NORTH);
        _statsCard.add(scrollPane, BorderLayout.CENTER);
        _statsCard.add(accuracyPanel, BorderLayout.SOUTH);
        
        masteredLabel.setText((masteredString + ": "+_statistics.getMasteredNumber(1)+ " words"));
        faultedLabel.setText((faultedString+ ": "+_statistics.getFaultedNumber(1)+ " words"));
        failedLabel.setText((failedString+ ": "+_statistics.getFailedNumber(1)+ " words"));
        
        
        accuracyPanel.repaint(); 
        
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
       
		int[] numbers = getAllThreeNumbers(level);
        updateAccuracyLabels(numbers);
        _chart.redrawAccuracyChart(numbers[0], numbers[1], numbers[2]);
		
		
	}
	
	public void updateAccuracy() {
		String levelString = (String) _levelList.getSelectedItem();
		
		levelString = levelString.substring(levelString.length() - 2);
        levelString = levelString.trim();
        int level = Integer.valueOf(levelString);
        
        int[] numbers = getAllThreeNumbers(level);
        updateAccuracyLabels(numbers);
        _chart.redrawAccuracyChart(numbers[0], numbers[1], numbers[2]);
	}

	@Override
	public JPanel getPanel() {
		return _statsCard;
	}
	
	/**
	 * This method will return the mastered, faulted and failed 
	 * numbers of the words.
	 * @return
	 */
	public int[] getAllThreeNumbers(int level){
		int[] array = new int[3];
		array[0] = _statistics.getMasteredNumber(level);
		array[1] = _statistics.getFaultedNumber(level);
		array[2] = _statistics.getFailedNumber(level);
		
		return array;
	}
	
	private void updateAccuracyLabels(int[] numbers){
		masteredLabel.setText(masteredString + ": " + numbers[0] + " words");
		faultedLabel.setText(faultedString + ": " + numbers[1] + " words");
		failedLabel.setText(failedString + ": " + numbers[2] + " words");
	}
}
