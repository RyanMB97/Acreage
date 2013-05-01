import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile {
	public int x, y, tileID = 0;
	public int oX, oY; // Original x,y coords when created
	public static int size = 32;
	Rectangle bounding; // Used to check if the mouse is within the tile
	boolean showBorders = false; // Used to show the border around current mouse-block

	Game game;
	public boolean hasPlants = false;
	private int plantGrowth = 0;
	private int growthTime = 4;

	public Tile(int x, int y, int tileID) {
		this.oX = x;
		this.oY = y;
		this.tileID = tileID;
		bounding = new Rectangle(x, y, size, size);
		bounding.setBounds(x, y, size, size);
	}

	public void tick(Game game) {
		this.game = game;

		x = oX - Game.xOffset; // Current x after movement, Offset, etc
		y = oY - Game.yOffset; // Current y after movement, Offset, etc
		bounding.setBounds(x, y, size, size);

		growPlants();
	}

	private void growPlants() {
		if (game.ticks % 60 == 0 && hasPlants && plantGrowth < growthTime) {
			plantGrowth++;
		}
	}

	public void render(Graphics g) {
		if (tileID != -1) // If the tile ID is not -1
			g.drawImage(game.res.tiles[tileID], x, y, null); // Display appropriate texture/image

		if (showBorders) { // If it is allowed to show borders
			g.setColor(Color.WHITE); // White color
			g.drawRect(x, y, size - 1, size - 1); // Draw a border around image
		}
		
		if(hasPlants){
			g.setColor(Color.BLACK); // White color
			g.drawRect(x, y, size - 1, size - 1); // Draw a border around image
		}

		if (hasPlants) {
			g.setColor(Color.WHITE);
			//g.drawString("Growth: " + plantGrowth, x, y);
			g.drawImage(game.res.plants[plantGrowth], x, y, null);
		}

	}
}