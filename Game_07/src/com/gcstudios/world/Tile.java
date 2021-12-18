package com.gcstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Tile {
	
	public static BufferedImage TILE_GRAMA = Game.spritesheet.getSprite(0,0,16,16);
	public static BufferedImage TILE_TERRA = Game.spritesheet.getSprite(16,0,16,16);
	public static BufferedImage TILE_AR = Game.spritesheet.getSprite(0, 16, 16, 16);
	public static BufferedImage TILE_NOITE = Game.spritesheet.getSprite(16, 16, 16, 16);
	public static BufferedImage TILE_SOLID = Game.spritesheet.getSprite(32, 0, 16, 16);
	public static BufferedImage TILE_AREIA = Game.spritesheet.getSprite(48, 0, 16, 16);
	public static BufferedImage TILE_NEVE = Game.spritesheet.getSprite(64, 0, 16, 16);
	
	public static BufferedImage TILE_TREE = Game.spritesheet.getSprite(64,16,16,16);
	public static BufferedImage TILE_LEAF = Game.spritesheet.getSprite(80,16,16,16);
	
	public static BufferedImage CRACK_1 = Game.spritesheet.getSprite(144, 0, 16, 16);
	public static BufferedImage CRACK_2 = Game.spritesheet.getSprite(144, 16, 16, 16);
	
	private BufferedImage sprite;
	protected int x,y;
	public int vida;
	public int maxVida;
	
	public int type;
	
	public boolean solid = false;
	
	public Tile(int x,int y,BufferedImage sprite, int vida, int type){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.vida = vida;
		maxVida = vida;
		this.type = type;
	}
	
	public void render(Graphics g){
		
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
		if(vida <= maxVida - 2) {
			g.drawImage(CRACK_2, x - Camera.x, y - Camera.y, null);
		}else if(vida == maxVida - 1) {
			g.drawImage(CRACK_1, x - Camera.x, y - Camera.y, null);
		}
		
	}
	
	/*
	 * TYPE 0: GRASS
	 * TYPE 1: GROUND
	 * TYPE 2: ROCK
	 * TYPE 3: SNOW
	 * TYPE 4: SAND*/
	

}
