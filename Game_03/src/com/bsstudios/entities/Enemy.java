package com.bsstudios.entities;

import java.awt.Color;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.bsstudios.main.Game;
import com.bsstudios.main.Sound;
import com.bsstudios.world.AStar;
import com.bsstudios.world.Camera;
import com.bsstudios.world.Vector2i;
import com.bsstudios.world.World;

public class Enemy extends Entity{

	public boolean ghostMode = false;
	public int ghostFrames = 0;
	public int nextTime = Entity.rand.nextInt(420 - 180) + 180;
	
	private BufferedImage[] spriteEnemy1;
	private BufferedImage[] spriteEnemy1Ghost;
	
	private int index = 0, maxIndex = 1, frames = 0, maxFrames = 60, indexD = 0, maxDIndex = 1, framesD = 0, maxDFrames = 25;
	
	public Enemy(int x, int y, int width, int height,int speed, BufferedImage sprite) {
		super(x, y, width, height,speed, sprite);
		spriteEnemy1 = new BufferedImage[2];
		spriteEnemy1[0] = Game.spritesheet.getSprite(64, 32, 16, 16);
		spriteEnemy1[1] = Game.spritesheet.getSprite(80, 32, 16, 16);
		
		spriteEnemy1Ghost = new BufferedImage[2];
		spriteEnemy1Ghost[0] = Game.spritesheet.getSprite(64, 64, 16, 16);
		spriteEnemy1Ghost[1] = Game.spritesheet.getSprite(80, 64, 16, 16);
	}

	public void tick(){
		depth = 1;
		ghostFrames++;
		if(ghostFrames == nextTime) {
			ghostFrames = 0;
			
			if(ghostMode == false) {
				ghostMode = true;
				System.out.println("Está no modo fantasma");
			}
			else {
				ghostMode = false;
			}
		}
		if(ghostMode == false) {
			if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
				Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
				path = AStar.findPath(Game.world, start, end);
			}
			if(new Random().nextInt(100) < 95)
				followPath(path);
			
			if(x % 16 == 0 && y % 16 == 0) {
				if(new Random().nextInt(100) < 10) {
					Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
					Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
					path = AStar.findPath(Game.world, start, end);
				}
			}
		}
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex)
				index = 0;
		}
		
		if(isCollidingWithPlayer()) {
			World.generateParticles(5, Game.player.getX() + 8, Game.player.getY() + 8);
			Game.entities.remove(Game.player);
			framesD++;
			if(framesD == maxDFrames) {
				framesD = 0;
				indexD++;
				if(indexD > maxDIndex)
					Game.player.dead = true;
			}
		}
		
		
	}
	
	public boolean isCollidingWithPlayer(){
		Rectangle enemyCurrent = new Rectangle(this.getX(),this.getY(),16,15);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY() - 1,15,15);
		
		return enemyCurrent.intersects(player);
	}
	
	public void render(Graphics g) {
		if(ghostMode == false) {
			g.drawImage(spriteEnemy1[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else {
			g.drawImage(spriteEnemy1Ghost[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
	
	
}
