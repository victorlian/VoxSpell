package spellingAid;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * This class handles showing the Video Reward.
 * 
 * What a mighty nut to crack this was, so many things to import to the buildpath and googling because it
 * kept throwing errors. But it works now :)
 * @author Daniel 
 * 
 * Edited to play the edited (reversed) video.
 * Also keeps track of whether the edited video is avaliable.
 * This class made singleton to keep track of that state.
 * Code modified BY:  @author victor
 *
 */
public class VideoReward {
	private JFrame _videoReward;
	private EmbeddedMediaPlayerComponent _mediaPlayerComponent;
	private JButton _pauseBtn;
	private JButton _stopBtn;
	private JButton _reverseBtn;
	private FileManager _fm = new FileManager();
	
	private boolean _editAvaliable = false; 
	private static VideoReward _instance = null;
	private VideoToPlay _videoType = null;
	
	/**
	 * Code based from http://capricasoftware.co.uk/#/projects/vlcj/tutorial/my-first-media-player
	 * 
	 * Constructor was hidden by @author victor for singleton
	 */
	private VideoReward() {
	}
	
	/**
	 * Singleton Class getInstance method added.
	 * @return
	 */
	public static VideoReward getInstance(){
		if (_instance == null){
			_instance = new VideoReward();
		}
		return _instance;
	}
	
	public void createContents() {
		_videoReward = new JFrame("Video Reward");
		JPanel panel = new JPanel(new BorderLayout());
		_mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		
		EmbeddedMediaPlayer video = _mediaPlayerComponent.getMediaPlayer();
        
        _videoReward.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	exit();
            }
        });
        
        _pauseBtn = new JButton("Pause");
        _pauseBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_mediaPlayerComponent.getMediaPlayer().pause();
				
				if (_pauseBtn.getText().equals("Pause")) {
					_pauseBtn.setText("Continue");
				} else {
					_pauseBtn.setText("Pause");
				}
			}
        });
        
        _stopBtn = new JButton("Stop");
        _stopBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				if (_stopBtn.getText().equals("Stop")) {
					_stopBtn.setText("Play");
					_mediaPlayerComponent.getMediaPlayer().stop();
				} else {
					_stopBtn.setText("Stop");
					_mediaPlayerComponent.getMediaPlayer().play();
				}
			}
        });
        
        //Play the video in reverse. But note that if the reverse video is 
        //not prepared, then the forward video would still be played.
        _reverseBtn = new JButton("Reverse");
        _reverseBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				reverseVideoType();
				String path = getCurrentVideoFileName();
				_mediaPlayerComponent.getMediaPlayer().playMedia(path);
			}
        });

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(_pauseBtn);
        buttonPanel.add(_stopBtn);
        buttonPanel.add(_reverseBtn);
        
        panel.add(_mediaPlayerComponent, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        _videoReward.setContentPane(panel);
        _videoReward.setLocation(100, 100);
        _videoReward.setSize(525, 325);
        _videoReward.setVisible(true);
        
      //=== added for FFMEPG part to know which video to play
        String fileName;
        VideoToPlay videoType = Settings.getVideoType();
        if (_videoType == null){
        	if (videoType.equals(VideoToPlay.ORIGINAL) || _editAvaliable == false){
            	fileName = _fm.VIDEO;
            	_videoType = videoType;
            }
            else {
            	fileName = _fm.REVERSEVIDEO;
            	_videoType = videoType;
            }
        }
        else {
        	throw new RuntimeException ("video type should not exist.");
        }
        
        String path = _fm.getAbsolutePath(fileName);
        video.playMedia(path);
	}
	
	public void exit() {
		//Clean up the video player
		_mediaPlayerComponent.release();
	}
	
	/**
	 * This method is intended to be called after the video is created
	 * or the video is verified to be there.
	 */
	public void setEditedVideoAvaliable(){
		_editAvaliable = true;
	}
	
	/**
	 * This method will reverse the video type.
	 */
	public void reverseVideoType(){
		if (_videoType.equals(VideoToPlay.ORIGINAL)){
			_videoType = VideoToPlay.REVERSED;
		}
		else {
			_videoType = VideoToPlay.ORIGINAL;
		}
	}
	
	/**
	 * This method will return the path of video given the current 
	 * type of video
	 */
	public String getCurrentVideoFileName (){
		if (_videoType.equals(VideoToPlay.ORIGINAL) || _editAvaliable == false){
			return _fm.getAbsolutePath(_fm.VIDEO);
		}
		else {
			return _fm.getAbsolutePath(_fm.REVERSEVIDEO);
		}
	}
}
