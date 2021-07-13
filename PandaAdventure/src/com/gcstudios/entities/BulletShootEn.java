package com.gcstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;

public class BulletShootEn extends Entity{

	private double dx;
	private double dy;
	private double spd = 3;
	
	private BufferedImage[] MagicShootR;
	private BufferedImage[] MagicShootL;
	private BufferedImage[] MagicShootU;
	private BufferedImage[] MagicShootD;
	
	public static int bulletlife = 55,bulletcurLife = 0;
	
	private int indexMagic = 0, indexMaxMagic = 3;
	
	private int frames = 0,maxFrames = 15;
	
	public BulletShootEn(int x, int y, int width, int height, BufferedImage sprite,double dx,double dy) {
		super(x, y, width, height, sprite);
		
		this.dx = dx;
		this.dy = dy;
		MagicShootR = new BufferedImage[4];
		MagicShootL = new BufferedImage[4];
		MagicShootU = new BufferedImage[4];
		MagicShootD = new BufferedImage[4];

		for(int i = 0; i < 3; i++){//Direita
			MagicShootR[i] = Game.Enemyspritesheet.getSprite(50 + (i*25), 225, 25, 25);
		}
		for(int i = 0; i < 3; i++) {//Esquerda
			MagicShootL[i] = Game.Enemyspritesheet.getSprite(100 - (i*25), 200, 25, 25);
		}
		for(int i = 0; i < 3; i++) {//Cima
			MagicShootU[i] = Game.Enemyspritesheet.getSprite(200, 200 - (i*25), 25, 25);
		}
		for(int i = 0; i < 3; i++) {//Baixo
			MagicShootD[i] = Game.Enemyspritesheet.getSprite(200, 150 + (i*25), 25, 25);
		}
		
	}
	
	public void tick() {
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			indexMagic++;
			if(indexMagic > indexMaxMagic)
				indexMagic = 0;
		}
		x+=dx*spd;
		y+=dy*spd;
		bulletcurLife++;
		if(bulletcurLife == bulletlife) {
			bulletcurLife = 0;
			Game.bulletsen.remove(this);
			Game.bulletsen.clear();
			return;
		}
	}
	
	public void render(Graphics g){
		if(Enemy2.dir == Enemy2.right_dir) {
			g.drawImage(MagicShootR[indexMagic], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
		}
		if(Enemy2.dir == Enemy2.left_dir) {
			g.drawImage(MagicShootL[indexMagic], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
		}
		if(Enemy2.dir == Enemy2.up_dir) {
			g.drawImage(MagicShootU[indexMagic], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
		}
		if(Enemy2.dir == Enemy2.down_dir) {
			g.drawImage(MagicShootD[indexMagic], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
		}
		
	}
	
}
