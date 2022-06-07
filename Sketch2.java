import java.util.ArrayList;
import java.util.Iterator;
 
import javax.swing.text.DefaultStyledDocument.ElementSpec;
 
import processing.core.PApplet;
import processing.core.PImage;
 
public class Sketch2 extends PApplet {
  PImage background;
  PImage background1;
  PImage background2;
  PImage background3;
  PImage playerSprite;
  PImage bossSprite;
  PImage bossSword1;
  PImage bossDefault1;
  PImage bossSlash1;
  PImage bossSword2;
  PImage bossSlash2;
  PImage bossDefault2;
  PImage bossDefault3;
  PImage bossShield3;
  PImage bossSlash3;

  PImage menu;
 
  double bossX = 500;
  double bossY = 500;
  double bossXSpd;
  double bossYSpd;
  boolean bossAlive = true;
  int bossHealth = 1000;
  int phase = 1;
  int attack = 0;
  int attackTimer = 0;

  boolean startGame = false;
  boolean credits = false;
  boolean help = false;
 
  double playerX;
  double playerY;
  int playerSpd = 5;
  int playerHealth = 1000;
  int combatTimer = 0;
	boolean upPressed;
  boolean downPressed;
  boolean leftPressed;
  boolean rightPressed;
  int bulletRed = color(255, 50, 50);
 
 
 
  ArrayList <playerBullet> playerBullets = new ArrayList <playerBullet>();
  ArrayList <normalBullet> normalBullets = new ArrayList <normalBullet>();
  ArrayList <rectBullet> rectBullets = new ArrayList <rectBullet>();
  ArrayList <bomb> bombs = new ArrayList <bomb>();
  ArrayList <beam> beams = new ArrayList <beam>();
 
  PImage [] player = new PImage[8];
  PImage [] menuScreen = new PImage[6];
 
  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {
	// put your size call here
    size(800, 800);
  }
 
  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  public void setup() {
    frameRate(30);
    background1 = loadImage("dark_background.png");
    background2 = loadImage("default_background.png");
    background3 = loadImage("light_background.png");
    
    
    bossDefault1 = loadImage("black_default.png");
    bossDefault2 = loadImage("silver_default.png");
    bossDefault3 = loadImage("white_default.png");
    bossSword1 = loadImage("black_sword.png");
    bossSword2 = loadImage("silver_sword.png");
    bossShield3 = loadImage("white_shield.png");
    bossSlash1 = loadImage("black_slash.png");
    bossSlash2 = loadImage("silver_slash.png");
    bossSlash3 = loadImage("white_slash.png");

    menuScreen[0] = loadImage("start_menu.png");
    menuScreen[1] = loadImage("start_highlight.png");
    menuScreen[2] = loadImage("help_highlight.png");
    menuScreen[3] = loadImage("credits_highlight.png");
    menuScreen[4] = loadImage("credits.png");
    menuScreen[5] = loadImage("help.png");
 
    player[0] = loadImage("Gardevoir_Up.png");
    player[1] = loadImage("Gardevoir_Down.png");
    player[2] = loadImage("Gardevoir_Right.png");
    player[3] = loadImage("Gardevoir_Left.png");
    player[4] = loadImage("Gardevoir_Up_Left.png");
    player[5] = loadImage("Gardevoir_Up_Right.png");
    player[6] = loadImage("Gardevoir_Down_Left.png");
    player[7] = loadImage("Gardevoir_Down_Right.png");
    playerX = 800;
    playerY = 800;
    playerSprite = player[1];
    bossSprite = bossDefault1;
  }
 
  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
 
    if (startGame == false) {

      if (credits) {
        image(menuScreen[4], 0 ,0 );
        if (keyPressed) {
          if (keyCode == LEFT) {
            credits ^= true;

          }
        }
      }
      if (help) {
        image(menuScreen[5], 0, 0);
        if (keyPressed) {
          if (keyCode == LEFT) {
            help ^= true;
          }
        }

      }
      if (credits == false && help == false) {
        if (mouseX >= 305 && mouseX <= 493 && mouseY >= 207 && mouseY <= 276) {
          image(menuScreen[1], 0, 0);
          if(mousePressed) {
            startGame = true;
          }
        }
  
        else if(mouseX >= 205 && mouseX <= 593 && mouseY >= 322 & mouseY <= 389) {
          image(menuScreen[2], 0, 0);
          if (mousePressed) {
            help ^= true;
          }
        }
  
  
        else if(mouseX >= 286 && mouseX <= 514 && mouseY >= 434 && mouseY <= 502) {
          image(menuScreen[3], 0, 0);
          if(mousePressed) {
            credits ^= true;
          }
        }
  
        else {
          image(menuScreen[0], 0 ,0);
        }
      }
      /* 
      first hit box
      rect(305, 207, 188, 69);

      second hit box
      rect(205, 322, 388,67);

      third hit box
      rect(286, 434, 228,68);
      */

    }
    if (startGame) {
      if(combatTimer>0){
        combatTimer--;
        }
        if(combatTimer == 0 && playerHealth < 1000 && frameCount % 2 == 0){
          playerHealth++;
        }
        if(upPressed && playerY > 400){
          playerY -= playerSpd;
          playerSprite = player[0];
        }
        if(downPressed && playerY < 1586){
          playerY += playerSpd;
          playerSprite = player[1];
        }
        if(leftPressed && playerX > 412){
          playerX -= playerSpd;
          playerSprite = player[3];
        }
        if(rightPressed && playerX < 1588){
          playerX += playerSpd;
          playerSprite = player[2];
        }
        if (upPressed && leftPressed) {
          playerSprite = player[4];
        }
        else if (upPressed && rightPressed) {
          playerSprite = player[5];
        }
        else if (downPressed && leftPressed) {
          playerSprite = player[6];
        }
        else if (downPressed && rightPressed) {
          playerSprite = player[7];
         }
     
        translate(-(float)playerX+400, -(float)playerY+400);
     
        if(phase == 1){
          image(background1, 0, 0);
        }
        if(phase == 2){
          image(background2, 0, 0);
        }
         if(phase == 3){
          image(background3, 0, 0);
        }
        image(playerSprite, (float)playerX-12, (float)playerY-14);
     
     
     
        /**
         * 
         * LOAD BULLETS
         * 
         */
     
        // player bullets
        if (frameCount%5==0 && mousePressed) {
          playerBullet b = new playerBullet(playerX, playerY, mouseX+playerX-400, mouseY+playerY-400);
          playerBullets.add(b);
        }
         Iterator <playerBullet> playerItr = playerBullets.iterator();
        while(playerItr.hasNext()) {
          playerBullet i = playerItr.next();
          i.update();
          boolean collide = circleRect(i.X, i.Y, 8f, bossX-42, bossY-50, 80,  80);
          if (collide) {
            bossHealth -= 5;
            playerItr.remove();
          }
          else if (i.X > 1600 || i.X < 400 || i.Y > 1600 || i.Y < 400 || i.time == 20){
            playerItr.remove();
          }
        }
     
        // rectangle bullets
        Iterator <rectBullet> rectItr = rectBullets.iterator();
        while(rectItr.hasNext()){
          rectBullet i = rectItr.next();
          i.update();
          if(rectRect(i.X, i.Y, (float) i.width, (float) i.height, playerX-12, playerY-14, 23, 28)){
            playerHealth -= i.damage ;
            rectItr.remove();
            combatTimer = 90;
          }
          else if (i.X > 1600 || i.X < 400 || i.Y > 1600 || i.Y < 400 || i.time == i.duration || i.width < 0 || i.height < 0){
          rectItr.remove();
          }
        }

        // normal bullet code (keep)
        Iterator <normalBullet> normalItr = normalBullets.iterator();
        while(normalItr.hasNext()) {
          normalBullet i = normalItr.next();
          i.update();
          if (circleRect((int) i.X, (int) i.Y, i.size, playerX-12, playerY-14, 23,  28)) {
            playerHealth -= 5;
            normalItr.remove();
            combatTimer = 90;
          }
          else if (i.X > 1600 || i.X < 400 || i.Y > 1600 || i.Y < 400 || (i.time == i.duration && !i.isBounce)){
            normalItr.remove();
          }
        }
     
        //bomb code
        Iterator <bomb> bombItr = bombs.iterator();
        while(bombItr.hasNext()) {
          bomb i = bombItr.next();
          i.update();
          if (i.fuse == 0){
            for(int j = 0; j < 8; j++){
              normalBullet b = new normalBullet((int) i.X, (int) i.Y, j * 45, 10, 20,   5, false, false);
              normalBullets.add(b);
            }
            bombItr.remove();
          }
          }
     
        //beam code 
        Iterator <beam> beamItr = beams.iterator();
        while(beamItr.hasNext()) {
          beam i = beamItr.next();
          i.update();
          if(i.time > 60){
            if(circleRect(i.X, i.Y, i.size, playerX-12, playerY-14, 23, 28)&& i.hasHit == false){
              playerHealth -= 30;
              combatTimer = 90;
              i.hasHit = true;
            }
          }
          if(i.time > 75){
            beamItr.remove();
          }
        }
     
        /**
         * 
         * BOSS ATTACKS
         * 
         */
        bossX += bossXSpd;
        bossY += bossYSpd;

         if(bossHealth == 600){
           phase = 2;
         }
         else if(bossHealth == 300){
           phase = 3;
         }
     
         if(attack == 0){
           if(phase == 1){
              attack = (int)random(1, 3);
              attackTimer = 3000;
           }
           if(phase == 2){

           }
         }
     
         if (attack == 1){
            attackTimer-=20;
            bossMove(bossX, bossY, playerX, playerY, 3);
              if(frameCount%10==0){
                normalBullet b = new normalBullet(bossX, bossY, getAngle(bossX, bossY, playerX, playerY), 15, 10, 300,  false, false);
                normalBullets.add(b);
            }
        }

        if(attack == 2){
          attackTimer-=10;
          if(!(bossX < 1040 && bossX > 960 && bossY < 1040 && bossY > 960)){
            bossMove(bossX, bossY, 1000, 1000, 40);
          }
          else{
            bossX = 1000;
            bossY = 1000;
          }
          if(attackTimer<2400){
            bossSprite = bossSlash1;
            if(frameCount % 30 >= 0 && frameCount % 30 < 5){
              bossSprite = bossSword1;
            }
            if(frameCount%20==0){
              for(int i=0; i<16; i++){
                normalBullet b = new normalBullet(bossX, bossY, i*22.5, 20, 5, 300, false, false);
                normalBullets.add(b);
            }
          }
            else if(frameCount%10==0){
              for(int i=0; i<16; i++){
                normalBullet b = new normalBullet(bossX, bossY, i*22.5 + 11.25, 20, 5, 300, false, false);
                normalBullets.add(b);
            }
        }
        }
        else{
          bossSprite = bossSword1;
        }
      }

        if(attackTimer == 0){
          bossXSpd = 0;
          bossYSpd = 0;
          attack = 0;
          bossSprite = bossDefault1;
        }
     
         /**
          * 
          * DRAW MAJOR SPRITES AND GRAPHICS 
          *
          */
        if (bossAlive) {
          image(bossSprite, (int)bossX-42, (int)bossY-50);
        }
     
        strokeWeight(1);
        translate((int)playerX-400, (int)playerY-400);
        if (bossHealth >= 1) {
          stroke(0);
          fill(50);
          rect(400, 50, 250, 10);
          fill(255, 0, 0);
          rect(400, 50, bossHealth/2, 10);
          fill(255);
          text(bossHealth, 600, 60);
          } 
     
          fill (50);
          rect(20, 700, 100, 5);
          fill(0, 255, 0);
          stroke(0);
          rect(20, 700, playerHealth/10, 10);
          fill(255);
          text(playerHealth, 80, 700);
     
        if (bossHealth <= 0) {
          bossAlive = false;
        }
    }
 
  }
 
  // Set booleans when wasd keys are pressed
  public void keyPressed() {
    if (key == 'w') {
      upPressed = true;
    }
    else if (key == 's') {
      downPressed = true;
    }
    else if (key == 'a') {
      leftPressed = true;
    }
    else if (key == 'd') {
      rightPressed = true;
    }
  }
 
  // Set booleans when wasd keys are released
  public void keyReleased() {
    if (key == 'w') {
      upPressed = false;
    }
    else if (key == 's') {
      downPressed = false;
    }
    else if (key == 'a') {
      leftPressed = false;
    }
    else if (key == 'd') {
      rightPressed = false;
    }
  }
 
  // calculates the angle between 2 points in degrees
  public double getAngle(double x1, double y1, double x2, double y2){
    double angle = atan2((int)y2 - (int)y1, (int)x2 - (int)x1) * 180 / PI;
    return angle;
  }
 
  // calculates collision between circle and rectangle
  public boolean circleRect(double circleX, double circleY, float size, double rectangleX, double rectangleY, float rectangleWidth, float rectangleHeight) {
    float radius = size/2;
    double tempX = circleX;
    double tempY = circleY;
 
    if (circleX < rectangleX) {
      tempX = rectangleX;
    }            
    else if (circleX > rectangleX + rectangleWidth) {
      tempX = rectangleX + rectangleWidth;
    }  
    if (circleY < rectangleY) {
      tempY = rectangleY; 
    } 
    else if (circleY > rectangleY + rectangleHeight) {
      tempY = rectangleY + rectangleHeight;
    }
 
    double distX = circleX - tempX;
    double distY = circleY - tempY;
    double distance = sqrt((float)((distX*distX) + (distY*distY)));
 
    if (distance <= radius) {
      return true;
    }
    return false;
  }
 
  public boolean rectRect(double rect1X, double rect1Y, float rect1Width, float rect1Height, double rect2X, double rect2Y, float rect2Width, float rect2Height){
    if (rect1X < rect2X + rect2Width && rect1X+rect1Width > rect2X && rect1Y < rect2Y + rect2Height && rect1Y + rect1Height > rect2Y){
      return true;
    } 
    return false;
  }
 
  public void bossMove(double x, double y, double destx, double desty, int speed){
    double dx = destx - x;
    double dy = desty - y;

    double length = Math.sqrt(dx*dx + dy*dy);
 
    dx /= length;
    dy /= length;
   
    bossXSpd = dx * speed;
    bossYSpd = dy * speed;
  }









  class playerBullet {
  double X;
  double Y;
  double velX;
  double velY;
  double dx;
  double dy;
  double length;
  int time = 0;
 
  playerBullet(double x, double y, double destx, double desty){
  this.X = x;
  this.Y = y;
  dx = destx - X;
  dy = desty - Y;
 
  length = Math.sqrt(dx*dx + dy*dy);
 
  dx /= length;
  dy /= length;
 
  velX = dx * 20;
  velY = dy * 20;
  }
 
  void update(){
    time++;
    X += velX;
    Y += velY;
    fill(255, 160, 255);
    strokeWeight(1);
    stroke(75, 0, 130);
    ellipse((int)X, (int)Y, 8, 8);
   }
  }
 
  class normalBullet {
    double X;
    double Y;
    double velX;
    double velY;
    boolean isGold;
    boolean isBounce;
    int duration;
    int size; 
    int time;
 
    normalBullet(double x, double y, double angle, int size, int speed, int duration, boolean isGold, boolean isBounce) {
      this.X = x;
      this.Y = y;
      this.isGold = isGold;
      this.isBounce = isBounce;
      this.size = size;
      this.duration = duration;
 
      velX = Math.cos(angle*Math.PI/180) * speed;
      velY = Math.sin(angle*Math.PI/180) * speed;
 
      this.time = 0;
    }
 
    void update() {
      X += velX;
      Y += velY;
      time++;
      if (isGold) {
        fill(255, 210, 100);
      }
      else if (isBounce){
        if(time == duration){
          velX = -velX;
          velY = -velY;
          time = 0;
          isBounce = false;
        }
        fill(255, 165, 0);
      }
      else{
        fill(bulletRed);
      }
      strokeWeight(1);
      stroke(0);
      ellipse((int)X, (int)Y, size, size);
    }
 
  }
  class rectBullet {
    double X;
    double Y;
    double velX;
    double velY;
    double duration;
    double time;
    double width;
    double height;
    double shrinkX;
    double shrinkY;
    int damage;
 
    rectBullet(double x, double y, double velX, double velY, double width, double height, int duration, double shrinkX, double shrinkY, int damage){
    this.X = x;
    this.Y = y;
    this.velX = velX;
    this.velY = velY;
    this.width = width;
    this.height = height;
    this.duration = duration;
    this.shrinkX = shrinkX;
    this.shrinkY = shrinkY;
    this.damage = damage;
    time = 0;
    }
 
    void update(){
      time++;
      X += velX;
      Y += velY;
      width -= shrinkX;
      height -= shrinkY;
      fill(200, 100, 100);
      strokeWeight(1);
      stroke(0);
      rect ((int)X, (int)Y, (float) width, (float) height);
    }
  }
 
 
  class bomb {
    double X;
    double Y;
    double velX;
    double velY;
    double dx;
    double dy;
    double destx;
    double desty;
    double length;
    int fuse;
 
    bomb(double x, double y, double destx, double desty, int fuse) {
      this.X = x;
      this.Y = y;
      dx = destx - X;
      dy = desty - Y;
      this.destx = destx;
      this.desty = desty;
 
      this.fuse = fuse;
 
      length = Math.sqrt(dx*dx + dy*dy);
 
      dx /= length;
      dy /= length;
 
      velX = dx * 20;
      velY = dy * 20;
    }
 
    void update() {
      if(X < destx + 10 && X > destx - 10 && Y < desty + 10 && Y > desty - 10){
        velX = 0;
        velY = 0;
        X = destx;
        Y = desty;
      }
      X += velX;
      Y += velY;
      fuse--;
      fill(20);
      strokeWeight(3);
      if(fuse >= 60){
      stroke(255, 255, 0);
      }
      else if(fuse >= 30){
        stroke(255, 120, 10);
      }
      else{
        stroke(200, 0, 0);
      }
      ellipse((int)X, (int)Y, 20, 20);
    }
  }
 
    class beam {
    double X;
    double Y;
    int size;
    int time;
    boolean hasHit = false;
    beam(double x, double y, int size){
      this.X = x;
      this.Y = y;
      this.size = size;
      time = 0;
    }
 
    void update() {
      time++;
      if(time < 60){
        noFill();
        strokeWeight(1);
        stroke(255, 0, 0);
        ellipse((int)X, (int)Y, size, size);    
        strokeWeight(0);
        fill(255, 200, 200);
        ellipse((int)X, (int)Y, (float) (size*time/60), (float) (size*time/60));
      }
      if(time >= 60){
      strokeWeight(1);
      stroke(255, 0, 0);
      fill(255, 0, 0);
      ellipse((int)X, (int)Y, size, size);
      }
    }
  }
}