package Core;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameResourceLoader {

	public BufferedImage tileMap; // All tiles in one image to be seperated
	public BufferedImage tiles[] = new BufferedImage[10]; // Each seperate tile
	public String tileNames[] = { "Dirt", "Plowed Earth", "Stone", "Grass", "Stone w/ Rock", "Grass w/ Tree", "Null", "Null", "Null", "Null" }; // Names of tiles

	public BufferedImage plantMap;
	public BufferedImage plants[] = new BufferedImage[5];

	public BufferedImage playerMap;
	public BufferedImage playerFaces[] = new BufferedImage[4];

	public BufferedImage toolMap;
	public BufferedImage tools[] = new BufferedImage[5];

	public GameResourceLoader() {
		loadImages();
	}

	private void loadImages() {
		try {
			// Load tiles
			tileMap = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("Tiles.png")); // Grab the tilemap

			for (int i = 0; i < tiles.length; i++) { // For every tile that there should be
				tiles[i] = tileMap.getSubimage(i * 32, 0, 32, 32); // Create a subimage from tile map, and store it as a separate image
			}

			// Load plants
			plantMap = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("Plant.png")); // Grab the tilemap

			for (int i = 0; i < plants.length; i++) { // For every tile that there should be
				plants[i] = plantMap.getSubimage(i * 32, 0, 32, 32); // Create a subimage from tile map, and store it as a seperate image
			}

			playerMap = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("Player.png"));
			for (int i = 0; i < playerFaces.length; i++) { // For every tile that there should be
				playerFaces[i] = playerMap.getSubimage(i * 32, 0, 32, 32); // Create a subimage from tile map, and store it as a separate image
			}

			toolMap = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("Tools.png"));
			for (int i = 0; i < tools.length; i++) { // For every tile that there should be
				tools[i] = toolMap.getSubimage(i * 32, 0, 32, 32); // Create a subimage from tile map, and store it as a separate image
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}