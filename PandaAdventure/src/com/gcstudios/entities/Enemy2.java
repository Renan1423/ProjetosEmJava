package com.gcstudios.entities;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.gcstudios.main.Game;
import com.gcstudios.main.Sound;
import com.gcstudios.world.AStar;
import com.gcstudios.world.Camera;
import com.gcstudios.world.Vector2i;
import com.gcstudios.world.World;

public class Enemy2 extends Entity{
	
	private double speed = 25;
	
	private int Level = 1;
	private int str = 1, def = 1;
	
	private boolean moved = false;
	private boolean notMaxLife = false;
	private boolean cajado = false;
	private boolean magia = false;
	public static boolean isCharging = false;
	private int mattackwidth, mattackheight;	
	
	public static int right_dir = 0,left_dir = 1, up_dir = 2, down_dir = 3;
	public static int dir = down_dir;
	private boolean right = false,up = false,left = false,down = false;
	private int frames = 0,maxFrames = 60,index = 0,maxIndex = 2, attackFrames = 0, attackMaxFrames = 30, attackIndex = 0, attackMaxIndex = 1, delayAttack = 0, delayMaxAttack = 120;
	private boolean EnemyAttack = false;
	private boolean isFreezing = false;
	private int freezeCountdown = 0;
	private int moveFrames = 0, maxMoveFrames = 60;
	
	private int stamina = 100, maxStamina = 100;
	
	private BufferedImage[] standDownSprites;
	private BufferedImage standUpSprites;
	private BufferedImage[] standLeftSprites;
	private BufferedImage[] standRightSprites;
	
	private BufferedImage damageUpSprite;
	private BufferedImage damageDownSprite;
	private BufferedImage damageLeftSprite;
	private BufferedImage damageRightSprite;
	
	private BufferedImage freezeUpSprite;
	private BufferedImage freezeDownSprite;
	private BufferedImage freezeLeftSprite;
	private BufferedImage freezeRightSprite;
	
	private BufferedImage moveUpSprite;
	private BufferedImage moveDownSprite;
	private BufferedImage moveLeftSprite;
	private BufferedImage moveRightSprite;
	
	private BufferedImage chargeDownSprites;
	private BufferedImage chargeUpSprites;
	private BufferedImage chargeLeftSprites;
	private BufferedImage chargeRightSprites;
	
	private double life = 10, maxLife = 10;
	
	private boolean isDamaged = false;
	private int damageFrames = 10,damageCurrent = 0;
	
	private boolean shoot = false;

	public Enemy2(int x, int y, int width, int height, BufferedImage[] sprite) {
		super(x, y, width, height, null);
		
		standDownSprites = new BufferedImage[3];
		standDownSprites[0] = Game.Enemyspritesheet.getSprite(25, 125, 25, 25);
		standDownSprites[1] = Game.Enemyspritesheet.getSprite(50, 125, 25, 25);
		standDownSprites[2] = Game.Enemyspritesheet.getSprite(75, 125, 25, 25);
		
		standUpSprites = Game.Enemyspritesheet.getSprite(225, 125, 25, 25);
		
		standLeftSprites = new BufferedImage[3];
		standLeftSprites[0] = Game.Enemyspritesheet.getSprite(100, 125, 25, 25);
		standLeftSprites[1] = Game.Enemyspritesheet.getSprite(125, 125, 25, 25);
		standLeftSprites[2] = Game.Enemyspritesheet.getSprite(150, 125, 25, 25);
		
		standRightSprites = new BufferedImage[3];
		standRightSprites[0] = Game.Enemyspritesheet.getSprite(150, 125, 25, 25);
		standRightSprites[1] = Game.Enemyspritesheet.getSprite(175, 125, 25, 25);
		standRightSprites[2] = Game.Enemyspritesheet.getSprite(200, 125, 25, 25);
		
		damageUpSprite = Game.Enemyspritesheet.getSprite(75, 175, 25, 25);
		damageDownSprite = Game.Enemyspritesheet.getSprite(0, 175, 25, 25);
		damageLeftSprite = Game.Enemyspritesheet.getSprite(25, 175, 25, 25);
		damageRightSprite = Game.Enemyspritesheet.getSprite(50, 175, 25, 25);
		
		freezeUpSprite = Game.Enemyspritesheet.getSprite(175, 175, 25, 25);
		freezeDownSprite = Game.Enemyspritesheet.getSprite(100, 175, 25, 25);
		freezeLeftSprite = Game.Enemyspritesheet.getSprite(125, 175, 25, 25);
		freezeRightSprite = Game.Enemyspritesheet.getSprite(150, 175, 25, 25);
		
		moveUpSprite = Game.Enemyspritesheet.getSprite(75, 150, 25, 25);
		moveRightSprite = Game.Enemyspritesheet.getSprite(50, 150, 25, 25);
		moveLeftSprite = Game.Enemyspritesheet.getSprite(25, 150, 25, 25);
		moveDownSprite = Game.Enemyspritesheet.getSprite(0, 150, 25, 25);
		
		chargeDownSprites = Game.Enemyspritesheet.getSprite(0, 200, 25, 25);
		
		chargeUpSprites = Game.Enemyspritesheet.getSprite(0, 225, 25, 25);

		chargeLeftSprites = Game.Enemyspritesheet.getSprite(25, 200, 25, 25);
		
		chargeRightSprites= Game.Enemyspritesheet.getSprite(25, 225, 25, 25);
	}

	public void tick(){
		depth = 0;
		maskx = 0;
		masky = 0;
		mwidth = 25;
		mheight = 25;
		mattackwidth = 50;
		mattackheight = 50;
		if(Level == 1) {
			str = 2;
		}
		else {
			str += Level;
		}
		def = Level;
		
		if(isFreezing) {
			freezeCountdown++;
			if(freezeCountdown == 300) {
				isFreezing = false;
				freezeCountdown = 0;
			}
		}
		
		if(Game.bulletsen.size() >= 1) {
			BulletShootEn.bulletcurLife++;
			if(BulletShootEn.bulletcurLife == BulletShootEn.bulletlife) {
				BulletShootEn.bulletcurLife = 0;
				Game.bulletsen.clear();
				return;
			}
		}else {
			BulletShootEn.bulletcurLife = 0;
		}
		
		if(stamina < maxStamina) {
			stamina+=1;
			if(stamina >= maxStamina) {
				stamina = maxStamina;
			}
		}
		this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY());
			if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) > 100 && !isFreezing && !isCharging) {
				if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 200) {
					moved = true;
					isCharging = false;
					if(isCollidingWithPlayer() == false){
						if((int)x < Game.player.getX() - 25 && World.isFree((int)(x+speed), this.getY()) && !isColliding((int)(x+speed), this.getY()) && left == false && up == false && down == false){
							dir = right_dir;
							if(stamina == maxStamina) {	
								right = true;
								left = false;
								up = false;
								down = false;
								stamina-=100;
								x+=speed;
								//right = false;
							}
						}
						else if((int)x > Game.player.getX() + 25 && World.isFree((int)(x-speed), this.getY()) && !isColliding((int)(x-speed), this.getY()) && right == false && up == false && down == false) {
							dir = left_dir;
							if(stamina == maxStamina) {	
								left = true;
								right = false;
								up = false;
								down = false;
								stamina-=100;
								x-=speed;
								//left = false;
							}
						}
						
						if((int)y < Game.player.getY() - 25 && World.isFree(this.getX(), (int)(y+speed)) && !isColliding(this.getX(), (int)(y+speed)) && left == false && up == false && right == false){
							dir = down_dir;
							if(stamina == maxStamina) {
								down = true;
								left = false;
								right = false;
								up = false;
								stamina -=100;
								y+=speed;
								//down= false;
							}
						}
						else if((int)y > Game.player.getY() + 25 && World.isFree(this.getX(), (int)(y-speed)) && !isColliding(this.getX(), (int)(y-speed)) && left == false && right == false && down == false) {
							dir = up_dir;
							if(stamina == maxStamina) {
								up = true;
								left = false;
								down = false;
								right = false;
								stamina -=100;
								y-=speed;
								//up = false;
							}
						}
						moveFrames++;
						if(moveFrames >= maxMoveFrames) {
							moveFrames = 0;
							up = false;
							right = false;
							left = false;
							down = false;
							moved = false;
						}
						}
				}
			}else if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) <= 100 && !isFreezing){
				up = false;
				right = false;
				left = false;
				down = false;
				if((int)x < Game.player.getX() - 25 && World.isFree((int)(x+speed), this.getY()) && (dir == left_dir || dir == up_dir || dir == down_dir)){
					dir = right_dir;
					isCharging = false;
				}
				if((int)x > Game.player.getX() + 25 && World.isFree((int)(x-speed), this.getY()) && (dir == right_dir || dir == up_dir || dir == down_dir)){
					dir = left_dir;
					isCharging = false;
				}
				if((int)y < Game.player.getY() - 25 && World.isFree(this.getX(), (int)(y+speed)) && (dir == left_dir || dir == up_dir || dir == right_dir)){
					dir = down_dir;
					isCharging = false;
				}
				if((int)y > Game.player.getY() + 25 && World.isFree(this.getX(), (int)(y-speed)) && (dir == left_dir || dir == right_dir || dir == down_dir)){
					dir = up_dir;
					isCharging = false;
				}
				
				
				delayAttack++;
				if(delayAttack == delayMaxAttack) {
					if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) <= 100) {
						isCharging = true;
					}
					else { 
						isCharging = false;
					}
					delayAttack = 0;
				}
				if(ChargeShootEn.Charged == true) {
					shoot = true;
					ChargeShootEn.Charging = 0;
					isCharging = false;
				}
			}
		
			if(!isDamaged) {
				frames++;
				if(frames == maxFrames) {
					frames = 0;
					index++;
					if(index == maxIndex) {
						index = 0;
					}
				}
			}
			
			if(isCharging) {
				ChargeShootEn.Charging++;
				if(ChargeShootEn.Charging >= 110) {
					ChargeShootEn.Charged = true;
				}
			}
			
			if(isCharging && stamina == maxStamina && Game.bulletsen.size() == 0 && !isDamaged) {
				stamina = 0;
				int dx = 0;
				int px = 0;
				int py = 0;
				if(dir == right_dir) {
					px = 16;
					py = 6;
					dx = 1;
				}
				else if(dir == left_dir){
					px = -13;
					py = 6;
					dx = -1;
				}
				else if(dir == down_dir){
					px = 1;
					py = 15;
					dx = -1;
				}
				else if(dir == up_dir){
					px = 1;
					py = -17;
					dx = -1;

				}
				ChargeShootEn chargeRight = new ChargeShootEn(this.getX()+px,this.getY()+py,3,3,null,dx,0);
				ChargeShootEn chargeLeft = new ChargeShootEn(this.getX()+px,this.getY()+py,3,3,null,dx,0);
				ChargeShootEn chargeDown = new ChargeShootEn(this.getX()+px,this.getY()+py,3,3,null,dx,0);
				ChargeShootEn chargeUp = new ChargeShootEn(this.getX()+px,this.getY()+py,3,3,null,dx,0);
				
				if(dir == right_dir && Game.chargebulletsen.size() == 0) {
					Game.chargebulletsen.add(chargeRight);
					Game.chargebulletsen.remove(chargeLeft);
					Game.chargebulletsen.remove(chargeUp);
					Game.chargebulletsen.remove(chargeDown);
				}
				else if(dir == left_dir && Game.chargebulletsen.size() == 0) {
					Game.chargebulletsen.add(chargeLeft);
					Game.chargebulletsen.remove(chargeRight);
					Game.chargebulletsen.remove(chargeUp);
					Game.chargebulletsen.remove(chargeDown);
				}
				else if(dir == down_dir && Game.chargebulletsen.size() == 0) {
					Game.chargebulletsen.add(chargeDown);
					Game.chargebulletsen.remove(chargeUp);
					Game.chargebulletsen.remove(chargeLeft);
					Game.chargebulletsen.remove(chargeRight);
				}
				else if(dir == up_dir && Game.chargebulletsen.size() == 0) {
					Game.chargebulletsen.add(chargeUp);
					Game.chargebulletsen.remove(chargeLeft);
					Game.chargebulletsen.remove(chargeRight);
					Game.chargebulletsen.remove(chargeDown);
				}
				
			}
			
			if(!isCharging) {
				Game.chargebulletsen.clear();
				ChargeShootEn.Charging = 0;
				ChargeShootEn.Charged = false;
			}
			
			if(shoot && Game.bulletsen.size() == 0) {
				shoot = false;
				ChargeShootEn.Charged = false;
				int dx = 0;
				int dy = 0;
				int px = 0;
				int py = 0;
				if(dir == right_dir) {
					px = 18;
					py = 5;
					dx = 1;
				}else if(dir == left_dir) {
					px = -8;
					py = 5;
					dx = -1;
				}
				if(dir == up_dir) {
					dy = -1;
					py = -15;
				}else if(dir == down_dir) {
					dy = 1;
					py = 10;
				}
					BulletShootEn bullet = new BulletShootEn(this.getX()+px,this.getY()+py,3,3,null,dx,dy);
					Game.bulletsen.add(bullet);
			}
			
			collidingBullet();
			
			if(life < maxLife) {
				notMaxLife = true;
			}
			
			if(life <= 0) {
				Player.exp += 100;
				destroySelf();
				return;
			}
			
			if(isDamaged) {
				isCharging = false;
				right = false;
				left = false;
				up = false;
				down = false;
				this.damageCurrent++;
				if(this.damageCurrent == this.damageFrames) {
					this.damageCurrent = 0;
					isDamaged = false;
					if(cajado) {
						if(Player.str - def <= 1) {
							life -= 0.5; 
						}
						else {
							life -= 0.8 * (Player.str - def);
						}
					}
					if(magia) {
						if(Player.magia[Player.magiaAtual] == "fogo") {
							if(Player.Level == 1) {
								life -= 2.5; 
							}else {
								if((2 * Player.str) - (def + 1) <= 0) {
									life -= 1.5; 
								}
								else {
									life -= (2 * Player.str) - (def - 1);
								}
							}
						}
						if(Player.magia[Player.magiaAtual] == "gelo") {
							if(Player.Level == 1) {
								life -= 1.5; 
							}else {
								if((2 * Player.str) - (def + 1) <= 0) {
									life -= 0.5; 
								}
								else {
									life -= (2 * Player.str) - (def - 1);
								}
							}
							isFreezing = true;
						}
						if(Player.magia[Player.magiaAtual] == "raio") {
							if(Player.Level == 1) {
								life -= 3; 
							}else {
								if((2 * Player.str) - (def + 1) <= 0) {
									life -= 2.5; 
								}
								else {
									life -= (3 * Player.str) - (def + 1);
								}
							}
						}
						
					}
					cajado = false;
					magia = false;
				}
				
			}
		
		
	}
	
	public void destroySelf() {
		Game.enemies2.remove(this);
		Game.entities.remove(this);
	}
	
	public void collidingBullet() {
		for(int i = 0; i < Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
				if(Entity.isColidding(this,e)) {
					isDamaged = true;
					magia = true;
					Game.bullets.remove(i);
					return;
				}
		}
		for(int i = 0; i < Game.staffStrike.size(); i++) {
			Entity e = Game.staffStrike.get(i);
				if(Entity.isColidding(this,e)) {
					isDamaged = true;
					cajado = true;
					/*switch(Player.dir) {
					case 0:
						x = this.getX() + 25;
					case 1:
						x = this.getX() - 25;
					case 2:
						y = this.getY() + 25;
					case 3:
						y = this.getY() - 25;
					}*/
					if(Player.dir == Player.right_dir && !isColliding((int)(x+25), this.getY())) {
						x = this.getX() + 25;
					}
					if(Player.dir == Player.left_dir && !isColliding((int)(x-25), this.getY())) {
						x = this.getX() - 25;
					}
					if(Player.dir == Player.down_dir && !isColliding(this.getX(), (int)(y + 25))) {
						y = this.getY() + 25;
					}
					if(Player.dir == Player.up_dir && !isColliding(this.getX(), (int)(y - 25))) {
						y = this.getY() - 25;
					}
					return;
				}
		}
		
	}
	
	public boolean isCollidingWithPlayer(){
		if(EnemyAttack && !isDamaged && dir == right_dir) {
			mwidth = mattackwidth;
		}
		if(EnemyAttack && !isDamaged && dir == left_dir) {
			mwidth = mattackwidth;
			maskx -= 25;
		}
		if(EnemyAttack && !isDamaged && dir == up_dir) {
			mheight = mattackheight;
			masky -= 25;
		}
		if(EnemyAttack && !isDamaged && dir == down_dir) {
			mheight = mattackheight;
		}
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx,this.getY() + masky,mwidth,mheight);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),25,25);
		
		return enemyCurrent.intersects(player);
	}
	
	
	
	public void render(Graphics g) {
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth,mheight);
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mattackwidth,mattackheight);
		if(!isDamaged && !right && !left && !up && !down && !isCharging && !isFreezing) {
			if(dir == down_dir) {
				g.drawImage(standDownSprites[index], this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(dir == up_dir) {
				g.drawImage(standUpSprites, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(dir == left_dir) {
				g.drawImage(standLeftSprites[index], this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(dir == right_dir) {
				g.drawImage(standRightSprites[index], this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
		}
		else if(isDamaged && moved == false && !isFreezing){
			if(dir == down_dir) {
				g.drawImage(damageDownSprite, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(dir == up_dir) {
				g.drawImage(damageUpSprite, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(dir == left_dir) {
				g.drawImage(damageLeftSprite, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(dir == right_dir) {
				g.drawImage(damageRightSprite, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
		
		}
			if(up && !isDamaged) {
				g.drawImage(moveUpSprite, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(down && !isDamaged) {
				g.drawImage(moveDownSprite, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(left && !isDamaged) {
				g.drawImage(moveLeftSprite, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(right && !isDamaged) {
				g.drawImage(moveRightSprite, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
		if(notMaxLife) {
			g.setColor(Color.black);
			g.fillRect(this.getX() + 2 - Camera.x, this.getY() - 6 - Camera.y, 20, 5);
			g.setColor(Color.green);
			g.fillRect(this.getX() + 4 - Camera.x, this.getY() - 5 - Camera.y, (int)((life/maxLife) * 16), 3);
		}
		if(isCharging && !isDamaged && !isFreezing) {
			if(dir == down_dir) {
				g.drawImage(chargeDownSprites, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(dir == up_dir) {
				g.drawImage(chargeUpSprites, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(dir == left_dir) {
				g.drawImage(chargeLeftSprites, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(dir == right_dir) {
				g.drawImage(chargeRightSprites, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
		}
		if(isFreezing) {
			if(dir == down_dir) {
				g.drawImage(freezeDownSprite, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(dir == up_dir) {
				g.drawImage(freezeUpSprite, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(dir == left_dir) {
				g.drawImage(freezeLeftSprite, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(dir == right_dir) {
				g.drawImage(freezeRightSprite, this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
		}
	}
	
	
}
