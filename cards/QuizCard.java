package cards;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import fileIO.Images;
import quiz.NewQuiz;
import quiz.Quiz;
import quiz.ReviewQuiz;
import quiz.Submission;
import spellingAid.MessageType;
import spellingAid.Option;
import spellingAid.Viewer;
import video.VideoReward;

/**
 * This class populates the contents of a Card
 * Mainly to make the MainGUI class less cluttered and make it
 * easier to modify the card UI
 * @author Daniel
 * 
 * UI updates include: 
 * Changed the colour of the instruction set.
 * Layout the buttons longer to fit the screen.
 * Addition of yesNoIcon (with auto hiding) 
 * Hiding code adapted from http://stackoverflow.com/questions/18397282/make-jdialog-disappear-after-a-few-seconds;
 * Add progress bar, add scoring.
 * @author Victor
 *
 */
public class QuizCard extends Card implements ActionListener, Viewer {
	private static QuizCard  _thisCard = null;
	
	private static final String SUBMIT = "Submit";
	private static final String SAYAGAIN = "Repeat Word";
	private static final String NEWQUIZ = "New Quiz";
	
	private JPanel _quizCard;
	private static JTextField txtInput = new JTextField("Spell your words here!");
	private static JButton btnSubmit = new JButton(SUBMIT);
	private static JButton btnSayAgain = new JButton(SAYAGAIN);
	private static JButton btnNewQuiz = new JButton(NEWQUIZ);
	private static JTextArea txtOutput = new JTextArea(6, 20);
	private static JLabel levelIndicator = new JLabel("");
	 //need the empty screen here as place holder.
	private static JLabel yesNoIcon = new JLabel(Images.getInstance().getBlankIcon());
	private Timer autoHideTimer = new Timer(1500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            hideYesNo();
        }
    });
	private JProgressBar progressBar = new JProgressBar();
	private JLabel scoreLabel = new JLabel("Score: 0");
	
	private Quiz _quiz;
	private Option _option;
	private boolean finished;
	
	public enum QuizType {
		NORMAL, REVIEW, CANCEL;
	}
	
	private QuizCard() {}
	
	/**
	 * Since this is singleton, get instance of a Quiz card
	 * @return
	 */
	public static QuizCard getInstance() {
		if (_thisCard == null) {
			_thisCard = new QuizCard();
		}
		
		return _thisCard;
	}
	
	
	/**
	 * Populates the Quiz Card UI
	 */
	public JPanel createContents() {
		_quizCard = new JPanel();		
		
		_quizCard.setLayout(new BorderLayout());
		
		//The top panel. (contains labels and progress bar)
		JPanel topPanel = setupTopPanel();
		
		//The middle panel. (a scroll)
		JScrollPane textBox = new JScrollPane(txtOutput);
		txtOutput.setFont(Card.outputFont);
		txtOutput.setEditable(false);
		
		//The bottom panel. (contains: label, inputtext, yesNoIconLabel, buttons)
		JPanel bottomPanel = setupBottomPanel();
       
		_quizCard.add(topPanel, BorderLayout.NORTH);
		_quizCard.add(textBox, BorderLayout.CENTER);
		_quizCard.add(bottomPanel, BorderLayout.SOUTH);
		
		//Padding inside JPanel
		_quizCard.setBorder(new EmptyBorder(5,10,0,10));
		
		addActionListeners();
		disableSubmissionButtons();
		
		return _quizCard;
	}
	
	/**
	 * This method will set up the top panel properly and return it.
	 * (Private helper method for creating contents.)
	 * @return
	 */
	private JPanel setupTopPanel(){
		//The top of text field contains a panel with a progress bar, a total score(Label), 
		//and a level indicator(Label).
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,0, 120, 0));

		levelIndicator.setFont(Card.instructionFont);
		levelIndicator.setForeground(Card.blueInstructionColor);
		topPanel.add(levelIndicator);

		progressBar.setStringPainted(true);
		topPanel.add(progressBar);

		scoreLabel.setFont(Card.outputFont);
		topPanel.add(scoreLabel);
		return topPanel;	
	}
	
	/**
	 * This method will set up the bottom panel properly and return it.
	 * (Private helper method for creating contents.)
	 * @return
	 */
	private JPanel setupBottomPanel(){
		txtInput.setFont(Card.inputFont);
		txtInput.requestFocus();
		txtInput.setPreferredSize(new Dimension(700,50));
		//Now insert a panel for the textInput textField, so a JLabel could be used along side it.
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());


		inputPanel.add(txtInput, BorderLayout.WEST);
		inputPanel.add(yesNoIcon, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());

		JLabel label = new JLabel("Enter your spelling below:");
		label.setFont(Card.instructionFont);
		label.setForeground(Card.blueInstructionColor);
		bottomPanel.add(label, BorderLayout.NORTH);
		bottomPanel.add(inputPanel, BorderLayout.CENTER);
		
		btnNewQuiz.setPreferredSize(new Dimension(220,40));
		btnSayAgain.setPreferredSize(new Dimension(220,40));
		btnSubmit.setPreferredSize(new Dimension(220,40));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(btnNewQuiz);
		buttonPanel.add(btnSayAgain);
		buttonPanel.add(btnSubmit);
		
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

		return bottomPanel;
	}
	
	/**
	 * Adds the actionListeners to the buttons
	 */
	public void addActionListeners() {
		btnNewQuiz.addActionListener(this);
		btnSubmit.addActionListener(this);
		btnSayAgain.addActionListener(this);
		txtInput.addActionListener(this);
	}
	
	/**
	 * ActionListener class.
	 * 
	 * Handles all the button presses and the actions that occur.
	 */
	public void actionPerformed(ActionEvent e) {
		String text;
		if (e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			text = button.getText();
		} else {
			text = "textField";
		}
		
		//Just for error checking in case a cancel button was pushed
		int level = -1;
		
		//Actions depending on the button
		switch(text) {
			//New Quiz button pressed
			case NEWQUIZ:
				//Clear the textInput, and reset progress bar.
				txtInput.setText("");
				progressBar.setValue(0);
				
				QuizType quizType = quizTypeDialog();
				//Give the user dialog options
				if (quizType.equals(QuizType.REVIEW)) {
					//Review Quiz
					
					//Get the level of the quiz
					level = new LevelSelector(_quizCard).ReviewQuizSelector();
					
					//If cancel was hit in the Level selector
					if (level == -1) {
						return;
					}
					
					//Get the ReviewQuiz with the appropriate levels
					_quiz = ReviewQuiz.getInstance(this, level);
					levelIndicator.setText("Review Quiz - Level: " + _quiz.getCurrentLevel());
					txtOutput.setText("");
					
					finished = false;
				} else if (quizType.equals(QuizType.NORMAL)){
					//Normal Quiz
					
					//Get the level of the quiz
					level = new LevelSelector(_quizCard).NewQuizSelector();
					
					//If cancel was hit in the Level selector
					if (level == -1) {
						return;
					}
					
					//Get the NewQuiz with the appropriate levels
					_quiz = NewQuiz.getInstance(this, level);
					levelIndicator.setText("New Quiz - Level: " + _quiz.getCurrentLevel());
					txtOutput.setText("");
					
					finished = false;
				} else {	
					//Cancel Button - QuizType.CANCEl
					return;
				}
					
				//_option allows us to then perform polymorphic action.
				_option = _quiz;
				
				
				
				disableStartButton();
				break;
			case "textField":
				//Pressing Enter to submit
				//No break statement as we want to flow through to Submit
				if (finished) {
					return;
				}
			case SUBMIT:
				if (_quiz == null) {
					throw new RuntimeException("Quiz is null");
				}
				//Ensure text entry is valid by first trimming the spaces in the string
				//Then convert to a char array to check each character
				char[] textInput = txtInput.getText().trim().toCharArray();
				if (textInput.length==0) {
					popMessage("Please enter something in the text field!", MessageType.ERROR);
					return;
				} else {
					for (int i=0; i<textInput.length; i++) {
						if (textInput[i] == '\'') {
							
						} else if (!Character.isLetter(textInput[i])) {
							popMessage("Invalid Characters Entered", MessageType.ERROR);
							//Repeat the word
							_quiz.repeatWord();
							return;
						}
					}
				}
				
				Submission submission = new Submission(_quiz, txtInput.getText(), this);
				_option = submission;
				
				txtInput.setText("");
				break;
			case SAYAGAIN:
				_quiz.repeatWord();
				return;
		}
		
		if (_option != null) {
			_option.execute();
		}
	}
	
	/**
	 * Return true if Review quiz
	 * Return false if Normal quiz
	 * @return
	 */
	public QuizCard.QuizType quizTypeDialog() {
		String[] quizOptions = {"New Quiz", "Review Quiz"};
		String quizType = (String) JOptionPane.showInputDialog(
                _quizCard,
                "Please select a type of Quiz: ",
                "Select a Quiz Type",
                JOptionPane.PLAIN_MESSAGE,
                null, //No Icon
                quizOptions,
                "New Quiz");
		
		//Handling Cancel button
		if ((quizType != null) && (quizType.length() > 0)) {
			if (quizType.equals(quizOptions[0])) {
				return QuizType.NORMAL;
			} else {
				return QuizType.REVIEW;
			}
		} else {
			return QuizType.CANCEL;
		}
	}
	
	public void appendText(String text) {
		txtOutput.append(text);
	}

	public void disableStartButton() {
		btnNewQuiz.setEnabled(false);
	}

	public void enableStartButton() {
		btnNewQuiz.setEnabled(true);
	}

	public void disableSubmissionButtons() {
		btnSayAgain.setEnabled(false);
		btnSubmit.setEnabled(false);
		
		finished = true;
	}

	public void displayMainMenu() {
		txtOutput.append("Quiz Completed\nPlease Select 'New Quiz' to start another quiz.");
	}

	public JPanel getPanel() {
		return _quizCard;
	}

	public void enableSubmissionButtons() {
		btnSayAgain.setEnabled(true);
		btnSubmit.setEnabled(true);
	}
	
	public void popMessage(String msg, MessageType typeOfMessage) {
		if (typeOfMessage == MessageType.ERROR) {
			JOptionPane.showMessageDialog(getPanel(), msg, "Error", JOptionPane.ERROR_MESSAGE);
		} else if (typeOfMessage == MessageType.WARNING) {
			JOptionPane.showMessageDialog(getPanel(), msg, "Warning", JOptionPane.WARNING_MESSAGE);
		} else if (typeOfMessage == MessageType.INFORMATION) {
			JOptionPane.showMessageDialog(getPanel(), msg, "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public boolean videoOption() {
		int result = JOptionPane.showConfirmDialog(getPanel(), "Would you like to see the video reward?", "Reward!",
				JOptionPane.YES_NO_OPTION);

		if (result == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean levelUpOption() {
		int result = JOptionPane.showConfirmDialog(getPanel(), "Would you like to level up?", "Level Up!",
				JOptionPane.YES_NO_OPTION);

		if (result == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void playVideo() {
		VideoReward vidReward = VideoReward.getInstance();
		vidReward.createContents();
	}

	@Override
	public boolean spellWord() {
		int result = JOptionPane.showConfirmDialog(null, "Would you like to hear the spelling of the word?", "Hear Spelling!",
				JOptionPane.YES_NO_OPTION);

		if (result == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void displayTick() {
		yesNoIcon.setIcon(Images.getInstance().getYesIcon());
		autoHideTimer.restart();
		
	}

	@Override
	public void displayCross() {
		yesNoIcon.setIcon(Images.getInstance().getNoIcon());
		autoHideTimer.restart();
		
	}

	@Override
	public void hideYesNo() {
		yesNoIcon.setIcon(Images.getInstance().getBlankIcon());
		autoHideTimer.stop();
	}

	@Override
	public void setProgess(int percentage) {
		progressBar.setValue(percentage);
		
	}
	
	/**
	 * This method will update the score.
	 * The score will be shown red if negative.
	 * @param score
	 */
	@Override
	public void setScore(int score) {
		if(score < 0){
			scoreLabel.setForeground(Card.redColor);;
		}
		else {
			scoreLabel.setForeground(Card.blackColour);
		}
		
		scoreLabel.setText("Score: " + score);
	}
	
	/**
	 * This method will terminate the current quiz, ready for
	 * another quiz to begin.
	 * 
	 * To acheive this, need to:
	 * 1. Reinitialize quiz object. (automatically done when clicking on new quiz)
	 * 2. Clear all the text field and text inputs.
	 * 3. Reset all the buttons. 
	 */
	public void terminateCurrentQuiz(){
		progressBar.setValue(0);
		txtInput.setText("");
		txtOutput.setText("");
		enableStartButton();
		disableSubmissionButtons();
	}
}