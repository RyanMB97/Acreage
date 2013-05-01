import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile {
	public int x, y, tileID = 0;
	public static int size = 32;
	Rectangle bounding;
	boolean showBorders = false;

	Game game;

	public Tile(int x, int y, int tileID) {
		this.x = x;
		this.y = y;
		this.tileID = tileID;
		bounding = new Rectangle(x, y, size, size);
		bounding.setBounds(x, y, size, size);
	}

	public void tick(Game game) {
		this.game = game;

	}

	public void render(Graphics g) {
		if (tileID != -1)
			g.drawImage(game.res.tiles[tileID], x, y, null);

		if (showBorders) {
			g.setColor(Color.WHITE);
			g.drawRect(x, y, size, size);
		}

	}
}