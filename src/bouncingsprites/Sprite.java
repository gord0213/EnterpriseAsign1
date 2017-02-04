/* File Name: Sprite.java
 * Author Name: Algonquin College
 * Modified By: Michael Gordanier
 * Date: Feb 03, 2014
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
	private int prevY;
    private int prevX;

    private int dx;
	private int dy;
	private Color color = Color.BLUE;

    /**
     * This creates the the sprite and adds it to the panel,
     * the sprites spawn at x 300 and y 400.
     * this also creates the speed for how the balls move
     *
     * @param panel
     */
    public Sprite (SpritePanel panel)
    {
    	this.panel = panel;
        x = 300;
        y = 400;
        dx = random.nextInt(2*MAX_SPEED) - MAX_SPEED + 1;
        dy = random.nextInt(2*MAX_SPEED) - MAX_SPEED + 1;

    }

    /**
     * this method Draws the actual moving sprite itself
     * @param g Graphics class
     */
    public void draw(Graphics g){
        g.setColor(color);
	    g.fillOval(x, y, SIZE, SIZE);
    }

    /**
     * This method is the main bulk of the moving sprite
     * as it defines hoe the sprite moves and mekes sure the sprite doesnt
     * exit the window
     */
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

    /**
     * Collision detection Mathamatical equation
     *
     *  Authors: dasblinkenlight
     *  		peter Gruden
     *  		Craigo
     *  		cHero
     *  		Jeroen Vannevel
     *  		John Conner
     *  Type: Equation
     *  Location: http://stackoverflow.com/questions/8367512/algorithm-to-detect-if-a-circles-intersect-with-any-other-circle-in-the-same-pla
     * @return True if inside false if outside
     */
    public boolean isInCircle(){
        return isInCircle(x, y);
    }

    /**This method calculates weather the ball is inside or outside the oval
     *
     * @param xPos The posistion x of the sprite
     * @param yPoz The posistion x of the sprite
     * @return True if yPos, xPos are in the oval
     */
    public boolean isInCircle(int xPos, int yPoz) {
        int nLastXPos;
        int nLastYPos;
        int ovalRadius = panel.ovalWidth / 2;
        int spriteRadius = SIZE / 2;

        nLastXPos = xPos - 7;
        nLastYPos = yPoz - 7;

        double distance = Math.pow((panel.ovalCenter - nLastXPos) - 5, 2)
                + Math.pow(panel.ovalCenter - nLastYPos - 5, 2);
        double doesEqual = Math.pow((ovalRadius + spriteRadius), 2);

        return distance <= doesEqual;

    }

    /**
     * This Method controles how many frames per second the sprite moves
     * it also controles whether the sprite should be consumed or produced
     */
    @Override
    public void run() {
        while (true) {
            prevX = x;
            prevY = y;
            move();

            if (!isInCircle(prevX, prevY) && isInCircle()) {
                try {
                    panel.produce();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(isInCircle(prevX, prevY) && !isInCircle()){
                try {
                    // System.out.println("There are more  than 2 balls in the circle");
                    panel.consume();
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
