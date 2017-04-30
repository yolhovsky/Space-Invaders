import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import javax.swing.JPanel;

public class Target extends JPanel {

   private Image display;
   public boolean isAlive;
   public int x, y, lives;
   public ArrayList<Bullet> bulletsE;
   
   public Target(Image image, int x, int y, int lives) {
   
      //The default Image for the target, or a new image:
      display = image == null ?  new ImageIcon(this.getClass().getResource("Target.png")).getImage() : image;
     
      isAlive = true;
      this.x = x;
      this.y = y;
      this.lives = lives;
      
      bulletsE = new ArrayList<Bullet>();
   }

   public void drawTarget(Graphics g) {
   
      if (isAlive)
         g.drawImage(display, x, y, 60, 60, null); 
      
   
   }
}