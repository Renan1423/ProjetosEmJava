package com.gcstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;

public class StaffStrike extends Entity{
	
	private double dx;
	private double dy;
	private double spd = 4;
	
	private BufferedImage[] rightStaff;
	private BufferedImage[] leftStaff;
	private BufferedImage[] upStaff;
	private BufferedImage[] downStaff;
	
	public static int indexStaff = 0, maxindexStaff = 2;
	
	private int frames = 0,maxFrames = 13;

	public StaffStrike(int x, int y, int width, int height, BufferedImage sprite,double dx,double dy) {
		super(x, y, width, height, sprite);
		
		this.dx = dx;
		this.dy = dy;
		
		rightStaff = new BufferedImage[3];
		leftStaff = new BufferedImage[3];
		upStaff = new BufferedImage[3];
		downStaff = new BufferedImage[3];
		
		for(int i = 0; i < 2; i++){
			//rightStaff[i] = Game.Playerspritesheet.getSprite(200 + (i*25), 175, 25, 25);
			rightStaff[i] = Game.Playerspritesheet.getSprite(200 + (i*25), 175, 25, 25);
		}
		for(int i =0; i < 2; i++){
			//leftStaff[i] = Game.Playerspritesheet.getSprite(225 - (i*25), 200, 25, 25);
			leftStaff[i] = Game.Playerspritesheet.getSprite(225 - (i*25), 200, 25, 25);
		}
		for(int i =0; i < 2; i++){	
			//upStaff[i] = Game.Playerspritesheet.getSprite(100 + (i*25), 175, 25, 25);
			upStaff[i] = Game.Playerspritesheet.getSprite(100 + (i*25), 175, 25, 25);
		}
		for(int i =0; i < 2; i++){
			//downStaff[i] = Game.Playerspritesheet.getSprite(50 + (i * 25), 200, 25, 25);
			downStaff[i] = Game.Playerspritesheet.getSprite(50 + (i * 25), 200, 25, 25);
		}
		
	}
	
	public void tick(){
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			indexStaff++;
			if(indexStaff > maxindexStaff)
				indexStaff = 0;
		}
		/*if(Player.Attack) {
			if(indexStaff > maxindexStaff) {
				indexStaff = 0;
				Game.staffStrike.remove(this);
			}
		}*/
		if(!Player.Attack) {
			Game.staffStrike.remove(this);
			Game.staffStrike.clear();
			return;
		}
	}
	
	public void render(Graphics g){
		if(Game.player.dir == Game.player.right_dir) {
			g.drawImage(rightStaff[indexStaff], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
		}
		else if(Game.player.dir == Game.player.left_dir) {
			g.drawImage(leftStaff[indexStaff], this.getX() - Camera.x,this.getY() - Camera.y - z, null);	
		}
		if(Game.player.dir == Game.player.up_dir) {
			g.drawImage(upStaff[indexStaff], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
		}
		else if(Game.player.dir == Game.player.down_dir) {
			g.drawImage(downStaff[indexStaff], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
		}
	}

}
