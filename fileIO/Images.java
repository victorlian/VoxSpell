package fileIO;

import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * This class stores all the images. Singleton class.
 * Will return images as icons to be used with
 * different swing components.
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
	public final static String welcomeFile = "welcome.jpg";
	private Icon welcomeIcon;
	public final static String yesFile = "yes.png";
	private Icon yesIcon;
	public final static String noFile = "no.png";
	private Icon noIcon;
	public final static String blankFile = "blank.png";
	private Icon blankIcon;

	
	private FileManager _fm = new FileManager();
	
	
	/**
	 * Private constructor as it is a singleton. First time constructed always create Icon.
	 */
	private Images() {
		createIcons();
	}

	/**
	 * Singleton class to get Image object.
	 * When initializing, loads all the image files.
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
		BufferedImage image5 = _fm.readInImage(welcomeFile);
		welcomeIcon = new ImageIcon(image5);
		BufferedImage image6 = _fm.readInImage(yesFile);
		yesIcon = new ImageIcon(image6);
		BufferedImage image7 = _fm.readInImage(noFile);
		noIcon = new ImageIcon(image7);
		BufferedImage image8 = _fm.readInImage(blankFile);
		blankIcon = new ImageIcon(image8);
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
	public Icon getWelcomeIcon(){
		return welcomeIcon;
	}
	public Icon getNoIcon(){
		return noIcon;
	}
	public Icon getYesIcon(){
		return yesIcon;
	}
	public Icon getBlankIcon(){
		return blankIcon;
	}

}
