import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {

	BufferedImage tileMap;
	BufferedImage tiles[] = new BufferedImage[10];
	String tileNames[] = {"Dirt", "Plank", "Stone", "Grass", "Null", "Null", "Null", "Null", "Null", "Null"};

	public Resources() {
		loadImages();
	}

	private void loadImages() {
		try {
			tileMap = ImageIO.read(getClass().getResource("Tiles.png"));
			System.out.println("Grabbed tilemap");

			for (int i = 0; i < tiles.length; i++) {
				tiles[i] = tileMap.getSubimage(i * 32, 0, 32, 32);
				System.out.println("Created tile: " + i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}