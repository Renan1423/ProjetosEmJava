import java.awt.Color;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends Rectangle{
	
	public int dir = 0;
	public int Vdir = 1;
	public int speed = 3;
	
	public int frames = 0, maxFrames = 10;
	public int curAnimation = 0;
	
	public boolean Forward = true;
	public int directionCount = 0, directionMaxCount = 30;
	
	public Bullet(int x, int y, int dir, int Vdir) {
		super(x + (32*dir),y,20,20);
		this.dir = dir;
		this.Vdir = Vdir;
	}
	
	public void tick() {
		
		CollidingWithEnemy(x,y);
		
		if(Forward) {
			x+= speed * dir;
			y+= speed * Vdir;
			
			directionCount++;
			if(directionCount >= directionMaxCount) {
				directionCount = 0;
				Forward = false;
			}
		}else {
			x-= speed * dir;
			y-= speed * Vdir;
			
			directionCount++;
			if(directionCount >= directionMaxCount) {
				directionCount = 0;
				Player.bullets.remove(this);
			}
			
		}
		
		
		//Animation
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			curAnimation++;
			if(curAnimation == Spritesheet.boomerang.length) {
				curAnimation = 0;
			}
			//Player.bullets.remove(this);
			//return;
		}
	}
	
	
	public void CollidingWithEnemy(int x, int y) {
		Rectangle bullet = new Rectangle(x,y,16,16);
		
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy thisEnemy = Game.enemies.get(i);
			
			if(bullet.intersects(thisEnemy)) {
				thisEnemy.StunDamage();
				Forward = false;
				return;
			}
		}
	}
	
	
	public void render(Graphics g) {
		//g.setColor(Color.red);
		//g.fillRect(x, y, width, height);
		g.drawImage(Spritesheet.boomerang[curAnimation],x - Camera.x,y - Camera.y,25,25,null);
	}
}
