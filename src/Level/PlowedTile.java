package Level;

import java.awt.Color;
import java.awt.Graphics;

import Core.Game;

public class PlowedTile extends Tile {
	private static final long serialVersionUID = 1L;

	int plantGrowTicks = 0;

	public PlowedTile(int x, int y, Game game) {
		this.game = game;
		this.oX = x;
		this.oY = y;

		setBounds(x, y, size, size);
		tileID = 1;
	}

	public void tick(Game game) {
		this.game = game;

		x = oX - game.xOffset; // Current x after movement, Offset, etc
		y = oY - game.yOffset; // Current y after movement, Offset, etc
		setBounds(x, y, size, size);

		plantGrowth();

		// If tile contains mouse
		if (contains(game.mouseP)) {
			containsMouse = true;
		} else {
			containsMouse = false;
		}

		Visibility();
	}

	private void plantGrowth() {
		if (hasPlants && plantGrowth < growthTime) {
			if (plantGrowTicks % 120 == 0) {
				plantGrowth++;
			}
			plantGrowTicks++;
		} else if (!hasPlants) {
			plantGrowth = 0;
			plantGrowTicks = 0;
		}
	}

	public void render(Graphics g) {
		if (hasPlants) {
			g.drawImage(game.res.plants[plantGrowth], x, y, game);
		} else {
			g.drawImage(game.res.tiles[tileID], x, y, game);
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