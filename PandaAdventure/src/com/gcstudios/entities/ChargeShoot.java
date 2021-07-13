package com.gcstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;

public class ChargeShoot extends Entity{
	
	private BufferedImage[] MagicLevel1;
	private BufferedImage[] MagicLevel2;
	
	private BufferedImage[] MagicLevel1Ice;
	private BufferedImage MagicLevel2Ice;
	
	private BufferedImage[] MagicLevel1Thunder;
	private BufferedImage MagicLevel2Thunder;
	
	private int indexCharge = 0, indexMaxCharge = 2;
	
	private int frames = 0,maxFrames = 15;
	
	public static int Charging = 0;
	
	public static boolean Charged = false;
	
	public ChargeShoot(int x, int y, int width, int height, BufferedImage sprite,double dx,double dy) {
		super(x, y, width, height, sprite);
		
		MagicLevel1 = new BufferedImage[4];
		MagicLevel2 = new BufferedImage[4];
		
		MagicLevel1Ice = new BufferedImage[4];
		MagicLevel2Ice = Game.Playerspritesheet.getSprite(200, 125,25,25);
		
		MagicLevel1Thunder = new BufferedImage[4];
		MagicLevel2Thunder = Game.Playerspritesheet.getSprite(225, 125,25,25);
		
		for(int i = 0; i < 3; i++){
			MagicLevel1[i] = Game.Playerspritesheet.getSprite(75 + (i*25), 225, 25, 25);
		}
		for(int i = 0; i < 3; i++){
			MagicLevel2[i] = Game.Playerspritesheet.getSprite(150 + (i*25), 225, 25, 25);
		}
		for(int i = 0; i < 3; i++){
			MagicLevel1Ice[i] = Game.Playerspritesheet.getSprite(125 + (i*25), 50, 25, 25);
		}
		for(int i = 0; i < 3; i++){
			MagicLevel1Thunder[i] = Game.Playerspritesheet.getSprite(175 + (i*25), 150, 25, 25);
		}
	}
	
	public void tick() {
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			indexCharge++;
			if(indexCharge > indexMaxCharge)
				indexCharge = 0;
		}
			Charging++; //Determina o nível da magia
			if(Charging > 100) {
				Charged = true;
			}
		if(!Game.player.isCharging) {
			Game.chargebullets.remove(this);
			Game.chargebullets.clear();
			Charging = 0;
			Charged = false;
			return;
		}
		
	}
	
	public void render(Graphics g){
		if(Game.player.isCharging && Charging < 100) {
			if(Player.magia[Player.magiaAtual] == "fogo") {
				g.drawImage(MagicLevel1[indexCharge], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
			if(Player.magia[Player.magiaAtual] == "gelo") {
				g.drawImage(MagicLevel1Ice[indexCharge], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
			if(Player.magia[Player.magiaAtual] == "raio") {
				g.drawImage(MagicLevel1Thunder[indexCharge], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
		}
		if(Game.player.isCharging && Charging > 100) {
			if(Player.magia[Player.magiaAtual] == "fogo") {
				g.drawImage(MagicLevel2[indexCharge], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
			if(Player.magia[Player.magiaAtual] == "gelo") {
				g.drawImage(MagicLevel2Ice, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
			if(Player.magia[Player.magiaAtual] == "raio") {
				g.drawImage(MagicLevel2Thunder, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
		}
	}
	
}
