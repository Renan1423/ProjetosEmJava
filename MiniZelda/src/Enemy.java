import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Enemy extends Rectangle{
	
	public int spd = 1;
	public boolean right,left,up,down;
	
	public float life = 3;
	//Damage
	public int damageDelay = 45, damageMaxDelay = 45;
	public boolean isTakingDamage;
	int frames = 0;
	int MaxFrames = 60;
	
	public boolean inCooldown = false;
	public int cooldownCount = 0, cooldownMax = 20;
	
	public boolean isDead;
	
	//Animation
	public int curAnimation = 0;
	
	public int curFrames = 0, targetFrames = 10;
	
	public int deadFrames = 0, deadMaxFrames = 10;
	
	//Bullets
	public static List<Bullet> bullets = new ArrayList<>();
	
	public boolean shoot = false;
	
	public int dir = 0;
	public int Vdir = 1;
	
	private double distance;
	
	public Enemy(int x, int y) {
		super(x,y,32,32);
		
	}
	
	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public void TakeDamage() {
		//Damage
		if(damageDelay >= damageMaxDelay) {
			damageDelay = 0;
			life--;
			isTakingDamage = true;
		}
		
		//Death
		if(life <= 0) {
			//curAnimation = 0;
			isTakingDamage = false;
			isDead = true;
			//destroySelf();
		}
	}
	
	public void StunDamage() {
		if(damageDelay >= damageMaxDelay) {
			damageDelay = 0;
			isTakingDamage = true;
		}
	}
	
	public void destroySelf() {
		Game.enemies.remove(this);
	}
	
	public void tick() {
		boolean moved = true;
		
		distance = this.calculateDistance(x,y,Game.player.x,Game.player.y);
		
		if(!isTakingDamage && !isDead && !inCooldown) {	
			if(distance < 300 && distance > 30) {
				if(World.isFree(x + spd, y) && x < Game.player.x && !isColliding(x + spd, y)) {
					x += spd;
				}else if(World.isFree(x - spd, y) && x > Game.player.x && !isColliding(x - spd, y)) {
					x -= spd;
				}if(World.isFree(x, y - spd) && y > Game.player.y && !isColliding(x, y - spd)) {
					y -= spd;
				}else if(World.isFree(x, y + spd) && y < Game.player.y && !isColliding(x, y + spd)) {
					y += spd;
				}
				
				
				
				if(moved) {
					curFrames++;
					if(curFrames == targetFrames) {
						curFrames = 0;
						curAnimation++;
						if(curAnimation == Spritesheet.enemySprite.length) {
							curAnimation = 0;
						}
					}
				}else {
					curAnimation = 0;
				}
			}
			
			if(shoot) {
				shoot = false;
				bullets.add(new Bullet(x,y,dir,Vdir));
			}
			
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).tick();
			}
		}else if(isTakingDamage && !isDead){
			frames++;
			if(frames >= MaxFrames) {
				frames = 0;
				curAnimation++;
				if(curAnimation >= Spritesheet.enemySpriteDamage.length) {
					curAnimation = 0;
				}
				isTakingDamage = false;
			}
		}else if(isDead) {
			isTakingDamage = false;
			deadFrames++;
			if(deadFrames >= deadMaxFrames) {
				frames = 0;
				curAnimation++;
				if(curAnimation == Spritesheet.enemySpriteDead.length) {
					curAnimation = 0;	
					destroySelf();
				}
				
				
			}
		}

		//Damage delay counter
		damageDelay++;
		
		
		//Attacking Player
		
		if(isCollidingWithPlayer()) {
			Game.player.isTakingDamage = true;
			inCooldown = true;
		}
		
		if(inCooldown) {
			cooldownCount++;
			if(cooldownCount >= cooldownMax) {
				cooldownCount = 0;
				inCooldown = false;
			}
		}
	}
	
	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext,ynext,World.TILE_SIZE,World.TILE_SIZE);
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.x,e.y,World.TILE_SIZE,World.TILE_SIZE);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isCollidingWithPlayer(){
		Rectangle enemyCurrent = new Rectangle(x - Camera.x,y - Camera.y,width,height);
		Rectangle player = new Rectangle(Game.player.x - Camera.x,Game.player.y - Camera.y,32,32);
		
		return enemyCurrent.intersects(player);
	}
	
	public void render(Graphics g) {
		//g.setColor(Color.blue);
		//g.fillRect(x,y,width,height);
		
		if(isTakingDamage) {
			g.drawImage(Spritesheet.enemySpriteDamage[curAnimation],x - Camera.x,y - Camera.y,32,32,null);
		}else if(!isDead && !isTakingDamage){
			g.drawImage(Spritesheet.enemySprite[curAnimation],x - Camera.x,y - Camera.y,32,32,null);
		}else if(isDead) {
			g.drawImage(Spritesheet.enemySpriteDead[curAnimation],x - Camera.x,y - Camera.y,32,32,null);
		}
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
	}
}
