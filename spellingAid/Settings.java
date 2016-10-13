package spellingAid;

import fileIO.FileManager;
import speech.Voices;
import video.VideoType;

/**
 * This class is singelton and holds fields of settings. Classes store
 * the information in the field and retrieve them when necessary through getters
 * and setters.
 * @author Daniel
 *
 */
public class Settings {
	private static Settings _settings = null;
	private static int _currentLevel = -1;
	
	private static final String NZLVOICE = "(voice_akl_nz_jdt_diphone)";
	
	private static String _currVoice = "";
	private static VideoType _currVideo = VideoType.ORIGINAL;
	
	private static String _wordlist = new FileManager().getAbsolutePath(new FileManager().WORDLIST);
	
	private Settings() {}
	
	/**
	 * Since this is singleton, get instance of a Settings card
	 * @return
	 */
	public static Settings getInstance() {
		if (_settings == null) {
			_settings = new Settings();
		}
		
		return _settings;
	}
	
	/**
	 * Get current level field
	 * @return
	 */
	public static int getLevel() {
		return _currentLevel;
	}
	
	/**
	 * Find out if it's the first time the user has used Spelling Aid
	 * @return
	 */
	public static boolean isFirstTime() {
		if (_currentLevel == -1) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Set the level of the program
	 * @param level
	 */
	public static void setlevel(int level) {
		_currentLevel = level;
	}
	
	/**
	 * Set the voice we want to use
	 * @param voice
	 */
	public static void setVoice(Voices voice) {
		if (voice.equals(Voices.DEFAULT)) {
			_currVoice = "";
		} else if (voice.equals(Voices.NEWZEALAND)) {
			_currVoice = NZLVOICE;
		}
	}
	
	/**
	 * Get the voice chosen
	 * @return
	 */
	public static String getVoice() {
		return _currVoice;
	}
	
	/**
	 * Get the video type we want to use
	 * @return
	 */
	public static VideoType getVideoType() {
		return _currVideo;
	}
	
	/**
	 * Set the video type we want to use
	 * @param videoType
	 */
	public static void setVideoType(VideoType videoType) {
		_currVideo = videoType;
	}
	
	/**
	 * Get the wordlist file
	 * @return
	 */
	public static String getWordlist(){
		return _wordlist;
	}
	
	/**
	 * Set the wordlist file path
	 * @return
	 */
	public static void setWordlist(String wordlist){
		_wordlist=wordlist;
	}
	
}
