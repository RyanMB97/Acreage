package Core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Debug {
	Game game;
	Rectangle debugArea;

	public Debug(Game game) {
		this.game = game;
	}

	public void render(Graphics g) {
		debugArea = new Rectangle(game.getWidth() - 100, 0, 100, 150);

		// Draw background for contrast
		g.setColor(Color.DARK_GRAY);
		g.fillRect(debugArea.x, debugArea.y, debugArea.width, debugArea.height);

		// Write info
		g.setColor(Color.WHITE);
		g.drawString("Player X: " + (game.player.x + game.xOffset), debugArea.x, 10);
		g.drawString("Player Y: " + (game.player.y + game.yOffset), debugArea.x, 25);
		g.drawString("Player Tile X: " + (game.player.x + game.xOffset) / 32, debugArea.x, 40);
		g.drawString("Player Tile Y: " + (game.player.y + game.yOffset) / 32, debugArea.x, 55);
	}
}