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

public class VideoReward {
	private JFrame _videoReward;
	private EmbeddedMediaPlayerComponent _mediaPlayerComponent;
	private JButton _pauseBtn;
	private JButton _stopBtn;
	
	/**
	 * Code based from http://capricasoftware.co.uk/#/projects/vlcj/tutorial/my-first-media-player
	 */
	public VideoReward() {
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
					_pauseBtn.setText("Play");
				} else {
					_pauseBtn.setText("Pause");
				}
			}
        });
        
        _stopBtn = new JButton("Stop");
        _stopBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Close and Exit
				exit();
				_videoReward.dispose();
			}
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(_pauseBtn);
        buttonPanel.add(_stopBtn);
        
        panel.add(_mediaPlayerComponent, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        _videoReward.setContentPane(panel);
        _videoReward.setLocation(100, 100);
        _videoReward.setSize(525, 300);
        _videoReward.setVisible(true);
        
        String filename = "big_buck_bunny_1_minute.avi";
        video.playMedia(filename);
	}
	
	public void exit() {
		//Clean up the video player
		_mediaPlayerComponent.release();
	}
}
