package gamepack;

import java.util.Random;

public class Spawn {

		private Handler handler;
		private HUD hud;
		private Game game;
		private Random r = new Random();
		
		private int scoreKeep = 0;
		
		public Spawn(Handler handler, HUD hud, Game game) {
			this.handler = handler;
			this.hud = hud;
			this.game = game;
		}
		
		public void tick() {
			scoreKeep++;
			
			if (scoreKeep >= 1000) {
				scoreKeep = 0;
				hud.setLevel(hud.getLevel() + 1);
				if (game.diff == 0) {
					if (hud.getLevel() % 2 == 0 && hud.getLevel() % 10 != 0) {
						handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler));
					}
					if (hud.getLevel() % 4 == 0) {
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler));
					}
					if (hud.getLevel() % 7 == 0) {
						handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.SmartEnemy, handler));
						handler.addObject(new Health(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.Health, handler));
					}
					if (hud.getLevel() % 10 == 0) {
						handler.clearEnemies();
						handler.addObject(new EnemyBoss((Game.WIDTH/2), -120, ID.EnemyBoss, handler));
					}
					if (hud.getLevel() % 12 == 0) {
						handler.clearEnemies();
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler));
					}
				} else if (game.diff == 1) {
					if (hud.getLevel() % 2 == 0 && hud.getLevel() % 10 != 0) {
						handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler));
					}
					if (hud.getLevel() % 4 == 0) {
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler));
						handler.addObject(new Health(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.Health, handler));
					}
					if (hud.getLevel() % 7 == 0) {
						handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.SmartEnemy, handler));
					}
					if (hud.getLevel() % 10 == 0) {
						handler.clearEnemies();
						handler.addObject(new EnemyBoss((Game.WIDTH/2), -120, ID.EnemyBoss, handler));
					}
					if (hud.getLevel() % 12 == 0) {
						handler.clearEnemies();
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.FastEnemy, handler));
					}
				}
			}
			
		} 
		
}
