package cards;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import speech.Voices;
import spellingAid.FileManager;
import spellingAid.Settings;
import statistics.Statistics;
import video.VideoType;

/**
 * This class populates the contents of a Card Mainly to make the MainGUI class
 * less cluttered and make it easier to modify the card UI
 * 
 * @author Daniel
 *
 *added Functionality: file chooser, clear statistics.
 *@author Victor
 *
 */
public class SettingsCard extends Card implements ActionListener {
	private JPanel _settingsCard;
	private static final String DEFBUTTON = "English - Default";
	private static final String NZBUTTON = "English - NZ";
	private static final String DEFVIDEO = "Default Video";
	private static final String ALTVIDEO = "Special Video";
	private static final String FILEBTN = "Choose a new wordlist";
	private static final String STATSBTN = "Clear all statistics";
	
	private FileManager _fm = new FileManager();
	
	JButton fileChooserBtn = new JButton (FILEBTN);
	JLabel fileLabel = new JLabel ("     Current wordlist is: ");
	JTextField fileText = new JTextField (_fm.getAbsolutePath(_fm.WORDLIST));
	
	JButton clearStatsBtn = new JButton (STATSBTN);

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
		
		//Panel 1: voice selection panel.
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

		//Panel 2: video selection panel.
		JPanel videoPanel = new JPanel(new GridLayout(0, 1));
		videoPanel.add(new JLabel("Video Type"));
		videoPanel.add(defVideo);
		videoPanel.add(altVideo);
		
		//Panel 3: clear stats panel.
		JPanel clearStatsPanel = new JPanel(null);
		JLabel clearStatsWarning = new JLabel ("Warning! This option will clear all the statistics, including history files.");
		clearStatsWarning.setFont(Card.bold16);
		clearStatsWarning.setForeground(Card.blueInstructionColor);
		
		clearStatsPanel.add(clearStatsWarning);
		clearStatsPanel.add(clearStatsBtn);
		clearStatsWarning.setBounds(0,20,700,30);
		clearStatsBtn.setBounds(30, 60, 200, 30);
		
		
		
		
		//Panel 4: file chooser panel.
		JPanel fileChooserPanel = new JPanel(new BorderLayout());

		JPanel belowLabelPanel = new JPanel();
		belowLabelPanel.add(fileChooserBtn);
		belowLabelPanel.add(fileLabel);
		belowLabelPanel.add(fileText);
		
		JLabel warning = new JLabel ("Warning! By choosing a new wordlist, the current quiz will be stopped.");
		warning.setFont(Card.bold16);
		warning.setForeground(Card.blueInstructionColor);
		
		fileChooserPanel.add(warning,BorderLayout.NORTH);
		fileChooserPanel.add(belowLabelPanel, BorderLayout.CENTER);
		
		fileChooserBtn.setPreferredSize(new Dimension(200,30));
		fileChooserBtn.setActionCommand(FILEBTN);
		fileLabel.setPreferredSize(new Dimension(150,30));
		fileText.setPreferredSize(new Dimension(350,30));
		fileText.setEditable(false);
		
		//Add the four panels to the Card
		_settingsCard.add(voicePanel);
		_settingsCard.add(videoPanel);
		_settingsCard.add(clearStatsPanel);
		_settingsCard.add(fileChooserPanel);
		
		//Padding inside JPanel
		_settingsCard.setBorder(new EmptyBorder(10,10,10,10));

		//Set the action listeners for all buttons.
		defVoiceBtn.addActionListener(this);
		nzVoiceBtn.addActionListener(this);
		defVideo.addActionListener(this);
		altVideo.addActionListener(this);
		clearStatsBtn.addActionListener(this);
		fileChooserBtn.addActionListener(this);

		return _settingsCard;
	}
	
	/**
	 * Private helper method to open the File Chooser.
	 * And return the full path of the chosen file.
	 */
	private String openFileChooser(){
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("wordlist .txt files", "txt");
	    chooser.setFileFilter(filter);
	    chooser.setCurrentDirectory(new File(_fm.getCurrentDir()));
	    int returnVal = chooser.showOpenDialog(this.getPanel());
	    String path="";
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	path = chooser.getSelectedFile().getAbsolutePath();
	    }
	    else{
	    	return path;
	    }
	    //Must ends with .txt 
	    if (path.endsWith(".txt")){
	    	return path;
	    }
	    return null;
	}

	@Override
	public JPanel getPanel() {
		return _settingsCard;
	}
	
	/**
	 * Actions to do when buttons are pressed.
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
		case FILEBTN:
			String path = null;
			while(path==null){
				path = openFileChooser();
				if (path == null){
					JOptionPane.showMessageDialog(this.getPanel(), "you must choose a .txt file as your wordlist", 
							"Error when choosing wordlist", JOptionPane.ERROR_MESSAGE);
				}
			};
			if (path.isEmpty()){
				return;
			}
			else{
				fileText.setText(path);
				Settings.setWordlist(path);
			}
			QuizCard.getInstance().terminateCurrentQuiz();
			break;
		case STATSBTN:
			int result = JOptionPane.showConfirmDialog(this.getPanel(), 
					"Are you sure you want to clear all statistics, including history files?", 
					"Confirm Deleting history" , 
					JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION){
				Statistics.getInstance().clearStats();
			}
			break;
		}
	}
}
