/* File Name: SpritePanel.java
 * Author Name: Algonquin College
 * Modified By: Michael Gordanier
 * Date: Feb 03, 2014
 * Description:
 */

package bouncingsprites;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SpritePanel extends JPanel{
	protected List<Sprite> spriteList;
	protected int ovalX = 175;
	protected int ovalY = 175;
	protected int ovalCenter = 235;
	protected int ovalWidth = 150;
    private ExecutorService executorService;
	protected int numOfSpritesinCircle = 0;
    private int numOfBalls = 1;
	public SpritePanel(){
		addMouseListener(new Mouse());
        executorService = Executors.newCachedThreadPool();
        spriteList = new LinkedList<Sprite>();
    }


    /** Creates a new Sprite
     *
     * @param event
     */
	private void newSprite (MouseEvent event){
		Sprite sprite = new Sprite(this);
		spriteList.add(sprite);
        //executorService.execute(sprite);
        new Thread(sprite, ("Ball " + numOfBalls )).start();
		++numOfBalls;
	}

    /**Creates the movement of the sprite
     *
     */
	public void animate(){
        while (true) {
            repaint();
            //sleep while waiting to display the next frame of the animation
            try {
                Thread.sleep(40);  // wake up roughly 25 frames per second
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
	 * This method forces the thread to wait if there are less than 2 balls in the circle
	 * balls are leaving
     * @throws InterruptedException
     */
	public synchronized void consume()throws InterruptedException {
		while (numOfSpritesinCircle  <= 2){
			wait();
		}
		numOfSpritesinCircle--;
        notifyAll();
	}
	/**
	 * This method forces the thread to wait if there are more than 4 balls moving in the circle.
	 * Balls are entering
	 * @throws InterruptedException
	 */
	public synchronized void produce() throws InterruptedException {
        while (numOfSpritesinCircle >= 4){
            wait();
        }
		numOfSpritesinCircle++;
        notifyAll();
    }

	/**This method allows the user to createa new ball every time they click
	 *
	 */
	private class Mouse extends MouseAdapter {
		@Override
	    public void mousePressed( final MouseEvent event ){

	        newSprite(event);
	    }
	}

	/**This Method draws the moving sprite and the ocal that the sprites come in contact with
	 * @param g Graphics class
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (spriteList != null){
			for (Sprite sprite : spriteList){
				sprite.draw(g);
			}
		}
		g.drawOval(ovalX - 10, ovalY - 10, ovalWidth + 20, ovalWidth + 20);
	}
}
