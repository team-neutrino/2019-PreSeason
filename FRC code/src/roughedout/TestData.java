package roughedout;

import java.awt.*;

public class TestData {
	private static Point[] P = new Point[10];
	public static Point[] testdata() {
		Point a = new Point(0,0);
		int i = 0;
		int x = Rint();
		int y = Rint();
		P[i] = a;
		i++;
		a.move(x,y);
		while(i<10) {
			x = Rint();
			y = Rint();
//			System.out.println(a.toString());
			a = new Point(x,y);
//			System.out.println(a.toString());
			Intarray.addpoint(a);
//			P[i] = a;
			i++;
		}
		return P;
	}
	public static int Rint() {											//Random int generator within the range 0-10
		int randomNum = (int) Math.round((Math.random()*10));
		return randomNum;
	}
}
