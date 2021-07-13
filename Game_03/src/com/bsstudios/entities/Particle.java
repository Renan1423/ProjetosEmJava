package com.bsstudios.entities;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.bsstudios.main.Game;
import com.bsstudios.world.Camera;

public class Particle extends Entity{
	
	public int lifeTime = 15;
	public int curlife = 0;
	
	public int spd = 1;
	public double dx = 0;
	public double dy = 0;

	public Particle(int x, int y, int width, int height,double speed, BufferedImage sprite) {
		super(x, y, width, height,speed, sprite);
		
		dx = new Random().nextGaussian();
		dy = new Random().nextGaussian();
	}
	
	public void tick() {
		x += dx*spd;
		y += dy*spd;
		curlife++;
		if(lifeTime == curlife) {
			Game.entities.remove(this);
		}
	}
	
	public void render(Graphics g) {
		Color SlimeBlue = new Color(0, 255, 255);
		g.setColor(SlimeBlue);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 4, 4);
		//g.fillRect(this.getX(), this.getY(), 3, 3);
		
	}
}
