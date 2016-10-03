package fileIO;

import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * This class stores all the images. Singleton class.
 * @author victor
 *
 */
public class Images {
	private static Images _images = null;
	public final static String quizFile = "quiz.png";
	private Icon quizIcon;
	public final static String hiFile = "hi.png";
	private Icon hiIcon;
	public final static String statsFile = "stats.png";
	private Icon statsIcon;
	public final static String settingsFile = "settings.png";
	private Icon settingsIcon;

	
	private FileManager _fm = new FileManager();
	
	
	/**
	 * Private constructor as it is a singleton. First time constructed always create Icon.
	 */
	private Images() {
		createIcons();
	}

	/**
	 * Singleton so passing an instance of itself to other people
	 * 
	 * @return
	 */
	public static Images getInstance() {
		if (_images == null) {
			_images = new Images();
		}
		return _images;
	}
	
	public void createIcons(){
		BufferedImage image = _fm.readInImage(quizFile);
		quizIcon = new ImageIcon(image);
		BufferedImage image2 = _fm.readInImage(hiFile);
		hiIcon = new ImageIcon(image2);
		BufferedImage image3 = _fm.readInImage(statsFile);
		statsIcon = new ImageIcon(image3);
		BufferedImage image4 = _fm.readInImage(settingsFile);
		settingsIcon = new ImageIcon(image4);
	}
	
	public Icon getQuizIcon(){
		return quizIcon;
	}
	public Icon getHiIcon(){
		return hiIcon;
	}
	public Icon getStatsIcon(){
		return statsIcon;
	}
	public Icon getSettingsIcon(){
		return settingsIcon;
	}

}
