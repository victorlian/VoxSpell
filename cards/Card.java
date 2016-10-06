package cards;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;

/**
 * This class is abstract and allows classes to extend it.
 * This class makes up the fundamental layout of the GUI
 * @author Daniel
 * 
 * UI updates:
 * added colours and fonts.
 *
 */
public abstract class Card {
	//Colour and Font fields. (public to other classes in this package)
	public static final String fontName = "Century";
	 
	protected static final Color blackColour = new Color (0, 0, 0);
	protected static final Color redColor = new Color (240, 10, 10);
	protected static final Color blueInstructionColor = new Color(50, 0, 240);
	protected static final Font instructionFont = new Font(fontName, Font.BOLD, 16);
	protected static final Font outputFont = new Font(fontName, Font.PLAIN, 20);
	protected static final Font inputFont = new Font(fontName, Font.PLAIN, 35);
	protected static final Font welcomeFont = new Font(fontName, Font.PLAIN, 16);

	public abstract JPanel createContents();

	public abstract JPanel getPanel();
}
