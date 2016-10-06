package cards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;


/**
 * This class will draw the accuracy chart (in bar) to show 
 * the amount of correct words as a percentage.
 * @author Victor
 *
 */
public class AccuracyChart extends JComponent{
	private final int WIDTH = 600;
	private final int HEIGHT = 100;
	private final int xCo = 10;
	private final int yCo = 10;
	
	private int _mastered = 0;
	private int _faulted = 0;
	private int _failed = 0;
	private int _total = 0;
	
	public AccuracyChart(int mastered, int faulted, int failed){
		_mastered = mastered;
		_faulted = faulted;
		_failed = failed;
		_total = mastered+faulted+failed;
	}
	
	@Override
	protected void paintComponent (Graphics g){
		
		int masteredWidth = WIDTH * _mastered / _total;
		int faultedWidth = WIDTH * _faulted / _total;
		int failedWidth = WIDTH * _failed / _total;
		
		g.setColor(Color.GREEN);
		g.fillRect(xCo, yCo, masteredWidth, HEIGHT);
		g.setColor(Color.YELLOW);
		g.fillRect(xCo+masteredWidth, yCo, faultedWidth, HEIGHT);		
		g.setColor(Color.RED);
		g.fillRect(xCo+masteredWidth+faultedWidth, yCo,WIDTH-masteredWidth-faultedWidth, HEIGHT);
		g.setColor(Color.BLACK);
		g.drawRect(xCo, yCo, WIDTH, HEIGHT);
	}
}