package com.gcstudios.graficos;


import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.gcstudios.entities.Player;
import com.gcstudios.main.Game;
import com.gcstudios.world.Tile;
import com.gcstudios.world.World;

public class UI {

	public static int seconds = 0;
	public static int minutes = 0;
	public static int frames = 0;
	
	public static BufferedImage HEART = Game.spritesheet.getSprite(112,0,16,16);
	public static BufferedImage HUNGRYHEART = Game.spritesheet.getSprite(128, 0, 16, 16);
	
	public static boolean CraftOpen = false; 
	public BufferedImage craftingUI;
	
	public static String[] craftArray = {"","","","","","","","",""};
	
	private int InventoryVerticalPosDifference;
	private int InventoryHorizontalPosDifference;
	
	//checking the number of itens
	
	public static int[] Itens = {0,0,0,0,0,0,0,0,0};
	
	public static int ResultItemType;
	
	public void tick() {
		try {
			craftingUI = ImageIO.read(getClass().getResource("/Crafting_Table_GUI.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		frames++;
		if(frames == 60) {
			frames = 0;
			seconds++;
			if(Game.player.fome <= 1) {
				Game.player.life-=0.25;
			}
			if(seconds == 60) {
				seconds = 0;
				minutes++;
				
				// operador lógico % usa o resto da divisão
				
				if(minutes % 2 == 0) {
					World.CICLO++;
					if(World.CICLO > World.noite) {
						World.CICLO = 0;
					}
				}
				
				Game.player.fome -= 0.5;
				
			}
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < (int)(Game.player.life); i++) {
			g.drawImage(HEART,Game.inventario.initialPosition + (i*20),60,24,24,null);
		}
		
		for(int i = 0; i < (int)(Game.player.fome); i++) {
			g.drawImage(HUNGRYHEART,Game.inventario.initialPosition + 216 + (i*20),60,24,24,null);
		}
		
		/*
		int curLife = (int)((Game.player.life/100) * 200);
		g.setColor(Color.red);
		g.fillRect(Game.WIDTH * Game.SCALE - 220,Game.HEIGHT * Game.SCALE - 55, 200, 30);
		g.setColor(Color.green);
		g.fillRect(Game.WIDTH * Game.SCALE - 220,Game.HEIGHT * Game.SCALE - 55, curLife, 30);
		g.setColor((Color.white));
		g.setFont(new Font("arial", Font.BOLD,18));
		g.drawString((int)Game.player.life +"/" + "100", Game.WIDTH * Game.SCALE - 150, Game.HEIGHT * Game.SCALE - 32);
		 */
		
		String formatTime = "";
		if(minutes < 10) {
			formatTime+="0"+minutes+":";
		}else {
			formatTime+=minutes+":";
		}
		
		if(seconds < 10) {
			formatTime+="0"+seconds;
		}else{
			formatTime+=seconds;
		}
		g.setColor(Color.white);
		g.fillRect(26, 15, 70, 30);
		g.setColor(Color.darkGray);
		g.setFont(new Font("arial", Font.BOLD,24));
		g.drawString(formatTime, 30, 40);
		
		
		if(CraftOpen) {
			g.drawImage(craftingUI,Game.WIDTH/2 + (craftingUI.getWidth()/4) - 20,Game.HEIGHT/2, null);
			
			//Inputing crafting itens
			
			for(int i = 0; i < craftArray.length; i++) {
				
				//Verifying the blocks position inside the crafting menu
				
				if(i < 3) {
					InventoryVerticalPosDifference = 0;
				}else if(i >= 3 && i < 6){
					InventoryVerticalPosDifference = 36;
				}else {
					InventoryVerticalPosDifference = 72;
				}
				
				if(i < 3) {
					InventoryHorizontalPosDifference = i;
				}else if(i >= 3 && i < 6){
					InventoryHorizontalPosDifference = i - 3;
				}else {
					InventoryHorizontalPosDifference = i - 6;
				}
				
				//drawing the blocks inside the crafting menu
				
				if(craftArray[i] == "grama") {
					g.drawImage(Tile.TILE_GRAMA,(Game.WIDTH/2 * Game.SCALE) - 109 + (InventoryHorizontalPosDifference * 36), Game.HEIGHT/2 * Game.SCALE - 123 + (InventoryVerticalPosDifference), 25, 25, null);
				}else if(craftArray[i] == "terra") {
					g.drawImage(Tile.TILE_TERRA,(Game.WIDTH/2 * Game.SCALE) - 109 + (InventoryHorizontalPosDifference * 36), Game.HEIGHT/2 * Game.SCALE - 123 + (InventoryVerticalPosDifference), 25, 25, null);
				}else if(craftArray[i] == "rocha") {
					g.drawImage(Tile.TILE_SOLID,(Game.WIDTH/2 * Game.SCALE) - 109 + (InventoryHorizontalPosDifference * 36), Game.HEIGHT/2 * Game.SCALE - 123 + (InventoryVerticalPosDifference), 25, 25, null);
				}else if(craftArray[i] == "neve") {
					g.drawImage(Tile.TILE_NEVE,(Game.WIDTH/2 * Game.SCALE) - 109 + (InventoryHorizontalPosDifference * 36), Game.HEIGHT/2 * Game.SCALE - 123 + (InventoryVerticalPosDifference), 25, 25, null);
				}else if(craftArray[i] == "areia") {
					g.drawImage(Tile.TILE_AREIA,(Game.WIDTH/2 * Game.SCALE) - 109 + (InventoryHorizontalPosDifference * 36), Game.HEIGHT/2 * Game.SCALE - 123 + (InventoryVerticalPosDifference), 25, 25, null);
				}else if(craftArray[i] == "madeira") {
					g.drawImage(Tile.TILE_TREE,(Game.WIDTH/2 * Game.SCALE) - 109 + (InventoryHorizontalPosDifference * 36), Game.HEIGHT/2 * Game.SCALE - 123 + (InventoryVerticalPosDifference), 25, 25, null);
				}
			}
			
			//Creating a new item!
			
			//Choosing the ingredients
				
			if(Itens[0] == 1 && Itens[1] == 1) {
				g.drawImage(Tile.TILE_SOLID,(Game.WIDTH/2 * Game.SCALE) + 75, Game.HEIGHT/2 * Game.SCALE - 90, 32, 32, null);
				
				ResultItemType = 2;
				//ResultItem = new Rectangle((Game.WIDTH/2 * Game.SCALE) + 75, Game.HEIGHT/2 * Game.SCALE - 90, 32, 32);
			}
			
		}
		
		
	}
	
}
