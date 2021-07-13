package com.gcstudios.entities;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.Node;
import com.gcstudios.world.Vector2i;

public class Entity {
	
	public static BufferedImage LIFEPACK_EN = Game.Itensspritesheet.getSprite(0, 0,25,25);
	public static BufferedImage BULLET_EN = Game.Itensspritesheet.getSprite(25,0,25,25);
	public static BufferedImage FLOR = Game.TilesSpritesheet.getSprite(0, 25, 25, 25);
	public double x;
	public double y;
	protected int z;
	protected int width;
	protected int height;

	
	public int depth = 0;
	
	protected List<Node> path;
	
	public boolean debug = false;
	
	private BufferedImage sprite;
	
	public int maskx,masky,mwidth,mheight;
	
	protected int strength;
	protected int defence;
	
	public Entity(int x,int y,int width,int height,BufferedImage sprite){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		@Override

		public int compare(Entity n0, Entity n1) {
			if (n1.depth < n0.depth) {
				return +1;
			}
			if (n1.depth > n0.depth) {
				return -1;
			}
			return 0;
		}
	};
	
	public void setMask(int maskx,int masky,int mwidth,int mheight){
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}
	
	
	
	public void tick(){
		
	}
	
	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public boolean isColliding(int xnext,int ynext){
		Rectangle enemyCurrent = new Rectangle(xnext + maskx,ynext + masky,mwidth,mheight);
		for(int i =0; i < Game.enemies.size(); i++){
			Enemy e = Game.enemies.get(i);
			if(e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX()+ maskx,e.getY()+ masky,mwidth,mheight);
			if(enemyCurrent.intersects(targetEnemy)){
				return true;
			}
		}
		for(int i =0; i < Game.enemies2.size(); i++){
			Enemy2 e = Game.enemies2.get(i);
			if(e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX()+ maskx,e.getY()+ masky,mwidth,mheight);
			if(enemyCurrent.intersects(targetEnemy)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isCollidingPlayerToEnemy(int xnext,int ynext){
		Rectangle Player = new Rectangle(xnext + maskx,ynext + masky,mwidth,mheight);
		for(int i =0; i < Game.enemies.size(); i++){
			Enemy e = Game.enemies.get(i);
			Rectangle targetEnemy = new Rectangle(e.getX()+ maskx,e.getY()+ masky,mwidth,mheight);
			if(Player.intersects(targetEnemy)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isCollidingPlayerToEnemy2(int xnext,int ynext){
		Rectangle Player = new Rectangle(xnext + maskx,ynext + masky,mwidth,mheight);
		for(int i =0; i < Game.enemies2.size(); i++){
			Enemy2 e = Game.enemies2.get(i);
			Rectangle targetEnemy = new Rectangle(e.getX()+ maskx,e.getY()+ masky,mwidth,mheight);
			if(Player.intersects(targetEnemy)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isCollidingPlayerToNPC(int xnext,int ynext){
		Rectangle Player = new Rectangle(xnext + maskx,ynext + masky,mwidth,mheight);
		for(int i =0; i < Game.NPc.size(); i++){
			Npc e = Game.NPc.get(i);
			Rectangle target = new Rectangle(e.getX()+ maskx,e.getY()+ masky,mwidth,mheight);
			if(Player.intersects(target)){
				return true;
			}
		}
		
		return false;
	}
	
	/*public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				//xprev = x;
				//yprev = y;
				if(x < target.x * 25 && !isColliding(this.getX() + 1, this.getY())) {
					x++;
				}else if(x > target.x * 25 && !isColliding(this.getX() - 1, this.getY())) {
					x--;
				}
				if(y < target.y * 25 && !isColliding(this.getX(), this.getY() + 1)) {
					y++;
				}else if(y > target.y * 25 && !isColliding(this.getX(), this.getY() - 1)) {
					y--;
				}
				
				if(x == target.x * 25 && y == target.y * 25) {
					path.remove(path.size() - 1);
				}
				
			}
		}
	}*/
	
	public static boolean isColidding(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx,e1.getY()+e1.masky,e1.mwidth,e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx,e2.getY()+e2.masky,e2.mwidth,e2.mheight);
		
		return(e1Mask.intersects(e2Mask) && e1.z == e2.z);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y,null);
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + maskx - Camera.x,this.getY() + masky - Camera.y,mwidth,mheight);
	}
	
}
