package auton;

public class PathFinder 
{
	public static void main(String[] args)
	{
		DrawPath path = new DrawPath();
		
		while(!path.pathFinished())
		{
			try 
			{
				Thread.sleep(1);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		path.drivePath();
	}
}
