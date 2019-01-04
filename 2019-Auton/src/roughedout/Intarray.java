package roughedout;

import java.awt.Point;

public class Intarray {
	private static Point[] points;
	
	public static void addpoint(Point p)													//Add a point to the end of the array
	{
		points[points.length] = p;
	}
	
	public int removepoint(Point p)															//Removes a point from the array and returns the number of removed points
	{
		int i = 0;
		int l = points.length;
		int removed = 0;
		while ((l-i)>=0)
		{
			if(points[i]==p)
			{
				int j=i;
				while(j<=l)
				{
					points[j]=points[j+1];
					j++;
				}
				removed++;
			}
			i++;
		}
		return removed;
	}
	public static Point[] getpoints()														//returns point array
	{
		return points;
	}
}
