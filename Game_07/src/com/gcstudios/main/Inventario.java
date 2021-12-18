package com.gcstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.gcstudios.graficos.UI;
import com.gcstudios.world.Camera;
import com.gcstudios.world.FloorTile;
import com.gcstudios.world.Tile;
import com.gcstudios.world.WallTile;
import com.gcstudios.world.World;

public class Inventario {
	
	public int selected = 0;
	public boolean isPressed = false;
	public int mx,my;
	public Rectangle mouseRectangle;
	
	public boolean isPlaceItem = false;
	public int inventoryBoxSize = 40;
	
	public String[] itens = {"grama","terra","rocha","neve","areia","madeira","","ar"};
	
	public int[] numItens = {0,0,0,0,0,0,0,0};
	
	public int initialPosition = ((Game.WIDTH * Game.SCALE) / 2) - ((itens.length * inventoryBoxSize) / 2);
	
	public void tick() {
		if(isPressed) {
			isPressed = false;
			if(mx >= initialPosition && mx < initialPosition + (inventoryBoxSize * itens.length)) {
				if(my >= 10 && my < 10 + inventoryBoxSize) {
					selected = (int)(mx - initialPosition)/inventoryBoxSize;
				}
			}
			
			if(Game.ui.CraftOpen && numItens[selected] != 0) {
				mouseRectangle = new Rectangle((int)(mx - 0.5),(int)(my - 0.5),1,1);
				Rectangle ResultItem = new Rectangle((Game.WIDTH/2 * Game.SCALE) + 75, Game.HEIGHT/2 * Game.SCALE - 90, 32, 32);
				if(mouseRectangle.intersects(ResultItem)) {
					//Collecting new item
					numItens[Game.ui.ResultItemType]++;
					
					//Removing crafting itens
					
					for(int i = 0; i < Game.ui.Itens.length; i++) {
						if(Game.ui.Itens[i] > 0) {
							numItens[i]--;
							Game.ui.Itens[i] = 0;
							Game.ui.craftArray[i] = "";
							Game.ui.ResultItemType = 10;
						}
					}
				}else {
					for(int i = 0; i < Game.ui.craftArray.length; i++) {
						if(Game.ui.craftArray[i] == "" && itens[selected] != "ar") {
							Game.ui.craftArray[i] = itens[selected];
							Game.ui.Itens[selected]++;
							break;
						}else {
							continue;
						}
					}
				}
			}
		}
		
		if(isPlaceItem) {
			isPlaceItem = false;
			
			mx = (int)mx/3 + Camera.x;
			my = (int)my/3 + Camera.y;
			
			int tilex = mx/16;
			int tiley = my/16;
			if(World.tiles[tilex + tiley * World.WIDTH].solid == false) {
				
				if(itens[selected] == "grama" && numItens[0] > 0) {
					World.tiles[tilex + tiley * World.WIDTH] = new WallTile(tilex*16,tiley*16,Tile.TILE_GRAMA,3,0);
					numItens[0]--;
				}else if(itens[selected] == "terra" && numItens[1] > 0) {
					World.tiles[tilex + tiley * World.WIDTH] = new WallTile(tilex*16,tiley*16,Tile.TILE_TERRA,3,1);
					numItens[1]--;
				}else if(itens[selected] == "ar") {
					World.tiles[tilex + tiley * World.WIDTH].vida--;
					if(World.tiles[tilex + tiley * World.WIDTH].vida <= 0) {
						if(World.tiles[tilex + tiley * World.WIDTH].type != 10) {
							numItens[World.tiles[tilex + tiley * World.WIDTH].type]++;
						}
						World.tiles[tilex + tiley * World.WIDTH] = new FloorTile(tilex*16,tiley*16,Tile.TILE_AR,1,10);
					}
				}else if(itens[selected] == "neve" && numItens[3] > 0) {
					World.tiles[tilex + tiley * World.WIDTH] = new WallTile(tilex*16,tiley*16,Tile.TILE_NEVE,2,3);
					numItens[3]--;
				}else if(itens[selected] == "areia" && numItens[4] > 0) {
					World.tiles[tilex + tiley * World.WIDTH] = new WallTile(tilex*16,tiley*16,Tile.TILE_AREIA,2,4);
					numItens[4]--;
				}
				else if(itens[selected] == "rocha" && numItens[2] > 0) {
					World.tiles[tilex + tiley * World.WIDTH] = new WallTile(tilex*16,tiley*16,Tile.TILE_SOLID,5,2);
					numItens[2]--;
				}
				else if(itens[selected] == "madeira" && numItens[5] > 0) {
					World.tiles[tilex + tiley * World.WIDTH] = new WallTile(tilex*16,tiley*16,Tile.TILE_TREE,5,5);
					numItens[5]--;
				}
				else {
					World.tiles[tilex + tiley * World.WIDTH].vida--;
					if(World.tiles[tilex + tiley * World.WIDTH].vida <= 0) {
						if(World.tiles[tilex + tiley * World.WIDTH].type != 10) {
							numItens[World.tiles[tilex + tiley * World.WIDTH].type]++;
						}
						World.tiles[tilex + tiley * World.WIDTH] = new FloorTile(tilex*16,tiley*16,Tile.TILE_AR,1,10);
					}
				}
				
				if(World.isFree(Game.player.getX(), Game.player.getY()) == false) {
					World.tiles[tilex + tiley * World.WIDTH] = new FloorTile(tilex*16,tiley*16,Tile.TILE_AR,1,10);
					
				}
			}
		}
	}
	
	public void render(Graphics g) {		
		
		//Inventory UI
		
		for(int i = 0; i < itens.length; i++) {
			
			g.setColor(Color.gray);
			g.fillRect((int)(initialPosition + (i * inventoryBoxSize)) + 1, 0 + (10), inventoryBoxSize, inventoryBoxSize);
			g.setColor(Color.darkGray);
			g.drawRect((int)(initialPosition + (i * inventoryBoxSize)) + 1, 0 + (10), inventoryBoxSize, inventoryBoxSize);
		
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD,19));
			
			if(itens[i] == "grama" && numItens[i] > 0) {
				g.drawImage(Tile.TILE_GRAMA,(int)(initialPosition + (i * inventoryBoxSize)) + 4, 0 + (13), 32, 32, null);
				g.drawString("" + numItens[0], (int)initialPosition + (i * inventoryBoxSize) + inventoryBoxSize - 20, inventoryBoxSize + 6);
			}else if(itens[i] == "terra" && numItens[i] > 0) {
				g.drawImage(Tile.TILE_TERRA,(int)(initialPosition + (i * inventoryBoxSize)) + 4, 0 + (13), 32, 32, null);
				g.drawString("" + numItens[1], (int)initialPosition + (i * inventoryBoxSize) + inventoryBoxSize - 20, inventoryBoxSize + 6);
			}
			else if(itens[i] == "ar") {
				g.drawImage(Tile.TILE_AR,(int)(initialPosition + (i * inventoryBoxSize)) + 4, 0 + (13), 32, 32, null);
			}else if(itens[i] == "neve" && numItens[i] > 0) {
				g.drawImage(Tile.TILE_NEVE,(int)(initialPosition + (i * inventoryBoxSize)) + 4, 0 + (13), 32, 32, null);
				g.drawString("" + numItens[3], (int)initialPosition + (i * inventoryBoxSize) + inventoryBoxSize - 20, inventoryBoxSize + 6);
			}else if(itens[i] == "areia" && numItens[i] > 0) {
				g.drawImage(Tile.TILE_AREIA,(int)(initialPosition + (i * inventoryBoxSize)) + 4, 0 + (13), 32, 32, null);
				g.drawString("" + numItens[4], (int)initialPosition + (i * inventoryBoxSize) + inventoryBoxSize - 20, inventoryBoxSize + 6);
			}else if(itens[i] == "rocha" && numItens[i] > 0) {
				g.drawImage(Tile.TILE_SOLID,(int)(initialPosition + (i * inventoryBoxSize)) + 4, 0 + (13), 32, 32, null);
				g.drawString("" + numItens[2], (int)initialPosition + (i * inventoryBoxSize) + inventoryBoxSize - 20, inventoryBoxSize + 6);
			}else if(itens[i] == "madeira" && numItens[i] > 0) {
				g.drawImage(Tile.TILE_TREE,(int)(initialPosition + (i * inventoryBoxSize)) + 4, 0 + (13), 32, 32, null);
				g.drawString("" + numItens[5], (int)initialPosition + (i * inventoryBoxSize) + inventoryBoxSize - 20, inventoryBoxSize + 6);
			}
			
			if(selected == i) {
				g.setColor(Color.white);
				g.drawRect((int)(initialPosition + (i * inventoryBoxSize)) , 0 + (10), inventoryBoxSize, inventoryBoxSize);
			}
		}
	}
}
