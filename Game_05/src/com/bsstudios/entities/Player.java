package com.bsstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.bsstudios.main.Game;
import com.bsstudios.world.Camera;
import com.bsstudios.world.World;

public class Player extends Entity{

	public boolean right,left;
	
	private double gravity = 0.3;
	private double vspd = 0;
	
	public boolean jump = false;
	
	public boolean isJumping = false;
	public int jumpHeight = 64;
	public int jumpFrames = 0;
	
	public int dir = 1;
	
	private int framesAnimation = 0;
	private int maxFrames = 15;
	
	private int maxSprite = 2;
	private int curSprite = 0;
	
	public static double life = 100;
	 
	public Player(int x, int y, int width, int height,double speed, BufferedImage sprite) {
		super(x, y, width, height,speed, sprite);
	}
	
	public void tick(){
		depth = 2;
		
		vspd+=gravity;
		if(!World.isFree((int)x, (int)(y + 1)) && jump) {
			vspd = -6;
			jump = false;
		}
		
		if(!World.isFree((int)x, (int)(y+vspd))){
			int signVsp = 0;
			if(vspd >= 0) {
				signVsp = 1;
			}else {
				signVsp = -1;
			}while(World.isFree((int)x, (int)(y + signVsp))) {
				y = y + signVsp;
			}
			vspd = 0;
		}
		
		y = y + vspd;
		
		/*if(World.isFree((int)x, (int)(y + gravity)) && isJumping == false) {
			y+=gravity;
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Enemy) {
					if(Entity.isColidding(this, e)) {
						isJumping = true;
						jumpHeight = 32;
						((Enemy)(e)).vida--;
						if(((Enemy)(e)).vida <= 0) {
							Game.entities.remove(i);
							break;
						}
					}
				}
			}
		}
		
		if(jump) {
			if(!World.isFree(this.getX(), this.getY() + 2)) {
				isJumping = true;
			}else {
				jump = false;
			}
		}
		
		if(isJumping) {
			if(World.isFree(this.getX(), this.getY() - 2)) {
				y-=2;
				jumpFrames+=2;
				if(jumpFrames == jumpHeight) {
					if(jumpFrames == jumpHeight) {
						isJumping = false;
						jump = false;
						jumpFrames = 0;
					}
				}
			}else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
			}
		}
		*/
		
		if(right && World.isFree((int)(x + speed),(int)y)) {
			x+=speed;
			dir = 1;
		}
		
		else if(left && World.isFree((int)(x - speed),(int)y)) {
			x-=speed;
			dir = 2;
		}
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
				if(Entity.isColidding(this, e)) {
					if(Entity.rand.nextInt(100) < 5) {
						life--;
					}
					}
				}
			}
		
		Camera.x = Camera.clamp((int)x - Game.WIDTH / 2, 0, World.WIDTH * 32);
		Camera.y = Camera.clamp((int)y - Game.HEIGHT / 2, 0, World.HEIGHT * 32 - Game.HEIGHT);
	}
	
	public void render(Graphics g) {
		framesAnimation++;
		if(framesAnimation == maxFrames) {
			curSprite++;
			framesAnimation = 0;
			if(curSprite == maxSprite) {
				curSprite = 0;
			}
		}
		if(dir == 1) {
			sprite = Entity.PLAYER_SPRITE_RIGHT[curSprite];
		}else if(dir == 2) {
			sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];
		}
		super.render(g);
	}
	
}
