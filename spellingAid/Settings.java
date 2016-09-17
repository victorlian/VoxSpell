package spellingAid;

import speech.Voices;

public class Settings {
	private static Settings _settings = null;
	private static int _currentLevel = -1;
	
	private static final String NZLVOICE = "(voice_akl_nz_jdt_diphone)";
	
	private static String _currVoice = "";
	
	private Settings() {}
	
	public static Settings getInstance() {
		if (_settings == null) {
			_settings = new Settings();
		}
		
		return _settings;
	}
	
	public static int getLevel() {
		return _currentLevel;
	}
	
	public static boolean isFirstTime() {
		if (_currentLevel == -1) {
			return true;
		}
		
		return false;
	}
	
	public static void setlevel(int level) {
		_currentLevel = level;
	}
	
	public static void setVoice(Voices voice) {
		if (voice.equals(Voices.DEFAULT)) {
			_currVoice = "";
		} else if (voice.equals(Voices.NEWZEALAND)) {
			_currVoice = NZLVOICE;
		}
	}
	
	public static String getVoice() {
		return _currVoice;
	}
	
}
