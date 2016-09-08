package spellingAid;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cards.Card;
import cards.MenuCard;
import cards.QuizCard;
import cards.SettingsCard;
import cards.StatsCard;

/**
 * This class handles the GUI of the program. It creates a tabbed pane structure.
 * When a method from the Viewer interface is called, we first work out which card
 * is currently selected before then calling the method on that card itself. All the cards
 * extend the Card class which is abstract and also implements the Viewer interface.
 * 
 * Code modified from
 * https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.
 * oracle.com/javase/tutorial/uiswing/examples/layout/TabDemoProject/src/layout/
 * TabDemo.java
 * 
 * @author Daniel
 *
 */
public class MainGUI implements Viewer {
	private final static String MENU = "Menu";
	private final static String QUIZ = "Quiz";
	private final static String STATS = "Statistics";
	private final static String SETTINGS = "Settings";

	private JFrame _frame = new JFrame("SpellingAid");
	private static JTabbedPane _tabbedPane;

	private MenuCard _menuCard = new MenuCard();
	private QuizCard _quizCard = new QuizCard();
	private StatsCard _statsCard = new StatsCard();
	private SettingsCard _settingsCard = new SettingsCard();

	final static int extraWindowWidth = 100;

	public MainGUI() {
	}

	public void addComponentsToPane(Container pane) {
		_tabbedPane = new JTabbedPane();

		// Create Main Menu card
		JPanel menuCard = _menuCard.createContents();
		menuCard.setName(MENU);

		// Create Quiz card
		JPanel quizCard = _quizCard.createContents();
		quizCard.setName(QUIZ);

		// Create Stats card
		JPanel statsCard = _statsCard.createContents();
		statsCard.setName(STATS);

		// Create Settings card
		JPanel settingsCard = _settingsCard.createContents();
		settingsCard.setName(SETTINGS);

		// Add all the cards to the tabbedPane layout
		_tabbedPane.addTab(MENU, menuCard);
		_tabbedPane.addTab(QUIZ, quizCard);
		_tabbedPane.addTab(STATS, statsCard);
		_tabbedPane.addTab(SETTINGS, settingsCard);

		pane.add(_tabbedPane, BorderLayout.CENTER);
	}

	public void createAndShowGUI() {
		// Create and set up the window.
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane.
		addComponentsToPane(_frame.getContentPane());

		// Display the window.
		_frame.pack();
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
	}

	/**
	 * This returns the string identifier of the current card
	 * @return
	 */
	public Card getCurrentCard() {
		//All the cards extend the Card class
		JPanel card = null;
		for (Component comp : _tabbedPane.getComponents()) {
			if (comp.isVisible() == true) {
				card = (JPanel) comp;
				break;
			}
		}

		String name = card.getName();
		switch (name) {
			case MENU:
				return _menuCard;
			case QUIZ:
				return _quizCard;
			case STATS:
				return _statsCard;
			case SETTINGS:
				return _settingsCard;
			default:
				return null;
		}
	}
	
	/**
	 * These functions call the current cards function from the Viewer interface
	 */
	@Override
	public void appendText(String text) {
		getCurrentCard().appendText(text);
	}

	@Override
	public void disableStartButton() {
		getCurrentCard().disableStartButton();
	}

	@Override
	public void enableStartButton() {
		getCurrentCard().enableStartButton();
	}

	@Override
	public void disableSubmissionButtons() {
		getCurrentCard().disableSubmissionButtons();
	}

	@Override
	public void enableSubmissionButton() {
		getCurrentCard().enableSubmissionButton();
	}

	@Override
	public void popErrorMessage(String errorMsg) {
		JOptionPane.showMessageDialog(_frame, errorMsg ,"Error",JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void displayMainMenu() {
		getCurrentCard().displayMainMenu();
	}
}