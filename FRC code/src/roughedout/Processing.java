package roughedout;

import java.awt.Point;
import java.util.List;

public class Processing {

	public static void main(String[] args) {
		Point[] p = TestData.testdata();
		Point c;
//		Point Start = p[0];
//		Point End = p[p.length-1];
		for(int i=1;i<(p.length-2);i++) {
			c=p[i];
			Double d = c.distance(p[i-1]);
			System.out.println(d+" , "+c+p[i-1]);
			
		}
	}
//	public static Point[] getloc(int refreshrate, Point[] p, Robot_obj R){
//		Point[] locations = new Point[15*refreshrate];			//Refresh rate for electronics in hz multiplied by length of auto period
//		locations[0] = p[0]; 									//Sets the start point
//		Robot_obj Rob = R;
//		return locations;
//	}
}