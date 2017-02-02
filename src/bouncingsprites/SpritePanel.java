/* File Name:
 * Author Name: Algonquin College
 * Modified By: 
 * Date:
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
	protected int numOfSpritesinCircle = 1;
    private int numOfBalls = 1;
	public SpritePanel(){
		addMouseListener(new Mouse());
        executorService = Executors.newCachedThreadPool();
        spriteList = new LinkedList<Sprite>();
    }


    /**
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

    /**
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
     *
     * @throws InterruptedException
     */
	public synchronized void consume()throws InterruptedException {
		++numOfSpritesinCircle;
		while (numOfSpritesinCircle <= 4 && numOfSpritesinCircle > 0){
			wait();
		}
        notifyAll();
	    --numOfSpritesinCircle;
	}

    /**
     *
     * @throws InterruptedException
     */
	public synchronized void produce() throws InterruptedException {
        while (numOfSpritesinCircle ==3){
            wait();
            --numOfSpritesinCircle;
        }
        notifyAll();
    }

	/**
	 *
	 */
	private class Mouse extends MouseAdapter {
		@Override
	    public void mousePressed( final MouseEvent event ){

	        newSprite(event);
	    }
	}

	/**
	 *
	 * @param g
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
