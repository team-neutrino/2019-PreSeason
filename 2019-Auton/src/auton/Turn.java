package auton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Turn part for a path. The angle will be found be using two lines from three points.
 * This part path requires three points: The start point from the first line, the center 
 * point where the lines meet and the angle is formed, and the endpoint of the second line.
 * 
 * @author JoelNeppel
 *
 */
public class Turn extends PathPart 
{
	/**
	 * Constructor for a turn using the start and end point from the previous line.
	 * @param start
	 * 	The start point form the line before the turn
	 * @param center
	 * 	The pivot point of the turn, the end point of the first
	 * 	line and beginning of the second
	 */
	public Turn(Point start, Point center) 
	{
		super(3, Color.yellow, 1, 1);		
		addPoint(start);
		addPoint(center);
	}

	@Override
	protected void drawFinished(Graphics g, Point[] p) 
	{
		fillPoint(g, p[1]);
		g.drawArc(p[1].x - 25, p[1].y - 25, 50, 50, (int) 
				MathSupport.getImageAngle(p[1], p[2]), 
				(int) MathSupport.getAngleDegree(p[0], p[1], p[2]));
	}

	@Override
	public String describePath() 
	{
		Point[] p = getPoints();
		return "Turning " + MathSupport.getAngleDegree(p[0], p[1], p[2]) + " degrees";
	}

	@Override
	public void drivePath() 
	{
		// TODO Auto-generated method stub
		
	}
}