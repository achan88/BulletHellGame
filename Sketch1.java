import processing.core.PApplet;
import processing.core.PImage;

public class Sketch1 extends PApplet {
  PImage background1;
  PImage playerJoe;

  int playerX;
  int playerY;
  int playerSpd = 5;
	boolean upPressed;
  boolean downPressed;
  boolean leftPressed;
  boolean rightPressed;

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
    background(210, 255, 173);
    background1 = loadImage("normal_background.png");
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
    playerJoe = player[1];
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    if(upPressed){
      playerY -= playerSpd;
      playerJoe = player[0];
    }
    if(downPressed){
      playerY += playerSpd;
      playerJoe = player[1];

    }
    if(leftPressed){
      playerX -= playerSpd;
      playerJoe = player[3];
    }
    if(rightPressed){
      playerX += playerSpd;
      playerJoe = player[2];
    }
    if (upPressed && leftPressed) {
      playerJoe = player[4];
    }
    if (upPressed && rightPressed) {
      playerJoe = player[5];
    }
    if (downPressed && leftPressed) {
      playerJoe = player[6];
    }
    if (downPressed && rightPressed) {
      playerJoe = player[7];
    }
    translate(-playerX+400, -playerY+400);
	  image(background1, 0, 0);
    
    image(playerJoe, playerX, playerY);
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
}