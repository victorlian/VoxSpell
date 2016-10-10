package cards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JComponent;

/**
 * This class will draw the accuracy chart (in bar) to show the amount of
 * correct words as a percentage.
 * 
 * @author Victor
 *
 */
@SuppressWarnings("serial")
public class AccuracyChart extends JComponent {
	private final int WIDTH = 700;
	private final int HEIGHT = 60;
	private final int xCo = 5;
	private final int yCo = 5;

	private int _mastered = 0;
	private int _faulted = 0;
	private int _failed = 0;
	private int _total = 0;

	public AccuracyChart(int mastered, int faulted, int failed) {
		_mastered = mastered;
		_faulted = faulted;
		_failed = failed;
		_total = mastered + faulted + failed;
	}

	@Override
	public Dimension getPreferredSize() {
		return (new Dimension(WIDTH + 10, HEIGHT + 10));
	}

	@Override
	protected void paintComponent(Graphics g) {
		int masteredWidth = 0;
		int faultedWidth = 0;
		int failedWidth = 0;

		if (_total != 0) {
			masteredWidth = WIDTH * _mastered / _total;
			faultedWidth = WIDTH * _faulted / _total;
			failedWidth = WIDTH * _failed / _total;

			g.setColor(Color.GREEN);
			g.fillRect(xCo, yCo, masteredWidth, HEIGHT);
			g.setColor(Color.YELLOW);
			g.fillRect(xCo + masteredWidth, yCo, faultedWidth, HEIGHT);
			g.setColor(Color.RED);
			g.fillRect(xCo + masteredWidth + faultedWidth, yCo, WIDTH - masteredWidth - faultedWidth, HEIGHT);
		}

		g.setColor(Color.BLACK);
		g.drawRect(xCo, yCo, WIDTH, HEIGHT);

		// Paint string on these if the amount if not 0.
		g.setFont(Card.bold14);

		// To calculate where to put the string, think as:
		// leftLength+string+rightLength=Width
		// String width use font metrics.
		int masteredTextWidth = g.getFontMetrics().stringWidth(Card.masteredString);
		int faultedTextWidth = g.getFontMetrics().stringWidth(Card.faultedString);
		int failedTextWidth = g.getFontMetrics().stringWidth(Card.faultedString);

		// Now calculate the different percentages width
		DecimalFormat df = new DecimalFormat("#.00");
		String masteredPercent = df.format(_mastered * 100.0D / _total) + "%";
		String faultedPercent = df.format(_faulted * 100.0D / _total) + "%";
		String failedPercent = df.format(_failed * 100.0D / _total) + "%";
		int masteredPercentWidth = g.getFontMetrics().stringWidth(masteredPercent);
		int faultedPercentWidth = g.getFontMetrics().stringWidth(faultedPercent);
		int failedPercentWidth = g.getFontMetrics().stringWidth(failedPercent);

		// Now draw all the text using the graphics
		if (masteredWidth != 0) {
			if (masteredWidth > masteredPercentWidth) {
				g.drawString(Card.masteredString, xCo + calculateXCoText(masteredTextWidth, masteredWidth),
						yCo + HEIGHT * 1 / 3);
				g.drawString(masteredPercent, xCo + calculateXCoText(masteredPercentWidth, masteredWidth),
						yCo + HEIGHT * 2 / 3);
			}

		}
		if (faultedWidth != 0) {
			if (faultedWidth > faultedPercentWidth) {
				g.drawString(Card.faultedString, xCo + masteredWidth + calculateXCoText(faultedTextWidth, faultedWidth),
						yCo + HEIGHT * 1 / 3);
				g.drawString(faultedPercent, xCo + masteredWidth + calculateXCoText(faultedPercentWidth, faultedWidth),
						yCo + HEIGHT * 2 / 3);
			}
		}
		if (failedWidth != 0) {
			if (failedWidth > failedPercentWidth) {
				g.drawString(Card.failedString,
						xCo + masteredWidth + faultedWidth + calculateXCoText(failedTextWidth, failedWidth),
						yCo + HEIGHT * 1 / 3);
				g.drawString(failedPercent,
						xCo + masteredWidth + faultedWidth + calculateXCoText(failedPercentWidth, failedWidth),
						yCo + HEIGHT * 2 / 3);
			}
		}

	}

	private int calculateXCoText(int textWidth, int barWidth) {
		return (barWidth - textWidth) / 2;
	}

	/**
	 * This method will redraw the accuracy chart given new parameters
	 * 
	 * @param mastered
	 * @param faulted
	 * @param failed
	 */
	public void redrawAccuracyChart(int mastered, int faulted, int failed) {
		_mastered = mastered;
		_faulted = faulted;
		_failed = failed;
		_total = mastered + faulted + failed;

		repaint();
	}
}