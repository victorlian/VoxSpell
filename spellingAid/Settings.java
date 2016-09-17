package spellingAid;

public class Settings {
	private static Settings _settings = null;
	private static int _currentLevel = -1;
	
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
	
}
