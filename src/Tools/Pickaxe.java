package Tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Core.Game;

public class Pickaxe extends Tool {

	public Pickaxe(Game game) {
		this.game = game;
		toolID = 1;
		toolArea = new Rectangle(toolID * 32, 0, 32, 32);
	}

	public void tick(Game game) {

	}

	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(toolID * 32, 0, 32, 32);
	}

}