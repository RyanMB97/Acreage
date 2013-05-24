package Level;

import java.awt.Graphics;
import java.awt.Rectangle;

import Core.Game;

public abstract class Tile extends Rectangle {
	private static final long serialVersionUID = 1L;

	Game game;
	public int x, y;
	public int tileID;
	public int oX, oY; // Original x,y coords when created
	public static int size = 32; // Size of tiles

	// Plowed tile
	boolean hasPlants = false;
	int plantGrowth = 0;
	int growthTime = 4;

	// Used for other images
	boolean hasRock = false; // Stone tile
	boolean hasOre = false; // Stone tile
	int oreAmount = 0; // Amount of Ore, if any within Vein
	boolean hasTree = false; // Grass tile

	// Misc
	boolean isVisible;
	boolean containsMouse;

	public abstract void tick(Game game);

	public abstract void render(Graphics g);

	protected void Visibility() {
		if (x >= 0 - 32 && x <= game.getWidth() + 32 && y >= 0 - 32 && y <= game.getHeight() + 32) {
			isVisible = true;
		} else {
			isVisible = false;
		}
	}
}