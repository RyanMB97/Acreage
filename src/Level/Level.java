package Level;

import java.awt.Graphics;
import java.util.Random;

import Core.Game;

public class Level {
	Game game;
	Tile tileArray[][];
	public boolean canAffectTile = false;;

	public Level(Game game) {
		this.game = game;
		tileArray = new Tile[game.worldWidth][game.worldHeight];
		generateLevel();
	} // End constructor

	public void generateLevel() {
		// Tile generator
		for (int y = 0; y < game.worldHeight; y++) {
			for (int x = 0; x < game.worldWidth; x++) {
				int tileRandomizer = new Random().nextInt(3);
				switch (tileRandomizer) {
				case 0:
					tileArray[x][y] = new DirtTile(x * 32, y * 32, game); // Place dirt
					break;
				case 1:
					tileArray[x][y] = new StoneTile(x * 32, y * 32, game); // Place stone
					break;
				case 2:
					tileArray[x][y] = new GrassTile(x * 32, y * 32, game); // Place grass
					break;
				default:
					tileArray[x][y] = new GrassTile(x * 32, y * 32, game); // Place grass
					break;
				}
			}
		} // End tile Generator
	} // End generateLevel

	public void updateLevel(Game game) {
		this.game = game;

		for (int y = 0; y < game.worldHeight; y++) {
			for (int x = 0; x < game.worldWidth; x++) {
				Tile tile = tileArray[x][y];

				tile.tick(game);
				// If tile contains mouse
				if (tile.bounding.contains(game.mouseP)) {
					tile.showBorders = true;
				} else {
					tile.showBorders = false;
				}

				if (game.input.rightButton && tile.bounding.contains(game.mouseP) && canAffectTile)
					rightClick(tile, x, y);

				if (game.input.leftButton && tile.bounding.contains(game.mouseP) && canAffectTile)
					leftClick(tile);
			}
		} // End loops
	} // End update

	private void rightClick(Tile tile, int x, int y) { // if Right click
		switch (game.player.toolSelected) {
		case 1: // Axe
			break;
		case 2: // Pickaxe
			break;
		case 3: // Hoe
			if (game.player.toolSelected == 3 && !tile.hasTree) {
				if (tile.tileID == 3 || tile.tileID == 0) {
					tileArray[x][y] = new PlowedTile(x * 32, y * 32, game);
					tile.tileID = 1;
				}
			}
			break;
		case 4: // Shovel
			if (game.player.toolSelected == 4 && !tile.hasTree) {
				if (tile.tileID == 3) {
					tileArray[x][y] = new DirtTile(x * 32, y * 32, game);
					tile.tileID = 0;
				}
			}
			break;
		}
	}

	private void leftClick(Tile tile) { // If Left click
		switch (tile.tileID) {
		case 0: // Dirt
			break;
		case 1: // Plowed
			if (!tile.hasPlants) {
				tile.hasPlants = true;
				game.inv.resourceAmounts[game.inv.seedID] -= 1;
			} else if (tile.hasPlants && tile.plantGrowth == tile.growthTime) {
				game.inv.addResource(game.inv.wheatID, 1);
				tile.hasPlants = false;
				tile.plantGrowth = 0;
			}
			break;
		case 2: // Stone
			if (tile.hasRock && game.player.toolSelected == 2) {
				game.inv.addResource(game.inv.stoneID, 1);
			}
			break;
		case 3: // Grass
			if (tile.hasTree && game.player.toolSelected != 1) {
				game.inv.addResource(game.inv.stickID, 1);
			} else if (tile.hasTree && game.player.toolSelected == 1) {
				game.inv.addResource(game.inv.lumberID, 1);
				tile.hasTree = false;
			}
		} // End switch
		game.input.leftButton = false;
	} // End left-click

	public void renderLevel(Graphics g) {
		// Tile loops
		for (int y = 0; y < game.worldHeight; y++) {
			for (int x = 0; x < game.worldWidth; x++) {
				if (tileArray[x][y].x >= game.player.x - (game.getWidth() / 2) - 32 && tileArray[x][y].x <= game.player.x + (game.getWidth() / 2) + 32 & tileArray[x][y].y >= game.player.y - (game.getHeight() / 2) - 32 && tileArray[x][y].y <= game.player.y + (game.getHeight() / 2) + 64) {
					tileArray[x][y].render(g);
				}
			}
		} // End loops
	} // End render
}