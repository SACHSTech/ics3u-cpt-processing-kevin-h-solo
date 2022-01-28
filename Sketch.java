import processing.core.PApplet;

public class Sketch extends PApplet {
	
  // Map is made using a tile system with a 2d array, if the 2d array is true, then it will trigger states to stop player from moving there
  public int xTile = 20;
  public int yTile = 20; 
  boolean[][] mapCoord = new boolean[xTile][yTile];
  public float xTileSize = width / 40;
  public float yTileSize = height / 40;
  public int playerPosX = 400;
  public int playerPosY = 400;
  // Each individual tile equals 40x40 pixels
	
 
  public void settings() {
	// put your size call here
    size(800, 800);
  }


  public void setup() {
    background(80, 150, 55);

    // Nested for loop to 15% chance to randomly assign a tile with collision in mapCoord[][]
    for (int row = 0; row < xTile; row++) {
      for (int column = 0; column < yTile; column++) {
    
        if (random(1) < .15) {
          mapCoord[row][column] = true;
        }
        else {
          mapCoord[row][column] = false;
        }
      }
    }

  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    background(80, 150, 55);

    for (int row = 0; row < xTile; row++) {
      // Draws the grid lines going vertically
      strokeWeight(2);
      stroke(50, 110, 15);
      int rowMultiplier = row + 1;
      line(rowMultiplier * (2 * xTile), 0, rowMultiplier * (2 * xTile), 800);

      for (int column = 0; column < yTile; column++) {
        // Draws the grid lines going horizontal
        int columnMultiplier = column + 1;
        line(0, columnMultiplier * (2 * yTile), 800, columnMultiplier * (2 * yTile));
        
        // float xTileLocation = xTileSize * column;
        // float yTileLocation = yTileSize * row;

        if (mapCoord[row][column] && column != 10 && row != 10) {
          // triangle(xTileLocation, yTileLocation, xTileLocation + 10, yTileLocation + 10, xTileLocation + 20, yTileLocation);
          // triangle(xTileLocation, yTileLocation + 5, xTileLocation + 10, yTileLocation + 15, xTileLocation + 20, yTileLocation + 5);
          // triangle(xTileLocation, yTileLocation + 10, xTileLocation + 10, yTileLocation + 20, xTileLocation + 20, yTileLocation + 10);
          stroke(0);
          strokeWeight(2);
        
          // Draws red tiles
          stroke(50, 110, 15);
          fill(30, 120, 50);
          rect(40 * row, 40 * column, 40, 40);
          
          // Draws Trees WIP
          noStroke();
          fill(95, 60, 25);
          rect(40 * (row + 20), 40 * (column + 5), 5, 10);
          fill(60, 100, 20);
          }
          else {
            noFill();
          }
        
        
      }
    }

    // Draws Player WIP
    fill(255);
    rect(playerPosX, playerPosY, 40, 40);
  }
  
  /**
   * When WASD/ARROW keys are pressed, the player will move to an adjacent tile, it willn't move to a tile with an obstacle (collision).
   */
  public void keyPressed() {
    if (keyCode == UP || key == 'w') {
      if (playerPosY >= 0 && !mapCoord[playerPosX/20 - 1][playerPosY/20 - 2]) {
        playerPosY -= 40;
      }
    }
    else if (keyCode == LEFT || key == 'a') {
      if (playerPosX >= 0 && !mapCoord[playerPosX/20 - 2][playerPosY/20 - 1]) {
        playerPosX -= 40;
      }
    }
    else if (keyCode == DOWN || key == 's') {
      if (playerPosY <= 800 && !mapCoord[playerPosX/20 - 1][playerPosY/20 + 2]) {
        playerPosY += 40;
      }
    }
    else if (keyCode == RIGHT || key == 'd') {
      if (playerPosX <= 800 && !mapCoord[playerPosX/20 + 2][playerPosY/20 - 1]) {
        playerPosX += 40;
      }
    }

  }
}