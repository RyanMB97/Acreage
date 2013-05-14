package Core;

import java.awt.Color;
import java.awt.Graphics;

public class Inventory {
	public int seedID = 0;
	public int wheatID = 1;
	public int stoneID = 2;

	public int resourceAmounts[] = { 100, 0, 0 };
	String resourceNames[] = { "Seed", "Wheat", "Stone" };

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
		g.drawString(resourceNames[seedID] + ": " + resourceAmounts[seedID], 0, 10);
		g.drawString(resourceNames[wheatID] + ": " + resourceAmounts[wheatID], 0, 25);
		g.drawString(resourceNames[stoneID] + ": " + resourceAmounts[stoneID], 0, 40);
	}
}