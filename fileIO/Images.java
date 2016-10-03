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
	public final static String questionMark = "questionMark.png";
	private Icon questionIcon;
	
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
		BufferedImage image = _fm.readInImage(questionMark);
		questionIcon = new ImageIcon(image);
	}
	
	public Icon getQuestionIcon(){
		return questionIcon;
	}

}
