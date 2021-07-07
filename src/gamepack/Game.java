package gamepack;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -240840600533728354L;
	
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	
	private Thread thread;
	private boolean running = false;
	
	public static boolean paused = false;
	public int diff = 0;
	
	// 0 = normal
	// 1 = hard
	
	private Random r = new Random();;
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Menu menu;
	private Shop shop;
	
	public static BufferedImage sprite_sheet;
	
	public enum STATE {
		Menu,
		Game,
		About,
		End,
		Select,
		Shop,
	}
	
	public STATE gameState = STATE.Menu;
	
	public Game() {
		try {
			BufferedImageLoader loader = new BufferedImageLoader();
			sprite_sheet = loader.loadImage("/textures.png");
			System.out.println("loaded");
		} catch (Exception e) {
			e.printStackTrace();
		}
		handler = new Handler();
		hud = new HUD();
		menu = new Menu(this,handler,hud);
		
		this.addKeyListener(new KeyInput(handler,this));
		this.addMouseListener(menu);
		
		AudioPlayer.load();
		AudioPlayer.getMusic("music").loop((float)1.0,(float) 0.03);
		
		new Window(WIDTH, HEIGHT, "WAVE", this);
		spawner = new Spawn(handler, hud, this);
		for (int i = 0; i < 10; i++) {
			handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.MenuParticle, handler));
		}
		
		
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				render();
			}
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		if (gameState == STATE.Game) {
			if(!paused) {
				handler.tick();
				hud.tick();
				spawner.tick();
				
				if (HUD.HEALTH <= 0) {
					HUD.HEALTH = 100;
					handler.clearAll();
					gameState = STATE.End;
					for (int i = 0; i < 10; i++) {
						handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.MenuParticle, handler));
					}
				}
			}
		} else if (gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Select || gameState == STATE.About) {
			menu.tick();
			handler.tick();
		} else if (gameState == STATE.Shop) {
			shop.tick();
			handler.tick();
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		handler.render(g);
		
		if (paused) {
			g.setColor(Color.white);
			g.drawString("PAUSED", 100, 100);
		}
		
		if (gameState == STATE.Game) {
			hud.render(g);
		} else if (gameState == STATE.Menu || gameState == STATE.About || gameState == STATE.End || gameState == STATE.Select) {
			menu.render(g);
		}
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max) {
		if (var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		} else {
			return var;
		}
	}
	
	public static void main(String[] args) {
		new Game();
	}
	
}
