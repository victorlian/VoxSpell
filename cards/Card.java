package cards;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import spellingAid.MessageType;
import spellingAid.Viewer;

public abstract class Card implements Viewer {
	public abstract JPanel createContents();

	public abstract JPanel getPanel();

	public void popMessage(String msg, MessageType typeOfMessage) {
		if (typeOfMessage == MessageType.ERROR) {
			JOptionPane.showMessageDialog(getPanel(), msg, "Error", JOptionPane.ERROR_MESSAGE);
		} else if (typeOfMessage == MessageType.WARNING) {
			JOptionPane.showMessageDialog(getPanel(), msg, "Warning", JOptionPane.WARNING_MESSAGE);
		} else if (typeOfMessage == MessageType.INFORMATION) {
			JOptionPane.showMessageDialog(getPanel(), msg, "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public boolean videoOption() {
		int result = JOptionPane.showConfirmDialog(getPanel(), "Would you like to see the video reward?", "Reward!",
				JOptionPane.YES_NO_OPTION);

		if (result == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean levelUpOption() {
		int result = JOptionPane.showConfirmDialog(getPanel(), "Would you like to level up?", "Level Up!",
				JOptionPane.YES_NO_OPTION);

		if (result == 0) {
			return true;
		} else {
			return false;
		}
	}
}
