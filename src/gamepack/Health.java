package gamepack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Health extends GameObject {

	@SuppressWarnings("unused")
	private Handler handler;
	
	public Health(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
	}

	public void tick() {
		
	}

	
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)x, (int)y, 16, 16);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,16,16);
	}

}
