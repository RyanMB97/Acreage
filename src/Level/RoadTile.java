package Level;

import java.awt.Color;
import java.awt.Graphics;

import Core.Game;
import Core.GameResourceLoader;

public class RoadTile extends Tile {

	public RoadTile(int x, int y, Game game) {
		this.game = game;
		this.setoX(x);
		this.setoY(y);

		setTileBoundaries(x, y, getTileSize(), getTileSize());
		setTileID(7);
	}

	public void tick(Game game) {
		this.game = game;

		setX(getoX() - game.xOffset); // Current x after movement, Offset, etc
		setY(getoY() - game.yOffset); // Current y after movement, Offset, etc
		getTileBoundaries().setBounds(getX(), getY(), getTileSize(), getTileSize());

		// If tile contains mouse
		if (getTileBoundaries().contains(game.mouseP)) {
			setContainsMouse(true);
		} else {
			setContainsMouse(false);
		}

		Visibility();
	}

	@Override
	public void render(Graphics g) {
		if (isHasWall()) {
			g.drawImage(game.res.tiles[GameResourceLoader.logWall], getX(), getY(), game);
		} else {
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