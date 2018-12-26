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
	 * The index where the previous path part connects to
	 */
	private int startConnection;
	
	/**
	 * The index that the next path part will connect to
	 */
	private int endConnection;
	
	/**
	 * Constructor for a path part that uses the number of points
	 * to set the size of the array. The start and end connection 
	 * indexes are default at 0 and the last index
	 * @param numPoints
	 * 	The number of points the path part will use
	 * @param color
	 * 	The color the path will be drawn in
	 */
	protected PathPart(int numPoints, Color color)
	{
		points = new Point[numPoints];
		this.color = color;
		startConnection = 0;
		endConnection = points.length - 1;
	}
	
	/**
	 * Constructor for a path part that uses the number of points
	 * to set the size of the array. The start and end connection 
	 * indexes are the given values.
	 * @param numPoints
	 * 	The number of points in the array
	 * @param color
	 * 	The color to draw the path part
	 * @param startConnection
	 * 	The index for the point that connects this path part to the previous
	 * @param endConnection
	 * 	The index for the point that connects this path part to the next
	 */
	protected PathPart(int numPoints, Color color, int startConnection, int endConnection)
	{
		points = new Point[numPoints];
		this.color = color;
		this.startConnection = startConnection;
		this.endConnection = endConnection;
	}
	
	/**
	 * Returns whether the part needs more points for it to be completed.
	 * @return
	 * 	True if the part requires more points, 
	 * 	false if the part has all the needed points
	 */
	public boolean searchingForPoints()
	{
		for(Point p : points)
		{
			if(p == null)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Adds a point to the points array.
	 * @param point
	 * 	The point to be added to the path part
	 */
	public void addPoint(Point point)
	{
		//Won't allow user to add a null point
		if(point == null)
		{
			throw new NullPointerException("Given point cannot be null,"
					+ " try finishing the previous path part.");
		}
		
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
				return shouldRemove();
			}
		}
		
		return shouldRemove();
	}
	
	/**
	 * Removes the specified point.
	 * @param p
	 * 	The point to be removed
	 */
	public boolean removePoint(Point p)
	{
		for(int arrPos = 0; arrPos < points.length; arrPos++)
		{
			if(points[arrPos] != null)
			{
				if(points[arrPos].equals(p))
				{
					points[arrPos] = null;
				}
			}
		}
		
		return shouldRemove();
	}
	
	/**
	 * Returns true if there are no points in the path part and 
	 * should be removed, false if there is at least 1 point
	 * @return
	 * 	True if the path part should be removed, false if not
	 */
	private boolean shouldRemove()
	{
		for(Point p : points)
		{
			if(p != null)
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Returns the last point in the array that is not null.
	 * @return
	 * 	The closest non-null point to the end
	 */
	public Point getMostRecent()
	{
		for(int arrPos = points.length - 1; arrPos >= 0; arrPos--)
		{
			if(points[arrPos] != null)
			{
				return points[arrPos];
			}
		}
		
		return null;
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
	 * Returns the index of the point that connects this path part to the previous.
	 * @return
	 * 	The index of the point that connects this path part to the previous
	 */
	public int connectStart()
	{
		return startConnection ;
	}
	
	/**
	 * Returns the index that connects this path part to the next
	 * @return
	 * 	The index of the point that connects this path part to the next
	 */
	public int connectEnd()
	{
		return endConnection;
	}
	
	/**
	 * Returns the point that connects this path part to the previous.
	 * @return
	 * 	The point that connects this path part to the previous
	 */
	public Point getStartPoint()
	{
		return points[startConnection];
	}
	
	/**
	 * Returns the point that connects this path part to the next.
	 * @return
	 * 	The point that connects this path part to the next
	 */
	public Point getEndPoint()
	{
		return points[endConnection];
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
					fillPoint(g, point);
				}
			}
		}
	}
	
	/**
	 * Helper method that fills a point.
	 * @param g
	 * 	The graphics object to be used
	 * @param p
	 * 	The point to be drawn
	 */
	protected void fillPoint(Graphics g, Point p)
	{
		g.fillOval(p.x - 6, p.y - 6, 12, 12);
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
	 * Describes what the robot will do during this path part.
	 */
	public abstract String describePath();
}