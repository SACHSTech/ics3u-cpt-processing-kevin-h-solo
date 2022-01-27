import processing.core.PApplet;

public class Sketch extends PApplet {
	
  // Map is made using a tile system with a 2d array, if the 2d array is true, then it will trigger states to stop player from moving there
  public int xTile = 20;
  public int yTile = 20; 
  boolean[][] mapCoord = new boolean[xTile][yTile];
  public float xTileSize = width / 40;
  public float yTileSize = height / 40;
  // Each individual tile equals 40x40 pixels
	
 
  public void settings() {
	// put your size call here
    size(800, 800);
  }


  public void setup() {
    background(80, 150, 55);

    for (int row = 0; row < xTile; row++) {
      strokeWeight(2);
      stroke(50, 110, 15);
      int rowMultiplier = row + 1;
      line(rowMultiplier * (2 * xTile), 0, rowMultiplier * (2 * xTile), 800);

      for (int column = 0; column < yTile; column++) {
        int columnMultiplier = column + 1;
        line(0, columnMultiplier * (2 * yTile), 800, columnMultiplier * (2 * yTile));
        if (random(1) < .25) {
          mapCoord[xTile-1][yTile-1] = true;
        }
      }
    }

  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {

	  
    for (int row = 0; row < xTile; row++) {
      for (int column = 0; column < yTile; column++) {
        
        float xTileLocation = xTileSize * column;
        float yTileLocation = yTileSize * row;

        if (mapCoord[row][column]) {
          fill(95, 60, 25);
          rect(xTileLocation, yTileLocation, 5, 10);
          fill(60, 100, 20);
          noStroke();
          triangle(xTileLocation, yTileLocation, xTileLocation + 10, yTileLocation + 10, xTileLocation + 20, yTileLocation);
          triangle(xTileLocation, yTileLocation + 5, xTileLocation + 10, yTileLocation + 15, xTileLocation + 20, yTileLocation + 5);
          triangle(xTileLocation, yTileLocation + 10, xTileLocation + 10, yTileLocation + 20, xTileLocation + 20, yTileLocation + 10);

          stroke(0);
          rect(xTileLocation, yTileLocation, xTileSize, yTileSize);
        }

      }
    }

    // Button for refreshing the map
    
  }
  
  // define other methods down here.
// Left edge at rectX, top edge at rectY, right edge at rectX + rectWidth, bottom edge at rectY + rectHeight
  // public int collisionDetect(int ) {
    // if(collision > rectX && collision < rectX + rectWidth && collision > rectY && collision < rectY + rectHeight){

  // }
  /*
  public void mouseClicked() {
    if (mouseClicked )
  }
  */
}