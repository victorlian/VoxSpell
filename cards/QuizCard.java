package cards;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class populates the contents of a Card
 * Mainly to make the MainGUI class less cluttered and make it
 * easier to modify the card UI
 * @author Daniel
 *
 */
public class QuizCard {
	public QuizCard() {}
	
	public static JPanel createContents() {
		JPanel quizCard = new JPanel();
		
		quizCard.add(new JTextField("QuizCard", 20));
		
		return quizCard;
	}
}
