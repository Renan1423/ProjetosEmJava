package com.bsstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bsstudios.world.World;

public class Enemy extends Entity{
	
	public boolean right = true,left = false;
	
	private double gravity = 2;
	
	public int vida = 3;

	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		depth = 1;
		
		if(World.isFree((int)x, (int)(y + gravity))) {
			y+=gravity;
		}
		
		if(right && World.isFree((int)(x+speed), (int)y)) {
			x+=speed;
			if(World.isFree((int)x+32, (int)y + 1)) {
				right = false;
				left = true;
			}
		}else {
			right = false;
			left = true;
		}
			
		if(left && World.isFree((int)(x-speed), (int)y)) {
				x-=speed;
			if(World.isFree((int)x-32, (int)y + 1)) {
				right = true;
				left = false;
			}
		}
	}
	
	public void render(Graphics g) {
		if(right) {
			sprite = Entity.ENEMY1_RIGHT;
		}else if(left) {
			sprite = Entity.ENEMY1_LEFT;
		}
		super.render(g);
	}

}
