package d0;
import java.awt.event.KeyEvent;

import edu.princeton.cs.algs4.StdDraw;
public class Tester { 

    public static void main(String[] args) {

        // set the scale of the coordinate system
        StdDraw.setXscale(-1.0, 1.0);
        StdDraw.setYscale(-1.0, 1.0);
        StdDraw.enableDoubleBuffering();

        // initial values
        double rx = Math.random()*0.480;
        double ry = Math.random()*0.860;     // position
        double vx = Math.random()*0.1;
        double vy = Math.random()*0.2;     // velocity
        double radius = 0.05;              // radius

        // main animation loop
        while (true)  { 

            // bounce off wall according to law of elastic collision
            if (Math.abs(rx + vx) > 1.0 - radius) vx = -vx;
            if (Math.abs(ry + vy) > 1.0 - radius) vy = -vy;

            // update position
            rx = rx + vx; 
            ry = ry + vy; 

            // clear the background
            StdDraw.clear(StdDraw.PINK);

            // draw ball on the screen
            //String ballCountString = this.getParameter(20);
            for(int i=0; i< 20;i++) {
                StdDraw.setPenColor(StdDraw.BLUE); 
                StdDraw.filledCircle(rx, ry, radius); 

            // copy offscreen buffer to onscreen
            StdDraw.show();

            // pause for 20 ms
            StdDraw.pause(20);

            // Press Space to bring balls to center
            
            }
            
                
            
        } 
    } 
} 