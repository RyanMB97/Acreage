package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Core.Game;

public class Player {
	Game game;

	public int x, y;
	int width = 16, height = 32;
	Rectangle bounding;
	int speed = 2;

	boolean canLeft = true, canRight = true, canUp = true, canDown = true;

	public Player() {
		x = Game.WIDTH / 2 - width;
		y = Game.HEIGHT / 2 - height;

		bounding = new Rectangle(x, y, width, height);
		bounding.setBounds(x, y, width, height);
	}

	public void tick(Game game) {
		this.game = game;
		bounding.setBounds(x, y, width, height);

		worldEdgeCollision();

		if (game.input.left.down && canLeft) {
			game.xOffset -= speed * Game.delta;
		}
		if (game.input.right.down && canRight) {
			game.xOffset += speed * Game.delta;
		}
		if (game.input.up.down && canUp) {
			game.yOffset -= speed * Game.delta;
		}
		if (game.input.down.down && canDown) {
			game.yOffset += speed * Game.delta;
		}
	}

	private void worldEdgeCollision() {
		if (x <= 0 - game.xOffset) {
			canLeft = false;
		} else {
			canLeft = true;
		}

		if (x + width >= (game.worldWidth * 32) - game.xOffset) {
			canRight = false;
		} else {
			canRight = true;
		}

		if (y <= 0 - game.yOffset) {
			canUp = false;
		} else {
			canUp = true;
		}

		if (y + height >= (game.worldHeight * 32) - game.yOffset) {
			canDown = false;
		} else {
			canDown = true;
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fill3DRect(x, y, width, height, false);
	}
}