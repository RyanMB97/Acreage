package Level;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import Core.Game;
import Core.GameResourceLoader;

public class GrassTile extends Tile {
	private static final long serialVersionUID = 1L;

	public GrassTile(int x, int y, Game game) {
		this.game = game;
		oX = x;
		oY = y;

		setBounds(x, y, size, size);
		tileID = 3;

		switch (new Random().nextInt(2)) {
		case 0:
			hasTree = true;
			break;
		case 1:
			hasTree = false;
			break;
		}
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
		if (!hasTree) {
			g.drawImage(game.res.tiles[tileID], x, y, game);
		} else {
			g.drawImage(game.res.tiles[GameResourceLoader.Tree], x, y, game);
		}

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