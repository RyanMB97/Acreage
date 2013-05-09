package Core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	public class Key {

		public boolean down, clicked;
		private int absorbs, presses;

		public void tick() {
			if (absorbs < presses) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}

		public void toggle(boolean pressed) {
			if (pressed != down) {
				down = pressed;
			}

			if (pressed) {
				presses++;
			}
		}

		public Key() {
			keys.add(this);
		}
	}

	public List<Key> keys = new ArrayList<Key>();

	public Key left = new Key();
	public Key right = new Key();
	public Key up = new Key();
	public Key down = new Key();
	public Key I = new Key();

	Game game;

	public boolean leftButton = false;
	public boolean rightButton = false;

	public InputHandler(Game game) {
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
		game.addMouseWheelListener(this);
		game.addKeyListener(this);
		this.game = game;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) { // If left button pressed
			leftButton = true;
		}
		if (e.getButton() == MouseEvent.BUTTON3) { // If right button pressed
			rightButton = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) { // If left button pressed
			leftButton = false;
		}
		if (e.getButton() == MouseEvent.BUTTON3) { // If right button pressed
			rightButton = false;
		}
	}

	public void mouseDragged(MouseEvent e) {
		game.mouseP = e.getPoint();

		if (e.getButton() == MouseEvent.BUTTON1) { // If left button pressed
			leftButton = true;
		}
		if (e.getButton() == MouseEvent.BUTTON3) { // If right button pressed
			rightButton = true;
		}
	}

	public void mouseMoved(MouseEvent e) {
		game.mouseP = e.getPoint(); // Grab mouse position

	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL && e.getWheelRotation() > 0) { // If scrolling down
			if (game.tileSelection > 0) { // If the tileSelection is greater than 0
				game.tileSelection--; // Lower tileSelection
			} else { // If not
				game.tileSelection = game.res.tiles.length - 1; // Set the tileSelection back to maximum
			}
		} else if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL && e.getWheelRotation() < 0) { // If scrolling up
			if (game.tileSelection < game.res.tiles.length - 1) { // If tileSelection is lower than the maximum tiles
				game.tileSelection++; // Increase tileSelection
			} else { // If not
				game.tileSelection = 0; // Set to 0
			}
		}
	}

	public void tick() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).tick();
		}
	}

	public void releaseAll() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).down = false;
		}
	}

	public void toggle(KeyEvent e, boolean pressed) {
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
			left.toggle(pressed);
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
			right.toggle(pressed);
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
			up.toggle(pressed);
		if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
			down.toggle(pressed);
	}

	public void keyPressed(KeyEvent e) {
		toggle(e, true);

		if (e.getKeyCode() == KeyEvent.VK_I)
			game.showInventory = !game.showInventory;
		if (e.getKeyCode() == KeyEvent.VK_F3)
			game.showDebug = !game.showDebug;
		if (e.getKeyCode() == KeyEvent.VK_G)
			game.showGrid = !game.showGrid;
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
	}

	public void keyReleased(KeyEvent e) {
		toggle(e, false);
	}

	public void keyTyped(KeyEvent e) {

	}
}