package Level;

import java.awt.Color;
import java.awt.Graphics;

import Core.Game;

public class DirtTile extends Tile {
	private static final long serialVersionUID = 1L;

	public DirtTile(int x, int y, Game game) {
		this.game = game;
		this.oX = x;
		this.oY = y;

		setBounds(x, y, size, size);
		tileID = 0;
	}

	public void tick(Game game) {
		this.game = game;

		x = oX - game.xOffset; // Current x after movement, Offset, etc
		y = oY - game.yOffset; // Current y after movement, Offset, etc
		setBounds(x, y, size, size);

		// If tile contains mouse
		if (contains(game.mouseP)) {
			containsMouse = true;
		} else {
			containsMouse = false;
		}

		Visibility();
	}

	public void render(Graphics g) {
		g.drawImage(game.res.tiles[tileID], x, y, game);

		if (game.showGrid) { // If the player wants to draw grids
			g.setColor(Color.WHITE); // White color
			g.drawRect(x, y, size - 1, size - 1); // Draw a border around tile
		}

		if (containsMouse) { // If it is allowed to show borders
			g.setColor(Color.BLACK); // Black color
			g.drawRect(x, y, size - 1, size - 1); // Draw a border around image
		}
	}
}