package auton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Ultrasonic path part that uses three points to drive to a distance away from an object.
 * This path part requires three points: A beginning point, the end point, the point where
 * the object to be tracked it.
 * 
 * @author JoelNeppel
 *
 */
public class Ultrasonic extends PathPart
{
	/**
	 * Constructor for the ultrasonic path part using the point where the 
	 * robot begins.
	 * @param start
	 * 	The point where the path part will begin
	 */
	public Ultrasonic(Point start) 
	{
		super(3, Color.cyan, 0, 1);
		addPoint(start);
	}

	@Override
	protected void drawFinished(Graphics g, Point[] p) 
	{
		g.drawLine(p[0].x, p[0].y, p[1].x, p[1].y);	
		g.fillOval(p[2].x - 6, p[2].y - 6, 12, 12);
		
		//Draw perpendicular line as the object the ultrasonic will detect
//		Perpendicular vector
//		i1 j1 known
//		i1 * i2 + j1 * j2 = 0
//		j2 = (-i1 / j1) * i2
		
		int vi1 = p[1].x - p[0].x;
		int vj1 = p[1].y - p[0].y;
		int l = 30;
		
		double vi2 = 0;
		double vj2 = 0;
		
		if(vj1 == 0)
		{
			vj2 = l;
		}
		else
		{
			vi2 = Math.sqrt(l * l / (1 + (vi1 * vi1) / (vj1 * vj1)));
			
			if(vi2 == 0)
			{
				vj2 = l;
			}
			else
			{
				vj2 = (-vi1 / vj1) * vi2;
			}
		}

		int i = (int) vi2;
		int j = (int) vj2;

		g.drawLine(p[2].x - i, p[2].y - j, p[2].x + i, p[2].y + j);
	}

	@Override
	public String describePath() 
	{
		double distance = MathSupport.getDistance(getPoints()[1], getPoints()[2]);
		distance *= DrawPath.INCHPIXEL;
		return "Driving to " + distance + "in away from object";
	}

	@Override
	public void drivePath() 
	{
		// TODO Auto-generated method stub
		
	}
}