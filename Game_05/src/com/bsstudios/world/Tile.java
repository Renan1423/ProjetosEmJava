package com.bsstudios.world;

import java.awt.Graphics;

import java.awt.image.BufferedImage;

import com.bsstudios.main.Game;

public class Tile {
	
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(32,32,32,32);
	public static BufferedImage TILE_FLOORSUB = Game.spritesheet.getSprite(0,64,32,32);
	public static BufferedImage TILE_FLOORSUB2 = Game.spritesheet.getSprite(0,96,32,32);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(0,32,32,32);

	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x,int y,BufferedImage sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g){
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}

}
