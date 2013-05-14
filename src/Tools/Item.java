package Tools;

import java.awt.Graphics;

import Core.Game;

public abstract class Item {

	public abstract void tick(Game game);

	public abstract void render(Graphics g);

}