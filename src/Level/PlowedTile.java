package Level;

import java.awt.Color;
import java.awt.Graphics;

import Core.Game;

public class PlowedTile extends Tile {

	int plantGrowTicks = 0;

	public PlowedTile(int x, int y, Game game) {
		this.game = game;
		this.setoX(x);
		this.setoY(y);

		setTileBoundaries(x, y, getTileSize(), getTileSize());
		setTileID(1);
	}

	public void tick(Game game) {
		this.game = game;

		setX(getoX() - game.xOffset); // Current x after movement, Offset, etc
		setY(getoY() - game.yOffset); // Current y after movement, Offset, etc
		getTileBoundaries().setBounds(getX(), getY(), getTileSize(), getTileSize());

		plantGrowth();

		// If tile contains mouse
		if (getTileBoundaries().contains(game.mouseP)) {
			setContainsMouse(true);
		} else {
			setContainsMouse(false);
		}

		Visibility();
	}

	private void plantGrowth() {
		if (hasPlants && getPlantGrowth() < getGrowthTime()) {
			if (plantGrowTicks % 120 == 0) {
				setPlantGrowth(getPlantGrowth() + 1);
			}
			plantGrowTicks++;
		} else if (!hasPlants) {
			setPlantGrowth(0);
			plantGrowTicks = 0;
		}
	}

	@Override
	public void render(Graphics g) {
		if (hasPlants) {
			g.drawImage(game.res.plants[getPlantGrowth()], getX(), getY(), game);
		} else if (!hasPlants) {
			g.drawImage(game.res.tiles[getTileID()], getX(), getY(), game);
		}

		if (game.showGrid) { // If the player wants to draw grids
			g.setColor(Color.WHITE); // White color
			g.drawRect(getX(), getY(), getTileSize() - 1, getTileSize() - 1); // Draw a border around tile
		}

		if (isContainsMouse() && isCanAffect()) { // If it is allowed to show borders
			g.setColor(Color.BLACK); // Black color
			g.drawRect(getX(), getY(), getTileSize() - 1, getTileSize() - 1); // Draw a border around image
		}

	}
}