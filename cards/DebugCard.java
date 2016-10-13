package cards;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import fileIO.FileManager;
import quiz.NewQuiz;
import quiz.Quiz;
import video.VideoReward;
import words.Word;

/**
 * This class populates the contents of a Card Mainly to make the MainGUI class
 * less cluttered and make it easier to modify the card UI
 * 
 * DEBUG card will be removed during deployment.
 * @author Daniel
 *
 */
public class DebugCard extends Card {
	private JPanel _debugCard;

	public DebugCard() {
	}

	public JPanel createContents() {
		_debugCard = new JPanel();
		
		//==============================================
		//For Debug use BELOWWWWWWW
		
		//note:instance should exsist so should have no problem passing null;
		//Also note that getInstance will initialize quiz back to test 1;
		JPanel debugPanel = new JPanel (new GridLayout(0,4));
		
		JButton debugBtn = new JButton("Debug");
		debugBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Quiz quiz = NewQuiz.getInstance(null, 12345);
				quiz.toNumberX(null, 9, 9, Word.SuccessStatus.FAILED);
				
			}
		});
		
		JButton cheatBtn = new JButton("Cheat");
		cheatBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Quiz quiz = NewQuiz.getInstance(null, 12345);
				quiz.cheat();
			}
		});
		
		JButton reviewBtn = new JButton("ReviewDebug");
		reviewBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FileManager().updateStatsFile();
			}
		});
		
		JButton videoBtn = new JButton("Video");
		videoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				VideoReward vidReward = VideoReward.getInstance();
				vidReward.createContents();
			}
		});
		
		debugPanel.add(debugBtn);
		debugPanel.add(cheatBtn);
		debugPanel.add(reviewBtn);
		debugPanel.add(videoBtn);
		
		_debugCard.add(debugPanel);
		
		return _debugCard;
	}

	@Override
	public JPanel getPanel() {
		return _debugCard;
	}
}
