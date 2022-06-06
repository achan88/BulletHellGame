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

  int playerX;
  int playerY;
  int playerSpd = 10;
	boolean upPressed;
  boolean downPressed;
  boolean leftPressed;
  boolean rightPressed;
  int phase = 1;
  

  ArrayList <playerBullet> playerbullets = new ArrayList <playerBullet>();
  ArrayList <normalBullet> bossBullet = new ArrayList <normalBullet>();
  ArrayList <rectBullet> rectBullets = new ArrayList <rectBullet>();

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
    playerX = 400;
    playerY = 400;
    playerSprite = player[1];
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    
    if(upPressed && playerY > 372){
      playerY -= playerSpd;
      playerSprite = player[0];
    }
    if(downPressed && playerY < 1572){
      playerY += playerSpd;
      playerSprite = player[1];
    }
    if(leftPressed && playerX > 400){
      playerX -= playerSpd;
      playerSprite = player[3];
    }
    if(rightPressed && playerX < 1577){
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
    image(playerSprite, playerX, playerY);
  
    // player bullets
    if (frameCount%5==0 && mousePressed) {
      playerBullet b = new playerBullet(playerX+12, playerY+14, mouseX+playerX-400, mouseY+playerY-400);
      playerbullets.add(b);
    }
     Iterator <playerBullet> itr = playerbullets.iterator();
    while(itr.hasNext()) {
      playerBullet i = itr.next();
      i.update();
      if (i.X > 1600 || i.X < 400 || i.Y > 1600 || i.Y < 400){
        itr.remove();
      }
      boolean collide = circleRect(i.X, i.Y, 4f, bossX, bossY, 80,  80);
      if (collide) {
        bossHealth -= 5;
        itr.remove();
      }
    }

    // rectangle
    if (frameCount%30==0) {
      rectBullet b = new rectBullet(bossX, bossY, 0, 10, 80, 5, 60, 1, 0);
      rectBullets.add(b);
     }
    Iterator <rectBullet> rectItr = rectBullets.iterator();
    while(rectItr.hasNext()){
      rectBullet i = rectItr.next();
      i.update();
      if (i.X > 1600 || i.X < 400 || i.Y > 1600 || i.Y < 400 || i.time == i.duration || i.width < 0 || i.height < 0){
      rectItr.remove();
      }
    }

    // boss bullets
    if (frameCount%1 == 0) {
      normalBullet b = new normalBullet(bossX+42, bossY+100, playerX+12, playerY+14, false, 8);
      bossBullet.add(b);
    }
    Iterator <normalBullet> bossItr = bossBullet.iterator();
    while(bossItr.hasNext()) {
      normalBullet i = bossItr.next();
      i.update();
      if (i.X > 1600 || i.X < 400 || i.Y > 1600 || i.Y < 400){
        bossItr.remove();
      }
    }

    if (bossAlive) {
      image(bossSprite, bossX, bossY);
      bossX += bossSpd;
      if (bossX <= 500 || bossX >= 1416) {
        bossSpd = -bossSpd;
      }
    }

    if (bossHealth >= 1) {
      translate(playerX-400, playerY-400);
      fill(50);
      rect(400, 50, 250, 10);
      fill(255, 0, 0);
      stroke(0);
      rect(400, 50, bossHealth/2, 10);
      fill(255);
      text(bossHealth, 600, 60);
      } 

    if (bossHealth <= 0) {
      bossAlive = false;
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
    int X;
    int Y;
    double velX;
    double velY;
    double dx;
    double dy;
    double length;
    boolean isGold;
    int size; 
  
    normalBullet(int x, int y, int destx, int desty, boolean isGold, int size) {
      this.X = x;
      this.Y = y;
      dx = destx - X;
      dy = desty - Y;

      this.isGold = isGold;
      this.size = size;

      length = Math.sqrt(dx*dx + dy*dy);
  
      dx /= length;
      dy /= length;
  
      velX = dx * 20;
      velY = dy * 20;
    }
  
    void update() {
      X += velX;
      Y += velY;

      if (isGold) {
        fill(243, 236, 214);
      }
    
      else {
        fill(178, 0, 0);
      }

      strokeWeight(1);
      stroke(0);
      ellipse(X, Y, size, size);
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

    rectBullet(int x, int y, double velX, double velY, double width, double height, int duration, double shrinkX, double shrinkY){
    this.X = x;
    this.Y = y;
    this.velX = velX;
    this.velY = velY;
    this.width = width;
    this.height = height;
    this.duration = duration;
    this.shrinkX = shrinkX;
    this.shrinkY = shrinkY;
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

}