package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import main.Sound;
import world.Camera;
import world.World;

public class Enemy extends Entity{
	
	private double speed = 0.6;
	
	private int maskx = 12, masky = 12, maskw = 14, maskh = 14;

	private int frames = 0,maxFrames = 30, index = 0, maxIndex = 3;
	
	private BufferedImage[] sprites;
	
	private int life = 10;
	
	
	private boolean isDamaged = false;
	private int damageFrames = 10, damageCurrent = 0;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		sprites = new BufferedImage[3];
		sprites[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(128, 16, 16, 16);
		sprites[2] = Game.spritesheet.getSprite(144, 16, 16, 16);
		
	}
	
	public void tick() {
		//if(Game.rand.nextInt(100) < 50) {
		if(this.isCollidingWithPlayer() == false) {
			if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
					&& !isColliding((int)(x+speed),this.getY())) {
				x+= speed;
			}
			else if((int)x >Game.player.getX() && World.isFree((int)(x-speed), this.getY())
					&& !isColliding((int)(x-speed),this.getY())) {
				x-=speed;
			}
			/*usar else if para andar apenas uma direção por vez*/
			if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))
					&& !isColliding(this.getX(), (int)(y+speed))){
				y+= speed;
			}
			else if((int)y >Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))
					&& !isColliding(this.getX(), (int)(y-speed))){
				y-=speed;
			}
			}else {//dano no player
				if(Game.rand.nextInt(100) < 10) {
					Sound.hurtEffect.play();
					Game.player.life--;
					Game.player.isDamaged = true;
					System.out.println("Vida: " + Game.player.life);
				}
			}
		//}
		
		
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index == maxIndex) {
					index = 0;
				}
			}
			
			collidingBullet();
			
			if(life <= 0) {
				destroySelf();
				return;
			}
			
			if(isDamaged){
				this.damageCurrent++;
				if(this.damageCurrent == this.damageFrames) {
					this.damageCurrent = 0;
					this.isDamaged = false;
				}
			}
	}
	
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}
	
	public void collidingBullet() {
		for(int i = 0; i < Game.mana.size(); i++) {
			Entity e = Game.mana.get(i);
			if(e instanceof ManaShoot) {
				if(Entity.isColidding(this, e)) {
					isDamaged = true;
					life--;
					Game.mana.remove(i);
					return;
				}
			}
		}
	}
	
	public boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx,this.getY() + masky,maskw,maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(),12,12);
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx,ynext + masky,maskw,maskh);
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx,e.getY() + masky,maskw,maskh);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
			
		}
		return false;
		
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			g.drawImage(sprites[index], this.getX()- Camera.x, this.getY() - Camera.y, null);
		}
		else {
			g.drawImage(Entity.ENEMY_FEEDBACK, this.getX()- Camera.x, this.getY() - Camera.y, null);
		}
		/*super.render(g);
		g.setColor(null);
		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);*/
	}

}
