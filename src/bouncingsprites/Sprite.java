/* File Name:
 * Author Name: Algonquin College
 * Modified By: 
 * Date:
 * Description:
 */

package bouncingsprites;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Sprite implements Runnable{

	public final static Random random = new Random();
	
	final static int SIZE = 20;
	final static int MAX_SPEED = 5;
	private final static Object LOCK = new Object();
	SpritePanel panel;

	private int x;
	private int y;
	
	private int dx;
	private int dy;
	private Color color = Color.BLUE;

    public Sprite (SpritePanel panel)
    {
    	this.panel = panel;
        x = random.nextInt(panel.getWidth());
        y = random.nextInt(panel.getHeight());
        dx = random.nextInt(2*MAX_SPEED) - MAX_SPEED + 1;
        dy = random.nextInt(2*MAX_SPEED) - MAX_SPEED + 1;

    }

    public void draw(Graphics g){
        g.setColor(color);
	    g.fillOval(x, y, SIZE, SIZE);
    }

    public void move(){
        // check for bounce and make the ball bounce if necessary
        //
        if (x < 0 && dx < 0){
            //bounce off the left wall
            x = 0;
            dx = -dx;
        }
        if (y < 0 && dy < 0){
            //bounce off the top wall
            y = 0;
            dy = -dy;
        }
        if (x > panel.getWidth() - SIZE && dx > 0){
            //bounce off the right wall
        	x = panel.getWidth() - SIZE;
        	dx = - dx;
        }
        if (y > panel.getHeight() - SIZE && dy > 0){
            //bounce off the bottom wall
        	y = panel.getHeight() - SIZE;
        	dy = -dy;
        }


        //make the ball move
        x += dx;
        y += dy;
       // System.out.println("X = " + x + " Y = " + y);
    }
    public boolean isInCircle(){
        int nLastXPos;
        int nLastYPos;
        int ovalRadius = panel.ovalWidth / 2;
        int spriteRadius = SIZE / 2;

        nLastXPos = x - 7;
        nLastYPos = y - 7;

        double distance = Math.pow((panel.ovalCenter - nLastXPos) - 5, 2)
                + Math.pow(panel.ovalCenter - nLastYPos - 5, 2);
        double doesEqual = Math.pow((ovalRadius + spriteRadius), 2);
        return distance <= doesEqual;
    }

    @Override
    public void run() {
        while (true) {
            
            move();
            boolean inSphere = false;

            if (isInCircle()) {
                    try {
                        System.out.println(panel.numOfSpritesinCircle);
                        panel.consume();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
            if(isInCircle()){
                    try {

                       // System.out.println("There are more  than 2 balls in the circle");
                        panel.produce();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }

            try {
                Thread.sleep(40);  // wake up roughly 25 frames per second
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
