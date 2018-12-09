package auton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Start point class that contains a single point for where the robot will
 * start at the beginning of a match. 
 * 
 * @author JoelNeppel
 *
 */
public class Start extends PathPart
{
	/**
	 * Constructor for the start which is a single point.
	 * @param point
	 * 	The point where the robot will start
	 */
	public Start(Point point) 
	{
		super(1, Color.green);
		addPoint(point);
	}

	@Override
	public void drivePath() 
	{
		System.out.println("Start");	
	}

	@Override
	public void drawFinished(Graphics g, Point[] p) 
	{
		g.fillOval(p[0].x - 5, p[0].y - 5, 10, 10);
	}
}