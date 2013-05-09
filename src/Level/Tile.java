package Level;

import java.awt.Graphics;
import java.awt.Rectangle;

import Core.Game;

public abstract class Tile {
	Game game;
	public int x, y, tileID;
	public int oX, oY; // Original x,y coords when created
	public static int size = 32;
	Rectangle bounding; // Used to check if the mouse is within the tile
	boolean showBorders = false; // Used to show the border around current mouse-block
	
	boolean hasPlants = false;
	int plantGrowth = 0;
	int growthTime = 4;

	public abstract void tick(Game game);

	public abstract void render(Graphics g);
}