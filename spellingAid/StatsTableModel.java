package spellingAid;

import javax.swing.table.AbstractTableModel;

/**
 * This is the table model for the JTable
 * It reads data from the _masterList in the Statistics class
 * @author Daniel
 *
 */

@SuppressWarnings("serial")
public class StatsTableModel extends AbstractTableModel {
	Statistics _statistics = Statistics.getInstance();
	
	public StatsTableModel() {
		_statistics.passModel(this);
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		return _statistics.getRowCount();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return _statistics.getValueAt(arg0, arg1);
	}

	public String getColumnName(int col) {
		return _statistics.getColumnHeadings(col);
	}
}
