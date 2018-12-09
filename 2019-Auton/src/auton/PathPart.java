package auton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Abstract class that will be used to create different kinds of path parts.
 * The path will need the required number of points added and then the user may: Display 
 * the path, drive the path, or export the code needed to drive the path.
 * 
 * @author JoelNeppel
 *
 */
public abstract class PathPart 
{	
	/**
	 * Array of all the points that will be used to draw 
	 * and drive this part of the path
	 */
	private Point[] points;
	
	/**
	 * The color the path will be drawn in
	 */
	private Color color;
	
	/**
	 * Constructor for a path part that uses the number of points
	 * to set the size of the array.
	 * @param numPoints
	 * 	The number of points the path part will use
	 * @param color
	 * 	The color the path will be drawn in
	 */
	protected PathPart(int numPoints, Color color)
	{
		points = new Point[numPoints];
		this.color = color;
	}
	
	/**
	 * Returns whether the part needs more points for it to be completed.
	 * @return
	 * 	True if the part requires more points, 
	 * 	false if the part has all the needed points
	 */
	public boolean searchingForPoints()
	{
		return points[points.length - 1] == null;
	}
	
	/**
	 * Adds a point to the points array.
	 * @param point
	 * 	The point to be added to the path part
	 */
	public void addPoint(Point point)
	{
		for(int arrPos = 0; arrPos < points.length; arrPos++)
		{
			if(points[arrPos] == null)
			{
				points[arrPos] = point;
				return;
			}
		}
	}
	
	/**
	 * Removes the first point closest to the end and returns whether
	 * the path part should be removed or not.
	 * @return
	 * 	True if there are no points in the object and should be removed,
	 * 	false if the object still contains at least 1 point
	 */
	public boolean removePoint()
	{
		for(int arrPos = points.length - 1; arrPos >= 0; arrPos--)
		{
			if(points[arrPos] != null)
			{
				points[arrPos] = null;
				
				if(points[0] == null)
				{
					return true;
				}
				
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Returns the points being used in the path part.
	 * @return
	 * 	An array of the points being used, points may be null
	 */
	public Point[] getPoints()
	{
		return points;
	}
	
	/**
	 * Returns the last point in the array, possible null.
	 * @return
	 * 	The last point, possibly null
	 */
	public Point getLastPoint()
	{
		return points[points.length - 1];
	}
	
	/**
	 * Draws the path part based on the subclass if the path part has all the 
	 * required points and only draws the points if the path part is not yet complete.
	 * @param g
	 * 	The graphics object that will be used to draw
	 */
	public void drawPathPart(Graphics g)
	{
		g.setColor(color);
		
		if(!searchingForPoints())
		{
			drawFinished(g, points);
		}	
		else
		{
			for(Point point : points)
			{
				if(point != null)
				{
					g.fillOval(point.x - 5, point.y - 5, 10, 10);
				}
			}
		}
	}
	
	/**
	 * Draws the path part if it has all the needed points.
	 * @param g
	 * 	The graphics object that will be used to draw
	 * @param p
	 * 	The array of points for the path part
	 */
	protected abstract void drawFinished(Graphics g, Point[] p);
	
	/**
	 * Moves the robot along the specified path using 
	 * all the needed points.
	 */
	public abstract void drivePath();
}