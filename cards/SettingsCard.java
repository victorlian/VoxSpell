package cards;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import quiz.NewQuiz;
import quiz.Quiz;
import quiz.ReviewQuiz;
import speech.Voices;
import spellingAid.Settings;
import spellingAid.VideoReward;
import spellingAid.VideoToPlay;
import words.Word;

/**
 * This class populates the contents of a Card Mainly to make the MainGUI class
 * less cluttered and make it easier to modify the card UI
 * 
 * @author Daniel
 *
 */
public class SettingsCard extends Card implements ActionListener {
	private JPanel _settingsCard;
	private static final String DEFBUTTON = "English - Default";
	private static final String NZBUTTON = "English - NZ";
	private static final String DEFVIDEO = "Default Video";
	private static final String ALTVIDEO = "Special Video";

	public SettingsCard() {
	}

	public JPanel createContents() {
		_settingsCard = new JPanel();
		_settingsCard.setLayout(new BorderLayout());
		
		//This is the voice options
		JRadioButton defVoiceBtn = new JRadioButton(DEFBUTTON);
		defVoiceBtn.setSelected(true);
		defVoiceBtn.setActionCommand(DEFBUTTON);

		JRadioButton nzVoiceBtn = new JRadioButton(NZBUTTON);
		nzVoiceBtn.setActionCommand(NZBUTTON);

		ButtonGroup group = new ButtonGroup();
		group.add(defVoiceBtn);
		group.add(nzVoiceBtn);

		//Below is the video options
		JRadioButton defVideo = new JRadioButton(DEFVIDEO);
		defVideo.setSelected(true);
		defVideo.setActionCommand(DEFVIDEO);

		JRadioButton altVideo = new JRadioButton(ALTVIDEO);
		altVideo.setActionCommand(ALTVIDEO);

		ButtonGroup videoGroup = new ButtonGroup();
		videoGroup.add(defVideo);
		videoGroup.add(altVideo);

		//This is the panel holding the radio buttons
		JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
		buttonPanel.add(new JLabel("Speech Voice"));
		buttonPanel.add(defVoiceBtn);
		buttonPanel.add(nzVoiceBtn);
		buttonPanel.add(new JLabel("Video Type"));
		buttonPanel.add(defVideo);
		buttonPanel.add(altVideo);
		
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
				Quiz quiz = ReviewQuiz.getInstance(null, 12345);
				quiz.toNumberX(null, 8, 8, Word.SuccessStatus.MASTERED);
			}
		});
		
		JButton videoBtn = new JButton("Video");
		videoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				VideoReward vidReward = new VideoReward();
				vidReward.createContents();
			}
		});
		
		debugPanel.add(debugBtn);
		debugPanel.add(cheatBtn);
		debugPanel.add(reviewBtn);
		debugPanel.add(videoBtn);
		//==============================================
		//For Debug use UPPPPPPPPP

		_settingsCard.add(buttonPanel, BorderLayout.LINE_START);

		//FORDEBUG================
		_settingsCard.add(debugPanel, BorderLayout.SOUTH);
		//FORDEBUG================

		defVoiceBtn.addActionListener(this);
		nzVoiceBtn.addActionListener(this);
		defVideo.addActionListener(this);
		altVideo.addActionListener(this);

		return _settingsCard;
	}

	@Override
	public JPanel getPanel() {
		return _settingsCard;
	}
	
	/**
	 * Change Voices
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case DEFBUTTON:
			Settings.setVoice(Voices.DEFAULT);
			break;
		case NZBUTTON:
			Settings.setVoice(Voices.NEWZEALAND);
			break;
		case DEFVIDEO:
			Settings.setVideoType(VideoToPlay.ORIGINAL);
			break;
		case ALTVIDEO:
			Settings.setVideoType(VideoToPlay.REVERSED);
			break;
		}
	}
}
