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
	final static int MAX_SPEED = 10;
	
	SpritePanel panel;

	private int x;
	private int y;
	
	private int dx;
	private int dy;
	private Color color = Color.BLUE;
    private Thread animation;

    public Sprite (SpritePanel panel)
    {
    	this.panel = panel;
        x = random.nextInt(panel.getWidth());
        y = random.nextInt(panel.getHeight());
        dx = random.nextInt(2*MAX_SPEED) - MAX_SPEED;
        dy = random.nextInt(2*MAX_SPEED) - MAX_SPEED;

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
    @Override
    public void run() {
        while (true) {

            boolean inSphere = false;

            int nLastXPos;
            int nLastYPos;
            int ovalRadius = panel.ovalWidth / 2;
            int spriteRadius = SIZE / 2;

                nLastXPos = x-7;
                nLastYPos = y-7;
                move();
                panel.repaint();

                double distance = ((panel.ovalCenter - nLastXPos) * (panel.ovalCenter - nLastXPos)) +
                        ((panel.ovalCenter - nLastYPos) * (panel.ovalCenter - nLastYPos));
                double doesEqual = (ovalRadius + spriteRadius) * (ovalRadius + spriteRadius);


            if (distance <= doesEqual && inSphere == false ){
                System.out.println("they are in the circle");
                inSphere = true;
                if ( panel.numOfSpritesinCircle <= 2 ) {
                    try {
                        System.out.println(panel.numOfSpritesinCircle);
                        panel.consume();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if (panel.numOfSpritesinCircle > 4){
                    try {

                        System.out.println("There are more  than 2 balls in the circle");
                        panel.produce();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
//                if (distance <= doesEqual && inSphere == false ){
//                    System.out.println("they are in the circle");
//                    inSphere = true;
//                    if ( panel.numOfSpritesinCircle <= 2 ) {
//                        try {
//                            panel.consume();
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }else{
//                        try {
//
//                            System.out.println("There are more  than 2 balls in the circle");
//                            panel.produce();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
