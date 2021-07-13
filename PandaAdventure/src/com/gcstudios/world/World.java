package com.gcstudios.world;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.gcstudios.entities.Bullet;
import com.gcstudios.entities.Enemy;
import com.gcstudios.entities.Enemy2;
import com.gcstudios.entities.Entity;
import com.gcstudios.entities.Flor;
import com.gcstudios.entities.Lifepack;
import com.gcstudios.entities.Npc;
import com.gcstudios.entities.Player;
import com.gcstudios.entities.Weapon;
import com.gcstudios.graficos.Spritesheet;
import com.gcstudios.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 25;
	
	
	public World(String path){
		/*
			Game.player.setX(0);
			Game.player.setY(0);
			WIDTH = 25;
			HEIGHT = 25;
			tiles = new Tile[WIDTH*HEIGHT];
			
			
			for(int xx = 0; xx < WIDTH; xx++) {
				for(int yy = 0; yy < HEIGHT; yy++) {
					tiles[xx + (yy*WIDTH)] = new WallTile(xx*25,yy*25,Tile.TILE_WALL);
				}
			}
			
			int dir = 0;
			int xx = 0, yy = 0;
			
			
			
			for(int i = 0; i < 200; i++){
				tiles[xx + (yy*WIDTH)] = new FloorTile(xx*25,yy*25,Tile.TILE_FLOOR);
				if(dir == 0) {
					//direita
					if(xx < WIDTH) {
						xx++;
					}
				}else if(dir == 1) {
					//esquerda
					if(xx > 0) {
						xx--;
					}
				}else if(dir == 2) {
					if(yy < HEIGHT) {
						yy++;
					}
				}else if(dir == 3) {
					//cima
					if(yy > 0) {
						yy--;
					}
				}
				
				
				if(Game.rand.nextInt(100) < 30) {
					dir = Game.rand.nextInt(4);
				}
			}
			*/
			try {
				BufferedImage map = ImageIO.read(getClass().getResource(path));
				int[] pixels = new int[map.getWidth() * map.getHeight()];
				WIDTH = map.getWidth();
				HEIGHT = map.getHeight();
				tiles = new Tile[map.getWidth() * map.getHeight()];
				map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels, 0, map.getWidth());
				for(int xx = 0; xx < map.getWidth(); xx++){
					for(int yy = 0; yy < map.getHeight(); yy++){
						int pixelAtual = pixels[xx + (yy * map.getWidth())];
						if(Game.CUR_LEVEL == 1) {
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx*25,yy*25,Tile.TILE_FLOORINTRO);
						}else {
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx*25,yy*25,Tile.TILE_FLOOR);
						}
						if(pixelAtual == 0xFF000000){
							//Floor
							if(Game.CUR_LEVEL == 1) {
								tiles[xx + (yy * WIDTH)] = new FloorTile(xx*25,yy*25,Tile.TILE_FLOORINTRO);
							}
							else {
								tiles[xx + (yy * WIDTH)] = new FloorTile(xx*25,yy*25,Tile.TILE_FLOOR);
							}
						}else if(pixelAtual == 0xFFFFFFFF){
							//Parede
							tiles[xx + (yy * WIDTH)] = new WallTile(xx*25,yy*25,Tile.TILE_WALL);
						}else if(pixelAtual == 0xFF0026FF) {
							//Player
							Game.player.setX(xx*25);
							Game.player.setY(yy*25);
						}else if(pixelAtual == 0xFFFF0000) {
							//Enemy
							BufferedImage[] buf = new BufferedImage[2];
							buf[0] = Game.Enemyspritesheet.getSprite(112,25,25,25);
							buf[1] = Game.Enemyspritesheet.getSprite(112+25,25,25,25);
							Enemy en = new Enemy(xx*25,yy*25,25,25,buf);
							Game.entities.add(en);
							Game.enemies.add(en);
							
						}else if(pixelAtual == 0xFF0094FF) {
							//Enemy2
							BufferedImage[] buf = new BufferedImage[2];
							buf[0] = Game.Enemyspritesheet.getSprite(112,25,25,25);
							buf[1] = Game.Enemyspritesheet.getSprite(112+25,25,25,25);
							Enemy2 en = new Enemy2(xx*25,yy*25,25,25,buf);
							Game.entities.add(en);
							Game.enemies2.add(en);
							
						}else if(pixelAtual == 0xFF007F46) {
							//NPC
							BufferedImage buf;
							buf = Game.CoalaSpritesheet.getSprite(0,0,25,25);
							Npc n = new Npc(xx*25,yy*25,25,25,buf);
							Game.entities.add(n);
							Game.NPc.add(n);
						}
						else if(pixelAtual == 0XFF00FF21) {
							//Life Pack
							Lifepack pack = new Lifepack(xx*25,yy*25,25,25,Entity.LIFEPACK_EN);
							Game.entities.add(pack);
						}else if(pixelAtual == 0xFF00FFFF){
							//Bullet
							Game.entities.add(new Bullet(xx*25,yy*25,25,25,Entity.BULLET_EN));
						}else if(pixelAtual == 0xFF808080) {
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx*25,yy*25,Entity.FLOR);
						}
						
							
							
						
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void renderMiniMap() {
		for(int i = 0; i < Game.minimapaPixels.length; i++) {
			
			Game.minimapaPixels[i] = 0x00000000;
		}
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				if(tiles[xx + (yy * WIDTH)] instanceof WallTile) {
					Game.minimapaPixels[xx + (yy*WIDTH)] = 0xA9A9A9;
				} //Enemy(xx*25,yy*25,Entity.ENEMY_EN)
			}
		}
		for(int i = 0; i < Game.enemies.size(); i++){
		       Enemy en = Game.enemies.get(i);
		       int enX = en.getX()/25;
		       int enY = en.getY()/25;
		      
		       Game.minimapaPixels[enX + (enY*WIDTH)] = 0xff0000;
		}
		for(int i = 0; i < Game.enemies2.size(); i++){
		       Enemy2 en2 = Game.enemies2.get(i);
		       int enX = en2.getX()/25;
		       int enY = en2.getY()/25;
		      
		       Game.minimapaPixels[enX + (enY*WIDTH)] = 0xff0000;
		}
		for(int i = 0; i < Game.NPc.size(); i++){
			   Npc n = Game.NPc.get(i);
		       int enX = n.getX()/25;
		       int enY = n.getY()/25;
		      
		       Game.minimapaPixels[enX + (enY*WIDTH)] = 0x007F46;
		}
		
		int xPlayer = Game.player.getX()/25;
		int yPlayer = Game.player.getY()/25;
		
		Game.minimapaPixels[xPlayer + (yPlayer*WIDTH)] = 0x0000ff;
 	}
	
	public static boolean isFree(int xnext,int ynext){
		
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		if(!((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile))) {
			return true;
		}
			return false;
	}
	
	public static void restartGame(String level){
		Game.entities.clear();
		Game.enemies.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.Playerspritesheet = new Spritesheet("/PandaSpritesheet.png");
		Game.Enemyspritesheet = new Spritesheet("/EnemySpritesheet.png");
		Game.Itensspritesheet = new Spritesheet("/ItensSpritesheet.png");
		Game.TilesSpritesheet = new Spritesheet("/TilesSpritesheet.png");
		Game.player = new Player(0,0,25,25,Game.Playerspritesheet.getSprite(0, 0,25,25));
		Game.entities.add(Game.player);
		Game.world = new World("/"+level);
		return;
	}
	
	public void render(Graphics g){
		int xstart = Camera.x / 25; //>> 5;
		int ystart = Camera.y / 25; //>> 5;
		
		int xfinal = xstart + (Game.WIDTH / 25) + 5;
		int yfinal = ystart + (Game.HEIGHT / 25) + 5;
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
}
