import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PImage;

public class Sketch1 extends PApplet {
  PImage background1;
  PImage background2;
  PImage background3;
  PImage playerSprite;
  PImage bossSprite;

  int bossX = 500;
  int bossY = 500;
  int bossSpd = 5;
  boolean bossAlive = true;
  int bossHealth = 500;

  int playerectangleX;
  int playerectangleY;
  int playerSpd = 5;
	boolean upPressed;
  boolean downPressed;
  boolean leftPressed;
  boolean rightPressed;
  int phase = 1;
  

  ArrayList <playerBullet> bullets = new ArrayList <playerBullet>();

  PImage [] player = new PImage[8];

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

    player[0] = loadImage("Gardevoir_Up.png");
    player[1] = loadImage("Gardevoir_Down.png");
    player[2] = loadImage("Gardevoir_Right.png");
    player[3] = loadImage("Gardevoir_Left.png");
    player[4] = loadImage("Gardevoir_Up_Left.png");
    player[5] = loadImage("Gardevoir_Up_Right.png");
    player[6] = loadImage("Gardevoir_Down_Left.png");
    player[7] = loadImage("Gardevoir_Down_Right.png");
    playerectangleX = 400;
    playerectangleY = 400;
    playerSprite = player[1];
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {

    if (bossAlive) {

      if(upPressed){
        playerectangleY -= playerSpd;
        playerSprite = player[0];
      }
      if(downPressed){
        playerectangleY += playerSpd;
        playerSprite = player[1];
  
      }
      if(leftPressed){
        playerectangleX -= playerSpd;
        playerSprite = player[3];
      }
      if(rightPressed){
        playerectangleX += playerSpd;
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
  
      translate(-playerectangleX+400, -playerectangleY+400);
  
      if(phase == 1){
      image(background1, 0, 0);
      }
      if(phase == 2){
      image(background2, 0, 0);
      }
      if(phase == 3){
      image(background3, 0, 0);
      }
      image(playerSprite, playerectangleX, playerectangleY);
  
      image(bossSprite, bossX, bossY);
      bossX += bossSpd;
      if (bossX <= 500 || bossX >= 1416) {
        bossSpd = -bossSpd;
      }
  
      if (frameCount%5==0 && mousePressed) {
       playerBullet b = new playerBullet(playerectangleX+12, playerectangleY+14, mouseX+playerectangleX-400, mouseY+playerectangleY-400);
       bullets.add(b);
      }
      Iterator <playerBullet> itr = bullets.iterator();
      while(itr.hasNext()){
        playerBullet i = itr.next();
        i.update();
        if (i.X > 1600 || i.X < 400 || i.Y > 1600 || i.Y < 400){
        itr.remove();
        }
        boolean collide = circleRect(i.X, i.Y, 2.5f, bossX, bossY, 84,  100);
        if (collide) {
          bossHealth -= 5;
          itr.remove();
        }
      }

      for (int i = 0; i < bossHealth; i++) {
        rect(1000, 450, bossHealth, 50);
      }
    }
    // System.out.println(millis());

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

  velX = dx * 10;
  velY = dy * 10;
  }

  void update(){
    X += velX;
    Y += velY;
    fill(255, 160, 255);
    strokeWeight(1);
    stroke(75, 0, 130);
    ellipse(X, Y, 5, 5);
  }

  

  }


}