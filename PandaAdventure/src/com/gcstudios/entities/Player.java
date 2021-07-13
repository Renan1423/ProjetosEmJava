package com.gcstudios.entities;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.gcstudios.graficos.Spritesheet;
import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.Tile;
import com.gcstudios.world.World;

import javax.swing.Timer;

public class Player extends Entity{
	
	public boolean right = false,up = false,left = false,down = false;
	public boolean noright,noup,noleft,nodown;
	public static int right_dir = 0,left_dir = 1, up_dir = 2, down_dir = 3;
	public static int dir = down_dir;
	public int MoveX = 0, MoveY = 0;
	public int Delay = 0, MaxDelay = 20;
	public boolean strikedFirstTime = false;
	
	private int frames = 0,maxFrames = 10, atkframes = 0, atkMaxFrames = 7, standframes = 0, standmaxFrames = 60,index = 0,maxIndex = 2, standIndex = 0, maxstandIndex = 2, atkindex = 0, atkMaxIndex = 2;
	public static boolean movedR = false, movedL = false, movedU = false, movedD = false;
	public static boolean moved = false; //O moved sem a direção específica é usado para fora da fase
	public static double speed = 2; //também usado apenas fora de uma fase
	private boolean armRight = false, armLeft = false;
	private BufferedImage[] standrightPlayer;
	private BufferedImage[] standleftPlayer;
	private BufferedImage[] standupPlayer;
	private BufferedImage[] standdownPlayer;
	private BufferedImage[] rightPlayer;
	private BufferedImage rightPlayer2;
	private BufferedImage[] leftPlayer;
	private BufferedImage leftPlayer2;
	private BufferedImage[] upPlayer;
	private BufferedImage upPlayer2;
	private BufferedImage[] downPlayer;
	private BufferedImage[] downPlayer2;
	private BufferedImage[] rightAttack;
	private BufferedImage[] leftAttack;
	private BufferedImage[] upAttack;
	private BufferedImage[] downAttack;
	
	private BufferedImage[] playerDefeated;
	
	private BufferedImage playerDamageRight;
	private BufferedImage playerDamageLeft;
	private BufferedImage playerDamageUp;
	private BufferedImage playerDamageDown;
	
	private BufferedImage playerGetItem;
	
	
	private BufferedImage playerMagicRight;
	private BufferedImage playerMagicLeft;
	private BufferedImage playerMagicUp;
	private BufferedImage playerMagicDown;
	
	
	
	public boolean isDamaged = false;
	public boolean isHealing = false;
	public boolean isHealingMana = false;
	public boolean isCharging = false;
	public boolean isDead = false;
	
	private int damageFrames = 0;
	
	public boolean shoot = false;
	
	private int subir = 0; //subir o item quando coletado
	
	public double life = 100,maxlife = 100;
	public double mana = 50,maxMana = 50;
	public double stamina = 100, maxstamina = 100;
	public static int Level = 1;
	public static int str = 1, def = 1;
	public static double exp = 0,maxExp = 100 * Level;
	public int mx,my;
	
	public int z = 0;
	
	public static boolean Attack = false;
	public static boolean Attacking = false;
	
	public static boolean changeDirection = false;
	
	public static String[] magia = {"fogo","gelo","raio"};
	
	public static int magiaAtual = 0;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		
		standrightPlayer = new BufferedImage[3];
		standleftPlayer = new BufferedImage[3];
		standupPlayer = new BufferedImage[3];
		standdownPlayer = new BufferedImage[3];
		
		rightPlayer = new BufferedImage[3];
		leftPlayer = new BufferedImage[3];
		upPlayer = new BufferedImage[3];
		downPlayer = new BufferedImage[3];
		downPlayer2 = new BufferedImage[3];
		
		playerDamageRight = Game.Playerspritesheet.getSprite(125, 75,25,25);
		playerDamageLeft = Game.Playerspritesheet.getSprite(100, 75,25,25);
		playerDamageUp = Game.Playerspritesheet.getSprite(50, 100,25,25);
		playerDamageDown = Game.Playerspritesheet.getSprite(75, 75,25,25);
		
		playerGetItem = Game.Playerspritesheet.getSprite(75, 125, 25, 25);
		
		playerMagicRight = Game.Playerspritesheet.getSprite(25, 200, 25, 25);
		playerMagicLeft = Game.Playerspritesheet.getSprite(25, 175, 25, 25);
		playerMagicUp = Game.Playerspritesheet.getSprite(0, 200, 25, 25);
		playerMagicDown = Game.Playerspritesheet.getSprite(0, 175, 25, 25);
		
		playerDefeated = new BufferedImage[3];
		
		rightAttack = new BufferedImage[2];
		leftAttack = new BufferedImage[2];
		upAttack = new BufferedImage[2];
		downAttack = new BufferedImage[2];
		
		rightPlayer2 = Game.Playerspritesheet.getSprite(100, 150,25,25);
		leftPlayer2 = Game.Playerspritesheet.getSprite(75, 150,25,25);
		upPlayer2 = Game.Playerspritesheet.getSprite(25, 100, 25, 25);
		for(int i = 0; i < 3; i++){
			standrightPlayer[i] = Game.Playerspritesheet.getSprite(200 - (i*25), 0, 25, 25);
		}
		for(int i =0; i < 3; i++){
			standleftPlayer[i] = Game.Playerspritesheet.getSprite(75 + (i*25), 0, 25, 25);
		}
		for(int i =0; i < 3; i++){	
			standupPlayer[i] = Game.Playerspritesheet.getSprite(225, 0, 25, 25);
		}
		for(int i =0; i < 3; i++){
			standdownPlayer[i] = Game.Playerspritesheet.getSprite(0 + (25 * i), 0, 25, 25);
		}
		for(int i =0; i < 3; i++){
			rightPlayer[i] = Game.Playerspritesheet.getSprite(200 - (i*25), 25, 25, 25);
		}
		for(int i =0; i < 3; i++){
			leftPlayer[i] = Game.Playerspritesheet.getSprite(75 + (i*25), 25, 25, 25);
		}
		for(int i =0; i < 3; i++){
			upPlayer[i] = Game.Playerspritesheet.getSprite(0 + (25 * i), 75, 25, 25);
		}
		for(int i =0; i < 3; i++){
			downPlayer[i] = Game.Playerspritesheet.getSprite(0 + (25 * i), 25, 25, 25);
		}
		for(int i =0; i < 3; i++){
			downPlayer2[i] = Game.Playerspritesheet.getSprite(0 + (25 * i), 50, 25, 25);
		}
		for(int i =0; i < 3; i++){
			playerDefeated[i] = Game.Playerspritesheet.getSprite(0 + (25 * i), 150, 25, 25);
		}
		rightAttack[1] = Game.Playerspritesheet.getSprite(150, 175, 25, 25);
		leftAttack[1] = Game.Playerspritesheet.getSprite(150, 200, 25, 25);
		upAttack[1] = Game.Playerspritesheet.getSprite(100, 200, 25, 25);
		downAttack[1] = Game.Playerspritesheet.getSprite(50, 175, 25, 25);
		}
	
	public void tick(){
		depth = 1;
		//moved = false;
		if(stamina < maxstamina) {
			stamina+=2;
		}
		
		if(Game.CUR_LEVEL != 1) {
			if(right && World.isFree((int)(x+MoveX), this.getY()) && !up && !down && !Attack && !isCharging && !shoot && Game.bullets.size() == 0 && !movedL && !movedU && !movedD && !isHealing && !isHealingMana) {
				if(changeDirection) {
					dir = right_dir;
				}
				else if(!changeDirection && !isCollidingPlayerToEnemy((int)(x+25), this.getY()) && !isCollidingPlayerToEnemy2((int)(x+25), this.getY()) && !isCollidingPlayerToNPC((int)(x+25), this.getY())) {
					dir = right_dir;
					if(stamina >= 40) {
						stamina -=40;
						MoveY = 0;
						MoveX = 0;
						movedR = true;
						right = false;
					//	x = this.getX() + MoveX;
					}
				}
				
			}
			else if(left && World.isFree((int)(x-MoveX),this.getY()) && !up && !down && !Attack && !isCharging && !shoot && Game.bullets.size() == 0 && !movedR && !movedU && !movedD && !isHealing && !isHealingMana) {
				if(changeDirection) {
					dir = left_dir;
				}
				else if(!changeDirection && !isCollidingPlayerToEnemy((int)(x-25), this.getY()) && !isCollidingPlayerToEnemy2((int)(x-25), this.getY()) && !isCollidingPlayerToNPC((int)(x-25), this.getY())){
					dir = left_dir;
					if(stamina >= 40) {
						stamina -=40;
						MoveY = 0;
						MoveX = 0;
						movedL = true;
						//x = this.getX() - MoveX;
						left = false;
						
					}
				}
			}
			if(up && World.isFree(this.getX(),(int)(y-MoveY)) && !left && !right && !Attack && !isCharging && !shoot && Game.bullets.size() == 0 && !movedR && !movedL && !movedD && !isHealing && !isHealingMana){
				if(changeDirection) {
					dir = up_dir;
				}
				else if(!changeDirection && !isCollidingPlayerToEnemy(this.getX(), (int)(y - 25)) && !isCollidingPlayerToEnemy2(this.getX(), (int)(y - 25)) && !isCollidingPlayerToNPC(this.getX(), (int)(y - 25))){
					dir = up_dir;
					if(stamina >= 40) {
						stamina -=40;
						MoveY = 0;
						MoveX = 0;
						movedU = true;
						//y = this.getY() - MoveY;
						up = false;
					}
				}
			}
			else if(down && World.isFree(this.getX(),(int)(y+MoveY)) && !left && !right && !Attack && !isCharging && !shoot && Game.bullets.size() == 0 && !movedR && !movedL && !movedU && !isHealing && !isHealingMana){
				if(changeDirection) {
					dir = down_dir;
				}
				else if(!changeDirection && !isCollidingPlayerToEnemy(this.getX(), (int)(y + 25)) && !isCollidingPlayerToEnemy2(this.getX(), (int)(y + 25)) && !isCollidingPlayerToNPC(this.getX(), (int)(y + 25))){
					dir = down_dir;
					if(stamina >= 40) {
						stamina -=40;
						MoveY = 0;
						MoveX = 0;
						movedD = true;
						//y = this.getY() + MoveY;
						down = false;
					}
				}
			}
			
			if(movedD == false || movedL == false || movedU == false || movedR == false && Attack == false && !isHealing) {
				standframes++;
				if(standframes == standmaxFrames) {
					standframes = 0;
					standIndex++;
					if(standIndex > maxstandIndex)
						standIndex = 0;
				}
			}
	
			
			if(movedR) {
				frames++;
				if(frames == maxFrames) {
					frames = 0;
					index++;
					if(index == 1) {
						if(World.isFree((int)(x + 10), this.getY())) {
							MoveX = 10;
						}
						else {
							MoveX = 0;
						}
						x = this.getX() + MoveX;
					}
					if(index == 2) {
						if(World.isFree((int)(x + 10), this.getY())) {
							MoveX = 15;
						}
						else {
							MoveX = 0;
						}
						x = this.getX() + MoveX;
					}
					if(index > maxIndex) {
						index = 0;
						MoveX = 0;
						movedR = false;
					}
					
				}
			}
			if(movedL) {
				frames++;
				if(frames == maxFrames) {
					frames = 0;
					index++;
					if(index == 1) {
						if(World.isFree((int)(x - 10), this.getY())) {
							MoveX = -10;
						}
						else {
							MoveX = 0;
						}
						x = this.getX() + MoveX;
					}
					if(index == 2) {
						if(World.isFree((int)(x - 10), this.getY())) {
							MoveX = -15;
						}
						else {
							MoveX = 0;
						}
						x = this.getX() + MoveX;
					}
					if(index > maxIndex) {
						index = 0;
						MoveX = 0;
						movedL = false;
					}
					
				}
			}
			if(movedU) {
				frames++;
				if(frames == maxFrames) {
					frames = 0;
					index++;
					if(index == 1) {
						if(World.isFree(this.getX(),(int)(y-10))) {
							MoveY = -10;
						}
						else {
							MoveY = 0;
						}
						y = this.getY() + MoveY;
					}
					if(index == 2) {
						if(World.isFree(this.getX(),(int)(y-15))) {
							MoveY = -15;
						}
						else {
							MoveY = 0;
						}
						y = this.getY() + MoveY;
					}
					if(index > maxIndex) {
						index = 0;
						MoveY = 0;
						movedU = false;
					}
					
				}
			}
			if(movedD) {
				frames++;
				if(frames == maxFrames) {
					frames = 0;
					index++;
					if(index == 1) {
						if(World.isFree(this.getX(),(int)(y + 10))) {
							MoveY = 10;
						}
						else {
							MoveY = 0;
						}
						y = this.getY() + MoveY;
					}
					if(index == 2) {
						if(World.isFree(this.getX(),(int)(y + 15))) {
							MoveY = 15;
						}
						else {
							MoveY = 0;
						}
						y = this.getY() + MoveY;
					}
					if(index > maxIndex) {
						index = 0;
						MoveY = 0;
						movedD = false;
					}
					
				}
				if(new Random().nextInt(100) < 50) {
					armRight = true;
				}else {
					armLeft = true;
				}
			}
		}
		else {
			moved = false;
			if(right && World.isFree((int)(x+speed), this.getY())) {
				moved = true;
				dir = right_dir;
				x+=speed;
			}
			else if(left && World.isFree((int)(x-speed),this.getY())) {
				moved = true;
				dir = left_dir;
				x-=speed;
			}
			if(up && World.isFree(this.getX(),(int)(y-speed))){
				moved = true;
				dir = up_dir;
				y-=speed;
			}
			else if(down && World.isFree(this.getX(),(int)(y+speed))){
				moved = true;
				dir = down_dir;
				y+=speed;
			}
		}
		//timer.setInitialDelay(1000);
		//timer.start();
		
		
		if(Attack && !isDamaged) {
			movedR = false;
			movedL = false;
			movedU = false;
			movedD = false;
			atkframes++;
			if(atkframes == atkMaxFrames) {
				atkframes = 0;
				atkindex++;
				if(atkindex > atkMaxIndex) {
					atkindex = 0;
					Attack = false;
				}
			}
			stamina = 0;
			int dx = 0;
			int px = 0;
			int py = 0;
			if(dir == right_dir) {
				px = 16;
				py = 6;
				dx = 1;
			}else if(dir == left_dir){
				px = -17;
				py = 6;
				dx = -1;
			}
			else if(dir == down_dir){
				px = 0;
				py = 20;
				dx = -1;
			}
			else if(dir == up_dir){
				px = 0;
				py = -21;
				dx = -1;
			}
			StaffStrike staff = new StaffStrike(this.getX()+px,this.getY()+py,3,3,null,dx,0);
			Game.staffStrike.add(staff);
			StaffStrike.indexStaff = 0;
		}
		checkCollisionLifePack();
		checkCollisionMana();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 20) {
				this.damageFrames = 0;
				isDamaged = false;
			}
			if(stamina >= 95) {
				stamina -= 20;
			}
		}
		
		if(isHealing) {
			this.Delay++;
			if(this.Delay == MaxDelay) {
				this.Delay = 0;
				isHealing = false;
			}
		}
		
		if(isHealingMana) {
			this.Delay++;
			if(this.Delay == MaxDelay) {
				this.Delay = 0;
				isHealingMana = false;
			}
		}
		
		if(isCharging && mana > 0 && Game.bullets.size() == 0 && !isDamaged) {
			
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
			ChargeShoot chargeRight = new ChargeShoot(this.getX()+px,this.getY()+py,3,3,null,dx,0);
			ChargeShoot chargeLeft = new ChargeShoot(this.getX()+px,this.getY()+py,3,3,null,dx,0);
			ChargeShoot chargeDown = new ChargeShoot(this.getX()+px,this.getY()+py,3,3,null,dx,0);
			ChargeShoot chargeUp = new ChargeShoot(this.getX()+px,this.getY()+py,3,3,null,dx,0);
			//Game.chargebullets.clear();
			if(dir == right_dir && Game.chargebullets.size() == 0) {
				Game.chargebullets.add(chargeRight);
				Game.chargebullets.remove(chargeLeft);
				Game.chargebullets.remove(chargeUp);
				Game.chargebullets.remove(chargeDown);
			}
			else if(dir == left_dir && Game.chargebullets.size() == 0) {
				Game.chargebullets.add(chargeLeft);
				Game.chargebullets.remove(chargeRight);
				Game.chargebullets.remove(chargeUp);
				Game.chargebullets.remove(chargeDown);
			}
			else if(dir == down_dir && Game.chargebullets.size() == 0) {
				Game.chargebullets.add(chargeDown);
				Game.chargebullets.remove(chargeUp);
				Game.chargebullets.remove(chargeLeft);
				Game.chargebullets.remove(chargeRight);
			}
			else if(dir == up_dir && Game.chargebullets.size() == 0) {
				Game.chargebullets.add(chargeUp);
				Game.chargebullets.remove(chargeLeft);
				Game.chargebullets.remove(chargeRight);
				Game.chargebullets.remove(chargeDown);
			}
			
			
		}
		
		if(!isCharging) {
			Game.chargebullets.clear();
			ChargeShoot.Charging = 0;
		}
		
		if(shoot && Game.bullets.size() == 0) {
			shoot = false;
			
			
			int dx = 0;
			int dy = 0;
			int px = 0;
			int py = 0;
			if(Player.magia[Player.magiaAtual] == "fogo" || Player.magia[Player.magiaAtual] == "gelo" && mana >= 10) {
				mana -= 10;
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
				BulletShoot bullet = new BulletShoot(this.getX()+px,this.getY()+py,3,3,null,dx,dy);
				Game.bullets.add(bullet);
			}
			if(Player.magia[Player.magiaAtual] == "raio" && mana >= 15){
				mana -= 15;
				dy = -1;
				py = -15;
				BulletShoot bulletU = new BulletShoot(this.getX()+px,this.getY()+py,3,3,null,dx,dy);
				dy = 1;
				py = 10;
				BulletShoot bulletD= new BulletShoot(this.getX()+px,this.getY()+py,3,3,null,dx,dy);
				dy = 0;
				px = -8;
				py = 5;
				dx = -1;
				BulletShoot bulletL = new BulletShoot(this.getX()+px,this.getY()+py,3,3,null,dx,dy);
				dy = 0;
				px = 18;
				py = 5;
				dx = 1;
				BulletShoot bulletR = new BulletShoot(this.getX()+px,this.getY()+py,3,3,null,dx,dy);
				Game.bullets.add(bulletU);
				Game.bullets.add(bulletD);
				Game.bullets.add(bulletL);
				Game.bullets.add(bulletR);
			}
			
			
			}
		if(life<=0) {
			//Game over!
			life = 0;
			isDead = true;
			Game.gameState = "GAME_OVER";
		}
		
		if(isDead) {
			frames++;
			if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex)
				index = 2;
			}
		}
		
		if(exp >= maxExp) {
			Level += 1;
			if(Level <= 8) {
				maxlife += 5*Level;
				maxMana += 2*Level;
			}else {
				maxlife += 10;
				maxMana += 10;
			}
			life = maxlife;
			mana = maxMana;
			if(Level < 5) {
				if(Level == 2) {
					str += Level - 1;
				}else {
					str += Level - 2;
				}
				def += 1;
			}else {
				str += Level - 4;
				def += 2;
			}
			
			exp = 0;
		}
		
		updateCamera();
		collidingBullet();
	
	}
	
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*25 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*25 - Game.HEIGHT);
	}
	
	public void collidingBullet() {
		for(int i = 0; i < Game.bulletsen.size(); i++) {
			Entity e = Game.bulletsen.get(i);
				if(Entity.isColidding(this,e)) {
					isDamaged = true;
					life-=10;
					Game.bulletsen.remove(i);
					return;
				}
		}
	}
	
	public void checkCollisionMana() {
		for(int i = 0; i < Game.entities.size(); i++){
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bullet && !movedR && !movedL && !movedU && !movedD) {
				if(Entity.isColidding(this, atual)) {
					isHealingMana = true;
					subir++;
					if(subir >= 15) {
						mana+=10;
						if(mana > maxMana)
							mana = maxMana;
						Game.entities.remove(atual);
						subir = 0;
					}
				}
			}
		}
	}
	
	public void checkCollisionLifePack(){
		for(int i = 0; i < Game.entities.size(); i++){
			Entity atual = Game.entities.get(i);
			if(atual instanceof Lifepack && !movedR && !movedL && !movedU && !movedD) {
				if(Entity.isColidding(this, atual)) {
					isHealing = true;
					subir++;
					if(subir >= 15) {
						life+=10;
						if(life > maxlife)
							life = maxlife;
						Game.entities.remove(atual);
						subir = 0;
					}
				}
			}
		}
	}

	
	public void render(Graphics g) {
		if(Npc.showMessage == false) {
			if(Game.CUR_LEVEL != 1) {
				if(!isDamaged && !isDead && !isHealing && !isHealingMana) {
					if(!movedR && !movedL && !movedU && !movedD && !Attack && !isCharging && !isDead && !isHealing) {
						if(dir == right_dir) {
							g.drawImage(standrightPlayer[standIndex], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
							
						}
						else if(dir == left_dir) {
							g.drawImage(standleftPlayer[standIndex], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
						}
						if(dir == up_dir) {
							g.drawImage(standupPlayer[standIndex], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
						}
						else if(dir == down_dir) {
							g.drawImage(standdownPlayer[standIndex], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
						}
					}else if(movedR || movedL || movedU || movedD && !Attack){
					if(dir == right_dir) {
						Color CinzaSombra = new Color(40, 40, 40);
						g.setColor(CinzaSombra);
						g.fillOval(this.getX() - Camera.x + 7, this.getY() - Camera.y + 20, 10, 7);
						g.drawImage(rightPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y - index * 3, null);
					}
					else if(dir == left_dir) {
						Color CinzaSombra = new Color(40, 40, 40);
						g.setColor(CinzaSombra);
						g.fillOval(this.getX() - Camera.x + 7, this.getY() - Camera.y + 20, 10, 7);
						g.drawImage(leftPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y - index * 3, null);
					}
					if(dir == up_dir) {
						Color CinzaSombra = new Color(40, 40, 40);
						g.setColor(CinzaSombra);
						g.fillOval(this.getX() - Camera.x + 7, this.getY() - Camera.y + 20, 10, 7);
						g.drawImage(upPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y - index * 3, null);
					}
					else if(dir == down_dir) {
						Color CinzaSombra = new Color(40, 40, 40);
						g.setColor(CinzaSombra);
						g.fillOval(this.getX() - Camera.x + 7, this.getY() - Camera.y + 20, 10, 7);
						if(armRight = true) {
							g.drawImage(downPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y - index * 3, null);
							armRight = false;
						}else if(armLeft = true) {
							g.drawImage(downPlayer2[index], this.getX() - Camera.x,this.getY() - Camera.y - index * 3, null);
							armLeft = false;
						}
						
					}
					}
				}else if (isDamaged && !isCharging && !isHealing && !isHealingMana && !isDead){
					if(dir == right_dir) {
						g.drawImage(playerDamageRight, this.getX()-Camera.x, this.getY() - Camera.y,null);
					}
					if(dir == left_dir) {
						g.drawImage(playerDamageLeft, this.getX()-Camera.x, this.getY() - Camera.y,null);
					}
					if(dir == up_dir) {
						g.drawImage(playerDamageUp, this.getX()-Camera.x, this.getY() - Camera.y,null);
					}
					if(dir == down_dir) {
						g.drawImage(playerDamageDown, this.getX()-Camera.x, this.getY() - Camera.y,null);
					}
					
				}else if(isDead) {
					g.drawImage(playerDefeated[index],this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
				if(Attack && !isDamaged && !isHealing && !isHealingMana) {
					if(dir == right_dir) {
						g.drawImage(rightAttack[1], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
					}
					else if(dir == left_dir) {
						g.drawImage(leftAttack[1], this.getX() - Camera.x,this.getY() - Camera.y - z, null);	
					}
					if(dir == up_dir) {
						g.drawImage(upAttack[1], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
					}
					else if(dir == down_dir) {
						g.drawImage(downAttack[1], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
					}
				}
				if(isCharging && !isDamaged && !isHealing && !isHealingMana) {
					if(dir == right_dir) {
						g.drawImage(playerMagicRight, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
					}
					else if(dir == left_dir) {
						g.drawImage(playerMagicLeft, this.getX() - Camera.x,this.getY() - Camera.y - z, null);	
					}
					if(dir == up_dir) {
						g.drawImage(playerMagicUp, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
					}
					else if(dir == down_dir) {
						g.drawImage(playerMagicDown, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
					}
				}
				if(isHealing || isHealingMana && !isDamaged && !movedR && !movedL && !movedU && !movedD) {
					g.drawImage(playerGetItem, this.getX()-Camera.x, this.getY() - Camera.y,null);
				}
			}else {
				if(!moved) {
					if(dir == right_dir) {
						g.drawImage(standrightPlayer[standIndex], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
						
					}
					else if(dir == left_dir) {
						g.drawImage(standleftPlayer[standIndex], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
					}
					if(dir == up_dir) {
						g.drawImage(standupPlayer[standIndex], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
					}
					else if(dir == down_dir) {
						g.drawImage(standdownPlayer[standIndex], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
					}
				}else {
					if(dir == right_dir) {
						if(new Random().nextInt(100) < 50) {
							g.drawImage(rightPlayer[2], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
						}else {
							g.drawImage(rightPlayer2, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
						}
						
					}
					else if(dir == left_dir) {
						if(new Random().nextInt(100) < 50) {
							g.drawImage(leftPlayer[2], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
						}else {
							g.drawImage(leftPlayer2, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
						}
					}
					if(dir == up_dir) {
						if(new Random().nextInt(100) < 50) {
							g.drawImage(upPlayer[2], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
						}else {
							g.drawImage(upPlayer2, this.getX() - Camera.x,this.getY() - Camera.y - z, null);
						}
					}
					else if(dir == down_dir) {
						if(new Random().nextInt(100) < 50) {
							g.drawImage(downPlayer[2], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
						}else {
							g.drawImage(downPlayer2[2], this.getX() - Camera.x,this.getY() - Camera.y - z, null);
						}
					}
				}
				
			}
		}
	}
}
