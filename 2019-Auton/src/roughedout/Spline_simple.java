package roughedout;

import java.awt.Point;

public class Spline_simple {
	Point[] p = TestData.testdata();
	int s = 0;									//s is the "start" place counter
	float zi[] = new float[p.length];			//outputs of s2
	float s2[] = new float[p.length];			//second derivative of S()
	float s1[] = new float[p.length];			//first derivative of S()
	float S[] = new float[p.length];			//position function S()
	float hi[];									//t(i+1)-t(i)
	float y[];
	float t[];
	float b[];
	public Point[] newspline()
	{
		zi[0] = 0;
		zi[p.length-1] = 0;
		Point start = p[s];
		Point prev;
		Point next;
		Point[] mp = new Point[15000];
		while(s<(p.length-1))
		{
			prev = p[s-1];
			start = p[s];
			next = p[s+1];
			
			
			
			
			
			s++;
			
		}
		return p;
	}
	public void calcimput()
	{
		int i = s;
		t[i] = (float)p[i].getX();
		y[i] = (float)p[i].getY();
		while(i<(p.length-1))
		{
			t[i+1 ]= (float)p[i+1].getX();
			y[i+1] = (float)p[i+1].getY();
			hi[i] = t[i+1]-t[i];
			b[i] = (y[i+1]-y[i])/hi[i];
		}
	}
	public void zi()
	{
		int i = s;
		zi[0] = 0;
		zi[p.length-1] = 0;
	}
	public void S2()
	{
		int i = s;
		int x;
		
		while(i<(p.length-1))
		{
			x = (int)p[i].getX();
			float a,b;
			a = (float) ((zi[i+1]/hi[i]) * (x-t[i]));
			b = (float) ((zi[i]/hi[i])*(x-t[i+1]));
			s2[i] = (a-b);
			i++;
		}
	}
	public void S1()
	{
		int i = s;
		int x;
		while(i<(p.length-1))
		{
			x = (int)p[i].getX();
			float a,b,c,d;
			a = (float) ((zi[i+1]/(2*hi[i])) * Math.pow((x-t[i]),2));
			b = (float) ((zi[i]/(2*hi[i]))*Math.pow((x-t[i+1]),2));
			c = ((y[i+1]-y[i])/hi[i]);
			d = ((zi[i+1]-zi[i])*hi[i]/6);
			s1[i] =(a-b+c-d);
			i++;
		}
	}
	public void S()
	{
		int i = s;
		int x;
		while(i<(p.length-1))
		{
			x = (int)p[i].getX();
			float a,b,c,d;
			a = (float) ((zi[i+1]/(2*hi[i])) * Math.pow((x-t[i]),2));
			b = (float) ((zi[i]/(2*hi[i]))*Math.pow((x-t[i+1]),2));
			c = ((y[i+1]/hi[i])-((hi[i]*zi[i+1])/6))*(x-t[i]);
			d = ((y[i]/hi[i])-(hi[i]*zi[i])/6)*(x-t[i+1]);
			S[i] =(a-b+c-d);
			i++;
		}
	}
}