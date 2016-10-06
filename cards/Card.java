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
 * @author Victor
 *
 */
public abstract class Card {
	//Colour and Font fields. (public to other classes in this package)
	public static final String fontName = "Century";
	 
	protected static final Color blackColour = new Color (0, 0, 0);
	protected static final Color redColor = new Color (240, 10, 10);
	protected static final Color blueInstructionColor = new Color(50, 0, 240);
	//Bold 16, 20 used for instructions
	protected static final Font bold16 = new Font(fontName, Font.BOLD, 16);
	protected static final Font bold20 = new Font (fontName, Font.BOLD, 20);
	//Plain 16 used for JLabels in welcome
	protected static final Font plain16 = new Font(fontName, Font.PLAIN, 16);
	//Plain 20 used for text output in text field
	protected static final Font plain20 = new Font(fontName, Font.PLAIN, 20);
	//Plain 35 used in Text input.
	protected static final Font plain35 = new Font(fontName, Font.PLAIN, 35);
	
	//Used in Jtrees
	protected static final Font plain14 = new Font(fontName, Font.PLAIN, 14);
	protected static final Font bold14 = new Font(fontName, Font.BOLD, 14);


	public abstract JPanel createContents();

	public abstract JPanel getPanel();
}
