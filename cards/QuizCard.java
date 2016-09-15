package cards;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import spellingAid.MessageType;
import spellingAid.NewQuiz;
import spellingAid.Option;
import spellingAid.Quiz;
import spellingAid.Submission;
import spellingAid.VideoReward;
import spellingAid.Viewer;

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
	private static JTextArea txtOutput = new JTextArea(10, 30);
	
	//This should hold a reference to the current quiz at some point
	private Quiz _quiz;
	private Option _option;
	
	public QuizCard() {}
	
	/**
	 * Populates the Quiz Card UI
	 */
	public JPanel createContents() {
		_quizCard = new JPanel();
		
		_quizCard.setLayout(new GridBagLayout());
		_quizCard.setBorder(new EmptyBorder(5,5,5,5));

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		JScrollPane scroll = new JScrollPane(txtOutput);
		txtOutput.setEditable(false);
		constraints.gridwidth = 4;
		constraints.gridx = 0;
		constraints.gridy = 0;
		_quizCard.add(scroll, constraints);
		
		constraints.gridwidth = 4;
		constraints.gridx = 0;
		constraints.gridy = 1;
		_quizCard.add(txtInput, constraints);
		
		constraints.weightx = 0.3;
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 2;
		_quizCard.add(btnNewQuiz, constraints);
		
		constraints.weightx = 0.3;
		constraints.gridwidth = 1;
		constraints.gridx = 1;
		constraints.gridy = 2;
		_quizCard.add(btnSayAgain, constraints);
		
		constraints.weightx = 0.4;
		constraints.gridwidth = 1;
		constraints.gridx = 3;
		constraints.gridy = 2;
		_quizCard.add(btnSubmit, constraints);
		
		addActionListeners();
		
		return _quizCard;
	}
	
	/**
	 * Adds the actionListeners to the buttons
	 */
	public void addActionListeners() {
		btnNewQuiz.addActionListener(this);
		btnSubmit.addActionListener(this);
		btnSayAgain.addActionListener(this);
	}
	
	/**
	 * ActionListener class
	 */
	public void actionPerformed(ActionEvent e) {
		String text;
		if (e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			text = button.getText();
		} else {
			text = "textField";
		}
		
		//Actions depending on the button
		switch(text) {
			case NEWQUIZ:
				disableStartButton();
				txtInput.setText("");
				
				int level;
				//Give the user dialog options
				if (quizTypeDialog()) {
					//Review Quiz
					level = selectLevelType();
					//TODO put in Review Quiz
					//_currentQuiz = ReviewQuiz.getInstance(this, level);
				} else {
					//Normal Quiz
					level = selectLevelType();
					_quiz = NewQuiz.getInstance(this, level);
				}

				_option = _quiz;
				
				txtOutput.setText("New Quiz begins: " + _quiz.NL);
				break;
			case SUBMIT:
				if (_quiz == null) {
					throw new RuntimeException("Quiz is null");
				}
				
				//TODO modify text to avoid empty input and invalid characters
				String textInput = txtInput.getText();
				if (textInput == null) {
					return;
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
	public boolean quizTypeDialog() {
		String[] quizOptions = {"New Quiz", "Review Quiz"};
		String quizType = (String) JOptionPane.showInputDialog(
                _quizCard,
                "Please select a type of Quiz: ",
                "Select a Quiz Type",
                JOptionPane.PLAIN_MESSAGE,
                null, //No Icon
                quizOptions,
                "New Quiz");
		
		if (quizType.equals(quizOptions[0])) {
			return false;
		} else {
			return true;
		}
	}
	
	public int selectLevelType() {
		//New Quiz - select starting level
		String[] levelOptions = {"Level 1", 
				"Level 2", 
				"Level 3",
				"Level 4", 
				"Level 5",
				"Level 6",
				"Level 7",
				"Level 8",
				"Level 9",
				"Level 10",
				"Level 11" };
		String level = (String) JOptionPane.showInputDialog(
                _quizCard,
                "Please select the starting level: ",
                "Select a starting level",
                JOptionPane.PLAIN_MESSAGE,
                null, //No Icon
                levelOptions,
                "Level 1");
		
		return Integer.valueOf(level.substring(level.length() - 1));
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
	}

	public void displayMainMenu() {
		txtOutput.append("Quiz Completed\nSelect 'New Quiz' to start another Quiz");
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
		VideoReward vidReward = new VideoReward();
		vidReward.createContents();
	}
}
