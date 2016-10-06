package cards;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import fileIO.Images;

/**
 * This class populates the contents of a Card
 * Mainly to make the MainGUI class less cluttered and make it
 * easier to modify the card UI
 * @author Daniel
 * 
 * UI updates include:
 * added icons for tabbed pane
 * added new labels as instructions.
 * added the welcome diagram.
 * @author Victor
 *
 */
public class WelcomeCard extends Card {
	private static JTextArea _txtOutput = new JTextArea(7, 10);
	private static JLabel _welcomeImage;
	
	private static final String MENUTEXT = ""
			+ "==========================\n"
			+ "           Welcome to VoxSpell ! \n"
			+ "==========================\n"
			+ "\n\nClick on the Quiz tab to get started!";
	
	private JPanel _menuCard;
	
	public WelcomeCard() {}

	public JPanel createContents() {
		_menuCard = new JPanel();
		_menuCard.setLayout(new BorderLayout());
		
		_txtOutput.setFont(welcomeFont);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(6,1));
		
		JLabel label0 = new JLabel("");
		JLabel label1 = new JLabel(" Need Help?");
		JLabel label2 = new JLabel("Quiz: start a new quiz or review your mistakes!");
		JLabel label3 = new JLabel("Statistics: view your spelling history!");
		JLabel label4 = new JLabel("Settings: make your changes to VoxSpell!");
		JLabel label5 = new JLabel("Refer to the user manual for more information.");
		
		label0.setFont(welcomeFont);
		label1.setFont(welcomeFont);
		label2.setFont(welcomeFont);
		label3.setFont(welcomeFont);
		label4.setFont(welcomeFont);
		label5.setFont(welcomeFont);
		label5.setForeground(new Color(50, 0, 240)); //dark blue colour
		
		labelPanel.add(label0);
		labelPanel.add(label1);
		labelPanel.add(label2);
		labelPanel.add(label3);
		labelPanel.add(label4);
		labelPanel.add(label5);
		
		
		JScrollPane scroll = new JScrollPane(_txtOutput);
		_txtOutput.setEditable(false);
		_txtOutput.setText(MENUTEXT);
		panel.add(scroll, BorderLayout.NORTH);
		panel.add(labelPanel,BorderLayout.CENTER);
		
		_welcomeImage = new JLabel(Images.getInstance().getWelcomeIcon());
		
		_menuCard.add(_welcomeImage, BorderLayout.WEST);
		_menuCard.add(panel, BorderLayout.CENTER);
		
		return _menuCard;
	}

	public JPanel getPanel() {
		return _menuCard;
	}
}
