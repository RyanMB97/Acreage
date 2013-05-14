package Core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import Entities.Player;
import Level.Level;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public InputHandler input;
	public GameResourceLoader res = new GameResourceLoader();
	public Level level;
	public Player player;
	public Inventory inv;
	public Debug debug;

	public int tileSelection = 0; // Used for selecting tiles for placement

	public Point mouseP = new Point(-1, -1);

	public static boolean running = false;
	public static final String TITLE = "Acreage In-Dev 0.0.6";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final Dimension gameDim = new Dimension(WIDTH, HEIGHT);
	JFrame frame;

	public int worldWidth = 150;
	public int worldHeight = 150;

	public int xOffset = 0;
	public int yOffset = 0;

	// Variables for the FPS and UPS counter
	public int ticks = 0;
	private int frames = 0;
	private int FPS = 0;
	private int UPS = 0;
	public double delta;

	// Options
	boolean showInventory = false;
	boolean showDebug = false;
	public boolean showGrid = false;

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
		input = new InputHandler(this);
		level = new Level(this);
		player = new Player();
		inv = new Inventory();
		debug = new Debug(this);
	}

	public void tick() {
		player.tick(this);
		level.updateLevel(this);
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
		player.render(g);

		if (showInventory) {
			inv.render(g);
		}
		if (showDebug) {
			debug.render(g);
		}

		g.setColor(Color.WHITE);
		g.fillRect(0, this.getHeight() - 33, this.getWidth(), 33);

		g.drawImage(res.tileMap, 0, HEIGHT - 22, null); // Draws all tiles available for selection
		g.setColor(Color.CYAN); // Set red color
		g.drawRect(tileSelection * 32, HEIGHT - 22, 32, 32); // Selection box around the tile selected

		g.dispose();
		bs.show();
	}
}
