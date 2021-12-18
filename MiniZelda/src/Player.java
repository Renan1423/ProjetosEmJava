import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Player extends Rectangle{
	
	public double spd = 3.5;
	public boolean right,left,up,down;
	
	public int curAnimation = 0;
	public int curAnimationAttack = 0;
	
	public int curFrames = 0, targetFrames = 10;
	
	public static List<Bullet> bullets = new ArrayList<>();
	
	public boolean shoot = false;
	
	public boolean isAttacking = false;
	private int attackFrames = 0, attackMaxFrames = 35;
	
	public int dir = 0;
	public int Vdir = 1;
	
	public static float Life = 3;
	public static float maxLife = 5;
	
	public static boolean isTakingDamage = false;
	public int damageDelay = 0, damageMaxDelay = 10;
	
	public Player(int x, int y) {
		super(x,y,32,32);
		
	}
	
	public void AttackCollider(int Hdir, int Vdir) {
		Rectangle atk = new Rectangle(x + 16 * Hdir,y + 16 * Vdir,16,16);
		
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy thisEnemy = Game.enemies.get(i);
			
			if(atk.intersects(thisEnemy)) {
				thisEnemy.TakeDamage();
				if(!thisEnemy.isColliding(Hdir, Vdir)) {
					thisEnemy.x += Hdir;
					thisEnemy.y += Vdir;
				}
			}
		}
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void tick() {
		//Taking Damage
		
		if(isTakingDamage) {
			if(World.isFree(x - (3 * dir), y - (3*Vdir))){
				x -= 3 * dir;
				y -= 3 * Vdir;
			}
			damageDelay++;
			if(damageDelay >= damageMaxDelay) {
				Life--;
				damageDelay = 0;
				isTakingDamage = false;
			}
		}
		else {
			boolean moved = false;
			if(right && World.isFree(x + (int)spd, y)) {
				x+=spd;
				moved = true;
				dir = 1;
				Vdir = 0;
			}else if(left && World.isFree(x - (int)spd, y)) {
				x-=spd;
				moved = true;
				dir = -1;
				Vdir = 0;
			}
			else if(up && World.isFree(x, y - (int)spd)) {
				y-=spd;
				moved = true;
				dir = 0;
				Vdir = -1;
			}else if(down && World.isFree(x, y + (int)spd)) {
				y+=spd;
				moved = true;
				dir = 0;
				Vdir = 1;
			}
			
			if(isAttacking == true) {
				
				//Checking attack collision
				AttackCollider(dir, Vdir);
				
				//Animation
				attackFrames++;
				if(attackFrames >= attackMaxFrames) {
					attackFrames = 0;
					isAttacking = false;
				}
				curFrames++;
				if(curFrames == targetFrames) {
					curFrames = 0;
					curAnimationAttack++;
					if(curAnimationAttack == Spritesheet.player_front_attack.length) {
						curAnimationAttack = 0;
					}
				}
			}else {
				attackFrames = 0;
				curAnimationAttack = 0;
			}
			
			
			if(moved && !isAttacking) {
				curFrames++;
				if(curFrames == targetFrames) {
					curFrames = 0;
					curAnimation++;
					if(curAnimation == Spritesheet.player_front.length) {
						curAnimation = 0;
					}
				}
			}else {
				curAnimation = 0;
				
				
			}
			
			if(shoot) {
				shoot = false;
				bullets.add(new Bullet(x,y,dir,Vdir));
			}
			
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).tick();
			}
			
			//Camera.x = x - (Game.WIDTH/2);
			//Camera.y = y - (Game.HEIGHT/2);
			Camera.x = Camera.clamp((int)this.getX() - (Game.WIDTH/2),0,World.WIDTH * 32 - Game.WIDTH);
			Camera.y = Camera.clamp((int)this.getY() - (Game.HEIGHT/2),0,World.HEIGHT * 32 - Game.HEIGHT);
		}
		
	}
	
	public void render(Graphics g) {
		//g.setColor(Color.blue);
		//g.fillRect(x,y,width,height);
		
		if(Vdir == 1) {
			if(!isAttacking) {
				g.drawImage(Spritesheet.player_front[curAnimation],(int)this.getX() - Camera.x,y - Camera.y,32,32,null);
			}else {
				g.drawImage(Spritesheet.player_front_attack[curAnimationAttack],(int)this.getX() - Camera.x,y - Camera.y,32,55,null);
			}
		}else if(Vdir == -1) {
			if(!isAttacking) {
				g.drawImage(Spritesheet.player_back[curAnimation],(int)this.getX() - Camera.x,y - Camera.y,32,32,null);
			}else {
				g.drawImage(Spritesheet.player_back_attack[curAnimationAttack],(int)this.getX() - Camera.x,y - 23 - Camera.y,32,55,null);
			}
		}else if(dir == 1) {
			if(!isAttacking) {
				g.drawImage(Spritesheet.player_right[curAnimation],(int)this.getX() - Camera.x,y - Camera.y,32,32,null);
			}else {
				g.drawImage(Spritesheet.player_right_attack[curAnimationAttack], (int)this.getX() - Camera.x,y - Camera.y, 55, 32, null);
			}
			
		}else if(dir == -1) {
			Graphics2D g2 = (Graphics2D) g;
			if(!isAttacking) {
				g2.rotate(Math.toRadians(180),this.getX() + width/2 - Camera.x,this.getY() + height/2 - Camera.y);
				g2.drawImage(Spritesheet.player_left[curAnimation],(int)this.getX() - Camera.x,y + height - Camera.y,width,-height,null);
				g2.rotate(Math.toRadians(-180),this.getX() + width/2 - Camera.x,this.getY() + height/2 - Camera.y);
			}else {
				g2.rotate(Math.toRadians(180),this.getX() + width/2 - Camera.x,this.getY() + height/2 - Camera.y);
				g.drawImage(Spritesheet.player_right_attack[curAnimationAttack], (int)this.getX() - Camera.x,y + height - Camera.y, 55, -32, null);
				g2.rotate(Math.toRadians(-180),this.getX() + width/2 - Camera.x,this.getY() + height/2 - Camera.y);
			}
			
		}
		
	
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
	}
}
