import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 * Code modified from https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/TabDemoProject/src/layout/TabDemo.java
 * @author Daniel
 *
 */
public class MainGUI {
	private final static String QUIZ = "Quiz";
	private final static String STATS = "Statistics";
	private final static String SETTINGS = "Settings";
	
	private JFrame _frame = new JFrame("SpellingAid");

	final static int extraWindowWidth = 100;
	
	public MainGUI() {
		
	}
	
	/**
	 * 
	 * @param pane
	 */
	public void addComponentsToPane(Container pane) {
		JTabbedPane tabbedPane = new JTabbedPane();
		 
        //Create Quiz card
        JPanel quizCard = new JPanel();
        quizCard.add(new JTextField("TextField", 20));
        
        //Create Stats card
        JPanel statsCard = new JPanel();
        statsCard.add(new JTextField("TextField", 20));
        
        //Create Settings card
        JPanel settingsCard = new JPanel();
        settingsCard.add(new JTextField("TextField", 20));
        
        //Add all the cards to the tabbedPane layout
        tabbedPane.addTab(QUIZ, quizCard);
        tabbedPane.addTab(STATS, statsCard);
        tabbedPane.addTab(SETTINGS, settingsCard);
 
        pane.add(tabbedPane, BorderLayout.CENTER);
	}
	
	public void createAndShowGUI() {
		//Create and set up the window.
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Set up the content pane.
        addComponentsToPane(_frame.getContentPane());
 
        //Display the window.
        _frame.pack();
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
	}
}
