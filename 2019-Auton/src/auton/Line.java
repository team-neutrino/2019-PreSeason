package auton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Line path part that will drive the robot in a straight line the required distance.
 * This path part will require two points: The starting point and the end point.
 * 
 * @author JoelNeppel
 * 
 */
public class Line extends PathPart
{

	/**
	 * Constructor for a line that takes the last point from the previous 
	 * path part that will be used as the starting point for the line.
	 * @param p
	 * 	The starting point
	 */
	public Line(Point p) 
	{
		super(2, Color.blue);
		addPoint(p);
	}
	
	/**
	 * Constructs a complete line with the given points.
	 * @param points
	 * 	The two points of the line
	 */
	public Line(Point[] points)
	{
		super(2, Color.blue);
		
		for(Point p : points)
		{
			addPoint(p);
		}
	}

	@Override
	public String describePath() 
	{
		Point[] p = getPoints();
		double distance = MathSupport.getDistance(p[0], p[1]) * DrawPath.INCHPIXEL;
		
		return "Driving forward " + distance + "in";
	}

	@Override
	protected void drawFinished(Graphics g, Point[] p) 
	{
		g.drawLine(p[0].x, p[0].y, p[1].x, p[1].y);
	}

	@Override
	public void drivePath() 
	{
		// TODO Auto-generated method stub
		
	}
}