/* File Name: BouncingSprites.java
 * Author Name: Algonquin College
 * Modified By: Michael Gordanier
 * Date: Feb 03, 2014
 * Description:
 */

package bouncingsprites;

import javax.swing.JFrame;

public class BouncingSprites {
	
    private JFrame frame;
    private SpritePanel panel = new SpritePanel();
    private Sprite ball;

    /**
     * This method is used to create the frame and panel
     */
    public BouncingSprites() {
        frame = new JFrame("Bouncing Sprites 2017W");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
    }


    /**
     * This method starts the animation process of the panel
     */
    public void start(){
    	panel.animate();  // never returns due to infinite loop in animate method
    }

    /**This is the MAIN method that starts the rest of the code
     *
     * @param args
     */
    public static void main(String[] args) {
        new BouncingSprites().start();
    }
}
