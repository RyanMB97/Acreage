package Tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Core.Game;

public class Hoe extends Tool {

	public Hoe(Game game) {
		this.game = game;
		toolID = 3;
		toolArea = new Rectangle(toolID * 32 + 68, 0, 32, 32);
	}

	public void tick(Game game) {
		if(toolArea.contains(game.mouseP)){
			game.level.canAffectTile = false;
		}else{
			game.level.canAffectTile = true;
		}
	}

	public void render(Graphics g) {
		g.drawImage(game.res.tools[toolID - 1], toolID * 32 + 68, 0, game);
		
		if (toolArea.contains(game.mouseP)) {
			g.setColor(Color.YELLOW);
			g.drawString("Hoe - Used for farming!", toolArea.x, toolArea.y + 42);
		}
	}
}