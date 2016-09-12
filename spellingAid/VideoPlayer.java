package spellingAid;

import javax.swing.JFrame; 

public class VideoPlayer {
	public VideoPlayer() {
		JFrame mediaTest = new JFrame( "Media Tester" );
		mediaTest.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); 
		MediaPanel mediaPanel = new MediaPanel( mediaURL );
		mediaTest.add( mediaPanel );

	}
}
