package com.gcstudios.main;

import java.awt.image.BufferedImage;

import com.gcstudios.entities.Enemy;
import com.gcstudios.entities.Entity;

public class EnemySpawn {
	
	public int targetTime = 40*2;
	public int curTime = 0;
	
	BufferedImage enemySprite = Game.spritesheet.getSprite(16,0,16,16);
	BufferedImage enemy2Sprite = Game.spritesheet.getSprite(32,0,16,16);
	
	public void tick() {
		curTime++;
		if(curTime == targetTime) {
			targetTime = Entity.rand.nextInt(120) + 20;
			curTime = 0;
			int yy = 0;
			int xx = Entity.rand.nextInt(Game.WIDTH - 16);
			
			int randomSprite = Entity.rand.nextInt(100);
			Enemy enemy = new Enemy(xx,yy,16,16,1,enemySprite);
			if(randomSprite >= 50) {
				enemy = new Enemy(xx,yy,16,16,(int)Entity.rand.nextInt(3-1) + 1,enemySprite);
			}else {
				enemy = new Enemy(xx,yy,16,16,Entity.rand.nextInt(3-1) + 1,enemy2Sprite);
			}
			
			
			Game.entities.add(enemy);
		}
	}
}
