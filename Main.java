import processing.core.PApplet;

/**
 * Main class to execute sketch
 * @author 
 *
 */
class Main {
  public static void main(String[] args) {
    
    String[] processingArgs = {"MySketch"};
<<<<<<< HEAD
	 // Sketch mySketch = new Sketch();  //comment this out to run the other sketch files
	   Sketch1 mySketch = new Sketch1();  // uncomment this to run this sketch file
	  // Sketch2 mySketch = new Sketch2();  // uncomment this to run this sketch file
=======
	//   Sketch mySketch = new Sketch();  //comment this out to run the other sketch files
	//   Sketch1 mySketch = new Sketch1();  // uncomment this to run this sketch file
	  Sketch2 mySketch = new Sketch2();  // uncomment this to run this sketch file
>>>>>>> b7d3e8111ef7e7ce2e2fe55f9a5249616b4e7060
	  
	  PApplet.runSketch(processingArgs, mySketch);
  }
  
}
