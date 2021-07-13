package com.bsstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.bsstudios.main.Game;
import com.bsstudios.world.World;

public class Player extends Entity{

	public boolean isPressed = false;
	
	private int rotation = 20;

	public Player(int x, int y, int width, int height,double speed, BufferedImage sprite) {
		super(x, y, width, height,speed, sprite);
	}
	
	public void tick(){
		depth = 2;
		if(!isPressed) {
			y+=2;
			if(rotation <= 88) {
				rotation+=2;
			}
		}else {
			rotation = 20;
			if(y > 0) {
				y-=2;
			}
		}
		
		if(y > Game.HEIGHT) {
			World.restartGame();
		}
	
	for(int i = 0; i < Game.entities.size(); i++) {
		Entity e = Game.entities.get(i);
		if(e != this) {
			if(Entity.isColidding(this, e)) {
				World.restartGame();
				return;
			}
		}
	}
	}
	
	public void render(Graphics g) {
		//super.render(g);
		Graphics2D g2 = (Graphics2D) g;
		if(!isPressed) {
			g2.rotate(Math.toRadians(rotation),this.getX() + width/2,this.getY() + height/2);
			g2.drawImage(sprite, this.getX(), this.getY(), null);
			g2.rotate(Math.toRadians(-rotation),this.getX() + width/2,this.getY() + height/2);
		}
		else {
			super.render(g);
		}
	}
}
