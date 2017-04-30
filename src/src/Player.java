import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Player extends JPanel {

   private Image display;
   private boolean isAlive;
   public int x, y, lives, deadOrAlive;
   
   public ArrayList<Bullet> bulletsP; 
	
   public Player(Image image, int x, int y, int lives, int deadOrAlive){
   
      //The default Image for the player, or a new image:
      display = image == null ?  new ImageIcon(this.getClass().getResource("Player.png")).getImage() : image;
      
      this.isAlive = true;
      this.x = x;
      this.y = y;
      this.lives = lives;
      this.deadOrAlive = deadOrAlive;
   	
      bulletsP = new ArrayList<Bullet>();
   }

   public void drawPlayer(Graphics g) {
   
      if (isAlive)
         g.drawImage(display, x, y, 100, 100, null); 
   }
}