package cards;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

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

		_settingsCard.add(new JLabel("Speech Voice"), BorderLayout.NORTH);
		_settingsCard.add(radioPanel, BorderLayout.LINE_START);

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
		case NZBUTTON:
			System.out.println("NZ");
			
			VideoReward vidReward = new VideoReward();
			vidReward.createContents();
		}
	}
}
