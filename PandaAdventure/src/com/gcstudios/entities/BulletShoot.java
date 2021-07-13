package com.gcstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;

public class BulletShoot extends Entity{

	private double dx;
	private double dy;
	private double spd = 3;
	
	private BufferedImage[] MagicShootR;
	private BufferedImage[] MagicShootL;
	private BufferedImage[] MagicShootU;
	private BufferedImage[] MagicShootD;
	
	private BufferedImage MagicShootRICE;
	private BufferedImage MagicShootLICE;
	private BufferedImage MagicShootUICE;
	private BufferedImage MagicShootDICE;

	private BufferedImage MagicShootTHUNDER;
	
	private int life = 40,curLife = 0;
	
	private int indexMagic = 0, indexMaxMagic = 3;
	
	private int frames = 0,maxFrames = 15;
	
	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite,double dx,double dy) {
		super(x, y, width, height, sprite);
		
		this.dx = dx;
		this.dy = dy;
		MagicShootR = new BufferedImage[4];
		MagicShootL = new BufferedImage[4];
		MagicShootU = new BufferedImage[4];
		MagicShootD = new BufferedImage[4];
		
		MagicShootRICE = Game.Playerspritesheet.getSprite(200, 75, 25, 25);
		MagicShootLICE = Game.Playerspritesheet.getSprite(225, 75, 25, 25);
		MagicShootUICE = Game.Playerspritesheet.getSprite(225, 50, 25, 25);
		MagicShootDICE = Game.Playerspritesheet.getSprite(200, 50, 25, 25);
		
		MagicShootTHUNDER = Game.Playerspritesheet.getSprite(200, 100, 25, 25);

		for(int i = 0; i < 3; i++){//Direita
			MagicShootR[i] = Game.Playerspritesheet.getSprite(0 + (i*25), 225, 25, 25);
		}
		for(int i = 0; i < 3; i++) {//Esquerda
			MagicShootL[i] = Game.Playerspritesheet.getSprite(125 - (i*25), 100, 25, 25);
		}
		for(int i = 0; i < 3; i++) {//Cima
			MagicShootU[i] = Game.Playerspritesheet.getSprite(175, 125 - (i*25), 25, 25);
		}
		for(int i = 0; i < 3; i++) {//Baixo
			MagicShootD[i] = Game.Playerspritesheet.getSprite(150, 75 + (i*25), 25, 25);
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
		if(Player.magia[Player.magiaAtual] == "gelo") {
			spd = 2;
			life = 35;
		}
		if(Player.magia[Player.magiaAtual] == "raio") {
			spd = 2;
			life = 15;
		}
		x+=dx*spd;
		y+=dy*spd;
		curLife++;
		if(curLife == life) {
			Game.bullets.remove(this);
			Game.bullets.clear();
			return;
		}
	}
	
	public void render(Graphics g){
		if(Player.magia[Player.magiaAtual] == "raio") {
			g.drawImage(MagicShootTHUNDER, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
		}
		if(Game.player.dir == Game.player.right_dir) {
			if(Player.magia[Player.magiaAtual] == "fogo") {
				g.drawImage(MagicShootR[indexMagic], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
			if(Player.magia[Player.magiaAtual] == "gelo") {
				g.drawImage(MagicShootRICE, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
			if(Player.magia[Player.magiaAtual] == "raio") {
				g.drawImage(MagicShootTHUNDER, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
		}
		if(Game.player.dir == Game.player.left_dir) {
			if(Player.magia[Player.magiaAtual] == "fogo") {
				g.drawImage(MagicShootL[indexMagic], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
			if(Player.magia[Player.magiaAtual] == "gelo") {
				g.drawImage(MagicShootLICE, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
			if(Player.magia[Player.magiaAtual] == "raio") {
				g.drawImage(MagicShootTHUNDER, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
		}
		if(Game.player.dir == Game.player.up_dir) {
			if(Player.magia[Player.magiaAtual] == "fogo") {
				g.drawImage(MagicShootU[indexMagic], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
			if(Player.magia[Player.magiaAtual] == "gelo") {
				g.drawImage(MagicShootUICE, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
			if(Player.magia[Player.magiaAtual] == "raio") {
				g.drawImage(MagicShootTHUNDER, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
		}
		if(Game.player.dir == Game.player.down_dir) {
			if(Player.magia[Player.magiaAtual] == "fogo") {
				g.drawImage(MagicShootD[indexMagic], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
			if(Player.magia[Player.magiaAtual] == "gelo") {
				g.drawImage(MagicShootDICE, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
			if(Player.magia[Player.magiaAtual] == "raio") {
				g.drawImage(MagicShootTHUNDER, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
			}
		}
		
	}
	
}
