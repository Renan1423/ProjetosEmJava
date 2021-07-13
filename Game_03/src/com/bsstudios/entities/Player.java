package com.bsstudios.entities;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bsstudios.graficos.UI;
import com.bsstudios.main.Game;
import com.bsstudios.world.Camera;
import com.bsstudios.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	
	public int lastDir = 1;
	
	public boolean moving = false;
	
	public boolean dead = false;
	
	private int index = 0, maxIndex = 1, frames = 0, maxFrames = 30;
	
	public BufferedImage sprite_left;
	public BufferedImage sprite_up;
	public BufferedImage sprite_down;
	
	public BufferedImage[] sprite_move_right;
	public BufferedImage[] sprite_move_left;
	public BufferedImage[] sprite_move_up;
	public BufferedImage[] sprite_move_down;

	public Player(int x, int y, int width, int height,double speed, BufferedImage sprite) {
		super(x, y, width, height,speed, sprite);
		sprite_left = Game.spritesheet.getSprite(32, 16, 16, 16);
		sprite_up = Game.spritesheet.getSprite(80, 0, 16, 16);
		sprite_down = Game.spritesheet.getSprite(80, 16, 16, 16);
		
		sprite_move_right = new BufferedImage[2];
		sprite_move_right[0] = Game.spritesheet.getSprite(48, 0, 16, 16);
		sprite_move_right[1] = Game.spritesheet.getSprite(64, 0, 16, 16);
		
		sprite_move_left = new BufferedImage[2];
		sprite_move_left[0] = Game.spritesheet.getSprite(64, 16, 16, 16);
		sprite_move_left[1] = Game.spritesheet.getSprite(48, 16, 16, 16);
		
		sprite_move_up = new BufferedImage[2];
		sprite_move_up[0] = Game.spritesheet.getSprite(96, 0, 16, 16);
		sprite_move_up[1] = Game.spritesheet.getSprite(96, 16, 16, 16);
		
		sprite_move_down = new BufferedImage[2];
		sprite_move_down[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
		sprite_move_down[1] = Game.spritesheet.getSprite(112, 0, 16, 16);
	}
	
	public void tick(){
		depth = 1;
		if(moving) {
			if(right && World.isFree((int)(x+speed),this.getY())) {
				x+=speed;
				lastDir = 1;
			}
			else if(left && World.isFree((int)(x-speed),this.getY())) {
				x-=speed;
				lastDir = -1;
			}
			if(up && World.isFree(this.getX(),(int)(y-speed))){
				y-=speed;
				lastDir = 2;
			}
			else if(down && World.isFree(this.getX(),(int)(y+speed))){
				y+=speed;
				lastDir = -2;
			}
			
			if(right && !World.isFree((int)(x+speed),this.getY())) {
				right = false;
				moving = false;
			}
			if(left && !World.isFree((int)(x-speed),this.getY())) {
				left = false;
				moving = false;
			}
			if(up && !World.isFree(this.getX(),(int)(y-speed))) {
				up = false;
				moving = false;
			}
			if(down && !World.isFree(this.getX(),(int)(y+speed))) {
				down = false;
				moving = false;
			}
		}
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex)
				index = 0;
		}
		
		
		
		CheckItemCollision();
		
		if(Game.itens_contagem == Game.itens_atual) {
			Game.world.levelatual++;
			World.restartGame();
		}
		
		if(dead) {
			Game.entities.remove(this);
		}
		
	}
	
	public void CheckItemCollision() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Item) {
				if(Entity.isColidding(this, current)) {
					Game.itens_atual++;
					Game.entities.remove(i);
					return;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(lastDir == 1 && moving == false) {
			
			super.render(g);
			
		}else if(lastDir == -1 && moving == false) {
			
			g.drawImage(sprite_left,this.getX() - Camera.x,this.getY() - Camera.y,null);
			
		}else if(lastDir == 2 && moving == false) {
			
			g.drawImage(sprite_up,this.getX() - Camera.x,this.getY() - Camera.y,null);
			
		}else if(lastDir == -2 && moving == false) {
			
			g.drawImage(sprite_down,this.getX() - Camera.x,this.getY() - Camera.y,null);
			
		}else if(lastDir == 1 && moving == true) {
			
			g.drawImage(sprite_move_right[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			
		}else if(lastDir == -1 && moving == true) {
			
			g.drawImage(sprite_move_left[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			
		}else if(lastDir == 2 && moving == true) {
			
			g.drawImage(sprite_move_up[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			
		}else if(lastDir == -2 && moving == true) {
			
			g.drawImage(sprite_move_down[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			
		}
		
		if(Holder.isCollidingHolder) {
			g.setColor(Color.red);
			g.fillRect(this.getX() + 7, this.getY() - 6, 2, 8); //em cima
			g.fillRect(this.getX() + 7, this.getY() + 14, 2, 8); //em baixo
			g.fillRect(this.getX() + 14, this.getY() + 7, 8, 2); //direita
			g.fillRect(this.getX() - 6, this.getY() + 7, 8, 2); //esquerda
		}
	}

}
