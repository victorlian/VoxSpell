package cards;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
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
 * The new Quiz option is made easier. 
 * @author Victor
 *
 */
public class QuizCard extends Card implements ActionListener, Viewer {
	private static QuizCard  _thisCard = null;
	
	private static final String SUBMIT = "Submit";
	private static final String SAYAGAIN = "Repeat Word";
	private static final String NEWQUIZ = "New Quiz";
	private static final String ClickNewQuiz = "Click on the New Quiz button below to get started!";
	
	private static QuizType _quizType = QuizType.CANCEL;
	private static JFrame optionFrame = new JFrame();
	final ButtonGroup group = new ButtonGroup();
	
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
	
	private QuizCard() {
		txtOutput.setText(ClickNewQuiz);
	}
	
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
		txtOutput.setFont(Card.plain20);
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

		levelIndicator.setFont(Card.bold14);
		levelIndicator.setForeground(Card.blueInstructionColor);
		topPanel.add(levelIndicator);

		progressBar.setStringPainted(true);
		topPanel.add(progressBar);

		scoreLabel.setFont(Card.plain20);
		topPanel.add(scoreLabel);
		return topPanel;	
	}
	
	/**
	 * This method will set up the bottom panel properly and return it.
	 * (Private helper method for creating contents.)
	 * @return
	 */
	private JPanel setupBottomPanel(){
		txtInput.setFont(Card.plain35);
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
		label.setFont(Card.bold16);
		label.setForeground(Card.blueInstructionColor);
		bottomPanel.add(label, BorderLayout.NORTH);
		bottomPanel.add(inputPanel, BorderLayout.CENTER);
		
		btnNewQuiz.setPreferredSize(new Dimension(220,40));
		btnSayAgain.setPreferredSize(new Dimension(220,40));
		btnSubmit.setPreferredSize(new Dimension(220,40));
		
		btnNewQuiz.setFont(plain16);
		btnSayAgain.setFont(plain16);
		btnSubmit.setFont(plain16);
		
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
				createSimpleDialogBox();
				_option = null;
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
					popMessage("Please enter your spelling in the text field!", MessageType.ERROR);
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
			
			case "Confirm":
				String command = group.getSelection().getActionCommand();
				//Different options for setting the new Quiz.
				if (command.equals("new")){
					_quizType = QuizType.NORMAL;
				}
				else if (command.equals("review")){
					_quizType = QuizType.REVIEW;
				}
				else{
					_quizType = QuizType.CANCEL;
				}
				optionFrame.setVisible(false);
				
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
				

		}
		
		if (_option != null) {
			_option.execute();
		}
	}
	
	/**
	 * This method will show a frame that allows the user to select the different types of quiz.
	 */
    private void createSimpleDialogBox() {
        final int numButtons = 3;
        JRadioButton[] radioButtons = new JRadioButton[numButtons];
                

        JButton confirmButton = null;

        final String newCommand = "new";
        final String reviewCommand = "review";
        final String cancelCommand = "cancel";

        radioButtons[0] = new JRadioButton("New Quiz - \nstart a new quiz of 10 words.");
        radioButtons[0].setActionCommand(newCommand);

        radioButtons[1] = new JRadioButton("Review Quiz - \nstart a review quiz to go over your mistakes.");
        radioButtons[1].setActionCommand(reviewCommand);

        radioButtons[2] = new JRadioButton("Cancel");
        radioButtons[2].setActionCommand(cancelCommand);


        for (int i = 0; i < numButtons; i++) {
            radioButtons[i].setFont(plain14);
        	group.add(radioButtons[i]);
        }
        
        radioButtons[0].setSelected(true);

        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(this);

        
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Select the type of quiz to start: ");
        label.setFont(bold20);

        panel.setLayout(new GridLayout(0,1,0,30));
        panel.add(label);

        for (int i = 0; i < numButtons; i++) {
            panel.add(radioButtons[i]);
        }

        JPanel pane = new JPanel(new BorderLayout());
        pane.add(panel, BorderLayout.PAGE_START);
        pane.add(confirmButton, BorderLayout.PAGE_END);
        
		optionFrame.getContentPane().add(pane);
		optionFrame.setVisible(true);

		// Display the window.
		optionFrame.pack();
		optionFrame.setVisible(true);
		optionFrame.setResizable(false);
		
		optionFrame.setTitle("Type Of Quiz");
		optionFrame.setSize(500,300);
		optionFrame.setLocationRelativeTo(_quizCard);
		
    }

	
	/**
	 * Is always called after the option frame shows up.
	 * Will return they type of quiz selected in the frame.
	 * @return
	 */
	public QuizCard.QuizType quizTypeDialog() {
		QuizType localQuizType = _quizType;
		_quizType = QuizType.CANCEL; //Reset the quiz type in case it does not work next time.
		return localQuizType;
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
		txtOutput.append("\n\n------Quiz Completed------\nPlease Select 'New Quiz' to start another quiz.");
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