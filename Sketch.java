import processing.core.PApplet;

public class Sketch extends PApplet {
	
  // Declares variables used for 2d arrays
  public int xTile = 20;
  public int yTile = 20; 
  public float xTileSize = width / 40;
  public float yTileSize = height / 40;

  // Declare 2d arrays (obstacles and collisions)
  boolean[][] mapCoord = new boolean[xTile][yTile];
  int[][] healthBush = new int[xTile][yTile];
  int[][] healthRock = new int[xTile][yTile];
  public int dmgPerClick = 1;

  // Declare variables for player position in the grid
  public int playerPosX;
  public int playerPosY;
  public int indexPosX = 10;
  public int indexPosY = 10;

  // Declare boolean variables to trigger different events
  public boolean hasUpgrade = false;
  public boolean hasClicked;
  public boolean openMenu;
  public boolean openShop;

  public int currency = 0;
  
	
  public void settings() {
	// put your size call here
    size(800, 800);
  }


  public void setup() {
    background(80, 150, 55);

    // Iterates through a nested for loop and if 
    for (int row = 0; row < xTile; row++) {
      for (int column = 0; column < yTile; column++) {
        
        if (random(1) < .1) {
          mapCoord[row][column] = true;
          healthBush[row][column] = 20;
        }
        else if (random(1) < .05) {
          mapCoord[row][column] = true;
          healthRock[row][column] = 40;
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

      // Nested for loop that iterates through all the 2d arrays, and dyanmically updates. 
      // Will draw bush and rock obstacles and when they lose all health, they disappear.
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

          // If tile is a bush obstacle
          if (mapCoord[row][column] && column != 10 && row != 10 && healthBush[row][column] > 1) {
            stroke(0);
            strokeWeight(2);
            stroke(50, 110, 15);
            fill(30, 120, 50);
            rect(40 * row, 40 * column, 40, 40);
          }
          // If tile is a rock obstacle
          else if (mapCoord[row][column] && column != 10 && row != 10 && healthRock[row][column] > 1) {
            stroke(0);
            strokeWeight(2);
            stroke(50, 110, 15);
            fill(100);
            rect(40 * row, 40 * column, 40, 40);
          }
          // When an adjacent obstacle tile has no more health, collision is removed and turns to default colour
          else if (healthBush[row][column] <= 0 || healthRock[row][column] <= 0) {
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

      // If upgrade is bought, the player will look different
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
      // Draws Shop Button
      stroke(200, 105, 50);
      textSize(75);
      fill(200, 105, 50);
      rect(220, 300, 400, 250);
      fill(255);
      text("SHOP", 300, 450);
    }
    // When shop menu is true, hide main screen and menu screen and open shop menu
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
   * When WASD/ARROW keys are pressed, the player will move to an adjacent tile, it willn't move 
   * to a tile with an obstacle (collision) as it detects that if mapCoord[][] value is true.
   * ----- When backspace is pressed, it will go back to main screen, and when enter is pressed it will open the menu screen.
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
    // Open and closes different menus
    if (keyCode == BACKSPACE) {
      openMenu = false;
      openShop = false;
    }
    else if (keyCode == ENTER) {
      openMenu = true;
    }
  }


  /**
   * When mouse is clicked while player is adjacent to an obstacle tile, each click will deplete the health of the object by dmgPerClick, and reward $.
   * ----- When the menu screen is opened and the mouse is clicked in the button, it will make openShop = true, which triggers the shop screen to appear.
   */
  public void mouseClicked() {
    if (mapCoord[indexPosX-1][indexPosY] == true) {
      healthBush[indexPosX-1][indexPosY] -= dmgPerClick;
      healthRock[indexPosX-1][indexPosY] -= dmgPerClick;
      hasClicked = true;
      currency += 5 * dmgPerClick;
    }
    else if (mapCoord[indexPosX+1][indexPosY] == true) {
      healthBush[indexPosX+1][indexPosY] -= dmgPerClick;
      healthRock[indexPosX+1][indexPosY] -= dmgPerClick;
      hasClicked = true;
      currency += 5 * dmgPerClick;
    }
    else if (mapCoord[indexPosX][indexPosY+1] == true) {
      healthBush[indexPosX][indexPosY+1] -= dmgPerClick;
      healthRock[indexPosX][indexPosY+1] -= dmgPerClick;
      hasClicked = true;
      currency += 5 * dmgPerClick;
    }
    else if (mapCoord[indexPosX][indexPosY-1] == true) {
      healthBush[indexPosX][indexPosY-1] -= dmgPerClick;
      healthRock[indexPosX][indexPosY-1] -= dmgPerClick;
      hasClicked = true;
      currency += 5 * dmgPerClick;
    }

    // If mouse is clicked in these specific coordinate ranges (button) then it will change screens or buy the upgrade.
    if (openMenu && mouseX >= 220 && mouseX <= 620 && mouseY >= 300 && mouseY <= 550) {
      openShop = true;
      openMenu = false;
    }
    else if (openShop && mouseX >= 300 && mouseX <= 500 && mouseY >= 300 && mouseY <= 400) {
      if (currency >= 300) {
        currency -= 300;
        hasUpgrade = true;
        dmgPerClick++;
      }
    }   
  }


}



  

