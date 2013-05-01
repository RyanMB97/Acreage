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
		if (e.getButton() == MouseEvent.BUTTON1) {
			leftButton = true;
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			rightButton = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			leftButton = false;
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			rightButton = false;
		}
	}

	public void mouseDragged(MouseEvent e) {
		game.mouseP = e.getPoint();

		if (e.getButton() == MouseEvent.BUTTON1) {
			leftButton = true;
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			rightButton = true;
		}
	}

	public void mouseMoved(MouseEvent e) {
		game.mouseP = e.getPoint();

	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL && e.getWheelRotation() > 0) { // If scrolling up
			if (game.tileSelection > 0) {
				game.tileSelection--;
			} else {
				game.tileSelection = game.res.tiles.length - 1;
			}
			System.out.println(game.tileSelection);
		} else if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL && e.getWheelRotation() < 0) { // If scrolling down
			if (game.tileSelection < game.res.tiles.length - 1) {
				game.tileSelection++;
			} else {
				game.tileSelection = 0;
			}
		}
	}
}