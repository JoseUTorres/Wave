package gamepack;

import java.awt.Color;
import java.awt.Graphics;

public class Shop {

	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawRect(15,15,200,32);
		g.drawString("Buy Health", 200, 32);
	}
	
}
