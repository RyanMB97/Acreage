import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	InputHandler input;
	Resources res = new Resources();
	Level level;

	int tileSelection = 0; // Used for selecting tiles for placement

	Point mouseP = new Point(-1, -1);

	public static boolean running = false;
	public static final String TITLE = "Acreage In-Dev 0.0.4";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final Dimension gameDim = new Dimension(WIDTH, HEIGHT);
	JFrame frame;

	int worldWidth = 150;
	int worldHeight = 150;

	public static int xOffset = 0, yOffset = 0;

	// Variables for the FPS and UPS counter
	public int ticks = 0;
	private int frames = 0;
	private int FPS = 0;
	private int UPS = 0;
	static double delta;

	// Used in the "run" method to limit the frame rate to the UPS
	boolean limitFrameRate = true;
	boolean shouldRender;

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		long lastTimer = System.currentTimeMillis();
		delta = 0D;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;

			// If you want to limit frame rate, shouldRender = false
			if (limitFrameRate) {
				shouldRender = false;
			} else {
				shouldRender = true;
			}

			// If the time between ticks = 1, then various things (shouldRender = true, keeps FPS locked at UPS)
			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}

			// If you should render, render!
			if (shouldRender) {
				frames++;
				render();
			}

			// Reset stuff every second for the new "FPS" and "UPS"
			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				FPS = frames;
				UPS = ticks;
				frames = 0;
				ticks = 0;
				frame.setTitle(TITLE + " FPS: " + FPS + " UPS: " + UPS);
			}
		}
	}

	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}

	public static synchronized void stop() {
		running = false;
		System.exit(0);
	}

	public Game() { // Typical stuff
		input = new InputHandler(this);
		level = new Level(this);

		setMinimumSize(gameDim);
		setMaximumSize(gameDim);
		setPreferredSize(gameDim);
		frame = new JFrame(TITLE + " FPS: " + FPS + " UPS: " + UPS);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		init();

		requestFocus();
	}

	private void init() {
	}

	public void tick() {
		level.updateLevel(this);
		scanInput();
	}

	private void scanInput() {
		// World Movement
		if (input.left.down && xOffset > 0) {
			xOffset -= 5 * delta;
		}
		if (input.right.down && xOffset < (worldWidth * 32) - WIDTH - 16) {
			xOffset += 5 * delta;
		}
		if (input.up.down && yOffset > 0) {
			yOffset -= 5 * delta;
		}
		if (input.down.down && yOffset < (worldHeight * 32) - HEIGHT) {
			yOffset += 5 * delta;
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		level.renderLevel(g);

		g.setColor(Color.WHITE);
		g.fillRect(0, this.getHeight() - 32, this.getWidth(), 32);

		g.drawImage(res.tileMap, 0, HEIGHT - 22, null); // Draws all tiles available for selection
		g.setColor(Color.RED); // Set red color
		g.drawRect(tileSelection * 32, HEIGHT - 22, 32, 32); // Selection box around the tile selected

		g.dispose();
		bs.show();
	}
}
