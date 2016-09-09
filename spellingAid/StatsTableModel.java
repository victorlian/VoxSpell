package spellingAid;

import javax.swing.table.AbstractTableModel;

/**
 * This is the table model for the JTable
 * It reads data from the _masterList in the Statistics class
 * 
 * Basically an Adapter class
 * 
 * @author Daniel
 *
 */

@SuppressWarnings("serial")
public class StatsTableModel extends AbstractTableModel {
	Statistics _statistics = Statistics.getInstance();
	
	/**
	 * There will always be a fixed amount of columns so we return a fixed value
	 */
	public int getColumnCount() {
		return 4;
	}

	/**
	 * Call the same method in the Statistics class
	 */
	public int getRowCount() {
		return _statistics.getRowCount();
	}

	/**
	 * Call the getValueAt method in the Statistics class
	 */
	public Object getValueAt(int arg0, int arg1) {
		return _statistics.getValueAt(arg0, arg1);
	}
	
	/**
	 * Get the appropriate heading from the Statistics Class
	 */
	public String getColumnName(int col) {
		return _statistics.getColumnHeadings(col);
	}
}
