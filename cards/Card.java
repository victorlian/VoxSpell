package cards;

import javax.swing.JPanel;

/**
 * This class is abstract and allows classes to extend it.
 * This class makes up the fundamental layout of the GUI
 * @author Daniel
 *
 */
public abstract class Card {
	public abstract JPanel createContents();

	public abstract JPanel getPanel();
}
