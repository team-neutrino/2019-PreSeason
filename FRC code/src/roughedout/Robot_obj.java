package roughedout;

public class Robot_obj {
	private int Width;
	private int Length;
	private float Acceleration;
	private float maxvelocity;
	public Robot_obj(int w, int l, float a, float mv) {
		Width = w;
		Length = l;
		setAcceleration(a);
		setMaxvelocity(mv);
	}
	public void setsize(int w, int l) {
		System.out.println("Width changed from "+Width+" units to "+w+" units");
		System.out.println("Length changed from "+Length+" units to "+l+" units");
		Width = w;
		Length = l;
	}
	public int getlength() {return Length;}
	public int getwidth() {return Width;}
	public float getAcceleration() {return Acceleration;}
	public void setAcceleration(float acceleration) {Acceleration = acceleration;}
	public float getMaxvelocity() {return maxvelocity;}
	public void setMaxvelocity(float maxvelocity) {this.maxvelocity = maxvelocity;}
}