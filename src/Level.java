import java.awt.Graphics;
import java.util.Random;

public class Level {
	Game game;
	Tile tileArray[][];

	public Level(Game game) {
		this.game = game;
		tileArray = new Tile[game.worldWidth][game.worldHeight];
		generateLevel();
	} // End constructor

	public void generateLevel() {
		// Tile generator
		for (int y = 0; y < game.worldHeight; y++) {
			for (int x = 0; x < game.worldWidth; x++) {
				int randomTile = new Random().nextInt(100); // Random number between 0-100

				if (randomTile >= 50) { // If that number is 50+
					tileArray[x][y] = new Tile(x * 32, y * 32, 3); // Place grass
				} else { // If not
					tileArray[x][y] = new Tile(x * 32, y * 32, 0); // Place dirt
				}
			}
		} // End tile Generator
	} // End generateLevel

	public void updateLevel(Game game) {
		this.game = game;

		for (int y = 0; y < game.worldHeight; y++) {
			for (int x = 0; x < game.worldWidth; x++) {
				tileArray[x][y].tick(game);

				// If tile contains mouse
				if (tileArray[x][y].bounding.contains(game.mouseP)) {
					tileArray[x][y].showBorders = true;
				} else {
					tileArray[x][y].showBorders = false;
				}

				// Place tile
				if (tileArray[x][y].bounding.contains(game.mouseP) && game.input.rightButton) {
					tileArray[x][y].tileID = game.tileSelection;
				}

				// Plant tile
				if (tileArray[x][y].bounding.contains(game.mouseP) && game.input.leftButton && tileArray[x][y].tileID == 4) {
					tileArray[x][y].hasPlants = true;
				}
			}
		} // End loops
	} // End update

	public void renderLevel(Graphics g) {
		// Tile loops
		for (int y = 0; y < game.worldHeight; y++) {
			for (int x = 0; x < game.worldWidth; x++) {
				tileArray[x][y].render(g);
			}
		} // End loops
	} // End render
}