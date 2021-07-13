package com.bsstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bsstudios.main.Game;

public class Tubo extends Entity{

	public Tubo(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}

	public void tick() {
		if(Game.score <= 30) {
			x--;
		}
		else if(Game.score <= 70 && Game.score > 20) {
			x-=2;
		}
		else if(Game.score > 70 ) {
			x-=3;
		}
		if(x + width <= 0) {
			Game.score+=1;
			Game.entities.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		if(sprite != null) {
			g.drawImage(sprite, this.getX(), this.getY(), width, height, null);
		}
		else {
			g.setColor(Color.green);
			g.fillRect((int)x,(int)y, width, height);
		}
	}
}
