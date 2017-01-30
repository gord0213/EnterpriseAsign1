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
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SpritePanel extends JPanel{
	ArrayList<Sprite> spriteList  = new ArrayList<>();
	protected int ovalX = 175;
	protected int ovalY = 175;
	protected int ovalCenter = 235;
	protected int ovalWidth = 150;

	protected int numOfSpritesinCircle;
	public SpritePanel(){
		addMouseListener(new Mouse());
	}



	private void newSprite (MouseEvent event){
		Sprite sprite = new Sprite(this);
		spriteList.add(sprite);
		new Thread(sprite).start();
	}
	
	public void animate(){

	}
	public synchronized void consume()throws InterruptedException {
		++numOfSpritesinCircle;
		while (numOfSpritesinCircle <= 2){
			wait();
		}

		notifyAll();
	}
	public synchronized void produce() throws InterruptedException {
		--numOfSpritesinCircle;
		while (numOfSpritesinCircle > 2){
			System.out.println("There are more  than 2 balls in the circle");
			wait();
		}
		notifyAll();
	}
	private class Mouse extends MouseAdapter {
		@Override
	    public void mousePressed( final MouseEvent event ){

	        newSprite(event);
	    }
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (spriteList != null){
			for (Sprite sprite : spriteList){
				sprite.draw(g);
			}
		}
		//g.drawOval(ovalX, ovalY, ovalWidth, ovalWidth);
		g.drawOval(ovalX - 10, ovalY - 10, ovalWidth + 20, ovalWidth + 20);
	}
}
