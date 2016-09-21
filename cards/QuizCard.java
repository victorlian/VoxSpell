package cards;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
 */
public class QuizCard extends Card implements ActionListener, Viewer {
	private static final String SUBMIT = "Submit";
	private static final String SAYAGAIN = "Repeat Word";
	private static final String NEWQUIZ = "New Quiz";
	
	private JPanel _quizCard;
	private static JTextField txtInput = new JTextField("Spell your words here!");
	private static JButton btnSubmit = new JButton(SUBMIT);
	private static JButton btnSayAgain = new JButton(SAYAGAIN);
	private static JButton btnNewQuiz = new JButton(NEWQUIZ);
	private static JTextArea txtOutput = new JTextArea(8, 30);
	private static JLabel _levelIndicator = new JLabel("Select the New Quiz button to begin");
	
	private Quiz _quiz;
	private Option _option;
	private boolean finished;
	
	public enum QuizType {
		NORMAL, REVIEW, CANCEL;
	}
	
	public QuizCard() {}
	
	/**
	 * Populates the Quiz Card UI
	 */
	public JPanel createContents() {
		_quizCard = new JPanel();
		Font bigFont = txtInput.getFont().deriveFont(Font.PLAIN, 15f);
		
		_quizCard.setLayout(new BorderLayout());
		
		JScrollPane textBox = new JScrollPane(txtOutput);
		txtOutput.setFont(bigFont);
		txtOutput.setEditable(false);
		
        txtInput.setFont(bigFont);
        txtInput.requestFocus();
        
        JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		inputPanel.add(new JLabel("Enter your spelling below:"), BorderLayout.NORTH);
		inputPanel.add(txtInput, BorderLayout.CENTER);
		
		btnNewQuiz.setPreferredSize(new Dimension(150,30));
		btnSayAgain.setPreferredSize(new Dimension(200,30));
		btnSubmit.setPreferredSize(new Dimension(200,30));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(btnNewQuiz);
		buttonPanel.add(btnSayAgain);
		buttonPanel.add(btnSubmit);
		
		inputPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		_quizCard.add(_levelIndicator, BorderLayout.NORTH);
		_quizCard.add(textBox, BorderLayout.CENTER);
		_quizCard.add(inputPanel, BorderLayout.SOUTH);
		
		//Padding inside JPanel
		_quizCard.setBorder(new EmptyBorder(5,10,0,10));
		
		addActionListeners();
		disableSubmissionButtons();
		
		return _quizCard;
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
				//Clear the textInput
				txtInput.setText("");
				
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
					_levelIndicator.setText("Review Quiz - Level: " + _quiz.getCurrentLevel());
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
					_levelIndicator.setText("New Quiz - Level: " + _quiz.getCurrentLevel());
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
				
				Submission submission = new Submission(_quiz, txtInput.getText());
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
}