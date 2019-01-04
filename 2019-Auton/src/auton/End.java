package auton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * End point for the path.
 * 
 * @author JoelNeppel
 *
 */
public class End extends PathPart
{
	public End()
	{
		super(1, Color.red);
	}
	
	/**
	 * Constructor for the end that uses the last point on path to draw red dot.
	 * @param end
	 * 	The final point on the path
	 */
	public End(Point end) 
	{
		super(1, Color.red);
		addPoint(end);
	}

	@Override
	protected void drawFinished(Graphics g, Point[] p) 
	{
		fillPoint(g, p[0]);
	}

	@Override
	public String describePath() 
	{
		return "End";
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