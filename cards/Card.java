package cards;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import spellingAid.Viewer;

public abstract class Card implements Viewer {
	public abstract JPanel createContents();
	
	public void popErrorMessage(String errorMsg) {
		JOptionPane.showMessageDialog(new JPanel(), errorMsg ,"Error",JOptionPane.ERROR_MESSAGE);
	}
}
