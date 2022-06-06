import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import processing.core.PApplet;
import processing.core.PImage;

public class Sketch2 extends PApplet {
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
  int phase = 1;

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
  ArrayList <normalBullet> normalBullet = new ArrayList <normalBullet>();
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
    playerX = 800;
    playerY = 800;
    playerSprite = player[1];
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
  
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
    if (frameCount%30==0) {
      rectBullet b = new rectBullet(bossX, bossY, 0, 10, 80, 5, 60, 1, 0, 20);
      rectBullets.add(b);
     }
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
    // if (frameCount%5 == 0) {
    //   normalBullet b = new normalBullet(bossX, bossY, playerX, playerY, false, 8);
    //   normalBullet.add(b);
    // }

    Iterator <normalBullet> normalItr = normalBullet.iterator();
    while(normalItr.hasNext()) {
      normalBullet i = normalItr.next();
      i.update();
      if (circleRect(i.X, i.Y, i.size, playerX-12, playerY-14, 23,  28)) {
        playerHealth -= 5;
        normalItr.remove();
        combatTimer = 90;
      }
      else if (i.X > 1600 || i.X < 400 || i.Y > 1600 || i.Y < 400){
        normalItr.remove();
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

    translate(playerX-400, playerY-400);
    if (bossHealth >= 1) {
      fill(50);
      rect(400, 50, 250, 10);
      fill(255, 0, 0);
      stroke(0);
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
        fill(bulletRed);
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

}