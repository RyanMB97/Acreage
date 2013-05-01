import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class InputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {
	Game game;

	boolean leftButton = false;
	boolean rightButton = false;

	public InputHandler(Game game) {
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
		game.addMouseWheelListener(this);
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
			System.out.println(game.tileSelection);
		} else if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL && e.getWheelRotation() < 0) { // If scrolling up
			if (game.tileSelection < game.res.tiles.length - 1) { // If tileSelection is lower than the maximum tiles
				game.tileSelection++; // Increase tileSelection
			} else { // If not
				game.tileSelection = 0; // Set to 0
			}
		}
	}
}