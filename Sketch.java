import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
  PImage background1;
  PImage playerJoe;

  int playerX;
  int playerY;
  int playerSpd = 5;
	boolean upPressed;
  boolean downPressed;
  boolean leftPressed;
  boolean rightPressed;

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
    background1 = loadImage("background-game.png");
    playerJoe = loadImage("Gardevoir_Up.png");
    playerX = 400;
    playerY = 400;
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    if(upPressed){
      playerY -= playerSpd;
    }
    if(downPressed){
      playerY += playerSpd;
    }
    if(leftPressed){
      playerX -= playerSpd;
    }
    if(rightPressed){
      playerX += playerSpd;
    }
    translate(-playerX+400, -playerY+400);
	  image(background1, 0, 0);
    image(playerJoe, 100, 100);
    
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