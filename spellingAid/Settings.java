package spellingAid;

public class Settings {
	private static Settings _settings = null;
	
	private Settings() {}
	
	public Settings getInstance() {
		if (_settings == null) {
			_settings = new Settings();
		}
		
		return _settings;
	}
	
	
}
