package gamepack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import org.lwjgl.openal.AL;

import gamepack.Game.STATE;

public class Menu extends MouseAdapter {
	
	private Game game;
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	private Font fnt = new Font("arial", 1, 50);
	private Font fnt2 = new Font("arial", 1, 30);
	
	public Menu(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		// Menu state
		if (game.gameState == STATE.Menu) {
			// Play button
			if (mouseOver(mx,my,210,150,200,64)) {
				game.gameState = STATE.Select;
				
				AudioPlayer.getSound("menu_sound").play((float)1.0,(float)0.5);
			}
			// Quit button
			if (mouseOver(mx, my, 210, 350, 200, 64)) {
				AudioPlayer.getSound("menu_sound").play((float)1.0,(float)0.5);
				AL.destroy();
				System.exit(0);
			}
			// About button
			if (mouseOver(mx,my,210,250,200,64)) {
				AudioPlayer.getSound("menu_sound").play((float)1.0,(float)0.5);
				game.gameState = STATE.About;
			}
			// About state
		} else if (game.gameState == STATE.About) {
			// Back button for about
			if (mouseOver(mx,my,210, 350, 200, 64) && game.gameState == STATE.About) {
				game.gameState = STATE.Menu;
				AudioPlayer.getSound("menu_sound").play((float)1.0,(float)0.5);
				return;
			}
		} else if (game.gameState == STATE.End) {
			if (mouseOver(mx,my,210,350,200,64) && game.gameState == STATE.End) {
				game.gameState = STATE.Menu;
				AudioPlayer.getSound("menu_sound").play((float)1.0,(float)0.5);
				return;
			}
		} else if (game.gameState == STATE.Select) {
			// Normal button
			if (mouseOver(mx,my,210,150,200,64)) {
				game.gameState = STATE.Game;
				hud.setLevel(1);
				hud.setScore(0);
				handler.clearEnemies();
				handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2 - 32, ID.Player, handler));
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.BasicEnemy, handler));
				
				game.diff = 0;
				
				AudioPlayer.getSound("menu_sound").play((float)1.0,(float)0.5);
			}
			// Back button
			if (mouseOver(mx, my, 210, 350, 200, 64)) {
				AudioPlayer.getSound("menu_sound").play((float)1.0,(float)0.5);
				game.gameState = STATE.Menu;
			}
			// Hard button
			if (mouseOver(mx,my,210,250,200,64)) {
				game.gameState = STATE.Game;
				hud.setLevel(1);
				hud.setScore(0);
				handler.clearEnemies();
				handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2 - 32, ID.Player, handler));
				handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.BasicEnemy, handler));
				
				game.diff = 1;
				
				AudioPlayer.getSound("menu_sound").play((float)1.0,(float)0.5);
			}
		}
	}
	
	public void mouseReleased(MouseEvent e)	{
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else return false;
		} else return false;
	}
	
	public void tick() {
		
	}
	
	public void render (Graphics g) {
		if (game.gameState == STATE.Menu) {
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Wave", 240, 70);
			// Play button
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawString("Play", 270, 190);
			g.setColor(Color.white);
			g.drawRect(210, 150, 200, 64);
			// About button
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawString("About", 270, 290);
			g.setColor(Color.white);
			g.drawRect(210, 250, 200, 64);
			// Quit button
			g.setFont(fnt2);
			g.setColor(Color.red);
			g.drawString("Quit", 270, 390);
			g.setColor(Color.white);
			g.drawRect(210, 350, 200, 64);
		} else if (game.gameState == STATE.About) {
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Author", 225, 70);
			
			g.setFont(fnt2);
			g.drawString("Jose Torres", 220, 225);
			
			g.setColor(Color.red);
			g.drawString("Back", 270, 395);
			g.setColor(Color.white);
			g.drawRect(210, 350, 200, 64);
		} else if (game.gameState == STATE.End) {
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Game Over", 190, 70);
			
			g.setFont(fnt2);
			g.drawString("Final Score: " + hud.getScore(), 195, 225);
			
			g.setColor(Color.red);
			g.drawString("Back", 275, 395);
			g.setColor(Color.white);
			g.drawRect(210, 350, 200, 64);
		} else if (game.gameState == STATE.Select) {
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Select Difficulty", 150, 70);
			// Play button
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawString("Normal", 258, 190);
			g.setColor(Color.white);
			g.drawRect(210, 150, 200, 64);
			// About button
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawString("Hard", 278, 290);
			g.setColor(Color.white);
			g.drawRect(210, 250, 200, 64);
			// Quit button
			g.setFont(fnt2);
			g.setColor(Color.red);
			g.drawString("Back", 270, 390);
			g.setColor(Color.white);
			g.drawRect(210, 350, 200, 64);
		}
		
	}

}
