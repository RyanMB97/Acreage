package Level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Core.Game;

public class GrassTile extends Tile {
	int tileID = 3;

	public GrassTile(int x, int y, Game game) {
		this.game = game;
		oX = x;
		oY = y;
		
		bounding = new Rectangle(x, y, size, size);
		bounding.setBounds(x, y, size, size);
	}

	public void tick(Game game) {
		this.game = game;

		x = oX - game.xOffset; // Current x after movement, Offset, etc
		y = oY - game.yOffset; // Current y after movement, Offset, etc
		bounding = new Rectangle(x, y, size, size);
		bounding.setBounds(x, y, size, size);
	}

	public void render(Graphics g) {
		g.drawImage(game.res.tiles[tileID], x, y, game);

		if (game.showGrid) { // If the player wants to draw grids
			g.setColor(Color.WHITE); // White color
			g.drawRect(x, y, size - 1, size - 1); // Draw a border around tile
		}

		if (showBorders) { // If it is allowed to show borders
			g.setColor(Color.BLACK); // White color
			g.drawRect(x, y, size - 1, size - 1); // Draw a border around image
		}
	}
}