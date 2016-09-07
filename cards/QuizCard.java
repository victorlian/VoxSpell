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

/**
 * This class populates the contents of a Card
 * Mainly to make the MainGUI class less cluttered and make it
 * easier to modify the card UI
 * @author Daniel
 *
 */
public class QuizCard {
	private static JTextField txtInput = new JTextField("Spell your words here!");
	private static JButton btnSubmit = new JButton("Submit");
	private static JButton btnSayAgain = new JButton("Repeat Word");
	private static JButton btnNewQuiz = new JButton("New Quiz");
	private static JTextArea txtOutput = new JTextArea(10, 30);
	
	public QuizCard() {}
	
	public static JPanel createContents() {
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
		
		/**
		//Empty JPanel for spacing purposes
		constraints.weightx = 0.5;
		constraints.gridwidth = 2;
		constraints.gridx = 1;
		constraints.gridy = 2;
		quizCard.add(new JPanel(), constraints);
		*/
		
		constraints.weightx = 0.4;
		constraints.gridwidth = 1;
		constraints.gridx = 3;
		constraints.gridy = 2;
		quizCard.add(btnSubmit, constraints);
		
		return quizCard;
	}

	public void addActionListeners() {

	}
}
