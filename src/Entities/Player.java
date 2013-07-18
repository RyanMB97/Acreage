package Entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import Core.Game;

public class Player {
	Game game;

	public int x, y;
	public int worldX, worldY;
	public int tileX, tileY;
	public byte width = 32;
	public byte height = 32;

	public Rectangle bounding;
	double speed = 2;

	public boolean canAffectTile = true;
	public Point playerCenter;

	// Tools
	public static byte toolSelected = 1; // 1 = Axe, 2 = Pickaxe, 3 = Hoe, 4 = Shovel, 5 = Hand
	public static final byte Axe = 1;
	public static final byte Pickaxe = 2;
	public static final byte Hoe = 3;
	public static final byte Shovel = 4;
	public static final byte Hand = 5;

	// Moving
	public byte directionFacing = 0; // 0 = Down, 1 = Up, 2 = Left, 3 = Right
	public byte Down = 0;
	public byte Up = 1;
	public byte Left = 2;
	public byte Right = 3;

	// Collision Booleans
	public boolean canLeft = true;
	public boolean canRight = true;
	public boolean canUp = true;
	public boolean canDown = true;

	public Player(Game game) {
		this.game = game;
		x = game.getWidth() / 2 - width / 2;
		y = game.getHeight() / 2 - height / 2;
		playerCenter = new Point(worldX - width / 2, worldY - height / 2);

		bounding = new Rectangle(x, y, width, height);
		bounding.setBounds(x, y, width, height);
	}

	public void tick() {
		bounding.setBounds(x, y, width, height);
		speed = 2 * game.delta;

		movement();
		worldEdgeCollision();

		worldX = x + game.xOffset;
		worldY = y + game.yOffset;
		playerCenter = new Point(worldX - width / 2, worldY - height / 2);
		tileX = worldX / 32;
		tileY = worldY / 32;
	}

	private void movement() {
		if (game.input.down.down) {
			if (canDown)
				game.yOffset += speed;
			directionFacing = Down;
		}
		if (game.input.up.down) {
			if (canUp)
				game.yOffset -= speed;
			directionFacing = Up;
		}
		if (game.input.left.down) {
			if (canLeft)
				game.xOffset -= speed;
			directionFacing = Left;
		}
		if (game.input.right.down) {
			if (canRight)
				game.xOffset += speed;
			directionFacing = Right;
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
		g.drawImage(game.res.playerFaces[directionFacing], x, y, game);
	}
}