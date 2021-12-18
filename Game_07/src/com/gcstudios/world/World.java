package com.gcstudios.world;

import java.awt.Graphics;


//Create by Renan Nunes

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import com.gcstudios.entities.Enemy;
import com.gcstudios.entities.Entity;
import com.gcstudios.entities.Player;
import com.gcstudios.graficos.UI;
import com.gcstudios.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public static int dia = 0;
	public static int noite = 1;
	public static int CICLO = Entity.rand.nextInt(2);
	
	
	//RESTARITING THE FOR LOOPING
	
	private boolean forRestarted = false;
	private int numTree = 0;
	private int numWood = 3; //Quantity of wood in each tree
	
	public World(){
		String[] tilesTypes = {"grama","terra","areia","neve"};
		WIDTH = 1000;
		HEIGHT = 100;
		
		//Divisor do mapa
		int divisao = WIDTH/tilesTypes.length;
		
		tiles = new Tile[WIDTH * HEIGHT];
		for(int xx = 0; xx < WIDTH; xx++) {
			
			int initialHeight = Entity.rand.nextInt(20 - 16) + 16;
			
			for(int yy = 0; yy < HEIGHT; yy++) {
				
				if(yy == HEIGHT - 1 || xx == WIDTH - 1 || xx == 0 || yy == 0) {
					tiles[xx+yy * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_SOLID,10000,2);
					tiles[xx+yy * WIDTH].solid = true;
				}else {
					if(yy >= initialHeight) {
						int indexBioma = xx / divisao;
						if(tilesTypes[indexBioma] == "grama") {
							tiles[xx+yy * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_GRAMA,3,0);
							
							//Spawn Ground
							
							if(tiles[xx+ (yy-1) * WIDTH] instanceof WallTile && yy - 1 >= 0 && tiles[xx+ (yy-1) * WIDTH].type != 5) {
								tiles[xx+yy * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_TERRA,3,1);
							}
							
							//Spawn Trees
							
							int CreateTree = Entity.rand.nextInt(100);
							if(tiles[xx + (yy-1) * WIDTH] instanceof FloorTile && CreateTree <= 10) {
								tiles[xx + (yy) * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_TREE,3,5);
								numTree++;
								//createTree(xx,yy);
							}
							
							//Spawn Rock
							
							if(yy > HEIGHT - 50) {
								tiles[xx+yy * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_SOLID,6,2);
							}
						}else if(tilesTypes[indexBioma] == "terra") {
							tiles[xx+yy * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_TERRA,3,1);
						}else if(tilesTypes[indexBioma] == "areia") {
							tiles[xx+yy * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_AREIA,2,4);
						}else if(tilesTypes[indexBioma] == "neve") {
							tiles[xx+yy * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_NEVE,2,3);
						}
						else {
							tiles[xx+yy * WIDTH] = new FloorTile(xx*16,yy*16,Tile.TILE_AR,1,10);
						}
						
						/*
						if(xx > 80) {
							tiles[xx+yy * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_GRAMA);
						}else if(xx < 130) {
							tiles[xx+yy * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_TERRA);
						}else if(xx < 170) {
							tiles[xx+yy * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_AREIA);
						}else {
							tiles[xx+yy * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_NEVE);
						}
						*/
					}else {
						tiles[xx+yy * WIDTH] = new FloorTile(xx*16,yy*16,Tile.TILE_AR,1,10);
					}
					
				}
				if(yy == HEIGHT - 1 && forRestarted == false) {
					forRestarted = true;
				}
			}
		}
		
		
		//Remaking the for's and Creating tree
		
		if(forRestarted) {
			int WoodCount = numTree * numWood;
			
			if(WoodCount > 0) {
				for(int xx = 0; xx < WIDTH - 1; xx++) {
					
					int initialHeight = Entity.rand.nextInt(10 - 5) + 5;
					
					for(int yy = 0; yy < HEIGHT - 1; yy++) {
						if(yy >= initialHeight) {
							if(tiles[xx + (yy+1) * WIDTH].type == 5) {
								tiles[xx + (yy) * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_TREE,3,5);
								WoodCount--;
								//createTree(xx,yy);
							}
						}
						if(yy == HEIGHT - 2 || xx == WIDTH - 2) {
							if(WoodCount > 0) {
								for(int xxx = 0; xxx < WIDTH - 1; xxx++) {
									
									for(int yyy = 0; yyy < HEIGHT - 1; yyy++) {
										if(yyy >= initialHeight) {
											if(tiles[xxx + (yyy+1) * WIDTH].type == 5) {
												tiles[xxx + (yyy) * WIDTH] = new WallTile(xxx*16,yyy*16,Tile.TILE_TREE,3,5);
												WoodCount--;
												//createTree(xx,yy);
											}
										}
										if(yyy == HEIGHT - 2 || xxx == WIDTH - 2) {
											if(WoodCount > 0) {
													/*for(int xxxx = 0; xxxx < WIDTH - 1; xxxx++) {
														for(int yyyy = 0; yyyy < HEIGHT - 1; yyyy++) {
															if(yyyy >= initialHeight) {
																if(tiles[xxxx + (yyyy+1) * WIDTH].type == 5) {
																	tiles[xxxx + (yyyy) * WIDTH] = new WallTile(xxxx*16,yyyy*16,Tile.TILE_LEAF,2,5);
																	WoodCount--;
																	//createTree(xx,yy);
																}
															}
															if(yyyy == HEIGHT - 2 || xxxx == WIDTH - 2) {
																if(WoodCount > 0) {
																	
																}
															}
														}
													}*/
											}
										}
									}
								}
							}
						}
					}
				}
			}else {
				//All trees are completed!!!
			}
		}
		
		
		
	}
	
	public void createTree(int xx, int yy) {
		
		//wood
		tiles[xx+(yy-1) * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_TREE,4,6);
		tiles[xx+(yy-2) * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_TREE,4,6);
		tiles[xx+(yy-3) * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_TREE,4,6);
		
		//leaf
		
		tiles[xx+(yy-4) * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_LEAF,4,6);
		tiles[xx+(yy-5) * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_LEAF,4,6);
		tiles[xx+(yy-6) * WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_LEAF,4,6);
		
		
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
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}
	
	public void render(Graphics g){
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
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
