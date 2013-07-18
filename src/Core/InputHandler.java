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

import Entities.Player;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	public class Key {

		public boolean down, clicked;
		private short absorbs, presses;

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

	Game game;

	public List<Key> keys = new ArrayList<Key>();

	public Key left = new Key();
	public Key right = new Key();
	public Key up = new Key();
	public Key down = new Key();
	public Key I = new Key();

	public Key itemUp = new Key();
	public Key itemDown = new Key();

	public boolean leftButton = false;
	public boolean rightButton = false;

	public InputHandler(Game game) {
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
		game.addMouseWheelListener(this);
		game.addKeyListener(this);
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) { // If left button pressed
			leftButton = true;
		}
		if (e.getButton() == MouseEvent.BUTTON3) { // If right button pressed
			rightButton = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) { // If left button pressed
			leftButton = false;
		}
		if (e.getButton() == MouseEvent.BUTTON3) { // If right button pressed
			rightButton = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		game.mouseP = e.getPoint();

		if (e.getButton() == MouseEvent.BUTTON1) { // If left button pressed
			leftButton = true;
		}
		if (e.getButton() == MouseEvent.BUTTON3) { // If right button pressed
			rightButton = true;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		game.mouseP = e.getPoint(); // Grab mouse position

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL && e.getWheelRotation() > 0) { // If scrolling down
			if (game.inv.itemSelected > 0) { // If the tileSelection is greater than 0
				game.inv.itemSelected--; // Lower tileSelection
			} else { // If not
				game.inv.itemSelected = (byte) (game.inv.resourceNames.length - 1); // Set the tileSelection back to maximum
			}
		} else if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL && e.getWheelRotation() < 0) { // If scrolling up
			if (game.inv.itemSelected < (byte) (game.inv.resourceNames.length - 1)) { // If tileSelection is lower than the maximum tiles
				game.inv.itemSelected++; // Increase tileSelection
			} else { // If not
				game.inv.itemSelected = 0; // Set to 0
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
		// Movement
		if (e.getKeyCode() == KeyEvent.VK_A)
			left.toggle(pressed);
		if (e.getKeyCode() == KeyEvent.VK_D)
			right.toggle(pressed);
		if (e.getKeyCode() == KeyEvent.VK_W)
			up.toggle(pressed);
		if (e.getKeyCode() == KeyEvent.VK_S)
			down.toggle(pressed);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		toggle(e, true);

		// Misc
		if (e.getKeyCode() == KeyEvent.VK_F3)
			game.showDebug = !game.showDebug;
		if (e.getKeyCode() == KeyEvent.VK_G)
			game.showGrid = !game.showGrid;
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);

		// Tool Selection
		if (e.getKeyCode() == KeyEvent.VK_1)
			Player.toolSelected = 1;
		if (e.getKeyCode() == KeyEvent.VK_2)
			Player.toolSelected = 2;
		if (e.getKeyCode() == KeyEvent.VK_3)
			Player.toolSelected = 3;
		if (e.getKeyCode() == KeyEvent.VK_4)
			Player.toolSelected = 4;
		if (e.getKeyCode() == KeyEvent.VK_5)
			Player.toolSelected = 5;

		// Item Selection
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (game.inv.itemSelected > 0)
				game.inv.itemSelected--;
			else
				game.inv.itemSelected = (byte) (game.inv.resourceNames.length - 1);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (game.inv.itemSelected < game.inv.resourceNames.length - 1)
				game.inv.itemSelected++;
			else
				game.inv.itemSelected = 0;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		toggle(e, false);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}