package Tools;

import java.awt.Graphics;
import java.awt.Rectangle;

import Core.Game;

public abstract class Tool extends Item {
	byte toolID;
	public Rectangle toolArea;
	Game game;

	@Override
	public abstract void tick(Game game);

	@Override
	public abstract void render(Graphics g);
}