package com.bsstudios.entities;

import java.awt.Rectangle;

import java.awt.image.BufferedImage;

import com.bsstudios.main.Game;

public class Holder extends Entity{
	
	public static boolean isCollidingHolder = false;

	public Holder(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

	}
	
	public void tick(){
		if(CheckHolderCollision()) {
			Game.player.setX(this.getX());
			Game.player.setY(this.getY());
			Game.player.moving = false;
			isCollidingHolder = true;
		}
	}
	
	public boolean CheckHolderCollision() {
		/*for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Holder) {
				if(Entity.isColidding(this, current)) {
					moving = false;
					return;
				}
			}
		}*/
		Rectangle current = new Rectangle(this.getX() + 8,this.getY(),1,1);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),15,15);
		
		return current.intersects(player);
	}
}
