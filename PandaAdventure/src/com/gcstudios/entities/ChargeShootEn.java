package com.gcstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;

public class ChargeShootEn extends Entity{
	
	private BufferedImage[] MagicLevel1;
	private BufferedImage[] MagicLevel2;
	
	private int indexCharge = 0, indexMaxCharge = 2;
	
	private int frames = 0,maxFrames = 15;
	
	public static int Charging = 0;
	
	public static boolean Charged = false;
	
	public ChargeShootEn(int x, int y, int width, int height, BufferedImage sprite,double dx,double dy) {
		super(x, y, width, height, sprite);
		
		MagicLevel1 = new BufferedImage[4];
		MagicLevel2 = new BufferedImage[3];
		
		for(int i = 0; i < 3; i++){
			MagicLevel1[i] = Game.Enemyspritesheet.getSprite(125 + (i*25), 225, 25, 25);
		}
		for(int i = 0; i < 2; i++){
			MagicLevel2[i] = Game.Enemyspritesheet.getSprite(200 + (i*25), 225, 25, 25);
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
			if(Charging > 110) {
				Charged = true;
			}
		if(!Enemy2.isCharging) {
			Game.chargebulletsen.remove(this);
			Game.chargebulletsen.clear();
			Charging = 0;
			Charged = false;
			return;
		}
		
	}
	
	public void render(Graphics g){
		if(Enemy2.isCharging && Charging < 100) {
			g.drawImage(MagicLevel1[indexCharge], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
		}
		if(Enemy2.isCharging && Charging > 100) {
			g.drawImage(MagicLevel2[indexCharge], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
		}
	}
	
}
