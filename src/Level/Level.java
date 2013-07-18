package Level;

import java.awt.Graphics;
import java.util.Random;

import Core.Game;
import Core.GameResourceLoader;
import Entities.Player;

public class Level {
	Game game;
	Tile tileArray[][];

	public Level(Game game) {
		this.game = game;
		tileArray = new Tile[game.worldWidth][game.worldHeight];
		long beginTime = System.currentTimeMillis();
		generateLevel();
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - beginTime;
		System.out.println("It took " + resultTime + "ms to generate the level!");
	} // End constructor

	public void generateLevel() {
		// Tile generator
		float totalTiles = game.worldWidth * game.worldHeight;
		float spawnedTiles = 0;
		float percentOfTiles = 0;

		for (int y = 0; y < game.worldHeight; y++) {
			for (int x = 0; x < game.worldWidth; x++) {
				switch (new Random().nextInt(3)) { // Random int from 0 - 2, use as tileID
				case 0:
					tileArray[x][y] = new DirtTile(x * 32, y * 32, game); // Place dirt
					spawnedTiles++;
					break;
				case 1:
					tileArray[x][y] = new StoneTile(x * 32, y * 32, game); // Place stone
					spawnedTiles++;
					break;
				case 2:
					tileArray[x][y] = new GrassTile(x * 32, y * 32, game); // Place grass
					spawnedTiles++;
					break;
				default:
					tileArray[x][y] = new GrassTile(x * 32, y * 32, game); // Place grass
					spawnedTiles++;
					break;
				}
			}
			percentOfTiles = (spawnedTiles / totalTiles) * 100;
			System.out.println(percentOfTiles + "% of world Generated!");
		} // End tile Generator
	} // End generateLevel

	public void updateLevel(Game game) {
		this.game = game;

		for (int y = 0; y < game.worldHeight; y++) {
			for (int x = 0; x < game.worldWidth; x++) {
				Tile tile = tileArray[x][y];

				tile.tick(game);

				if (game.input.rightButton && tile.isContainsMouse() && tile.isCanAffect())
					rightClick(tile, x, y);

				if (game.input.leftButton && tile.isContainsMouse() && tile.isCanAffect())
					leftClick(tile);
			}
		} // End loops
	} // End update

	private void rightClick(Tile tile, int x, int y) { // if Right click
		switch (Player.toolSelected) {
		case 1: // Axe
			if (tile.isHasWall()) {
				tile.setHasWall(false);
			}
			break;
		case 2: // Pickaxe
			if (tile.getTileID() == GameResourceLoader.Stone && !tile.isHasRock()) {
				tile.setTileID(GameResourceLoader.Dirt);
				tileArray[x][y] = new DirtTile(x * 32, y * 32, game);
			}
			break;
		case 3: // Hoe
			if (!tile.isHasTree() && tile.getTileID() == GameResourceLoader.Grass || tile.getTileID() == GameResourceLoader.Dirt) {
				tile.setTileID(GameResourceLoader.Plowed);
				tileArray[x][y] = new PlowedTile(x * 32, y * 32, game);
			}
			break;
		case 4: // Shovel
			if (!tile.isHasTree() && tile.getTileID() == GameResourceLoader.Grass) {
				tile.setTileID(GameResourceLoader.Dirt);
				tileArray[x][y] = new DirtTile(x * 32, y * 32, game);
			}
			break;
		case 5: // Hand
			if (game.inv.itemSelected == game.inv.stoneID && game.inv.resourceAmounts[game.inv.stoneID] > 0) {
				if (!tile.isHasRock() && !tile.isHasTree()) { // If the tile is not stone and does not have a tree
					game.inv.addResource(game.inv.stoneID, -1);
					tile.setTileID(GameResourceLoader.Stone);
					tileArray[x][y] = new RoadTile(x * 32, y * 32, game);
					tileArray[x][y].setHasRock(false);
					tileArray[x][y].setHasOre(false);
				}
			}
			if (game.inv.itemSelected == game.inv.lumberID && game.inv.resourceAmounts[game.inv.lumberID] > 0 && !tile.isHasWall()) {
				if (!tile.isHasRock() && !tile.isHasTree() && tile.getTileID() != GameResourceLoader.Plowed) { // If the tile is not stone and does not have a tree
					tile.setHasWall(true);
					game.inv.addResource(game.inv.lumberID, -1);
				}
			}

		}
		game.input.rightButton = false;
	}

	private void leftClick(Tile tile) { // If Left click
		switch (tile.getTileID()) {
		case 0: // Dirt
			break;
		case 1: // Plowed
			if (!tile.hasPlants) {
				tile.hasPlants = true;
				game.inv.resourceAmounts[game.inv.seedID] -= 1;
			} else if (tile.hasPlants && tile.getPlantGrowth() == tile.getGrowthTime()) {
				game.inv.addResource(game.inv.wheatID, 1);
				tile.hasPlants = false;
				tile.setPlantGrowth(0);
			}
			break;
		case 2: // Stone
			if (tile.isHasRock() && Player.toolSelected != Player.Pickaxe && !tile.isHasOre()) {
				game.inv.addResource(game.inv.stoneID, 1);
			} else if (Player.toolSelected == Player.Pickaxe && !tile.isHasOre()) {
				game.inv.addResource(game.inv.stoneID, 1);
				tile.setHasRock(false);
			} else if (tile.isHasOre() && Player.toolSelected == Player.Pickaxe) {
				game.inv.addResource(game.inv.oreID, 1);
				tile.setOreAmount(tile.getOreAmount() - 1);
			}
			break;
		case 3: // Grass
			if (tile.isHasTree() && Player.toolSelected != Player.Axe) {
				game.inv.addResource(game.inv.stickID, 1);
			} else if (tile.isHasTree() && Player.toolSelected == Player.Axe) {
				game.inv.addResource(game.inv.lumberID, 1);
				tile.setHasTree(false);
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