package roughedout;

public class Field {
	private int Length;
	private int Width;
	private int PixelLength;
	private int PixelWidth;
	private Robot_obj robot;
	private int Lengthratio;
	private int Widthratio;
	private boolean ratioiscalc = true;
	
	public int getLength()
	{
		return Length;
	}
	
	public void setLength(int length)
	{
		Length = length;
	}
	
	public int getWidth()
	{
		return Width;
	}
	
	public void setWidth(int width)
	{
		Width = width;
	}
	
	public int getPlength() {
		return PixelLength;
	}
	
	public void setPlength(int plength)
	{
		PixelLength = plength;
	}
	
	public int getPwidth()
	{
		return PixelWidth;
	}
	
	public void setPwidth(int pwidth) {
		PixelWidth = pwidth;
	}
	
	public Robot_obj getRobot()
	{
		return robot;
	}
	
	public void setRobot(Robot_obj robot)
	{
		this.robot = robot;
	}
	
	public int getLratio()
	{
		ratioiscalc = true;
		if(Length<=0)
		{
			System.out.println("Please define feild length. Make sure to use the same units the robot is measured in");
			ratioiscalc = false;
		}
		
		if(PixelLength<=0)
		{
			System.out.println("Please define field image length in pixels");
			ratioiscalc = false;
		}
		
		if (ratioiscalc == true)
		{
			Lengthratio = PixelLength/Length;
		}
		
		return Lengthratio;
	}
	public int getWratio()
	{
		ratioiscalc = true;
		if (Width<=0)
		{
			System.out.println("Please define field width. Make sure to use the same units the robot is measured in");
			ratioiscalc = false;
		}
		if (PixelWidth<=0)
		{
			System.out.println("Please define field image width in pixels");
			ratioiscalc = false;
		}
		if(ratioiscalc == true)										//sets the Width ratio of pixels per unit distance
		{
			Widthratio = PixelWidth/Width;
		}
		else {
			Widthratio = -1;										//If the function returns -1, read the console for help
		}
		return Widthratio;
	}
}