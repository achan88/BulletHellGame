import java.util.ArrayList;
import java.util.Iterator;
 
import javax.swing.text.DefaultStyledDocument.ElementSpec;
 
import processing.core.PApplet;
import processing.core.PImage;
 
public class Sketch1 extends PApplet {
  PImage background1;
  PImage background2;
  PImage background3;
  PImage playerSprite;
  PImage bossSprite;
  PImage menu;
 
  int bossX = 500;
  int bossY = 500;
  int bossSpd = 5;
  boolean bossAlive = true;
  int bossHealth = 500;
  int phase = 1;
  boolean startGame = false;
 
  int playerX;
  int playerY;
  int playerSpd = 10;
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
  PImage [] menuScreen = new PImage[4];
 
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
 
    bossSprite = loadImage("silver_default.png");

    menuScreen[0] = loadImage("start_menu.png");
    menuScreen[1] = loadImage("start_highlight.png");
    menuScreen[2] = loadImage("help_highlight.png");
    menuScreen[3] = loadImage("credits_highlight.png");
 
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
    
  }
 
  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
 
    if (startGame == false) {

      if (mouseX >= 305 && mouseX <= 493 && mouseY >= 207 && mouseY <= 276) {
        image(menuScreen[1], 0, 0);
        if(mousePressed) {
          startGame = true;
        }
      }

      else if(mouseX >= 205 && mouseX <= 593 && mouseY >= 322 & mouseY <= 389) {
        image(menuScreen[2], 0, 0);
      }


      else if(mouseX >= 286 && mouseX <= 514 && mouseY >= 434 && mouseY <= 502) {
        image(menuScreen[3], 0, 0);
      }

      else {
        image(menuScreen[0], 0 ,0);
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
     
        translate(-playerX+400, -playerY+400);
     
        if(phase == 1){
          image(background1, 0, 0);
        }
        if(phase == 2){
          image(background2, 0, 0);
        }
         if(phase == 3){
          image(background3, 0, 0);
        }
        image(playerSprite, playerX-12, playerY-14);
     
     
     
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
          boolean collide = circleRect(i.X, i.Y, 4f, bossX-42, bossY-50, 80,  80);
          if (collide) {
            bossHealth -= 5;
            playerItr.remove();
          }
          else if (i.X > 1600 || i.X < 400 || i.Y > 1600 || i.Y < 400){
            playerItr.remove();
          }
        }
     
        // rectangle bullets
        // test
        if (frameCount%30==0) {
          rectBullet b = new rectBullet(bossX, bossY, 0, 10, 80, 5, 60, 1, 0, 20);
          rectBullets.add(b);
         }
        // rectangle bullet code (keep)
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
     
        // normal bullets
        // test
        if (frameCount%5 == 0) {
          if(frameCount%30 == 0){
            for(int i=0; i<18; i++){
              normalBullet b = new normalBullet(bossX, bossY, i * 20, 8, 10, 30, false, true);
              normalBullets.add(b);
            }
          }
          normalBullet b = new normalBullet(bossX, bossY, getAngle(bossX, bossY, playerX, playerY), 15, 10, 300,  true, false);
          normalBullets.add(b);
        }
        // normal bullet code (keep)
        Iterator <normalBullet> normalItr = normalBullets.iterator();
        while(normalItr.hasNext()) {
          normalBullet i = normalItr.next();
          i.update();
          if (circleRect((int) i.X, (int) i.Y, (i.size)/2, playerX-12, playerY-14, 23,  28)) {
            playerHealth -= 5;
            normalItr.remove();
            combatTimer = 90;
          }
          else if (i.X > 1600 || i.X < 400 || i.Y > 1600 || i.Y < 400 || (i.time == i.duration && !i.isBounce)){
            normalItr.remove();
          }
        }
     
        //bomb test
        if(frameCount % 60 == 0){
          bomb b = new bomb(bossX, bossY, playerX, playerY, 90);
          bombs.add(b);
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
     
        //beam test
        if(frameCount %10 == 0){
          beam b = new beam((int) random(400, 1600), (int) random(400, 1600), 50);
          beams.add(b);
        }
        if(frameCount %1 == 0){
          beam b = new beam(800, 800, 50);
          beams.add(b);
        }
        //beam code 
        Iterator <beam> beamItr = beams.iterator();
        while(beamItr.hasNext()) {
          beam i = beamItr.next();
          i.update();
          if(i.time > 60){
            if(circleRect(i.X, i.Y, (i.size)/2, playerX-12, playerY-14, 23, 28)&& i.hasHit == false){
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
     
     
     
     
     
     
     
     
     
         /**
          * 
          * DRAW MAJOR SPRITES AND GRAPHICS 
          *
          */
        if (bossAlive) {
          image(bossSprite, bossX-42, bossY-50);
          bossX += bossSpd;
          if (bossX <= 500 || bossX >= 1416) {
            bossSpd = -bossSpd;
          }
        }
     
        strokeWeight(1);
        translate(playerX-400, playerY-400);
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
  public double getAngle(int x1, int y1, int x2, int y2){
    double angle = atan2(y2 - y1, x2 - x1) * 180 / PI;
    return angle;
  }
 
  // calculates collision between circle and rectangle
  public boolean circleRect(float circleX, float circleY, float radius, float rectangleX, float rectangleY, float rectangleWidth, float rectangleHeight) {
    float tempX = circleX;
    float tempY = circleY;
 
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
 
    float distX = circleX - tempX;
    float distY = circleY - tempY;
    float distance = sqrt((distX*distX) + (distY*distY));
 
    if (distance <= radius) {
      return true;
    }
    return false;
  }
 
  public boolean rectRect(float rect1X, float rect1Y, float rect1Width, float rect1Height, float rect2X, float rect2Y, float rect2Width, float rect2Height){
    if (rect1X < rect2X + rect2Width && rect1X+rect1Width > rect2X && rect1Y < rect2Y + rect2Height && rect1Y + rect1Height > rect2Y){
      return true;
    } 
    return false;
  }
 
  class playerBullet {
  int X;
  int Y;
  double velX;
  double velY;
  double dx;
  double dy;
  double length;
 
  playerBullet(int x, int y, int destx, int desty){
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
    X += velX;
    Y += velY;
    fill(255, 160, 255);
    strokeWeight(1);
    stroke(75, 0, 130);
    ellipse(X, Y, 8, 8);
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
 
    normalBullet(int x, int y, double angle, int size, int speed, int duration, boolean isGold, boolean isBounce) {
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
    int X;
    int Y;
    double velX;
    double velY;
    double duration;
    double time;
    double width;
    double height;
    double shrinkX;
    double shrinkY;
    int damage;
 
    rectBullet(int x, int y, double velX, double velY, double width, double height, int duration, double shrinkX, double shrinkY, int damage){
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
      rect (X, Y, (float) width, (float) height);
    }
  }
 
 
  class bomb {
    double X;
    double Y;
    double velX;
    double velY;
    double dx;
    double dy;
    int destx;
    int desty;
    double length;
    int fuse;
 
    bomb(int x, int y, int destx, int desty, int fuse) {
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
    int X;
    int Y;
    int size;
    int time;
    boolean hasHit = false;
    beam(int x, int y, int size){
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
        ellipse(X, Y, size, size);    
        strokeWeight(0);
        fill(255, 200, 200);
        ellipse(X, Y, (float) (size*time/60), (float) (size*time/60));
      }
      if(time >= 60){
      strokeWeight(1);
      stroke(255, 0, 0);
      fill(255, 0, 0);
      ellipse(X, Y, size, size);
      }
    }
  }
}