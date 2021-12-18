package com.gcstudios.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.gcstudios.entities.Player;
import com.gcstudios.main.Game;
import com.gcstudios.world.World;

public class UI {
	
	public void tick() {

	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		
		g.setFont(new Font("arial",Font.BOLD,24));
		
		g.drawString("Score: " + Game.score, 15, 30);
		
		g.setColor(Color.red);
		g.fillRect(Game.WIDTH/2 + 140, 6, 150, 25);
		
		g.setColor(Color.green);
		g.fillRect(Game.WIDTH/2 + 140, 6, (int)((Game.life/10) * 150), 25);
		
	}
	
}
