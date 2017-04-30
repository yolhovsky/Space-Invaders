/**
 * © All rights reserved for Group 1 - A copy and/or implementing of that class or any other class in this 
 * project without a written consent from Backbone nor Mr.Silver (known as QA), will results in a serious issue 
 * and will lead to further actions in court. 
 * 
 * @author Group 1 
 * @version 1.0
 * @
 * 
 * The Space_invadors3 class is used to create a game in which the user will be able to 
 * interact with the program using the java GUI interface.
 * 
 * That particular class extending and implementing other classes such as JFrame or KeyListener,
 * to construct a full functioning interactive game.
 * */
 
/*
 *
 * Add a random number that will change the direction of the enemy each time.
 * 
 * Add the music
 *  
 * */
 
//import javax.swing.JProgressBar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.*;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Space_invadors3 extends JPanel implements KeyListener{//, Runnable {
	  
   //JProgressBar prg;
   
   private static JFrame frame;
	
   private Image background;
   private ImageIcon deadMeat  = new ImageIcon(this.getClass().getResource("Explosion.png")); // Add the explosion!!!!
   
   private static final int NUM_OF_TARGETS_COL = 10;//Can't be less than 10
   private static final int NUM_OF_TARGETS_ROW = 2;
   private static final int NUM_OF_OBSTACLES   = 7;
   
   private int targetsVelocity = 0; 						// initial velocity of targets
   private static int TARGET_LIVES = 1; // minimum life for the target
   private static int TARGET_SPEED = 1; // minimum speed for the target
   private Target[][] targets = new Target[NUM_OF_TARGETS_ROW][NUM_OF_TARGETS_COL];  // 2D array to hold more than 1 row of enemy
   
   private Player player;
   private static int PLAYER_LIVES = 10 ; // minimum life for the player
  
   private Obstacle [] obstacles   = new Obstacle[NUM_OF_OBSTACLES];
   
   private boolean isAnyAlive      = false; // Used with the player's bullets to determine if they are excited on the screen
   private boolean isAnyAliveEnemy = false; // Used for the enemy's bullets 
   private boolean enemyAlive      = false; // If the player didn't kill all the targets then a different menue will be shown
   
   private URL enemyDie  = Space_invadors3.class.getResource("Dying1.wav");
   private URL playerDie = Space_invadors3.class.getResource("Dying2.wav");
   private AudioClip enemyClip = Applet.newAudioClip(enemyDie);
   private AudioClip playerClip = Applet.newAudioClip(playerDie);

	/**
	 * The contractor of the class will instantiate the obstacle, target, Bullet and the player class in order to create full functioning game.
	 * Also, will create a new object for the background image.
	 * 
	 * @param obstaclesIcon - Passing an image parameter to replace the default one for the obstacle view. 
	 * @param enemyIcon - Passing an image parameter to replace the default one for the enemy view.
	 * @param playerIcon - Passing an image parameter to replace the default one for the player view.
	 * */
   public Space_invadors3(ImageIcon obstaclesIcon,ImageIcon enemyIcon,ImageIcon playerIcon){
   	
      //enemyDie  = Space_invadors3.class.getResource("Dying1.wav");
      //enemyClip = Applet.newAudioClip(enemyDie);
      /*
      prg = new JProgressBar(0,100);
      prg.setValue(100);
      prg.setStringPainted(true);
      prg.setEnabled(true);
      add(prg);
      //prg.setString("100");*/
      
      setFocusable(true);
      
   	// Creates obstacles
      int obstacleX = 25, lives = 5; // each brick has 5 lives
      for (int i = 0; i < obstacles.length; i++, obstacleX += 100){
        
         if(obstaclesIcon == null)
            obstacles[i] = new Obstacle(null, obstacleX + 50, 500, lives);
         else
            obstacles[i] = new Obstacle(obstaclesIcon.getImage(), obstacleX + 50, 500, lives);
        
         add(obstacles[i]);
      }
          
   	// Create targets
      int targetX = 0, targetY = 50;
      
      for (int a = 0; a < NUM_OF_TARGETS_ROW; a++){
      
         for (int i = 0; i < NUM_OF_TARGETS_COL; i++, targetX += 50){
          
            if(enemyIcon == null)
               targets[a][i] = new Target(null, targetX, targetY, TARGET_LIVES);
            else
               targets[a][i] = new Target(enemyIcon.getImage(), targetX, targetY, TARGET_LIVES);
          
            add(targets[a][i]);
         }
         
         targetY += 50;
         targetX = 0;
      }
     
      // Create the player:
      if(playerIcon == null)
         player = new Player(null, 350, 650, PLAYER_LIVES, 0);
      else
         player = new Player(playerIcon.getImage(), 350, 650, PLAYER_LIVES, 0);
      
      add(player); 				 // Adds player to the panel
      this.addKeyListener(this); // Adds keyListener to the panel
   	
      // Add the background:
      background = new ImageIcon(getClass().getResource("Background.jpg")).getImage();
   }
	
	/**
	 * The paint method is used to paint an object as a graphical user interface
	 * 
	 * @param g - The specified graphic window 	  
	 * */
   @Override 
   protected void paintComponent(Graphics g) {
   
      super.paintComponent(g);
   	
      g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
   	
      player.drawPlayer(g); 												// draws player on the screen
   
      for (int i = 0; i < obstacles.length; i++) {
         obstacles[i].drawObstacles(g); 									// draws obstacles on the screen
      }
     
      for(int a = 0; a < NUM_OF_TARGETS_ROW; a++){                              // draws targets on the screen
      
         for (int i = 0; i < NUM_OF_TARGETS_COL; i++) {
         
            targets[a][i].drawTarget(g);		
            
            for (Iterator<Bullet> it = targets[a][i].bulletsE.iterator(); it.hasNext();/*NO arg*/) {
            		  
               Bullet temp = it.next();
               temp.drawBullet(g);
            }
         }
      }
      /*
      for(int a = 0; a < NUM_OF_TARGETS_ROW; a++){                              // draws targets on the screen
          
         for (int i = 0; i < NUM_OF_TARGETS_COL; i++) {
          
            for (Iterator<Bullet> it = targets[a][i].bulletsE.iterator(); it.hasNext();/*NO arg) {
            	  
               Bullet temp = it.next();
               temp.drawBullet(g);
            }					
         }
      }
      */
      
      for (Iterator<Bullet> it = player.bulletsP.iterator(); it.hasNext();){// bullets.iterator()
         Bullet b = it.next();
         b.drawBullet(g); 												// draws bullets on the screen
      }
   	
      g.dispose();
   }

	/**
	 * moveObjects method is used to move the objects on the screen, in this case the objects are the bullets.
	 * */
   public void moveObjects() {
   	
      int moveY = 0; // A local variable to direct the enemy on the y-value, after the enemy reached every edge, it will go down ten levels.
   	
   	/*
   	 * The interface Iterator<E>  takes a type (Bullet class in this case) and iterate among the elements inside an
   	 * linked list.
   	 * 
   	 * The "it" is a pointer pointing to the head of the list and as long that .iterator returns another element the for loop 
   	 * will continue  
   	 * */
      for (Iterator<Bullet> it = player.bulletsP.iterator(); it.hasNext();/*NO arg*/) { // Draws the bullets fly up the screen (y decreases)
      	
         Bullet tempBullet = it.next(); 												  // pull out Bullet objects from the list 1 at a time
         isAnyAlive = tempBullet.isAlive ? true : false;
         tempBullet.y = tempBullet.y - 13; 												   	  // move the bullet 13 pixels up each repaint()
      }
      
   //----------------------------------------------------------------------------------------------------------------------------------   	
      if (targets[0][NUM_OF_TARGETS_COL-1].x == 750) { 						  // targets move in relation to the far right target
         targetsVelocity = -1 * TARGET_SPEED; 								  // targets move left
         moveY = 10; 														  // targets go down one row
      } 
      else if (targets[0][NUM_OF_TARGETS_COL-1].x == 450) { 				  // targets move in relation to the far right target
         targetsVelocity = TARGET_SPEED; 									  // targets move right
         moveY = 10; 														  // targets go down one row
      }
   //----------------------------------------------------------------------------------------------------------------------------------   
      
      for(int a = 0; a < NUM_OF_TARGETS_ROW; a++){
         
         for (int i = 0; i < NUM_OF_TARGETS_COL; i++) {
          
         //---------------------------------------------------------------------------------------
            for (Iterator<Bullet> it = targets[a][i].bulletsE.iterator(); it.hasNext();/*NO arg*/) {
              
               Bullet temp = it.next();
               isAnyAliveEnemy = temp.isAlive ?  true : false;
               temp.y += 6;
            }
            //---------------------------------------------------------------------------------------
            
            targets[a][i].x = targets[a][i].x + targetsVelocity; 					// move the targets to either left or right
            targets[a][i].y = targets[a][i].y + moveY; 								// move the targets down
         }
      }
   }
	
	/**
	* The anyHit method measuring the bullet and the target x and y axis to report if ther's a hit or not. 
	* */
   public void anyHit() { // compares each bullet x,y to each target x,y
   	
      //-----------------------------THE PLAYER BULLET-------------------------------------------
      for (Iterator<Bullet> it = player.bulletsP.iterator(); it.hasNext(); /*NO args*/){
      
         Bullet tempBullet = it.next();											  // pulling out 1 bullet object from the list at a time
      	
         if (tempBullet.isAlive) {												  // if bullet is still on the screen 	
           //Check the position of the bullet corresponding to the target:
            for(int a = 0; a < NUM_OF_TARGETS_ROW; a++){
            
               for (int i = 0; i < NUM_OF_TARGETS_COL; i++) {
               
                  if (targets[a][i].isAlive) { //If the enemy is still in the game
                  
                     boolean hit = false;
                    
                   //Checking for matching locations:
                     if (tempBullet.x >= targets[a][i].x && tempBullet.x <= targets[a][i].x + 50 && 
                      tempBullet.y >= targets[a][i].y && tempBullet.y <= targets[a][i].y + 50)
                        hit = true; // If there is a hit then the variable will be change to true
                  
                     if (hit) {//If the bullet hit the target:
                        tempBullet.isAlive = false;//The bullet is deleted from the screen
                          
                        if(targets[a][i].lives > 0){//If the target had more than 0, subtract 1
                           targets[a][i].lives -= 1; 
                        }
                        if(targets[a][i].lives == 0){// if target has 0 lives, delete the icon from the screen
                           targets[a][i].isAlive = false;
                           enemyClip.play();
                        }
                     }
                  }
               
                  if (tempBullet.isAlive && tempBullet.y <= 0) // if bullet flew off the screen without hitting any targets
                     tempBullet.isAlive = false;
               }
            }
         
            //Check the position of the bullet corresponding to the obstacle:
            for (int i = 0; i < obstacles.length; i++) {
            	
               boolean hit = false;
            	
               if (obstacles[i].isAlive) {
               	
                  if (tempBullet.x >= obstacles[i].x && tempBullet.x <= obstacles[i].x + 55 && 
                    tempBullet.y >= obstacles[i].y && tempBullet.y <= obstacles[i].y + 30)
                    
                     hit = true;
               	
                  if (hit) {
                     tempBullet.isAlive = false;
                     obstacles[i].lives -= 1; // reduces the brick life by 1;
                  }               	
                  if (obstacles[i].lives == 0) {
                     obstacles[i].isAlive = false; // brick dies after 5 hits
                  }
               }
            }
         }
      }
      
      //-----------------------------THE ENEMY BULLET-------------------------------------------
      for(int a = 0; a < NUM_OF_TARGETS_ROW; a++){
          
         for (int i = 0; i < NUM_OF_TARGETS_COL; i++) {
           
            for (Iterator<Bullet> it = targets[a][i].bulletsE.iterator(); it.hasNext();/*NO arg*/) {
              
              // Checking the position of the BULLET of EACH ENEMY:
              
               Bullet temp = it.next();
              
               if (temp.isAlive){
                 
                  boolean hit = false;
                 
                 //Check if one of the bullets of the enemy match the location of the player:
                  if (temp.x >= player.x && temp.x <= player.x + 50 && 
                      temp.y >= player.y && temp.y <= player.y + 50){
                     hit = true;
                  }
                  if (hit) {//If the bullet hit the Player:
                     temp.isAlive = false;//The enemy's bullet is deleted from the screen
                    
                     if(player.lives > 0){//If the Player had more than 0, subtract 1
                        player.lives -= 1; 
                     }
                  }
               }
              
              //If there was no hit:
               if (temp.isAlive && temp.y >= 800){ // if bullet flew off the screen without hitting any targets
                  isAnyAliveEnemy = false;
                  temp.isAlive = false;
               }
            }
         }
      }
   }

   boolean isGameOver() { // Checks if alive targets are left
   	
      boolean gameOver = true;
   
      Dead:   
      for(int a = 0; a < NUM_OF_TARGETS_ROW; a++){
      
         for (int i = 0; i < NUM_OF_TARGETS_COL; i++) {
         
            if (targets[a][i].isAlive){
               gameOver = false;
            
            //If the player didn't kill all the targets he/she automatically lose
               if(targets[a][i].y == 500){
               
                  gameOver = true;
                  enemyAlive = true; 
                  
                  playerClip.play();
                  
                  break Dead;
               }
            }
         }
      }
      
      
      //ADD HERE THE CASE WHEN THE USER GOT KILLED BY THE ENEMY
      if(player.lives == 0){
         playerClip.play();
         JOptionPane.showMessageDialog(null, "YOU'RE A DEAD MEAT!!!");
         
         
         
         gameOver = true;
      }
      
   	
      return gameOver;
   }

   @Override
   public void keyPressed(KeyEvent e) {
   	
      int code = e.getKeyCode();
   	
      if (code == KeyEvent.VK_RIGHT) {
        
         if (  !   (player.x >= 750)) {
            player.x = player.x + 10;
         } 
      } 
      
      else if (code == KeyEvent.VK_LEFT) {
      
         if (  !   (player.x <= 0)) {
            player.x = player.x - 10;
         }
      } 
      
      else if (code == KeyEvent.VK_SPACE) { // releases a bullet each time SPACE pressed
      	
         if(!isAnyAlive){ // Checks that there is no bullets in the screen before shooting new one
            Bullet bullet = new Bullet(player.x + 29, player.y + 0, 0);  // Releases a bullet from where the player currently is
            player.bulletsP.add(bullet);  								       // add the bullet to the list of Bullets
            add(bullet); 											                // add the bullet to the screen	
         }  
      }
   }

   /*public void run() {
      enemyShoot();
   }*/
   
   public void enemyShoot(){
      
      for(int a = 0; a < NUM_OF_TARGETS_ROW; a++){
            
         for (int i = 0; i < NUM_OF_TARGETS_COL; i++) {
             
            if (targets[a][i].isAlive && !isAnyAliveEnemy) {// if there is an enemy at the corrent location AND there is no bullets on the screen from the enemy then:
            	 
               Bullet enemyBullet = new Bullet(targets[a][i].x + 10, targets[a][i].y + 35, 1); // Releases a bullet from where the player currently is
               targets[a][i].bulletsE.add(enemyBullet);										// Add the bullet to the list of Bullets
               add(enemyBullet);															// Add the bullet to the screen
            }	
         }
      }
   }
   
   @Override public void keyTyped(KeyEvent e) {/*not in use*/}

   @Override public void keyReleased(KeyEvent e) {/*not in use*/} 

   public static void playGame(Space_invadors3 obj){
   
      frame = new JFrame();
      frame.setTitle("Space Invaders Group 1");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(800, 800);
      frame.setResizable(false);
      frame.setLocation(400, 100);
      frame.add(obj);
      frame.setVisible(true);
   }

   public static JComboBox startMenue(int firstLine){
      
      if(firstLine == 1) 
         JOptionPane.showMessageDialog(null, "Hello player and wellcome! \n"+"Please choose a level of dificulty from the folowing menue");
         
      String [] options = {"Grandma (Easy)", "Player (Mid)", "KILL ZONE (Hard)"};
      JComboBox optionsMenue = new JComboBox(options);
      optionsMenue.setEditable(true);
      JOptionPane.showMessageDialog( null, optionsMenue, "SELECT YOUR LEVEL!", JOptionPane.QUESTION_MESSAGE);
         
      return optionsMenue; 
   }

   public static void main(String[] args) throws InterruptedException {
     
      //Improving the basic java look and feel:
      //DO NOT MOVE IT TO SOMEWHERE ELSE
      try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
      catch(Exception e){e.printStackTrace();}
      
      URL mainURL = Space_invadors3.class.getResource("main_sound.wav");
      AudioClip mainClip = Applet.newAudioClip(mainURL);
      mainClip.loop();
   			
      JComboBox optionsMenue = startMenue(1);
      
      switch(optionsMenue.getSelectedIndex()){
      
      //No need for case 0, by default the life will be 1.
      
         case 1:
            {
               TARGET_LIVES = 2;
               TARGET_SPEED = 2;
               break;
            }
         case 2:
            {
               TARGET_LIVES = 3;
               TARGET_SPEED = 3;
               break;
            }
      }
      
      Space_invadors3 obj = new Space_invadors3(null,null,null);
     
      playGame(obj);
   
      while (true) {
      	
         obj.moveObjects();
         obj.enemyShoot();
         obj.anyHit(); 
         Thread.sleep(20); // The game is very slow, must use 2 threads!
         obj.repaint(); // This method call the paint (override) method by default
      
         if (obj.isGameOver()){
         	
            if(!obj.enemyAlive){
            
               int input = JOptionPane.showConfirmDialog(null, "WELL DONE!!! \nYou killed them all! \nWanna go for anouther round?!", "GOD BLESS AMERICA", 2);
               
               /*IF THE PLAYER WON*/
               if (input == 0){
                  JOptionPane.showMessageDialog(null, "YOU ARE THE MAN!!!\n" + "Select a difficulty level in the following menu\n" + "HAVE FUN!!!");
                  
                  optionsMenue = startMenue(0);
                   
                  switch(optionsMenue.getSelectedIndex()){
                  
                     case 0:
                        {
                        
                           TARGET_LIVES = 1;
                           TARGET_SPEED = 1;
                           break;
                        }
                     case 1:
                        {
                        
                           TARGET_LIVES = 2;
                           TARGET_SPEED = 2;
                           break;
                        }
                     case 2:
                        {
                        
                           TARGET_LIVES = 2;
                           TARGET_SPEED = 3;
                           break;
                        }
                  }
                  
                  frame.dispose();//Closing the window
                  obj = new Space_invadors3(null,null,null); // Starting a new session 
                  playGame(obj);
               } 
               else{
                  JOptionPane.showMessageDialog(null, "See you next time!!");
                  mainClip.stop();
                  frame.dispose();//Closing the window
                  break;
               }
            }
            /*IF THE PLAYER LOSE*/
            else{
            
               int input = JOptionPane.showConfirmDialog(null, "HEY GRANDPA! WHERE'S YOUR GLASSES?! Wanna try again?!", "Don't be a loser", 2);
               
               if (input == 0){
                  JOptionPane.showMessageDialog(null, "DO YOUR BEST THIS TIME!\n" + "Select a difficulty level in the following menu");
                  
                  optionsMenue = startMenue(0);
                  
                  switch(optionsMenue.getSelectedIndex()){
                  
                     case 0:
                        {
                        
                           TARGET_LIVES = 1;
                           TARGET_SPEED = 1;
                           break;
                        }
                     case 1:
                        {
                        
                           TARGET_LIVES = 2;
                           TARGET_SPEED = 2;
                           break;
                        }
                     case 2:
                        {
                        
                           TARGET_LIVES = 3;
                           TARGET_SPEED = 3;
                           break;
                        }
                  }
                  
                  frame.dispose();//Closing the window
                  obj = new Space_invadors3(null,null,null); // Starting a new session 
                  playGame(obj);
               } 
               else{
                  JOptionPane.showMessageDialog(null, "Tutoring sessions are in room N3029, good luck!");
                  mainClip.stop();
                  frame.dispose();//Closing the window
                  break;
               }
            }
         }
      }
   }
}
























