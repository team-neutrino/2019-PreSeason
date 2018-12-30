package auton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Spline extends PathPart 
{
	protected Spline(Point splineStart) 
	{
		super(3, Color.ORANGE);
		addPoint(splineStart);
	}

	@Override
	public void drawPathPart(Graphics g)
	{
		Point[] p = getPoints();
		g.setColor(Color.orange);
		if(searchingForPoints())
		{
			fillPoint(g, p[0]);
			
			//Draws circle instead of just a point
			if(p[1] != null)
			{
				double x = Math.abs(p[1].getX() - p[0].getX());
				double y = Math.abs(p[1].getY() - p[0].getY());
				int radius = (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
				
				g.drawOval(p[1].x - radius, p[1].y - radius, radius * 2, radius * 2);
			}
		}
		else
		{
			drawFinished(g, getPoints());
		}
	}
	
	@Override
	protected void drawFinished(Graphics g, Point[] p) 
	{
		Point center = getPoints()[1];
		int radius = (int) MathSupport.getDistance(getPoints()[0], center);
		int startAngle = (int) MathSupport.getImageAngle(getPoints()[0], center);
		int angle = (int) MathSupport.getAngleDegree(getPoints()[0], center, getPoints()[2]);
		g.drawArc(center.x - radius, center.y - radius, radius * 2, radius * 2, startAngle, angle);
	}

	@Override
	public String describePath() 
	{
		return "Driving Spline";
	}

	@Override
	public void drivePath() 
	{
		// TODO Auto-generated method stub
		
	}
}