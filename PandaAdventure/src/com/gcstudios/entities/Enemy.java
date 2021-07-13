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

public class Enemy extends Entity{
	
	private double speed = 25;
	
	private int Level = 1;
	private int str = 1, def = 1;
	
	private boolean moved = false;
	private boolean notMaxLife = false;
	private boolean cajado = false;
	private boolean magia = false;
	private boolean wascajado = false;
	private int mattackwidth, mattackheight;	
	
	public int right_dir = 0,left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = down_dir;
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
	
	private BufferedImage[] attackDownSprites;
	private BufferedImage[] attackUpSprites;
	private BufferedImage[] attackLeftSprites;
	private BufferedImage[] attackRightSprites;
	
	private double life = 10, maxLife = 10;
	
	private boolean isDamaged = false;
	private int damageFrames = 10,damageCurrent = 0;

	public Enemy(int x, int y, int width, int height, BufferedImage[] sprite) {
		super(x, y, width, height, null);
		
		standDownSprites = new BufferedImage[3];
		standDownSprites[0] = Game.Enemyspritesheet.getSprite(25, 0, 25, 25);
		standDownSprites[1] = Game.Enemyspritesheet.getSprite(50, 0, 25, 25);
		standDownSprites[2] = Game.Enemyspritesheet.getSprite(75, 0, 25, 25);
		
		standUpSprites = Game.Enemyspritesheet.getSprite(225, 0, 25, 25);
		
		standLeftSprites = new BufferedImage[3];
		standLeftSprites[0] = Game.Enemyspritesheet.getSprite(100, 0, 25, 25);
		standLeftSprites[1] = Game.Enemyspritesheet.getSprite(125, 0, 25, 25);
		standLeftSprites[2] = Game.Enemyspritesheet.getSprite(150, 0, 25, 25);
		
		standRightSprites = new BufferedImage[3];
		standRightSprites[0] = Game.Enemyspritesheet.getSprite(150, 0, 25, 25);
		standRightSprites[1] = Game.Enemyspritesheet.getSprite(175, 0, 25, 25);
		standRightSprites[2] = Game.Enemyspritesheet.getSprite(200, 0, 25, 25);
		
		damageUpSprite = Game.Enemyspritesheet.getSprite(75, 50, 25, 25);
		damageDownSprite = Game.Enemyspritesheet.getSprite(0, 50, 25, 25);
		damageLeftSprite = Game.Enemyspritesheet.getSprite(25, 50, 25, 25);
		damageRightSprite = Game.Enemyspritesheet.getSprite(50, 50, 25, 25);
		
		freezeUpSprite = Game.Enemyspritesheet.getSprite(175, 50, 25, 25);
		freezeDownSprite = Game.Enemyspritesheet.getSprite(100, 50, 25, 25);
		freezeLeftSprite = Game.Enemyspritesheet.getSprite(125, 50, 25, 25);
		freezeRightSprite = Game.Enemyspritesheet.getSprite(150, 50, 25, 25);
		
		moveUpSprite = Game.Enemyspritesheet.getSprite(75, 25, 25, 25);
		moveRightSprite = Game.Enemyspritesheet.getSprite(50, 25, 25, 25);
		moveLeftSprite = Game.Enemyspritesheet.getSprite(25, 25, 25, 25);
		moveDownSprite = Game.Enemyspritesheet.getSprite(0, 25, 25, 25);
		
		attackDownSprites = new BufferedImage[2];
		attackDownSprites[0] = Game.Enemyspritesheet.getSprite(25, 75, 25, 50);
		attackDownSprites[1] = Game.Enemyspritesheet.getSprite(25, 75, 25, 50);
		
		attackUpSprites = new BufferedImage[2];
		attackUpSprites[0] = Game.Enemyspritesheet.getSprite(75, 75, 25, 50);
		attackUpSprites[1] = Game.Enemyspritesheet.getSprite(75, 75, 25, 50);
		
		attackLeftSprites = new BufferedImage[2];
		attackLeftSprites[0] = Game.Enemyspritesheet.getSprite(150, 100, 50, 25);
		attackLeftSprites[1] = Game.Enemyspritesheet.getSprite(150, 100, 50, 25);
		
		attackRightSprites = new BufferedImage[2];
		attackRightSprites[0] = Game.Enemyspritesheet.getSprite(150, 75, 50, 25);
		attackRightSprites[1] = Game.Enemyspritesheet.getSprite(150, 75, 50, 25);
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
		
		if(stamina < maxStamina) {
			stamina+=1;
			if(stamina >= maxStamina) {
				stamina = maxStamina;
			}
		}
		//if(!isDamaged) {
		this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY());
			if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) > 25 && !isFreezing && !EnemyAttack) {
				if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 100 || notMaxLife) {
					moved = true;
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
			}else if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) <= 25 && !isFreezing){
				up = false;
				right = false;
				left = false;
				down = false;
				delayAttack++;
				if(delayAttack == delayMaxAttack) {
					if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) <= 25) {
						EnemyAttack = true;
					}
					else { 
						EnemyAttack = false;
					}
					delayAttack = 0;
				}
					if(EnemyAttack && this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) <= 25) {
						stamina -= 20;
						attackFrames++;
						if(attackFrames == attackMaxFrames) {
							attackFrames = 0;
							attackIndex++;
							if(attackIndex == attackMaxIndex) {
								attackIndex = 0;
								delayAttack++;
								if(delayAttack == delayMaxAttack) {
									EnemyAttack = false;
								}
							}
						}
						if(isCollidingWithPlayer()) {
							if(Game.rand.nextInt(100) < 10){
								//Sound.hurtEffect.play();
								Game.player.life -= 10*(str - def);
								Game.player.isDamaged = true;
								EnemyAttack = false;
							}
						}
						else {
							EnemyAttack = false;
						}
				}else {
					EnemyAttack = false;
				}
			}
	//}
		
/*
		if(!isCollidingWithPlayer()) {
			if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i((int)(x/25),(int)(y/25));
				Vector2i end = new Vector2i((int)(Game.player.x/25),(int)(Game.player.y/25));
				path = AStar.findPath(Game.world, start, end);
			}
		}*/
		/*else {
			if(new Random().nextInt(100) < 5) {
				Sound.hurtEffect.play();
				Game.player.life -= Game.rand.nextInt(3);
				Game.player.isDamaged = true;
			}
		}*/
			//followPath(path);
		
		
			
		
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
						wascajado = true;
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
		Game.enemies.remove(this);
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
		if(!isDamaged && !right && !left && !up && !down && !EnemyAttack && !isFreezing) {
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
		if(EnemyAttack && !isDamaged && !isFreezing) {
			if(dir == down_dir) {
				g.drawImage(attackDownSprites[attackIndex], this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			if(dir == up_dir) {
				g.drawImage(attackUpSprites[attackIndex], this.getX() - Camera.x,this.getY() - 25 - Camera.y,null);
			}
			if(dir == left_dir) {
				g.drawImage(attackLeftSprites[attackIndex], this.getX() - 25 - Camera.x,this.getY() - Camera.y,null);
			}
			if(dir == right_dir) {
				g.drawImage(attackRightSprites[attackIndex], this.getX() - Camera.x,this.getY() - Camera.y,null);
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
