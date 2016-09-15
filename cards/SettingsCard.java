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

import spellingAid.NewQuiz;
import spellingAid.Quiz;
import spellingAid.VideoReward;

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

	public SettingsCard() {
	}

	public JPanel createContents() {
		_settingsCard = new JPanel();
		_settingsCard.setLayout(new BorderLayout());

		JRadioButton defVoiceBtn = new JRadioButton(DEFBUTTON);
		defVoiceBtn.setSelected(true);
		defVoiceBtn.setActionCommand(DEFBUTTON);

		JRadioButton nzVoiceBtn = new JRadioButton(NZBUTTON);
		nzVoiceBtn.setActionCommand(NZBUTTON);

		ButtonGroup group = new ButtonGroup();
		group.add(defVoiceBtn);
		group.add(nzVoiceBtn);

		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		radioPanel.add(defVoiceBtn);
		radioPanel.add(nzVoiceBtn);
		
		JButton debugBtn = new JButton("Debug");
		debugBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//ADD YOUR CODE HERE VICTOR
				
				//note:instance should exsist so should have no problem passing null;
				//Also note that getInstance will initialize quiz back to test 1;
				Quiz quiz = NewQuiz.getInstance(null, 1);
				quiz.toNumberX(null, 8, 7);
				
			}
		});

		_settingsCard.add(new JLabel("Speech Voice"), BorderLayout.NORTH);
		_settingsCard.add(radioPanel, BorderLayout.LINE_START);
		_settingsCard.add(debugBtn, BorderLayout.SOUTH);

		defVoiceBtn.addActionListener(this);
		nzVoiceBtn.addActionListener(this);

		return _settingsCard;
	}

	@Override
	public JPanel getPanel() {
		return _settingsCard;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case DEFBUTTON:
			System.out.println("Default");
			break;
		case NZBUTTON:
			System.out.println("NZ");
			
			VideoReward vidReward = new VideoReward();
			vidReward.createContents();
			break;
		}
	}
}
