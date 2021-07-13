package com.gcstudios.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.gcstudios.main.Game;

public class Npc extends Entity{

	public String[] frases = new String[3];
	public String[] fraseEntrada = new String[3];
	
	public static boolean showMessage = false;
	public static boolean show = false;
	
	public int curIndex = 0;
	
	public int fraseIndex = 0;
	public int fraseEntradaIndex = 0;
	
	public int time = 0;
	public int maxTime = 5;
	
	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		frases[0] = "Precisamos de sua ajuda para derrotar o poderosíssimo Rei Panda Vermelho!!!";
		frases[1] = "Você pode pausar o jogo utilizando o ENTER para poder acessar o tutorial novamente";
		frases[2] = "Eu tenho uma plantação de bamboo. Se você quiser, você pode pegar alguns para se recuperar";
		
		fraseEntrada[0] = "aaaaaaaaaaaaaaaaaaaaa";
		fraseEntrada[1] = "aaaaaaaaaa";
		fraseEntrada[2] = "bbbbbbbbbbbbbb";
		
	}
	
	public void tick() {
		if(Game.estado_cena != Game.entrada) {
			if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) == 25) {
				if(show == false) {
					show = true;
					fraseIndex = new Random().nextInt(3);
				}
			}else {
				show = false;
				//showMessage = false;
			}
			
			if(showMessage) {
				time++;
				if(time >= maxTime) {
					time = 0;
					if(curIndex < frases[fraseIndex].length() - 1) {
						curIndex++;
					}
					}
				}
		}
		else {
			if(showMessage) {
				time++;
				if(time >= maxTime) {
					time = 0;
					if(curIndex < fraseEntrada[fraseEntradaIndex].length() - 1) {
						curIndex++;
					}
					else {  //Continuar o diálogo
						if(fraseEntradaIndex < fraseEntrada.length - 1) {
							fraseIndex++;
							curIndex = 0;
						}
					}
					}
				}
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
		if(Game.estado_cena != Game.entrada) {
			Font font1 = new Font("Segoe UI", Font.BOLD, 9);
			g.setFont(font1);
			if(showMessage) {
				g.setColor(Color.white);
				g.fillRect(14,14,217,142);
				g.setColor(Color.blue);
				g.fillRect(15,15,215,140);
				g.setColor(Color.black);
				g.drawString(frases[fraseIndex].substring(0,curIndex), 20, 25);
			}
		}
	}

}
