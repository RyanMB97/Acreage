package Level;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import Core.Game;
import Core.GameResourceLoader;

public class StoneTile extends Tile {
	private static final long serialVersionUID = 1L;

	public StoneTile(int x, int y, Game game) {
		this.game = game;
		this.oX = x;
		this.oY = y;

		setBounds(x, y, size, size);
		tileID = 2;

		generateRockOre();
	}

	private void generateRockOre() {
		switch (new Random().nextInt(2)) { // Switch a random int, 0 or 1 for a Rock
		case 0:
			hasRock = true;
			break;
		case 1:
			hasRock = false;
			break;
		}

		if (hasRock) {
			switch (new Random().nextInt(2)) { // Switch a random int, 0 or 1 for Ore
			case 0:
				hasOre = true;
				break;
			case 1:
				hasOre = false;
				break;
			}
		}

		if (hasOre) {
			oreAmount = new Random().nextInt(5);
		}
	} // End Generate RockOre

	public void tick(Game game) {
		this.game = game;

		x = oX - game.xOffset; // Current x after movement, Offset, etc
		y = oY - game.yOffset; // Current y after movement, Offset, etc
		setBounds(x, y, size, size);

		if (oreAmount <= 0) {
			hasOre = false;
		}

		// If tile contains mouse
		if (contains(game.mouseP)) {
			containsMouse = true;
		} else {
			containsMouse = false;
		}

		Visibility();
	}

	public void render(Graphics g) {
		if (!hasRock) {
			g.drawImage(game.res.tiles[tileID], x, y, game);
		} else if (hasRock && !hasOre) {
			g.drawImage(game.res.tiles[GameResourceLoader.Rock], x, y, game);
		} else if (hasRock && hasOre) {
			g.drawImage(game.res.tiles[GameResourceLoader.Metal], x, y, game);
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