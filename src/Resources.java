import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {

	BufferedImage tileMap; // All tiles in one image to be seperated
	BufferedImage tiles[] = new BufferedImage[10]; // Each seperate tile
	String tileNames[] = { "Dirt", "Plank", "Stone", "Grass", "Null", "Null", "Null", "Null", "Null", "Null" }; // Names of tiles

	public Resources() {
		loadImages();
	}

	private void loadImages() {
		try {
			tileMap = ImageIO.read(getClass().getResource("Tiles.png")); // Grab the tilemap
			System.out.println("Grabbed tilemap");

			for (int i = 0; i < tiles.length; i++) { // For every tile that there should be
				tiles[i] = tileMap.getSubimage(i * 32, 0, 32, 32); // Create a subimage from tile map, and store it as a seperate image
				System.out.println("Created tile: " + i); // Output that the tile has been created
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}