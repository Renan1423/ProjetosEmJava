package com.gcstudios.entities;


import java.awt.Color;


//Created by Renan Nunes
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;


public class Player extends Entity{

	
	public boolean right,left;
	
	public double life = 8;
	public double fome = 5;
	
	public int dir = 1;
	private double gravity = 0.35;
	private double vspd = 0;
	
	public boolean jump = false;
	
	public boolean isJumping = false;
	public int jumpHeight = 48;
	public int jumpFrames = 0;
	
	private int framesAnimation = 0;
	private int maxFrames = 15;
	
	private int maxSprite = 2;
	private int curSprite = 0;
	
	public BufferedImage ATTACK_RIGHT;
	public BufferedImage ATTACK_LEFT;
	
	public boolean attack = false;
	public boolean isAttacking = false;
	public int attackFrames = 0;
	public int maxFramesAttack = 15;
	
	public int maxDelayDamage = 45;
	public int delayDamage = maxDelayDamage;
	
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		ATTACK_RIGHT = Game.spritesheet.getSprite(32, 16, 16, 16);
		ATTACK_LEFT = Game.spritesheet.getSprite(48, 16, 16, 16);
	}
	
	public void tick(){
		depth = 2;
		if(World.isFree((int)x,(int)(y+gravity)) && isJumping == false) {
			y+=gravity;
		}
		if(right && World.isFree((int)(x+speed), (int)y)) {
			x+=speed;
			dir = 1;
		}
		else if(left && World.isFree((int)(x-speed), (int)y)) {
			x-=speed;
			dir = -1;
		}
		
		if(jump) {
			if(!World.isFree(this.getX(),this.getY()+1)) {
				isJumping = true;
			}else {
				jump = false;
			}
		}
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
		
		//Sistema de ataque
		
		if(attack) {
			if(isAttacking == false) {
				attack = false;
				isAttacking = true;
			}
		}
		
		if(isAttacking) {
			attackFrames++;
			if(attackFrames == this.maxFramesAttack) {
				attackFrames = 0;
				isAttacking = false;
			}
		}
		
		collisionEnemy();
		
		Camera.x = Camera.clamp((int)x - Game.WIDTH / 2, 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp((int)y - Game.HEIGHT / 2, 0, World.HEIGHT * 16 - Game.HEIGHT);
		
		
	}
	
	public void collisionEnemy() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
				if(Entity.isColidding(this, e)) {
					if(delayDamage >= maxDelayDamage) {
						delayDamage = 0;
						life-= 1;
					}else {
						delayDamage++;
					}
					if(isAttacking) {
						((Enemy) e).vida--;
					}
				}
				
			}
		}
	}
	
	public void render(Graphics g){
		framesAnimation++;
		if(framesAnimation == maxFrames) {
			curSprite++;
			framesAnimation = 0;
			if(curSprite == maxSprite) {
				curSprite = 0;
			}
		}
		if(dir == 1) {
			sprite = Entity.PLAYER_SPRITE_RIGHT[1];
			if(isAttacking) {
				g.drawImage(ATTACK_RIGHT, this.getX() + 11 - Camera.x, this.getY() - Camera.y, null);
			}
		}else if(dir == -1) {
			sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];
			if(isAttacking) {
				g.drawImage(ATTACK_LEFT, this.getX() - 11 - Camera.x, this.getY() - Camera.y, null);
			}
		}
		super.render(g);
	}
	

	


}
