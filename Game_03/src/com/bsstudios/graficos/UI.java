package com.bsstudios.graficos;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;

import com.bsstudios.main.Game;

public class UI {

	public boolean GameOver = true;
	
	public void render(Graphics g) {
		if(Game.player.dead == false) {
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 18));
			g.drawString("Pontos: " + Game.itens_atual + "/" + Game.itens_contagem, 30, 30);
		}
		else {
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 48));
			if(GameOver) {
				g.drawString("GAME OVER!", ((Game.WIDTH * Game.SCALE)/2) - 165, (Game.HEIGHT * Game.SCALE)/2);
			}
			else {
				g.drawString("", ((Game.WIDTH * Game.SCALE)/2) - 165, (Game.HEIGHT * Game.SCALE)/2);
			}
		}
	}
	
}
