package cards;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import spellingAid.Viewer;

/**
 * This class populates the contents of a Card
 * Mainly to make the MainGUI class less cluttered and make it
 * easier to modify the card UI
 * @author Daniel
 *
 */
public class QuizCard extends Card implements ActionListener {
	private static final String SUBMIT = "Submit";
	private static final String SAYAGAIN = "Repeat Word";
	private static final String NEWQUIZ = "New Quiz";
	
	private static JTextField txtInput = new JTextField("Spell your words here!");
	private static JButton btnSubmit = new JButton(SUBMIT);
	private static JButton btnSayAgain = new JButton(SAYAGAIN);
	private static JButton btnNewQuiz = new JButton(NEWQUIZ);
	private static JTextArea txtOutput = new JTextArea(10, 30);
	
	public QuizCard() {}
	
	public JPanel createContents() {
		JPanel quizCard = new JPanel();
		
		quizCard.setLayout(new GridBagLayout());
		quizCard.setBorder(new EmptyBorder(5,5,5,5));

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		JScrollPane scroll = new JScrollPane(txtOutput);
		txtOutput.setEditable(false);
		constraints.gridwidth = 4;
		constraints.gridx = 0;
		constraints.gridy = 0;
		quizCard.add(scroll, constraints);
		
		constraints.gridwidth = 4;
		constraints.gridx = 0;
		constraints.gridy = 1;
		quizCard.add(txtInput, constraints);
		
		constraints.weightx = 0.3;
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 2;
		quizCard.add(btnNewQuiz, constraints);
		
		constraints.weightx = 0.3;
		constraints.gridwidth = 1;
		constraints.gridx = 1;
		constraints.gridy = 2;
		quizCard.add(btnSayAgain, constraints);
		
		constraints.weightx = 0.4;
		constraints.gridwidth = 1;
		constraints.gridx = 3;
		constraints.gridy = 2;
		quizCard.add(btnSubmit, constraints);
		
		return quizCard;
	}

	public void addActionListeners() {
		btnNewQuiz.addActionListener(this);
		btnSubmit.addActionListener(this);
		btnSayAgain.addActionListener(this);
	}
	
	//ActionListener Class
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
			
			case SUBMIT:
			
			case SAYAGAIN:
			
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
	}

	public void enableSubmissionButton() {
		btnSayAgain.setEnabled(true);
		btnSubmit.setEnabled(true);
	}

	public void displayMainMenu() {
		txtOutput.setText("Quiz Completed\nSelect 'New Quiz' to start another Quiz");
	}
}
