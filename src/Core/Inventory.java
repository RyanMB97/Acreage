package Core;

import java.awt.Color;
import java.awt.Graphics;

public class Inventory {
	public int seedID = 0;
	public int wheatID = 1;
	public int stoneID = 2;
	public int stickID = 3;
	public int lumberID = 4;

	public int resourceAmounts[] = { 100, 0, 0, 0, 0};
	String resourceNames[] = { "Seed", "Wheat", "Stone", "Sticks", "Lumber" };

	public Inventory() {

	}

	public void addResource(int resourceID, int amount) {
		resourceAmounts[resourceID] += amount;
		System.out.println(resourceNames[resourceID] + ": " + resourceAmounts[resourceID]);
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 100, Game.HEIGHT / 3);

		g.setColor(Color.BLACK);
		for(int i = 0; i < resourceNames.length; i++){
			g.drawString(resourceNames[i] + ": " + resourceAmounts[i], 0, i * 10 + 10);
		}
	}
}