import processing.core.PApplet;

public class Sketch extends PApplet {
	
  // Map is made using a tile system with a 2d array, if the 2d array is true, then it will trigger states to stop player from moving there
  public int xTile = 20;
  public int yTile = 20; 
  boolean[][] mapCoord = new boolean[xTile][yTile];
  public float xTileSize = width / 40;
  public float yTileSize = height / 40;

  public int playerPosX;
  public int playerPosY;
  public int indexPosX = 10;
  public int indexPosY = 10;
  int[][] health = new int[xTile][yTile];

  public boolean hasUpgrade = false;
  public boolean openMenu;
  public boolean hasClicked;
  public boolean openShop;
  public int currency = 0;

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
          health[row][column] = 20;
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
  
    // If menu is not triggered, it will draw the main screen, and not draw menu screen
    if (openMenu == false && openShop == false) {
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

          if (mapCoord[row][column] && column != 10 && row != 10 && health[row][column] > 1) {
            // triangle(xTileLocation, yTileLocation, xTileLocation + 10, yTileLocation + 10, xTileLocation + 20, yTileLocation);
            // triangle(xTileLocation, yTileLocation + 5, xTileLocation + 10, yTileLocation + 15, xTileLocation + 20, yTileLocation + 5);
            // triangle(xTileLocation, yTileLocation + 10, xTileLocation + 10, yTileLocation + 20, xTileLocation + 20, yTileLocation + 10);
            
            // Draws obstacle tiles
            stroke(0);
            strokeWeight(2);
            stroke(50, 110, 15);
            fill(30, 120, 50);
            rect(40 * row, 40 * column, 40, 40);
          }
          // When an adjacent obstacle tile (bush) has no more health, collision is removed and turns to default colour
          else if (health[row][column] <= 0) {
            mapCoord[row][column] = false;
            stroke(50, 110, 15);
            strokeWeight(2);
            fill(80, 150, 55);
            rect(40 * row, 40 * column, 40, 40);
          }
          
        }
      }

      // Draws Player onto the grid
      playerPosX = indexPosX * 40;
      playerPosY = indexPosY * 40;

      fill(255);

      if (hasUpgrade) {
        fill(100, 20, 140);
      }
      rect(playerPosX, playerPosY, 40, 40);

      // Displays the currency
      fill(240, 160, 15);
      textSize(30);
      String currencyDisplay = "$" + currency;
      text(currencyDisplay, 680, 30);
    }

    // If menu is triggered, it will display menu screen, and hide the main screen and shop screen
    else if (openMenu) {
      background(30, .8f);
      fill(255);
      textSize(100);
      text("PAUSED", 220, 100);

      // Displays Shop Button
      stroke(200, 105, 50);
      textSize(75);
      fill(200, 105, 50);
      rect(220, 300, 400, 250);
      fill(255);
      text("SHOP", 300, 450);

    }
    // When shop menu is true, hide main screen and menu screen
    else if (openShop) {
      noStroke();
      background(80, 150, 55);
      fill(75, 110, 135);
      rect(200, 200, 400, 400);
      fill(100, 20, 140);
      rect(300, 300, 200, 100);
      fill(255);
      textSize(20);
      text("UPGRADE: COST $300", 300, 350);
      fill(240, 160, 15);
      textSize(30);
      String currencyDisplay = "$" + currency;
      text(currencyDisplay, 680, 30);
    }

    
  }
  
  /**
   * When WASD/ARROW keys are pressed, the player will move to an adjacent tile, it willn't move to a tile with an obstacle (collision).
   */
  public void keyPressed() {
    if (keyCode == UP || key == 'w') {
      if (indexPosY > 0 && !mapCoord[indexPosX][indexPosY-1]) {
        indexPosY -= 1;
      }
    }
    else if (keyCode == LEFT || key == 'a') {
      if (indexPosX > 0 && !mapCoord[indexPosX-1][indexPosY]) {
        indexPosX -= 1;
      }
    }
    else if (keyCode == DOWN || key == 's') {
      if (indexPosY < 20 && !mapCoord[indexPosX][indexPosY+1]) {
        indexPosY += 1;
      }
    }
    else if (keyCode == RIGHT || key == 'd') {
      if (indexPosX < 20 && !mapCoord[indexPosX+1][indexPosY]) {
        indexPosX += 1;
      }
    }

    if (keyCode == BACKSPACE) {
      openMenu = false;
      openShop = false;
    }
    else if (keyCode == ENTER) {
      openMenu = true;
    }
  }

  /**
   * Main Screen: If mouse is clicked while player is adjacent to an obstacle tile, each click will deplete the health of the object by 1, and add $5.
   * Menu Screen: When the mouse is clicked in the button, it will make openShop = true, which triggers the shop screen to appear.
   */
  public void mouseClicked() {
    if (mapCoord[indexPosX-1][indexPosY] == true) {
      health[indexPosX-1][indexPosY] -= 1;
      hasClicked = true;
      currency += 5;
    }
    else if (mapCoord[indexPosX+1][indexPosY] == true) {
      health[indexPosX+1][indexPosY] -= 1;
      hasClicked = true;
      currency += 5;
    }
    else if (mapCoord[indexPosX][indexPosY+1] == true) {
      health[indexPosX][indexPosY+1] -= 1;
      hasClicked = true;
      currency += 5;
    }
    else if (mapCoord[indexPosX][indexPosY-1] == true) {
      health[indexPosX][indexPosY-1] -= 1;
      hasClicked = true;
      currency += 5;
    }

    if (openMenu && mouseX >= 220 && mouseX <= 620 && mouseY >= 300 && mouseY <= 550) {
      openShop = true;
      openMenu = false;
    }
    else if (openShop && mouseX >= 300 && mouseX <= 500 && mouseY >= 300 && mouseY <= 400) {
      if (currency >= 300) {
        currency -= 300;
        hasUpgrade = true;
      }
    }
    
    
  }

}



  

