package cards;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import speech.Voices;
import spellingAid.Settings;
import video.VideoType;

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
		_settingsCard.setLayout(new GridLayout(0,1));
		
		//This is the voice options
		JRadioButton defVoiceBtn = new JRadioButton(DEFBUTTON);
		defVoiceBtn.setSelected(true);
		defVoiceBtn.setActionCommand(DEFBUTTON);

		JRadioButton nzVoiceBtn = new JRadioButton(NZBUTTON);
		nzVoiceBtn.setActionCommand(NZBUTTON);

		ButtonGroup group = new ButtonGroup();
		group.add(defVoiceBtn);
		group.add(nzVoiceBtn);
		
		//This is the panel holding the voice radio buttons
		JPanel voicePanel = new JPanel();
		voicePanel.setLayout(new GridLayout(0,1));
		voicePanel.add(new JLabel("Speech Voice"));
		voicePanel.add(defVoiceBtn);
		voicePanel.add(nzVoiceBtn);

		//Below is the video options
		JRadioButton defVideo = new JRadioButton(DEFVIDEO);
		defVideo.setSelected(true);
		defVideo.setActionCommand(DEFVIDEO);

		JRadioButton altVideo = new JRadioButton(ALTVIDEO);
		altVideo.setActionCommand(ALTVIDEO);

		ButtonGroup videoGroup = new ButtonGroup();
		videoGroup.add(defVideo);
		videoGroup.add(altVideo);

		//This is the panel holding the video radio buttons
		JPanel videoPanel = new JPanel(new GridLayout(0, 1));
		videoPanel.add(new JLabel("Video Type"));
		videoPanel.add(defVideo);
		videoPanel.add(altVideo);
		
		//Add the two panels to the Card
		_settingsCard.add(voicePanel);
		_settingsCard.add(videoPanel);
		_settingsCard.add(new JPanel());
		
		//Padding inside JPanel
		_settingsCard.setBorder(new EmptyBorder(10,10,10,10));

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
			Settings.setVideoType(VideoType.ORIGINAL);
			break;
		case ALTVIDEO:
			Settings.setVideoType(VideoType.REVERSED);
			break;
		}
	}
}
