package Level;

import java.awt.Graphics;

import Core.Game;

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

				tileArray[x][y] = new GrassTile(x * 32, y * 32, game); // Place grass
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

				if (game.input.rightButton && tileArray[x][y].bounding.contains(game.mouseP))
					rightClick(x, y);

				if (game.input.leftButton && tileArray[x][y].bounding.contains(game.mouseP))
					leftClick(x, y);
			}
		} // End loops
	} // End update

	private void rightClick(int x, int y) { // if Right click
		switch (game.tileSelection) {
		case 0: // Dirt
			tileArray[x][y] = new DirtTile(x * 32, y * 32, game);
			tileArray[x][y].tileID = 0;
			break;
		case 1: // Plowed
			tileArray[x][y] = new PlowedTile(x * 32, y * 32, game);
			tileArray[x][y].tileID = 1;
			break;
		case 2: // Stone
			tileArray[x][y] = new StoneTile(x * 32, y * 32, game);
			tileArray[x][y].tileID = 2;
			break;
		case 3: // Grass
			tileArray[x][y] = new GrassTile(x * 32, y * 32, game);
			tileArray[x][y].tileID = 3;
			break;
		}
	}

	private void leftClick(int x, int y) { // If Left click
		switch (tileArray[x][y].tileID) {
		case 0: // Dirt
			break;
		case 1: // Plowed
			if (!tileArray[x][y].hasPlants) {
				tileArray[x][y].hasPlants = true;
				game.inv.resourceAmounts[game.inv.seedID] -= 1;
				game.input.leftButton = false;
				System.out.println("Changed hasPlants");
			}else if (tileArray[x][y].hasPlants && tileArray[x][y].plantGrowth == tileArray[x][y].growthTime) {
				game.inv.addResource(game.inv.wheatID, 1);
				tileArray[x][y].hasPlants = false;
				tileArray[x][y].plantGrowth = 0;
				game.input.leftButton = false;
			}
			break;
		case 2: // Stone
			game.inv.addResource(game.inv.stoneID, 1);
			game.input.leftButton = false;
			break;
		case 3: // Grass
			System.out.println("Grass");
			break;
		} // End switch
	} // End left-click

	public void renderLevel(Graphics g) {
		// Tile loops
		for (int y = 0; y < game.worldHeight; y++) {
			for (int x = 0; x < game.worldWidth; x++) {
				if (tileArray[x][y].x >= game.player.x - (game.getWidth() / 2) - 32 && tileArray[x][y].x <= game.player.x + (game.getWidth() / 2) + 32 & tileArray[x][y].y >= game.player.y - (game.getHeight() / 2) - 32 && tileArray[x][y].y <= game.player.y + (game.getHeight() / 2) + 32) {
					tileArray[x][y].render(g);
				}
			}
		} // End loops
	} // End render
}