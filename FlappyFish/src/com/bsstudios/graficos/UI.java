package com.bsstudios.graficos;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;

import com.bsstudios.main.Game;

public class UI {

	public boolean GameOver = true;
	
	public void render(Graphics g) {
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 18));
			g.drawString("Pontuação:" + (int)Game.score, 20, 30);
	}
	
}
