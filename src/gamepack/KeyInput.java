package gamepack;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.lwjgl.openal.AL;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private boolean[] keyDown = new boolean[4];
	
	Game game;
	
	public KeyInput(Handler handler, Game game) {
		this.handler = handler;
		this.game = game;
		for (int i = 0; i < 4; i++) {
			keyDown[i] = false;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size(); i++ ) {
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.Player) {
				if (key == KeyEvent.VK_W) {
					keyDown[0] = true; 
					tempObject.setVelY(-5);
				}
				if (key == KeyEvent.VK_S) {
					keyDown[1] = true; 
					tempObject.setVelY(5);
				}
				if (key == KeyEvent.VK_A) {
					keyDown[2] = true;
					tempObject.setVelX(-5);
				}
				if (key == KeyEvent.VK_D) {
					keyDown[3] = true; 
					tempObject.setVelX(5);
				}
			}
		}
		if (key == KeyEvent.VK_P) {
			Game.paused = !Game.paused;
			AudioPlayer.getSound("menu_sound").play((float)1.0,(float)0.5);
		}
		if (key == KeyEvent.VK_ESCAPE) {
			AL.destroy();
			System.exit(1);
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.Player) {
				
				if (key == KeyEvent.VK_W) {
					keyDown[0] = false;
					if (keyDown[1]) {
						tempObject.setVelY(5);
					} else {
						tempObject.setVelY(0);
					}
				}
				
				if (key == KeyEvent.VK_S) {
					keyDown[1] = false;
					if (keyDown[0]) {
						tempObject.setVelY(-5);
					} else {
						tempObject.setVelY(0);
					}
				}
				
				if (key == KeyEvent.VK_A) {
					keyDown[2] = false;
					if (keyDown[3]) {
						tempObject.setVelX(5);
					} else {
						tempObject.setVelX(0);
					}
				}
				
				if (key == KeyEvent.VK_D) {
					keyDown[3] = false;
					if (keyDown[2]) {
						tempObject.setVelX(-5);
					} else {
						tempObject.setVelX(0);
					}
				}
				
			}
		}
		
	}
	
}
