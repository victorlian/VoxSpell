package spellingAid;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class SpellingAid {
	public static void main(String[] args) {
		//NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/usr/lib");
		//Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainGUI().createAndShowGUI();
			}
		});
	}
}
