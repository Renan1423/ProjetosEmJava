package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class ManaShoot extends Entity{
	
	private double dx;
	private double dy;
	private double spd = 4;
	
	private int life = 30, curLife = 0;
	
	public ManaShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x,y,width,height,sprite);
		this.dx = dx;
		this.dy = dy;
	}
	
	public void tick() {
		x+= dx*spd;
		y+= dy*spd;
		curLife++;
		if(curLife == life) {
			Game.mana.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) { //não precisa se for usar sprite
		g.setColor(Color.YELLOW);
		g.fillOval(this.getX() - Camera.x,this.getY() - Camera.y,3,3);
		
	}
}
