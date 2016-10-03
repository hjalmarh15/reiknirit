package d0;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;


public class Drawing {
	public static void main(String[] args) {
		
		StdDraw.setPenColor(250, 250,8);
		StdDraw.filledSquare(0.5, 0.5, 1);
		StdDraw.setPenColor(255, 0, 0);
		StdDraw.filledCircle(0.5, 0.85, 0.09);
		StdDraw.setPenColor(0, 0, 102);
		StdDraw.setPenRadius(0.03);
		StdDraw.circle(0.5, 0.85, 0.1);
		StdDraw.line(0.43, 0.78, 0.57, 0.92);
		StdDraw.line(0.43, 0.92, 0.57, 0.78);
		
		StdDraw.setPenColor(255, 0, 0);
		StdDraw.text(0.5, 0.7, "X - MEN");
		
		StdDraw.setPenRadius(0.01);
		for(int i =0; i< 10; i++) {
			StdDraw.line(0, i*0.1, i*0.1, 0);
			StdDraw.pause(100);
		}
		
		StdDraw.setPenColor(StdRandom.uniform(250), StdRandom.uniform(250), StdRandom.uniform(250));
		StdDraw.square(StdRandom.uniform(), StdRandom.uniform(), StdRandom.uniform());
	}
}
