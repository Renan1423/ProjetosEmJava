package com.bsstudios.graficos;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;

import com.bsstudios.entities.Player;
import com.bsstudios.main.Game;

public class UI {

	public boolean GameOver = true;
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(8, 8, 204, 34);
		g.setColor(Color.red);
		g.fillRect(10, 10, 200, 30);
		g.setColor(Color.green);
		g.fillRect(10, 10, ((int)(Player.life/100 * 200)), 30);
	}
	
}
