package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graficos.Spritesheet;
import main.Game;
import world.Camera;
import world.World;

public class Player extends Entity {

	public boolean right,up,left,down;
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	public double speed = 1;
	
	private int frames = 0,maxFrames = 5, index = 0, maxIndex = 4;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	private BufferedImage playerDamage;
	
	private boolean arma = false;
	public boolean shoot = false, mouseShoot = false;
	
	public int ammo = 0;
	
	public boolean isDamaged = false;
	private int  damageFrames = 0;
	
	public double life = 100, maxlife = 100;
	public int mx, my;//posição do mouse
	
	public boolean jump = false;
	public boolean isJumping = false;
	public int z = 0;
	public int jumpFrames = 50, jumpCur = 0;
	public boolean jumpUp = false, jumpDown = false;
	public int jumpSpd = 2;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		playerDamage = Game.spritesheet.getSprite(0, 16, 16, 16);
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, height);
		}
		for(int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, height);
		}
	}
	
	public void tick() {
		if(jump) {
			if(isJumping == false) {
				jump = false;
				isJumping = true;
				jumpUp = true;
			}
		}
		
		if(isJumping = true) {
			
				if(jumpUp) {
					jumpCur+=2;
				}else if(jumpDown) {
					jumpCur-=2;
					if(jumpCur <= 0) {
						isJumping = false;
						jumpDown = false;
						jumpUp = false;
					}
				}
				z = jumpCur;
				if(jumpCur >= jumpFrames) {
					jumpUp = false;
					jumpDown = true;
					System.out.println("Chegou na altura máxima");
				}
		}
		moved = false;
		if(right && World.isFree((int)(x+speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x+=speed;
		}
		else if (left && World.isFree((int)(x-speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		if(up && World.isFree(this.getX(), (int)(y-speed))) {
			moved = true;
			y-=speed;
		}
		else if (down && World.isFree(this.getX(), (int)(y+speed))) {
			moved = true;
			y+=speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index == maxIndex) {
					index = 0;
				}
			}			
		}
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 15) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(shoot) {
			shoot = false;
			if(arma && ammo > 0) {
				ammo--;
				int dx = 0;
				int px = 0;
				int py = 6;
				if(dir == right_dir) {
					px = 16;
					dx = 1;
				}else {
					px = -16;
					dx = -1;
				}
				ManaShoot bullet = new ManaShoot(this.getX() + px,this.getY() + py,3,3,null,dx,0);
				Game.mana.add(bullet);
			}
		}
		
		if(mouseShoot) {
			mouseShoot = false;
			if(arma && ammo > 0) {
				ammo--;
				int px = 0, py = 8;
				double angle = 0;
				if(dir == right_dir) {
					px = 16;
					angle = Math.atan2( my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
				}else {
					
					px = -16;
					angle = Math.atan2( my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
				}
				
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				
				ManaShoot bullet = new ManaShoot(this.getX() + px,this.getY() + py,3,3,null,dx,dy);
				Game.mana.add(bullet);
			}
		}
		
		checkItems();
		checkAmmo();
		checkWeapon();
		
		if(life <= 0) {
			life = 0;
			Game.gameState = "GAME_OVER";
		}
		updateCamera();
		
	}
		public void updateCamera() {
			Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
			Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
		}
	
	public void checkAmmo() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Mana) {
				if(Entity.isColidding(this,atual)) {
				ammo+=20;
				//System.out.println("Mana atual: " + ammo);
				Game.entities.remove(i);
				return;
				}
			}
		}
	}
	
	public void checkWeapon() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				if(Entity.isColidding(this,atual)) {
				arma=true;
				System.out.println("Pegou a arma");
				Game.entities.remove(i);
				return;
				}
			}
		}
	}
	
	public void checkItems() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Lifepack) {
				if(Entity.isColidding(this,atual)) {
				life+=8;
				if (life >= 100) {
					life = 100;
				}
				Game.entities.remove(i);
				return;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y - z,null);
				if(arma) {
					g.drawImage(Entity.WEAPON_RIGHT, this.getX() + 8 - Camera.x, this.getY() - Camera.y - z, null);
				}
			}
			else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				if(arma) {
					g.drawImage(Entity.WEAPON_LEFT, this.getX() - 8 - Camera.x, this.getY() - Camera.y - z, null);
				}
			}
		}else {
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
		}
		
	}

}
