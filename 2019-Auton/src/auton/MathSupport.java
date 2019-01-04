package auton;

import java.awt.Point;

/**
 * Math support class for finding paths. This class includes commonly used formulas
 * for drawing and path finding.
 * 
 * @author JoelNeppel
 *
 */
public class MathSupport 
{
	/**
	 * Static classes shouldn't be constructed.
	 */
	private MathSupport() {}
	
	/**
	 * Finds the angle between two lines created by three points.
	 * @param start
	 * 	One end of the angle
	 * @param center
	 * 	The center point of the angle
	 * @param end
	 * 	The other point of the angle
	 * @return
	 * 	The angle, in degrees, between the lines created by the points
	 */
	public static int getAngleDegree(Point start, Point center, Point end)
	{
		/*
		 * Angel between 2 vectors u and v: arccos((|u| dot |v|) / (magnitude u * magnitude v))
		 * Dot product = (ui * vi) + (uj * vj)
		 */
		
		//Finding vector i and j components 
		//multiplying j component by -1 since down is increasing
		double v1i = center.getX() - start.getX();
		double v1j = (center.getY() - start.getY()) * -1;
		
		double v2i = end.getX() - center.getX();
		double v2j = (end.getY() - center.getY()) * -1;
		
		//Dot product
		double dotProduct = v1i * v2i + v1j * v2j;
		
		//Magnitudes
		double v1Mag =	Math.sqrt(v1i * v1i + v1j * v1j);
		double v2Mag =	Math.sqrt(v2i * v2i + v2j * v2j);
		
		//Formula then to degrees
		double radians = Math.acos(dotProduct / (v1Mag * v2Mag));
		double degrees = Math.toDegrees(radians);

		//Get angle relative to the image with 0 degrees being 3 o'clock
		double startAngle = getImageAngle(start, center);
		double endAngle = getImageAngle(center, end);

		/*
		 * If the robot is turning left the angle should be negative 
		 */
		if(endAngle > startAngle)
		{
			if(endAngle - startAngle < 180)
			{
				degrees *= -1;
			}
		}
		else if (startAngle - endAngle > 180)
		{
			degrees *= -1;
		}
		
		return (int) Math.round(degrees);
	}
	
	/**
	 * returns angle, in degrees, from 0 degrees in the graphics window, 0 degees
	 * is defined as 3 o'clock. 
	 * @param start
	 * 	The start point of the line being measured
	 * @param end
	 * 	The end  point of the line being measured
	 * @return
	 * 	The angle in degrees
	 */
	public static double getImageAngle(Point start, Point end)
	{	
		double x = end.getX() - start.getX();
		double y = (end.getY() - start.getY()) * -1; //Multiply by -1 since down is increasing

		//Avoid dividing by 0
		if(x == 0)
		{
			if(y > 0)
			{
				return 90;
			}
			else
			{
				return 270;
			}
		}
		
		double radians = Math.atan(y / x);
		double degrees = Math.toDegrees(radians);

		/*
		 * arctan range (-90, 90) but we need range from [0, 360)
		 * so we make correction based on what quadrant the vector is in
		 */
		if(x > 0)
		{
			if(y > 0)
			{
				return degrees;
			}
			else
			{
				return degrees + 360;
			}
		}
		else
		{
			if(y > 0)
			{
				return 180 + degrees;
			}
			else
			{
				return 180 + degrees;
			}
		}
	}
	
	/**
	 * Returns the distance between two points.
	 * @param p1
	 * 	The first point
	 * @param p2
	 * 	The second point
	 * @return
	 * 	The distance between the points
	 */
	public static double getDistance(Point p1, Point p2)
	{
		double x = p1.getX() - p2.getX();
		double y = p1.getY() - p2.getY();
		double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		return distance;
	}
}