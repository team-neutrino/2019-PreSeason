package auton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Start point class that contains a single point for where the robot will
 * be at the beginning of a match. 
 * 
 * @author JoelNeppel
 *
 */
public class Start extends PathPart
{
	/**
	 * Constructor with no points intended for reading from file.
	 */
	public Start() 
	{
		super(1, Color.green);
	}
	
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
	public String describePath() 
	{
		return "Start";	
	}

	@Override
	protected void drawFinished(Graphics g, Point[] p) 
	{
		g.fillOval(p[0].x - 6, p[0].y - 6, 12, 12);
	}

	@Override
	public void drivePath() 
	{
		
	}

	@Override
	protected void sanityCheck() 
	{
		
	}
}