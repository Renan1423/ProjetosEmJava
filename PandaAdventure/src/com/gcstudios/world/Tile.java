package com.gcstudios.world;

import java.awt.Graphics;

import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Tile {
	
	private Tile[][] floor;
	
	public static BufferedImage TILE_FLOOR = Game.TilesSpritesheet.getSprite(0,0,25,25);
	public static BufferedImage TILE_FLOORINTRO = Game.TilesSpritesheet.getSprite(0,50,25,25);
	public static BufferedImage TILE_WALL = Game.TilesSpritesheet.getSprite(25,0,25,25);

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
