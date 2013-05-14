package Level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import Core.Game;

public class StoneTile extends Tile {

	public StoneTile(int x, int y, Game game) {
		this.game = game;
		this.oX = x;
		this.oY = y;

		bounding = new Rectangle(x, y, size, size);
		bounding.setBounds(x, y, size, size);
		tileID = 2;

		int randomRock = new Random().nextInt(2);
		switch (randomRock) {
		case 0:
			hasRock = true;
			break;
		case 1:
			hasRock = false;
			break;
		}

	}

	public void tick(Game game) {
		this.game = game;

		x = oX - game.xOffset; // Current x after movement, Offset, etc
		y = oY - game.yOffset; // Current y after movement, Offset, etc
		bounding = new Rectangle(x, y, size, size);
		bounding.setBounds(x, y, size, size);
	}

	public void render(Graphics g) {
		if (!hasRock) {
			g.drawImage(game.res.tiles[tileID], x, y, game);
		} else {
			g.drawImage(game.res.tiles[4], x, y, game);
		}

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