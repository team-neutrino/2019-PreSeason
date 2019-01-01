package roughedout;

public class Field {
	private int Length;
	private int Width;
	private int Plength;
	private int Pwidth;
	private Robot_obj robot;
	private int Lratio;
	private int Wratio;
	private boolean ratioiscalc = true;
	
	public int getLength() {return Length;}
	public void setLength(int length) {Length = length;}
	public int getWidth() {return Width;}
	public void setWidth(int width) {Width = width;}
	public int getPlength() {return Plength;}
	public void setPlength(int plength) {Plength = plength;}
	public int getPwidth() {return Pwidth;}
	public void setPwidth(int pwidth) {Pwidth = pwidth;}
	public Robot_obj getRobot() {return robot;}
	public void setRobot(Robot_obj robot) {this.robot = robot;}
	
	public int getLratio() {
		ratioiscalc = true;
		if(Length<=0) {
			System.out.println("Please define feild length. Make sure to use the same units the robot is measured in");
			ratioiscalc = false;
		}
		if(Plength<=0) {
			System.out.println("Please define field image length in pixels");
			ratioiscalc = false;
		}
		if (ratioiscalc == true) {Lratio = Plength/Length;}
		return Lratio;
	}
	public int getWratio() {
		ratioiscalc = true;
		if(Width<=0) {
			System.out.println("Please define field width. Make sure to use the same units the robot is measured in");
			ratioiscalc = false;
		}
		if(Pwidth<=0) {
			System.out.println("Please define field image width in pixels");
			ratioiscalc = false;
		}
		if(ratioiscalc == true) {Wratio = Pwidth/Width;}		//sets the Width ratio of pixels per unit distance
		else {Wratio = -1;}										//If the function returns -1, read the console for help
		return Wratio;
	}
}