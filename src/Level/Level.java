package Level;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import Core.Game;
import Core.GameResourceLoader;
import Entities.Player;

public class Level {
	Game game;
	Tile tileArray[][];
	ArrayList<Tile> tileList = new ArrayList<Tile>();

	public Level(Game game) {
		this.game = game;
		tileArray = new Tile[game.worldWidth][game.worldHeight];
		generateLevel();
	} // End constructor

	public void generateLevel() {
		// Tile generator
		for (int y = 0; y < game.worldHeight; y++) {
			for (int x = 0; x < game.worldWidth; x++) {
				switch (new Random().nextInt(3)) { // Random int from 0 - 2, use as tileID
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

				tileList.add(tileArray[x][y]);
			}
		} // End tile Generator
	} // End generateLevel

	public void updateLevel(Game game) {
		this.game = game;

		for (int y = 0; y < game.worldHeight; y++) {
			for (int x = 0; x < game.worldWidth; x++) {
				Tile tile = tileArray[x][y];

				tile.tick(game);

				if (game.input.rightButton && tile.containsMouse)
					rightClick(tile, x, y);

				if (game.input.leftButton && tile.containsMouse)
					leftClick(tile);
			}
		} // End loops
	} // End update

	private void rightClick(Tile tile, int x, int y) { // if Right click
		switch (Player.toolSelected) {
		case 1: // Axe
			break;
		case 2: // Pickaxe
			if (tile.tileID == GameResourceLoader.Stone && !tile.hasRock) {
				tile.tileID = GameResourceLoader.Dirt;
				tileArray[x][y] = new DirtTile(x * 32, y * 32, game);
			}
			break;
		case 3: // Hoe
			if (!tile.hasTree && tile.tileID == GameResourceLoader.Grass || tile.tileID == GameResourceLoader.Dirt) {
				tile.tileID = GameResourceLoader.Plowed;
				tileArray[x][y] = new PlowedTile(x * 32, y * 32, game);
			}
			break;
		case 4: // Shovel
			if (!tile.hasTree && tile.tileID == GameResourceLoader.Grass) {
				tile.tileID = GameResourceLoader.Dirt;
				tileArray[x][y] = new DirtTile(x * 32, y * 32, game);
			}
			break;
		case 5: // Hand
			if (game.inv.itemSelected == game.inv.stoneID && game.inv.resourceAmounts[game.inv.stoneID] > 0) {
				if (tile.tileID != GameResourceLoader.Stone && !tile.hasTree) { // If the tile is not stone and does not have a tree
					game.inv.addResource(game.inv.stoneID, -1);
					tile.tileID = GameResourceLoader.Stone;
					tileArray[x][y] = new StoneTile(x * 32, y * 32, game);
					tileArray[x][y].hasRock = false;
					tileArray[x][y].hasOre = false;
				}
			}
		}
		game.input.rightButton = false;
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
			if (tile.hasRock && Player.toolSelected != Player.Pickaxe && !tile.hasOre) {
				game.inv.addResource(game.inv.stoneID, 1);
			} else if (Player.toolSelected == Player.Pickaxe && !tile.hasOre) {
				game.inv.addResource(game.inv.stoneID, 1);
				tile.hasRock = false;
			} else if (tile.hasOre && Player.toolSelected == Player.Pickaxe) {
				game.inv.addResource(game.inv.oreID, 1);
				tile.oreAmount--;
			}
			break;
		case 3: // Grass
			if (tile.hasTree && Player.toolSelected != Player.Axe) {
				game.inv.addResource(game.inv.stickID, 1);
			} else if (tile.hasTree && Player.toolSelected == Player.Axe) {
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
				Tile tile = tileArray[x][y];
				if (tile.isVisible)
					tile.render(g);
			}
		} // End loops
	} // End render
}