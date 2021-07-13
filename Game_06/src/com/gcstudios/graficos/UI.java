package com.gcstudios.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.entities.Player;
import com.gcstudios.main.Game;

public class UI {

	public static BufferedImage HEART = Game.spritesheet.getSprite(0, 16, 8, 8);
	
	public void render(Graphics g) {
		for(int i = 0; i < (int)(Game.vida); i++) {
			g.drawImage(HEART,20 + (i*20),10,36,36,null);
		}
		
		g.setFont(new Font("arial",Font.BOLD,24));
		g.setColor(Color.white);
		g.drawString("$" + Game.dinheiro, (Game.WIDTH * Game.SCALE) - 70, 25);
	}
	
}
