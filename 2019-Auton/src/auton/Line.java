package auton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Line path part that will drive the robot in a straight line the required distance.
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
	public void drivePath() 
	{
		Point[] p = getPoints();
			
		double x = Math.abs(p[1].getX() - p[0].getX());
		double y = Math.abs(p[1].getY() - p[0].getY());
		double pixels = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		long distance = Math.round(pixels * DrawPath.INCHPIXEL);
		
		System.out.println("Driving forward " + distance + "in");
	}

	@Override
	public void drawFinished(Graphics g, Point[] p) 
	{
		g.drawLine(p[0].x, p[0].y, p[1].x, p[1].y);
	}
}