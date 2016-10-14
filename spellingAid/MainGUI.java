package spellingAid;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cards.Card;
import cards.DebugCard;
import cards.QuizCard;
import cards.SettingsCard;
import cards.StatsCard;
import cards.WelcomeCard;
import fileIO.FileManager;
import fileIO.Images;
import video.VideoEditor;
import video.VideoReward;

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
public class MainGUI {
	private final static String MENU = "Welcome";
	private final static String QUIZ = "Quiz";
	private final static String STATS = "Statistics";
	private final static String SETTINGS = "Settings";

	private JFrame _frame = new JFrame("SpellingAid");
	private static JTabbedPane _tabbedPane;
	private Images _images = Images.getInstance();

	private WelcomeCard _menuCard = new WelcomeCard();
	private QuizCard _quizCard = QuizCard.getInstance();
	private StatsCard _statsCard = new StatsCard();
	private SettingsCard _settingsCard = new SettingsCard();
	
	public final static int FRAME_WIDTH=800;
	public final static int FRAME_HEIGHT=550;

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
		
		//DEBUG
		//JPanel debugCard = new DebugCard().createContents();
		//debugCard.setName("Debug");

		// Add all the cards to the tabbedPane layout
		_tabbedPane.setFont(Card.bold20);
		_tabbedPane.addTab(MENU, _images.getHiIcon(), menuCard);
		_tabbedPane.addTab(QUIZ, _images.getQuizIcon(), quizCard);
		_tabbedPane.addTab(STATS, _images.getStatsIcon(), statsCard);
		_tabbedPane.addTab(SETTINGS, _images.getSettingsIcon(), settingsCard);
		
		//_tabbedPane.addTab("Debug", debugCard);

		pane.add(_tabbedPane, BorderLayout.CENTER);
	}

	public void createAndShowGUI() {
		preLaunchChecks();
		
		// Create and set up the window.
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane.
		addComponentsToPane(_frame.getContentPane());

		// Display the window.
		_frame.pack();
		_frame.setVisible(true);
		_frame.setResizable(false);
		
		_frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		_frame.setLocationRelativeTo(null);
		
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
		
		// BAM! POLYMORPHISM, I think...
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
	 * This method will immediately start after the GUI has been created.
	 * Used for generating the edited video in background.
	 * @author victor
	 */
	private void checkAndGenerateVideo(){
		FileManager fm = new FileManager();
		if (fm.checkFileExist(fm.REVERSEVIDEO)){
			VideoReward.getInstance().setEditedVideoAvaliable();
			return;
		}
		else {
			new VideoEditor().execute();
		}
	}
	
	/**
	 * This method will immediately start after the GUI has been created.
	 * Used for generating the images/icons used in the GUI.
	 * @author victor
	 */
	private void loadImages(){
		Images.getInstance();
	}
	
	/**
	 * This method will immediately start after the GUI has been created.
	 * It will check if the default wordlist exist. If it does not,
	 * an error message will be shown and system will close.
	 */
	private void checkWordList(){
		if(!new FileManager().checkWordList()){
			JOptionPane.showMessageDialog(null, "The wordlist file is missing. \n"
					+ "Please make sure it is in the correct directory. (same as the jar file)", 
					"Wordlist file missing!", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

	}
	
	/**
	 * This method uses the file manager to load stats file.
	 * If a statsFile is missing, create one.
	 */
	private void loadStatsFile(){
		new FileManager().loadStatsFile();
	}
	
	/**
	 * This method will load the dependency files to make sure the GUI 
	 * components are loaded properly.
	 */
	private void preLaunchChecks(){
		checkAndGenerateVideo();
		loadImages();
		checkWordList();
		loadStatsFile();
	}
	
}
