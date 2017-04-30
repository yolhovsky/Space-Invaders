/**
 * The flowing class is a class to describe a bullet in the game.
 *  
 * */
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class Bullet extends JPanel {
	
   private ImageIcon bulletIcon  = new ImageIcon(this.getClass().getResource("Bullet.png"));
   private ImageIcon bullet_E_Icon = new ImageIcon(this.getClass().getResource("EnemyBullet.png"));
   
   boolean isAlive;    // A variable to keep track of the object's state
   int x, y, whoShoot; // x and y are coordinates and whoShoots is to determine if it's the player or enemy 

	/**
	 * The contractor of the Bullet class, initializes the variables to a standard input
	 * 
	 * @param x - The x coordinate from the user input
	 * @param y	- The y coordinate from the user input
	 * */
   public Bullet(int x, int y, int whoShoot) {
   	
      this.x = x;
      this.y = y;
      this.whoShoot = whoShoot;
      isAlive = true;
   }

	/**
	 * drawBullet method calling to the fillOval method from the Graphics class 
	 * to draw the object.
	 * 
	 * @param g - An object to draw  
	 * */
   public void drawBullet(Graphics g) {
   	
      if (isAlive){//0 - Player shoot, 1 - Enemy shoot
         ImageIcon display = whoShoot == 0 ?  bulletIcon : bullet_E_Icon;
         g.drawImage(display.getImage(), x, y, 40, 40, null);
      }
   }
}
