package roughedout;

import java.awt.Point;

public class Spline_simple {
	Point[] p = TestData.testdata();
	int s = 0;									//s is the "start" place counter
	public Point[] newspline(){
		Point start = p[s];
		Point prev;
		Point next;
		Point[] mp = new Point[15000];
		while(s<p.length){
			start = p[s];
			s++;
		}
		return p;
	}
}
