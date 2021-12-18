package com.gcstudios.world;

import java.awt.Graphics;
//Created by Renan Nunes
import java.awt.image.BufferedImage;

public class FloorTile extends Tile{

	public FloorTile(int x, int y, BufferedImage sprite, int vida, int type) {
		super(x, y, sprite, vida,type);
	}
	
	public void render(Graphics g) {
		if(World.CICLO == World.dia) {
			g.drawImage(Tile.TILE_AR, x - Camera.x, y - Camera.y, null);
		}else if(World.CICLO == World.noite) {
			g.drawImage(Tile.TILE_NOITE, x - Camera.x, y - Camera.y, null);
		}
	}

}
