import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	ArrayList<Tile> tileList = new ArrayList<Tile>();
	InputHandler input;
	Resources res = new Resources();

	int tileSelection = 0; // Used for selecting tiles for placement

	Point mouseP = new Point(-1, -1);

	public static boolean running = false;
	public static final String TITLE = "Acreage In-Dev 0.0.2";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final Dimension gameDim = new Dimension(WIDTH, HEIGHT);
	JFrame frame;

	public void run() { // Typical
		while (running) {
			tick();
			render();
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

		setMinimumSize(gameDim);
		setMaximumSize(gameDim);
		setPreferredSize(gameDim);
		frame = new JFrame(TITLE);

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

		// Tile generator
		for (int y = 0; y < getHeight(); y += Tile.size) {
			for (int x = 0; x < getWidth(); x += Tile.size) {
				int randomTile = new Random().nextInt(100); // Random number between 0-100

				if (randomTile >= 50) { // If that number is 50+
					tileList.add(new Tile(x, y, 3)); // Place grass
				} else { // If not
					tileList.add(new Tile(x, y, 0)); // Place dirt
				}
			}
		} // End tile Generator
	}

	public void tick() {
		tileStuff();

	}

	private void tileStuff() {

		for (Tile tile : tileList) {
			tile.tick(this);

			// If tile contains mouse
			if (tile.bounding.contains(mouseP)) {
				tile.showBorders = true;
			} else {
				tile.showBorders = false;
			}

			// Place tile
			if (tile.bounding.contains(mouseP) && input.rightButton) {
				tile.tileID = tileSelection;
			}

			// Delete tile
			// if (tile.bounding.contains(mouseP) && input.leftButton) {
			// tile.tileID = -1;
			// }
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

		for (Tile tile : tileList) {
			tile.render(g);
		}

		g.setColor(Color.WHITE);
		g.fillRect(0, this.getHeight() - 32, this.getWidth(), 32);

		g.drawImage(res.tileMap, 0, HEIGHT - 22, null); // Draws all tiles available for selection
		g.setColor(Color.RED); // Set red color
		g.drawRect(tileSelection * 32, HEIGHT - 22, 32, 32); // Selection box around the tile selected

		g.dispose();
		bs.show();
	}
}
